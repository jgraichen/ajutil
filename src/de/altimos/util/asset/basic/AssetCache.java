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

import java.util.HashMap;

import de.altimos.util.asset.AssetKey;

/**
 * Caches loaded assets.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class AssetCache {
	
	@SuppressWarnings("rawtypes")
	private HashMap<AssetKey, Object> cache = new HashMap<AssetKey, Object>();
	
	public AssetCache() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public Object get(AssetKey key) {
		synchronized(cache) {
			return cache.get(key);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void add(AssetKey key, Object asset) {
		synchronized(cache) {
			cache.put(key, asset);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void remove(AssetKey key) {
		synchronized(cache) {
			cache.remove(key);
		}
	}
	
	public void clear() {
		synchronized(cache) {
			cache.clear();
		}
	}
}
