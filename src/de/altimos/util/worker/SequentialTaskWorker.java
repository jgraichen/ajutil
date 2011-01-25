package de.altimos.util.worker;

import java.util.Collection;

/**
 * A SequentialTaskWorker executes all given task 
 * in a sequential order and returns when all tasks 
 * are finished.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
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
