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
package de.altimos.util.res.basic.locators;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import de.altimos.util.res.ResourceInfo;
import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.ResourceLocator;
import de.altimos.util.res.basic.BasicResourceManager;

/**
 * 
 * @author Jan Graichen
 * @version $Id$
 */
public class ZipResourceLocator implements ResourceLocator {
	
	private ZipFile archive;
	
	@Override
	public void setPath(String path) {
		try {
			archive = new ZipFile(new File(path));
		} catch(ZipException e) {
			throw new IllegalArgumentException(e);
		} catch(IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public ResourceInfo locateResource(BasicResourceManager mgr, ResourceKey key) {
		if(archive != null) {
			ZipEntry ze = archive.getEntry(key.getName());
			if(ze != null) {
				return new ZipResourceInfo(mgr, key, archive, ze);
			}
		}
		return null;
	}
	
}
