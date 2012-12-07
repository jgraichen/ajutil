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
public interface AssetListener {
	
	@SuppressWarnings("rawtypes")
	public void assetRequested(AssetManager mgr, AssetKey key);
	
	@SuppressWarnings("rawtypes")
	public void assetLoaded(AssetManager mgr, AssetKey key, Class loader);
	
	@SuppressWarnings("rawtypes")
	public void assetNotFound(AssetManager mgr, AssetKey key);
	
	@SuppressWarnings("rawtypes")
	public void assetNoLoader(AssetManager mgr, AssetKey key);
	
	@SuppressWarnings("rawtypes")
	public void assetLoadError(AssetManager mgr, AssetKey key, Exception e);
	
}
