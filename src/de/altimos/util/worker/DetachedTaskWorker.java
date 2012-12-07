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
 * A DetachedTaskWorker executes each task in a single thread 
 * and does not block the source thread.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class DetachedTaskWorker implements TaskWorker {

	static private DetachedTaskWorker worker = new DetachedTaskWorker();

	/**
	 * This method returns a singelton instance.
	 * 
	 * @return singelton instance
	 */
	static public DetachedTaskWorker getWorker() {
		return worker;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void executeTasks(Collection<? extends Task> tasks) {
		executeTasksGroup(tasks);
	}

	@SuppressWarnings("rawtypes")
	protected ThreadGroup executeTasksGroup(Collection<? extends Task> tasks) {
		ThreadGroup threads = new ThreadGroup(this.toString());
		for (Task task : tasks) {
			new TaskThread(threads, task).start();
		}
		return threads;
	}

	private class TaskThread extends Thread {

		@SuppressWarnings("rawtypes")
		private Task task;

		@SuppressWarnings("rawtypes")
		public TaskThread(ThreadGroup group, Task task) {
			super(group, task.toString());
			this.task = task;
		}

		@Override
		public void run() {
			task.execute();
		}
	}
}
