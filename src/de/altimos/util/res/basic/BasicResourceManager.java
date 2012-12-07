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
package de.altimos.util.res.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.altimos.util.ExceptionListener;
import de.altimos.util.res.ResourceInfo;
import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.ResourceLocator;
import de.altimos.util.res.ResourceManager;
import de.altimos.util.res.basic.locators.ClassResourceLocator;


/**
 * A basic ResourceManager implementation with MultiThread support.
 * 
 * @author Jan Graichen
 */
public class BasicResourceManager implements ResourceManager {
	
	private ExceptionListener listener;
	private List<LocatorThreadHandler> locators = Collections.synchronizedList(new ArrayList<LocatorThreadHandler>());
	
	/**
	 * Creates new BasicResourceManager.
	 * 
	 * @param loadDefaults
	 * 			Should be set to true if the default resource locator should be added.
	 */
	public BasicResourceManager(boolean loadDefaults) {
		if(loadDefaults) {
			addLocator(ClassResourceLocator.class, ".");
		}
	}
	
	/**
	 * Adds an resource locator at front of list.
	 * 
	 * @param lClass
	 * 			The class of the resource locator that should be added.
	 * @param path
	 * 			The path given to resource locator when a new instance will be created.
	 */
	public void addLocator(Class<? extends ResourceLocator> lClass, String path) {
		addLocator(lClass, path, 0);
	}
	
	
	/**
	 * Adds an resource locator at a specific position if list.
	 * 
	 * @param lClass
	 * 			The class of the resource locator that should be added.
	 * @param path
	 * 			The path given to resource locator when a new instance will be created.
	 * @param index
	 * 			The index where the resource locator will be added in list.
	 */
	public void addLocator(Class<? extends ResourceLocator> lClass, String path, int index) {
		locators.add(index, new LocatorThreadHandler(lClass, path));
	}
	
	/**
	 * Removes a resource locator.
	 * 
	 * @param path
	 * 			The path of the resource locator that should be removed.
	 */
	public void removeLocator(String path) {
		for(LocatorThreadHandler h : locators) {
			if(h.getPath().equals(path)) {
				locators.remove(h);
				return;
			}
		}
	}
	
	/**
	 * Returns the number of added resource locators.
	 * 
	 * @return The number of resource locators.
	 */
	public int getLocatorCount() {
		return locators.size();
	}
	
	@Override
	public ResourceInfo getResource(String name) {
		return getResource(new BasicResourceKey(name));
	}
	
	@Override
	public ResourceInfo getResource(ResourceKey key) {
		for(LocatorThreadHandler h : locators) {
			ResourceLocator locator = h.get();
			if(locator != null) {
				ResourceInfo info = locator.locateResource(this, key);
				if(info != null) {
					return info;
				}
			}
		}
		return null;
	}
	
	/**
	 * Sets the exception listener for this object.
	 * 
	 * @param listener
	 * 			The new exception listener.
	 */
	public void setExceptionListener(ExceptionListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Returns current exception listener.
	 * 
	 * @return The current exception listener.
	 */
	public ExceptionListener getExceptionListener() {
		return listener;
	}
	
	/**
	 * Used for handling ResourceLocator instances per thread.
	 */
	protected class LocatorThreadHandler extends ThreadLocal<ResourceLocator> {
		
		private Class<? extends ResourceLocator> locator;
		private String path;
		
		public LocatorThreadHandler(Class<? extends ResourceLocator> locator, String path) {
			this.locator = locator;
			this.path = path;
		}
		
		public Class<? extends ResourceLocator> getLocatorClass() {
			return locator;
		}
		
		public void setPath(String path) {
			this.path = path;
		}
		
		public String getPath() {
			return path;
		}
		
		@Override
		public ResourceLocator initialValue() {
			try {
				ResourceLocator l = locator.newInstance();
				l.setPath(path);
				return l;
			} catch(Exception e) {
				if(listener != null) {
					listener.exceptionThrown(e);
				}
			} 
			return null;
		}
	}
}
