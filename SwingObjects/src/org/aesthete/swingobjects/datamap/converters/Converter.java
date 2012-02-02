package org.aesthete.swingobjects.datamap.converters;

import javax.swing.JComponent;

import org.aesthete.swingobjects.datamap.DataWrapper;

public interface Converter {

/**
 * You must implement this method to obtain the data from the component. There are default converters provided for you
 * for the common JComponent. In this method you would have to extract the model or data from the view component and
 * return the object back. This Object will be stored in the SwingDataObj for you with the same name as the variable name
 * of this component.
 *
 * For an example @see {@link JTextComponentConverter}
 * @param component
 * @return data of this component
 */
	public DataWrapper getDataFromViewComponent(JComponent component);


	/**
	 * This is the reverse of the above method. After working in your model if you would like to set data
	 * back to the view, then set the data in the SwingDataObj object. From there the framework will call this
	 * method on a converter to set the data back into the view.
	 *
	 * For an example @see {@link JTextComponentConverter}
	 * @param data
	 * @param component
	 */
	public void setDataIntoViewComponent(DataWrapper data,JComponent component);
}
