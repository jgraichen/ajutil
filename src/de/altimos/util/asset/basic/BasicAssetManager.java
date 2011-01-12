/*
 * Altimos JUtil
 * 
 * Copyright (C) 2010-2011 Jan Graichen <jan.graichen@gmx.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed along with this work for further
 * information.
 */
package de.altimos.util.asset.basic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.apache.log4j.Logger;

import de.altimos.util.asset.AssetInfo;
import de.altimos.util.asset.AssetKey;
import de.altimos.util.asset.AssetListener;
import de.altimos.util.asset.AssetLoader;
import de.altimos.util.asset.AssetManager;
import de.altimos.util.res.ResourceInfo;
import de.altimos.util.res.ResourceManager;

/**
 * Basic AssetManager implementation with thread-safe
 * AssetLoader and AssetLocator handling.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class BasicAssetManager implements AssetManager {
	
	static public final Logger logger = Logger.getLogger(BasicAssetManager.class);
	
	protected ResourceManager resourceManager;
	private HashMap<String, AssetImplHandler> loaders = new HashMap<String, AssetImplHandler>();
	protected AssetCache cache = new AssetCache();
	
	protected AssetListener listener;
	
	public BasicAssetManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
	@Override
	public void setListener(AssetListener listener) {
		this.listener = listener;
	}
	
	@Override
	public AssetListener getListener() {
		return listener;
	}
	
	@Override
	public void registerLoader(Class<? extends AssetLoader> loaderClass, String... types) {
		AssetImplHandler h = new AssetImplHandler(loaderClass);
		for(String type : types) {
			loaders.put(type, h);
		}
	}
	
	@Override
	public void unregisterLoader(String... types) {
		for(String type : types) {
			loaders.remove(type);
		}
	}
	
	public int getLoaderCount() {
		return loaders.size();
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T loadAsset(AssetKey key) {
		if(listener != null) {
			listener.assetRequested(this, key);
		}
		
		Object asset = key.shouldCached() ? cache.get(key) : null;
		if(asset == null) {
			AssetLoader loader = acquireLoader(key);
			if(loader == null) {
				if(listener != null) {
					listener.assetNoLoader(this, key);
				}
				return null;
			}
			
			AssetInfo info = locateAsset(key);
			if(info == null) {
				if(listener != null) {
					listener.assetNotFound(this, key);
				}
				return null;
			}
			
			try {
				asset = loader.loadAsset(info);
				if(asset == null) {
					listener.assetLoadError(this, key, null);
					return null;
				}
			} catch(IOException e) {
				if(listener != null) {
					listener.assetLoadError(this, key, e);
				}
				return null;
			}
			
			asset = key.postProcess(asset);
			
			if(key.shouldCached()) {
				cache.add(key, asset);
			}
			
			if(listener != null) {
				listener.assetLoaded(this, key, loader.getClass());
			}
		}
		
		return (T) key.cloneAsset(asset);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public AssetInfo locateAsset(AssetKey key) {
		ResourceInfo rinfo = resourceManager.getResource(key);
		if(rinfo != null) {
			return new BasicAssetInfo(this, key, rinfo);
		}
		return null;
	}
	
	public AssetLoader acquireLoader(AssetKey key) {
		synchronized(loaders) {
			Class<AssetLoader> loaderClass = key.getLoader();
			if(loaderClass != null) {
				for(AssetImplHandler impl : loaders.values()) {
					if(impl.getType().equals(loaderClass)) {
						return impl.get();
					}
				}
				return new AssetImplHandler(loaderClass).get();
			}else{
				AssetImplHandler impl = loaders.get(key.getType().toLowerCase(Locale.ENGLISH));
				if(impl != null) {
					return impl.get();
				}
			}
		}
		return null;
	}
	
	/**
	 * AssetImplHandler handles thread-safe AssetLoader instances using ThreadLocal.
	 * 
	 * @author Jan Graichen <jan.graichen@gmx.de>
	 */
	static protected class AssetImplHandler extends ThreadLocal<AssetLoader> {
		
		private Class<? extends AssetLoader> type;
		
		public AssetImplHandler(Class<? extends AssetLoader> type) {
			this.type = type;
		}
		
		public Class<? extends AssetLoader> getType() {
			return type;
		}
		
		@Override
		protected AssetLoader initialValue(){
			try {
				return type.newInstance();
			} catch(InstantiationException ex) {
				logger.fatal("Cannot instanciate "+type.getName()+".");
			} catch(IllegalAccessException ex) {
				logger.fatal("Cannot instanciate "+type.getName()+".");
			}
			return null;
		}
	}
}
