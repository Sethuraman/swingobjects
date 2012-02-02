package org.aesthete.swingobjects.datamap.converters;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.aesthete.swingobjects.datamap.ComboBoxData;
import org.aesthete.swingobjects.datamap.DataWrapper;

public class JComboBoxConverter implements Converter {

	@Override
	public DataWrapper getDataFromViewComponent(JComponent component) {
		if (component == null) {
			return null;
		} else {
			JComboBox cb=(JComboBox)component;
			return new DataWrapper(new ComboBoxData(cb.getSelectedIndex(), cb.getSelectedItem()));
		}
	}

	@Override
	public void setDataIntoViewComponent(DataWrapper data, JComponent component) {
		if (component == null) {
			return;
		}
		if(data!=null) {
			ComboBoxData cbdata=(ComboBoxData)data.getValue();
			JComboBox cb=(JComboBox)component;
			if(cbdata.isIndexSet()) {
				cb.setSelectedIndex(cbdata.getSelectedIndex());
			}else {
				cb.setSelectedItem(cbdata.getSelectedItem());
			}
		}
	}

}
