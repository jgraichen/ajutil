/*
 * A Java Utility Library
 * 
 * Copyright (C) 2010-2012 Jan Graichen <jg@altimos.de>
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
package de.altimos.util.asset;

/**
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public interface AssetManager {
	
	/**
	 * Sets a new AssetListener.
	 * 
	 * @param listener
	 */
	public void setListener(AssetListener listener);
	
	/**
	 * Returns AssetListener that currently listens at this AssetManager.
	 * 
	 * @return
	 */
	public AssetListener getListener();
	
	/**
	 * Registers an AssetLoader for given types.
	 * 
	 * @param loaderClass
	 * @param types
	 */
	public void registerLoader(Class<? extends AssetLoader> loaderClass, String ... types);
	
	/**
	 * Unregisters loaders for given types.
	 * 
	 * @param types
	 */
	public void unregisterLoader(String ... types);
	
	/**
	 * Loads an asset using given AssetKey.
	 * 
	 * @param key AssetKey to use for loading
	 * @return loaded asset or null
	 */
	@SuppressWarnings("rawtypes")
	public <T> T loadAsset(AssetKey key);
	
	/**
	 * Locates an asset and returns AssetInfo.
	 * 
	 * @param key AssetKey
	 * @return AssetInfo
	 */
	@SuppressWarnings("rawtypes")
	public AssetInfo locateAsset(AssetKey key);
	
}
