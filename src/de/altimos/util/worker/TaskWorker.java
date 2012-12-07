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
package de.altimos.util.worker;

import java.util.Collection;

/**
 * A TaskWorker can executes a collection of Tasks.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public interface TaskWorker {

	/**
	 * This method executes all given tasks.
	 * 
	 * @param tasks to execute
	 */
	@SuppressWarnings("rawtypes")
	public void executeTasks(Collection<? extends Task> tasks);
}
