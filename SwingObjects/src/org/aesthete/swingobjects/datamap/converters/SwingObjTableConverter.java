package org.aesthete.swingobjects.datamap.converters;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JToggleButton;

import org.aesthete.swingobjects.datamap.DataWrapper;
import org.aesthete.swingobjects.view.table.SwingObjTable;

public class SwingObjTableConverter implements Converter {

	@Override
	public DataWrapper getDataFromViewComponent(JComponent component) {
		if (component == null) {
			return null;
		} else {
			return new DataWrapper(((SwingObjTable<?>) component).getData());
		}
	}

	@Override
	public void setDataIntoViewComponent(DataWrapper data, JComponent component) {
		if (component == null) {
			return;
		}
		if(data!=null){
			List<?> dataList=(List<?>)data.getValue();
			((SwingObjTable<?>)component).setData(dataList);
		}
		((JToggleButton)component).setSelected(data.asBoolean());
	}

}
