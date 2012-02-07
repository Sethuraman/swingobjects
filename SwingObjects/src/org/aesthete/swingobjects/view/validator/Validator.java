package org.aesthete.swingobjects.view.validator;

/**
 * Hook to provide your own custom UI validation. This is called on the EDT. Make sure you only validate quickly what
 * you need. Any DB Accesses for example is to be done in the model.
 *
 * Remember - There is no guarantee on the order in which Validators are called! Read as is iterating
 * through a Set of Validators. So make sure you don't code in a way that depends on any particular Validator is called before another one.
 * The best way to code, would be to have ideally one Validator for a request.
 *
 *
 * Please refer to the implementation of both these methods and the java docs under the class
 * @see AbstractValidator
 * @author sethu
 *
 */
public interface Validator {

	/**
	 * Please see
	 * @see AbstractValidator#validate(String)
	 * @param action
	 * @return
	 */
	public abstract boolean validate(String action);

	/**
	 * Please see @see {@link AbstractValidator#continueIfError(String)}
	 * @param action
	 * @return
	 */
	public abstract boolean continueIfError(String action);

}