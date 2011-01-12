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

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Listener that listen at a Translator.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public interface TranslatorListener {
	
	/**
	 * This method is called when a translation string cannot be found.
	 * The returned string will be used as translation.
	 * 
	 * @param key    translation key
	 * @param locale target locale
	 * @param domain domain string
	 * @param args   arguments for parsing
	 * @param e      thrown MissingTranslationException
	 * @return place holder for missing string
	 */
	public String stringNotFound(String key, Locale locale, String domain, Object[] args, MissingTranslationException e);
	
	/**
	 * This method is called when a translation resource file cannot be found.
	 * The returned string will be used as translation.
	 * 
	 * @param key    translation key
	 * @param locale target locale
	 * @param domain domain string
	 * @param args   arguments for parsing
	 * @param e      thrown MissingResourceException
	 * @return place holder for missing string
	 */
	public String translationNotFound(String key, Locale locale, String domain, Object[] args, MissingResourceException e);
	
	/**
	 * This method is called when the default locale
	 * of Translator changes.
	 * 
	 * @param oldLocale old locale
	 * @param newLocale new locale
	 */
	public void localeChanged(Locale oldLocale, Locale newLocale);
	
}
