package org.aesthete.swingobjects.workers;

import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.scope.RequestScopeObject;

public interface SwingWorkerInterface {
	public String getAction();
    boolean proceed();
	public abstract boolean validateAndPopulate(RequestScopeObject scopeObj);
	public abstract void callModel(RequestScopeObject scopeObj) throws Exception;
	public abstract void callConnector(RequestScopeObject scopeObj);
	public abstract void execute();
}
