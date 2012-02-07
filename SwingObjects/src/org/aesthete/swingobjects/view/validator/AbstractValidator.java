package org.aesthete.swingobjects.view.validator;

import org.aesthete.swingobjects.SwingObjectsInit;
import org.aesthete.swingobjects.scope.RequestScope;
import org.aesthete.swingobjects.scope.RequestScopeObject;

/**
 * Remember - There is no guarantee on the order in which Validators are called! <i>(Read as is iterating
 * through a Set of Validators)</i> So make sure you don't code in a way that depends on any particular Validator is called before another one.
 * The best way to code, would be to have ideally one Validator for a request.
 *
 * @author sethu
 *
 */
public abstract class AbstractValidator implements Validator{

	private RequestScopeObject scopeObj;

	/**
	 *  You can set an error that you want displayed in the error object inside the scope object if you want
	 *  your error to be shown to the user. Create the error like this:
	 *
	 *  <pre>
	 * new SwingObjectException("<error property>", null,ErrorSeverity.ERROR, ActionProcessor.class)
	 * </pre>
	 *
	 * Where your error property should be a propery inside the error.properties file that you provided
	 * when you called {@link SwingObjectsInit#init(String, String)}
	 *
	 * If no error is set, then the default error - from the error.properties file having the key - swingobj.commonerror
	 * will be shown to the user.
	 */
	@Override
	public boolean validate(String action) {
		scopeObj = RequestScope.getRequestObj();
		return doValidations(action);
	}

	public abstract boolean doValidations(String action);


	/**
	 * This method is to tell the SwingObjects Framework to either continue calling the other validators
	 * or just stop and show the error immediately after this Validator.
	 */
	@Override
	public boolean continueIfError(String action) {
		return true;
	}

	public RequestScopeObject getScopeObj() {
		return scopeObj;
	}

	public void setScopeObj(RequestScopeObject scopeObj) {
		this.scopeObj = scopeObj;
	}

}
