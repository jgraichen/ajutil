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

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.basic.AbstractResourceInfo;
import de.altimos.util.res.basic.BasicResourceManager;

/**
 * 
 * @author Jan Graichen
 * @version $Id$
 */
public class ZipResourceInfo extends AbstractResourceInfo {
	
	private ZipFile archive;
	private ZipEntry ze;
	
	
	public ZipResourceInfo(BasicResourceManager mgr, ResourceKey key, ZipFile archive, ZipEntry ze) {
		super(mgr, key);
		this.archive = archive;
		this.ze = ze;
	}
	
	@Override
	public InputStream openStream() throws IOException {
		return archive.getInputStream(ze);
	}
}
