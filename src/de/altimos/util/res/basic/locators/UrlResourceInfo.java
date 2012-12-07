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
package de.altimos.util.res.basic.locators;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.basic.AbstractResourceInfo;
import de.altimos.util.res.basic.BasicResourceManager;

/**
 * A ResourceInfo that uses an URL to identify a resource and open a stream.
 * 
 * @author Jan Graichen
 */
public class UrlResourceInfo extends AbstractResourceInfo {
	
	private URL url;
	
	public UrlResourceInfo(BasicResourceManager mgr, ResourceKey key, URL url) {
		super(mgr, key);
		this.url = url;
	}
	
	public URL getUrl() {
		return url;
	}
	
	@Override
	public InputStream openStream() throws IOException {
		return url.openStream();
	}
	
}
