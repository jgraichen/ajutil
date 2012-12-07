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
package de.altimos.util.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * NativeLoader provides methods to extract native
 * libraries from jar file.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class NativeLoader {
	
	private static final Logger logger = Logger.getLogger(NativeLoader.class);
	private static File workingDir = new File("").getAbsoluteFile();
	
	public NativeLoader() { }
	
	/**
	 * Copies a library from given path to current working directory.
	 * 
	 * @param path    source path
	 * @param library library name
	 * @throws IOException
	 */
	static public void copyLibrary(String path, String library) throws IOException {
		copyLibrary(path, library, workingDir);
	}
	
	/**
	 * Copies a library from given source to target path.
	 * 
	 * @param path    source path
	 * @param library library name
	 * @param target  target directory
	 * @throws IOException
	 */
	static public void copyLibrary(String path, String library, File target) throws IOException {
		byte[] buffer = new byte[1024];
		
		String fullname = System.mapLibraryName(library);
		path = path + "/" + fullname;
		
		InputStream in =  Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if(in == null) {
			logger.warn("Cannot locate native library " + path + ".");
			return;
		}
		
		File targetFile = new File(target, fullname);
		if(targetFile.exists()) {
			logger.info("Native library " + fullname + " already exists.");
			return;
		}
		
		OutputStream out = new FileOutputStream(targetFile);
		int len;
		try{
			while((len = in.read(buffer)) != 0) {
				out.write(buffer, 0, len);
			}
		}catch(IndexOutOfBoundsException e) { }
		in.close();
		out.close();
		
		logger.trace("Copied " + fullname + " to " + targetFile + ".");
	}
	
	/**
	 * Copies libraries from given path to current working directory.
	 * 
	 * @param path      source path
	 * @param libraries library names
	 * @throws IOException
	 */
	static public void copyLibraries(String path, String ... libraries) throws IOException {
		for(String lib : libraries) {
			copyLibrary(path, lib);
		}
	}
	
	/**
	 * Copies libraries from given source to target path.
	 * 
	 * @param path      source path
	 * @param target    target directory
	 * @param libraries library names
	 * @throws IOException
	 */
	static public void copyLibraries(String path, File target, String ... libraries) throws IOException {
		for(String lib : libraries) {
			copyLibrary(path, lib, target);
		}
	}
}
