package org.aesthete.swingobjects.workers;

import org.aesthete.swingobjects.exceptions.SwingObjectException;

public interface SwingWorkerInterface {
	public String getAction();
	public abstract void callModel() throws SwingObjectException;
	public abstract void callConnector();
	public abstract void execute();
}
