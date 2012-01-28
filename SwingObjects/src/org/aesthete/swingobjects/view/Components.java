package org.aesthete.swingobjects.view;

/**
 * This is a marker interface for a java object holding JComponents to be displayed
 * in a container such as a JPanel, JDialog, JFrame, etc.
 * Always mark such a java object with this interface so that the Swing Objects framework
 * can access the fields in it.
 * @author sethu
 *
 */
public abstract class Components {

	public boolean validate(String action) {

		return validateLocally(action);
	}

	/**
	 * Override this method if you would like to perform validations on UI components that haven't been annotated
	 * or you would like to any additional validations.
	 * @param action - String
	 * @return  true if validation is successful, false if not
	 */
	public boolean validateLocally(String action) {
		return true;
	}

}
