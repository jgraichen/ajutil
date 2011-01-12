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
package de.altimos.util.asset.ext;

import de.altimos.util.asset.basic.BasicAssetKey;

/**
 * A AssetKey that identifies an xml document resoruce.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class XmlDocumentKey extends BasicAssetKey<XmlDocument> {
	
	public XmlDocumentKey(String name, boolean cached) {
		super(name, ".xml", cached);
	}
	
	@Override
	public Object cloneAsset(Object o) {
		return ((XmlDocument)o).clone();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof XmlDocumentKey) {
			return getName().equals(((XmlDocumentKey) obj).getName()) &&
			shouldCached() == true && ((XmlDocumentKey) obj).shouldCached();
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}
