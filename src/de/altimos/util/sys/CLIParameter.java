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
package de.altimos.util.sys;

/**
 * A parameter from command line interface.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class CLIParameter {
	
	private String name;
	private String arg;
	
	public CLIParameter(String name, String arg) {
		this.name = name;
		this.arg = arg;
	}
	
	/**
	 * Returns name of parameter.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns parameter argument if any.
	 * 
	 * @return argument
	 */
	public String getArgument() {
		return arg;
	}
}
