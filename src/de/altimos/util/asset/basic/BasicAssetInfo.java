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

import java.io.IOException;
import java.io.InputStream;

import de.altimos.util.asset.AssetInfo;
import de.altimos.util.asset.AssetKey;
import de.altimos.util.asset.AssetManager;
import de.altimos.util.res.ResourceInfo;

/**
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class BasicAssetInfo implements AssetInfo {
	
	@SuppressWarnings("rawtypes")
	private AssetKey key;
	private AssetManager owner;
	private ResourceInfo rinfo;
	
	@SuppressWarnings("rawtypes")
	public BasicAssetInfo(AssetManager owner, AssetKey key, ResourceInfo rinfo) {
		this.key = key;
		this.owner = owner;
		this.rinfo = rinfo;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public AssetKey getKey() {
		return key;
	}
	
	@Override
	public AssetInfo getRelativeResource(String name) {
		String resource = key.getName().substring(0, key.getName().lastIndexOf('/') + 1) + name;
		return owner.locateAsset(new BasicAssetKey<Object>(resource));
	}
	
	@Override
	public InputStream openStream() throws IOException {
		return rinfo.openStream();
	}
}
