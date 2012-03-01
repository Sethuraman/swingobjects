package org.aesthete.swingobjects.workers;

import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.scope.RequestScopeObject;

public interface SwingWorkerInterface {
	public String getAction();
	public abstract boolean validateAndPopulate(RequestScopeObject scopeObj);
	public abstract void callModel(RequestScopeObject scopeObj) throws SwingObjectException;
	public abstract void callConnector(RequestScopeObject scopeObj);
	public abstract void execute();
}
