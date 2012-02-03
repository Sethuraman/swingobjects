package org.aesthete.swingobjects.datamap.converters;

import java.awt.Container;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.datamap.ComboBoxData;
import org.aesthete.swingobjects.datamap.DataWrapper;
import org.aesthete.swingobjects.view.table.SwingObjTable;

public class ConverterUtils {

	private static final Map<Class<?>, Converter> map = new HashMap<Class<?>, Converter>();

	public static void registerConverter(Class<? extends JComponent> clz, Converter converter) {
		map.put(clz, converter);
	}

	public static void deregisterConverter(Class<? extends JComponent> clz) {
		map.remove(clz);
	}

	public static Converter getConverter(Class<?> clz) {
		for (Class<? extends Object> check = clz; check != null && check != Container.class; check = check.getSuperclass()) {
			for (Class<?> key : map.keySet()) {
				if (key == check) {
					return map.get(key);
				}
			}
		}
		return null;
	}

	public static class JComboBoxConverter implements Converter {

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

	public static class JTextComponentConverter implements Converter {

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

	public static class JToggleButtonConverter implements Converter {

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

	public static class SwingObjTableConverter implements Converter {

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
}
