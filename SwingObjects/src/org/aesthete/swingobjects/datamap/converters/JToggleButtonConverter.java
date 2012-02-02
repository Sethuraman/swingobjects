package org.aesthete.swingobjects.datamap.converters;

import javax.swing.JComponent;
import javax.swing.JToggleButton;

import org.aesthete.swingobjects.datamap.DataWrapper;

public class JToggleButtonConverter implements Converter {

	@Override
	public DataWrapper getDataFromViewComponent(JComponent component) {
		if (component == null) {
			return null;
		} else {
			return new DataWrapper(((JToggleButton) component).isSelected());
		}
	}

	@Override
	public void setDataIntoViewComponent(DataWrapper data, JComponent component) {
		if (component == null) {
			return;
		}
		((JToggleButton)component).setSelected(data.asBoolean());
	}

}
