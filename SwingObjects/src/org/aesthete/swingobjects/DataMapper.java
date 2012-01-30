package org.aesthete.swingobjects;

import java.lang.reflect.Field;

import javax.swing.JComponent;

import org.aesthete.swingobjects.annotations.DataBeanName;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.view.FrameFactory;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class DataMapper {
	
	public static Object mapData(Object container) {
		try {
			DataBeanName dataClass = container.getClass().getAnnotation(
					DataBeanName.class);
			if (dataClass != null) {
				String beanName = dataClass.value();
				

			}
		} catch (Exception e) {
			throw new SwingObjectRunException("Error processing action", e,
					ErrorSeverity.SEVERE, FrameFactory.class);

		}
		return null;	
	}
	
	private static void populateObject(Object container){
		Field[] fields=container.getClass().getFields();
		for(Field field : fields){
			String name=field.getName();
			if(JComponent.class.isAssignableFrom(field.getClass())){
				field.setAccessible(true);
				
			}
		}
	}
}
