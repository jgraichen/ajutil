/*
 * A Java Utility Library
 * 
 * Copyright (C) 2010 Jan Graichen <jg@altimos.de>
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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translator provides easy global access to a abstract translation
 * backend that returns translated string for given keys and
 * replaces place holders with given arguments.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class Translator {
	
	private static Translator global;
	
	/**
	 * Returns global Translator instance.
	 * 
	 * @return global instance
	 */
	static public Translator getGlobal() {
		if(global == null) {
			global = new Translator();
		}
		return global;
	}
	
	private Translation translation;
	private TranslatorListener listener;
	private TranslationSource source;
	private Locale locale;
	private String domain;
	
	private Pattern pattern = Pattern.compile("\\{([0-9]*)\\}");
	
	public Translator() {
		
	}
	
	/**
	 * Returns default Translator Locale.
	 * If NULL default Locale will be used.
	 * 
	 * @return current Locale
	 */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * Sets new Translator Locale.
	 * If NULL default Locale will be used.
	 * 
	 * @param locale new Locale
	 * @return this Translator instance
	 */
	public Translator setLocale(Locale locale) {
		Locale old = this.locale;
		this.locale = locale;
		if(listener != null) {
			listener.localeChanged(old, locale);
		}
		return this;
	}
	
	/**
	 * Returns default Translator domain string.
	 * 
	 * @return default domain
	 */
	public String getDomain() {
		return domain;
	}
	
	/**
	 * Sets new default domain string.
	 * 
	 * @param domain new domain string
	 * @return this Translator instance
	 */
	public Translator setDomain(String domain) {
		this.domain = domain;
		return this;
	}
	
	/**
	 * Return Translation backend used by this Translator.
	 * 
	 * @return Translation backen
	 */
	public Translation getTranslation() {
		return translation;
	}
	
	/**
	 * Sets new Translation backend.
	 * 
	 * @param translation new backend
	 * @return this Translator instance
	 */
	public Translator setTranslation(Translation translation) {
		this.translation = translation;
		return this;
	}
	
	/**
	 * Returns current TranslatorListener.
	 * 
	 * @return current listener
	 */
	public TranslatorListener getListener() {
		return listener;
	}
	
	/**
	 * Sets new TranslationListener.
	 * 
	 * @param l new listener
	 * @return this Translator instance
	 */
	public Translator setListener(TranslatorListener l) {
		listener = l;
		return this;
	}
	
	/**
	 * Returns current TranslationSource that is used
	 * for file loading.
	 * 
	 * @return current source loader
	 */
	public TranslationSource getSource() {
		return source;
	}
	
	/**
	 * Sets new TranslationSource that is used for file
	 * loading.
	 * 
	 * @param source new source loader
	 * @return this Translator instance
	 */
	public Translator setSource(TranslationSource source) {
		this.source = source;
		return this;
	}
	
	/**
	 * Returns translation for given key.
	 * 
	 * @param key translation key
	 * @return translated string
	 */
	public String translate(String key) {
		return translate(key, null, null, new Object[0]);
	}
	
	/**
	 * Returns translation for given key where
	 * all place holders are replaced with given
	 * arguments.
	 * 
	 * @param key  translation key
	 * @param args place holder values
	 * @return parsed and translated string
	 */
	public String translate(String key, Object ... args) {
		return translate(key, null, null, args);
	}
	
	/**
	 * Returns translation for given key, locale and
	 * domain where all place holders are replaced with
	 * given arguments.
	 * 
	 * @param key    translation key
	 * @param locale target locale
	 * @param domain domain name
	 * @param args   place holder values
	 * @return parsed and translated string
	 */
	public String translate(String key, Locale locale, String domain, Object ... args) {
		if(locale == null) {
			locale = (this.locale != null) ? this.locale : Locale.getDefault();
		}
		if(domain == null) {
			domain = (this.domain != null) ? this.domain : "";
		}
		
		try{
			String translation = getTranslation(key, locale, domain, args);
			if(translation != null) {
				if(!translation.contains("{")) {
					return translation;
				}
				return parseTranslation(translation, locale, domain, args);
			}
		}catch(MissingTranslationException e) {
			if(listener != null) {
				return listener.stringNotFound(key, locale, domain, args, e);
			}
		}catch(MissingResourceException e) {
			if(listener != null) {
				return listener.translationNotFound(key, locale, domain, args, e);
			}
		}
		return "<" + key + ">";
	}
	
	protected String getTranslation(String key, Locale locale, String domain, Object[] args) {
		return translation.getString(this, key, locale, domain, args);
	}
	
	protected String parseTranslation(String str, Locale locale, String domain, Object[] args) {
		Matcher m = pattern.matcher(str);
		while(m.find()) {
			int index = Integer.parseInt(m.group(1));
			if(args != null && args.length > index && args[index] != null) {
				str = str.replace(m.group(), args[index].toString());
			}else{
				str = str.replace(m.group(), "");
			}
		}
		return str;
	}
	
}
