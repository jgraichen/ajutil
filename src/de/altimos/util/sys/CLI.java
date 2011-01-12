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

import java.util.HashMap;

/**
 * Command line interface parser.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class CLI {
	
	private HashMap<String, CLIParameter> parameters = new HashMap<String, CLIParameter>();
	
	public CLI() {
		
	}
	
	public void parse(String[] args) {
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(arg.startsWith("-")) {
				String name = arg.substring(1);
				String parg = null;
				if(args.length > (i+1) && !args[i+1].startsWith("-")) {
					parg = args[i+1];
					i++;
				}
				parameters.put(name, new CLIParameter(name, parg));
			}
		}
	}
	
	/**
	 * Returns CLIParameter with given name if any.
	 * 
	 * @param name of parameter
	 * @return CLIParamater or NULL
	 */
	public CLIParameter getParameter(String ... name) {
		for(String n : name) {
			CLIParameter p = parameters.get(n);
			if(p != null) {
				return p;
			}
		}
		return null;
	}
	
}
