package org.aesthete.swingobjects.datamap.converters;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.datamap.DataWrapper;

public class JTextComponentConverter implements Converter {

	@Override
	public DataWrapper getDataFromViewComponent(JComponent component) {
		if (component == null) {
			return null;
		} else {
			return new DataWrapper(((JTextComponent) component).getText());
		}
	}

	@Override
	public void setDataIntoViewComponent(DataWrapper data, JComponent component) {
		if (component == null) {
			return;
		}
		((JTextComponent)component).setText(data.asString());
	}

}
