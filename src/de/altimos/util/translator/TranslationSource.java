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

import java.io.IOException;
import java.io.InputStream;

/**
 * TranslatorSource implementations use a user-defined system for file loading
 * and returns input streams for requested translation files.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public interface TranslationSource {
	
	/**
	 * Tries to find a Translator resource file and returns an InputStream.
	 *
	 * @param name resource name
	 * @return InputStream for requested resource or NULL if resource cannot be found.
	 */
	public InputStream getStream(String name) throws IOException;
	
}
