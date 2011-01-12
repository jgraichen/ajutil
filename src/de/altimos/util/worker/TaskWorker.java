package de.altimos.util.worker;

import java.util.Collection;

/**
 * A TaskWorker can executes a collection of Tasks.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public interface TaskWorker {

	/**
	 * This method executes all given tasks.
	 * 
	 * @param tasks to execute
	 */
	@SuppressWarnings("rawtypes")
	public void executeTasks(Collection<Task> tasks);
}
