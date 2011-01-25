package de.altimos.util.worker;

import java.util.Collection;

/**
 * A ParallelTaskWorker executes all given threads parallel wait 
 * until all threads are finished.
 *
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class ParallelTaskWorker extends DetachedTaskWorker {

	static private ParallelTaskWorker worker = new ParallelTaskWorker();

	/**
	 * This method returns a singelton instance.
	 * 
	 * @return singelton instance
	 */
	static public ParallelTaskWorker getWorker() {
		return worker;
	}

	/**
	 * This method waits until all Threads from given
	 * ThreadGroup are finished.
	 * 
	 * @param threads 
	 */
	static public void join(ThreadGroup threads) {
		synchronized (threads) {
			while (threads.activeCount() > 0) {
				try {
					threads.wait();
				} catch (InterruptedException ex) { }
			}
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void executeTasks(Collection<? extends Task> tasks) {
		ThreadGroup threads = executeTasksGroup(tasks);
		join(threads);
	}
}
