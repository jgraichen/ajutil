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

import de.altimos.util.res.ResourceKey;

/**
 * AssetKey is used to identifiy different assets.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 * @param <T> Returned asset class.
 */
public interface AssetKey<T> extends ResourceKey {
	
	/**
	 * Returns name of this AssetKey.
	 * 
	 * @return asset name
	 */
	@Override
	public String getName();
	
	/**
	 * Returns asset type (usally file extension with dot)
	 * 
	 * @return asset type
	 */
	public String getType();
	
	/**
	 * Returns explicit loader class (only if a specific loader must be used)
	 * 
	 * @return loader class
	 */
	public Class<? extends AssetLoader> getLoader();
	
	/**
	 * Returns true if asset should be cached.
	 * 
	 * @return asset should be cached
	 */
	public boolean shouldCached();
	
	/**
	 * Process after asset is loaded.
	 * 
	 * @param o loaded asset
	 * @return post processed asset
	 */
	public Object postProcess(Object asset);
	
	/**
	 * Clone asset if cached asset is requested.
	 * 
	 * @param o asset from cache
	 * @return cloned asset (if required)
	 */
	public Object cloneAsset(Object asset);
	
}
