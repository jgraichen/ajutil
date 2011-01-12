package de.altimos.util.worker;

/**
 * A Task is a single operationen that returns a result
 * and can be executed by a TaskWorker implementation.
 *
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public interface Task<T> {

	/**
	 * This method executes this task.
	 */
	public void execute();
	
	/**
	 * This method returns the exception thrown
	 * when executung this task.
	 * 
	 * @return thrown exeption
	 */
	public Exception getException();
	
	/**
	 * This method returns the produced result 
	 * of this task.
	 * 
	 * @return result
	 */
	public T getResult();
	
}
