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
 * A SequentialTaskWorker executes all given task 
 * in a sequential order and returns when all tasks 
 * are finished.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class SequentialTaskWorker implements TaskWorker {

	static private SequentialTaskWorker worker = new SequentialTaskWorker();

	/**
	 * This method returns a singelton SequentialTaskWorker instance.
	 * 
	 * @return singelton instance
	 */
	static public SequentialTaskWorker getWorker() {
		return worker;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void executeTasks(Collection<? extends Task> tasks) {
		synchronized (tasks) {
			for (Task task : tasks) {
				task.execute();
			}
		}
	}
}
