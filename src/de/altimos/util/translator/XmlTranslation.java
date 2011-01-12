/*
 * Altimos JUtil
 * 
 * Copyright (C) 2010 Jan Graichen <jan.graichen@gmx.de>
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
package de.altimos.util.translator;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Translation implementation that uses a xml file as source and
 * maps translation keys to tree structure.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class XmlTranslation implements Translation {
	
	private HashMap<String, Element> roots = new HashMap<String, Element>();
	
	private String delimiter = "\\.";
	
	public XmlTranslation() {
		this("\\.");
	}
	
	public XmlTranslation(String delimiter) {
		this.delimiter = delimiter;
	}
	
	protected Element loadRoot(Translator translator, String locale, String domain) {
		if(domain.length() > 0 && locale.length() > 0) {
			domain += "_";
		}
		try{
			InputStream in = translator.getSource().getStream(domain + locale + ".xml");
			if(in != null) {
				SAXBuilder builder = new SAXBuilder();
				return builder.build(in).getRootElement();
			}
		}catch(IOException e) {
			throw new MissingResourceException(e.getMessage(), XmlTranslation.class.getSimpleName(), null);
		} catch (JDOMException e) {
			throw new MissingResourceException(e.getMessage(), XmlTranslation.class.getSimpleName(), null);
		}
		return null;
	}
	
	protected Element getRoot(Translator translator, String locale, String domain) {
		if(!roots.containsKey(locale)) {
			Element root = loadRoot(translator, locale, domain);
			roots.put(locale, root);
		}
		return roots.get(locale);
	}
	
	protected Element getElement(Translator translator, String key, Locale locale, String domain) {
		if(locale.getLanguage().length() > 0 && locale.getCountry().length() > 0 && locale.getVariant().length() > 0) {
			Element root = getRoot(translator, locale.getLanguage() + "_" + locale.getCountry() + "_" + locale.getVariant(), domain);
			Element e = getElement(root, key.split(delimiter), 0);
			if(e != null) {
				return e;
			}
		}
		
		if(locale.getLanguage().length() > 0 && locale.getCountry().length() > 0) {
			Element root = getRoot(translator, locale.getLanguage() + "_" + locale.getCountry(), domain);
			Element e = getElement(root, key.split(delimiter), 0);
			if(e != null) {
				return e;
			}
		}
		
		if(locale.getLanguage().length() > 0) {
			Element root = getRoot(translator, locale.getLanguage(), domain);
			Element e = getElement(root, key.split(delimiter), 0);
			if(e != null) {
				return e;
			}
		}
		
		Element root = getRoot(translator, "", domain);
		Element e = getElement(root, key.split(delimiter), 0);
		if(e != null) {
			return e;
		}
		throw new MissingTranslationException(
				"Translation for " + key + " cannot be found in " + domain + " for " + locale + ".",
				XmlTranslation.class.getSimpleName(), key);
	}
	
	protected Element getElement(Element e, String[] key, int offset) {
		if(offset == key.length || e == null) {
			return e;
		}
		
		if(e.getChild(key[offset]) != null) {
			return getElement(e.getChild(key[offset]), key, offset+1);
		}
		return null;
	}
	
	@Override
	public String getString(Translator translator, String key, Locale locale, String domain, Object[] args) {
		Element e = getElement(translator, key.toLowerCase(), locale, domain);
		if(e != null) {
			return e.getTextTrim().replace("\\n", "\n");
		}
		return "";
	}
	
}
