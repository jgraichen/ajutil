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

/**
 * A ResourceManager handles ResourceLocators and can be used to locate
 * resources.
 * 
 * @author Jan Graichen
 */
public interface ResourceManager {
	
	/**
	 * Tries to find a resource with given name.
	 * 
	 * @param name
	 * 			The name or identifier of the resource that should be loaded.
	 * @return The ResourceInfo for requested resource object or NULL if the
	 * 			resource was not found.
	 */
	public ResourceInfo getResource(String name);
	
	/**
	 * Tries to find a resource for given ResourceKey.
	 * 
	 * @param key
				The ResourceKey used to identifier a resource.
	 * @return The ResourceInfo for requested resource object or NULL if the
	 * 			resource was not found.
	 */
	public ResourceInfo getResource(ResourceKey key);
	
}
