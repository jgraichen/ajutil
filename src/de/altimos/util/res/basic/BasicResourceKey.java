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
package de.altimos.util.res.basic;

import de.altimos.util.res.ResourceKey;


/**
 * A basic ResourceKey implementation using a string as name.
 * 
 * @author Jan Graichen
 */
public class BasicResourceKey implements ResourceKey {
	
	private String name;
	
	/**
	 * Creates new BasicResourceKey with given name.
	 * 
	 * @param name of resource this key identifies.
	 */
	public BasicResourceKey(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
