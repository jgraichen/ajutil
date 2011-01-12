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
import java.io.InputStream;

/**
 * AssetInfo connects a AssetKey with a specific resource
 * and can open a input stream to load asset from resource.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public interface AssetInfo {
	
	/**
	 * Returns AssetKey.
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public AssetKey getKey();
	
	/**
	 * Returns InputStream to read asset.
	 * 
	 * @return
	 * @throws IOException
	 */
	public InputStream openStream() throws IOException;
	
	/**
	 * Returns related asset resource.
	 * 
	 * @param name
	 * @return
	 */
	public AssetInfo getRelativeResource(String name);
	
}
