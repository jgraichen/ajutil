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
package de.altimos.util.res;

import java.io.IOException;
import java.io.InputStream;

/**
 * ResourceInfo identifies a specific resource and allows to search for
 * related resources and to open a input stream.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 */
public interface ResourceInfo {
	
	/**
	 * Returns ResourceKey used to locate this resource.
	 * @return
	 */
	public ResourceKey getKey();
	
	/**
	 * Tries to locate a related resource.
	 * 
	 * @param name of related resource.
	 * @return ResourceInfo pointing to related resource or NULL of resource cannot found
	 */
	public ResourceInfo getRelatedResource(String name);
	
	/**
	 * Open a InputStream to read resource.
	 * 
	 * @return InputStream to read resource.
	 */
	public InputStream openStream() throws IOException;
	
}
