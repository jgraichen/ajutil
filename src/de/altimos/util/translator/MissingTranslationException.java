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

import java.util.MissingResourceException;

/**
 * This exception is thrown internaly in Translator if used Translation
 * does not found key for translation but translation source exists.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class MissingTranslationException extends MissingResourceException {
	
	private static final long serialVersionUID = -7067232850760541357L;
	
	public MissingTranslationException(String s, String translation, String key) {
		super(s, translation, key);
	}
	
}
