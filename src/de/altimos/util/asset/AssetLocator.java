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
package de.altimos.util.asset;

import java.io.IOException;

/**
 * AssetLocator is used to locate a requested asset
 * and returns a AssetInfo that can open a input stream
 * for requested asset resource.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public interface AssetLocator {
	
	/**
	 * Sets path given when this loader was registered at an AssetManager.
	 * 
	 * @param path
	 */
	public void setPath(String path);
	
	/**
	 * Returns AssetInfo for request AssetKey.
	 * 
	 * @param mgr
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public AssetInfo locateAsset(AssetManager mgr, AssetKey key);
	
	/**
	 * Returns AssetInfo for request resource.
	 * 
	 * @param mgr
	 * @param name
	 * @return
	 */
	public AssetInfo locate(AssetManager mgr, String name) throws IOException;
	
}
