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
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class AppInstance {
	
	static private FileLock lock = null;
	static private FileChannel channel = null;
	static private File file = null;
	
	/**
	 * Checks if only one instance of application for given
	 * lock file name is running.
	 * 
	 * @param name lock file name
	 * @return true if file can be locked
	 */
	static public boolean isSingleAppInstance(String name) {
		if(lock == null) {
			try{
				file = new File(System.getProperty("java.io.tmpdir") + "/" + name).getAbsoluteFile();
				if(!file.exists()) {
					file.createNewFile();
				}
				
				// Get file channel to lock file
				channel = new FileOutputStream(file).getChannel();
				lock = channel.tryLock();
				if(lock == null) {
					channel.close();
					return false;
				}
				
				// Add shutdown hook to release lock (if hook is called ;-)
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						try {
							if(lock != null) {
								lock.release();
							}
							if(channel != null) {
								channel.close();
							}
							if(file != null) {
								file.delete();
							}
						}catch(IOException e) {
							e.printStackTrace();
						}
					}
				});
			}catch(Exception e) {
				return false;
			}
		}
		return true;
	}
}
