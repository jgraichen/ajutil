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
package de.altimos.util.asset.basic;

import de.altimos.util.asset.AssetKey;
import de.altimos.util.asset.AssetLoader;

/**
 * BasicAssetKey
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class BasicAssetKey<T> implements AssetKey<T> {
	
	private String name;
	private String type;
	private boolean cached = true;
	
	public BasicAssetKey(String name) {
		this(name, name.substring(name.lastIndexOf('.')));
	}
	
	public BasicAssetKey(String name, String type) {
		this(name, type, true);
	}
	
	public BasicAssetKey(String name, boolean cached) {
		this(name, name.substring(name.lastIndexOf('.')), cached);
	}
	
	public BasicAssetKey(String name, String type, boolean cached) {
		this.name = name;
		this.type = type;
		this.cached = cached;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public Class<? extends AssetLoader> getLoader() {
		return null;
	}
	
	@Override
	public boolean shouldCached() {
		return cached;
	}
	
	@Override
	public Object postProcess(Object asset) {
		return asset;
	}
	
	@Override
	public Object cloneAsset(Object asset) {
		return asset;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + type.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof BasicAssetKey) {
			return name.equals(((BasicAssetKey) o).getName()) &&
			type.equals(((BasicAssetKey) o).getType());
		}
		return super.equals(o);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+getName()+"]";
	}
}
