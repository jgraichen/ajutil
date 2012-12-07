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
package de.altimos.util.res.basic.locators;

import java.net.URL;

import de.altimos.util.res.ResourceInfo;
import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.ResourceLocator;
import de.altimos.util.res.basic.BasicResourceManager;

/**
 * A ResourceLocator that searches in classpath using system classloader.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class ClassResourceLocator implements ResourceLocator {
	
	private String path;
	
	@Override
	public void setPath(String path) {
		if(!path.endsWith("/")) {
			path += "/";
		}
		this.path = path;
	}
	
	@Override
	public ResourceInfo locateResource(BasicResourceManager mgr, ResourceKey key) {
		URL url = ClassLoader.getSystemClassLoader().getResource(path + key.getName());
		if(url != null) {
			return new UrlResourceInfo(mgr, key, url);
		}
		return null;
	}
	
}
