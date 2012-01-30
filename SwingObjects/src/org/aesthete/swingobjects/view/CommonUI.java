package org.aesthete.swingobjects.view;

import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

public class CommonUI {

	
	public static void nameComponents(Class<?> clz, Object obj) {
		try {
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {
				if (CommonUI.isNamedComponent(field.getType())) {
					field.setAccessible(true);
					if(field.get(obj) != null){
						String name = (String) MethodUtils.invokeMethod(field.get(obj), "getName", null);
						if(StringUtils.isNotEmpty(name)) {
							MethodUtils.invokeMethod(field.get(obj), "setName", field.getName());
						}
					}
				}
			}
		}  catch (Exception e) {
			throw new SwingObjectRunException("Error naming components", e,
					ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}


	public static boolean isNamedComponent(Class<?> type) {
		if(!JComponent.class.isAssignableFrom(type)) {
			return false;
		}
		if(JPanel.class.isAssignableFrom(type)) {
			return false;
		}else if (JDialog.class.isAssignableFrom(type)){
			return false;
		}else if (JFrame.class.isAssignableFrom(type)){
			return false;
		}

		return true;
	}

}
