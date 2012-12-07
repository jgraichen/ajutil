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
package de.altimos.util.res;

import de.altimos.util.res.basic.BasicResourceManager;


/**
 * A ResourceLocator tries to locate a resource and returns a ResourceInfo for
 * this resource. ResourceLocators must have a public constructor without
 * arguments.
 * 
 * @author Jan Graichen
 */
public interface ResourceLocator {
	
	/**
	 * Sets locator path when creating a new instance.
	 * 
	 * @param path
	 */
	public void setPath(String path);
	
	/**
	 * Tries to locate a resource and returns a ResourceInfo object or
	 * NULL if resource cannot found with this locator.
	 * 
	 * @param mgr ResourceManager calling this ResourceLocator
	 * @param key ResourceKey to locate
	 * @return ResourceInfo or NULL if resource cannot found.
	 */
	public ResourceInfo locateResource(BasicResourceManager mgr, ResourceKey key);
	
}
