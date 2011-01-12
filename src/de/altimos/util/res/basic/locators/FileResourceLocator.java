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
package de.altimos.util.res.basic.locators;

import java.io.File;
import java.net.MalformedURLException;

import de.altimos.util.res.ResourceInfo;
import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.ResourceLocator;
import de.altimos.util.res.basic.BasicResourceManager;

/**
 * FileResourceLocator locates resource in file system.
 * 
 * @author Jan Graichen
 */
public class FileResourceLocator implements ResourceLocator {
	
	private File file;
	
	@Override
	public void setPath(String path) {
		file = new File(path);
	}
	
	@Override
	public ResourceInfo locateResource(BasicResourceManager mgr, ResourceKey key) {
		File f = new File(file, key.getName());
		if(f.exists()) {
			try {
				return new UrlResourceInfo(mgr, key, f.toURI().toURL());
			} catch(MalformedURLException e) {
				
			}
		}
		return null;
	}
	
}
