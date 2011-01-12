package de.altimos.util.worker;

import java.util.Collection;

/**
 * A DetachedTaskWorker executes each task in a single thread 
 * and does not block the source thread.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
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
	public void executeTasks(Collection<Task> tasks) {
		executeTasksGroup(tasks);
	}

	@SuppressWarnings("rawtypes")
	protected ThreadGroup executeTasksGroup(Collection<Task> tasks) {
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
