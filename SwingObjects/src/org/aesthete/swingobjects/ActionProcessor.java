package org.aesthete.swingobjects;

import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.border.Border;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.view.FrameFactory;
import org.aesthete.swingobjects.workers.CommonSwingWorker;

public class ActionProcessor {

	public enum CLIENT_PROPS{BORDER,ENABLED};
	
	public static void processAction(Object container,CommonSwingWorker swingworker){
		try{
			initCompsBeforeAction(container);
		}catch(Exception e){
			throw new SwingObjectRunException("Error processing action", e,
					ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}
	
	
	private static void initCompsBeforeAction(Object comp) throws IllegalArgumentException, IllegalAccessException{
		Field[] fields=comp.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			Object prop=field.get(comp);
			if(JComponent.class.isAssignableFrom(field.getClass())){
				JComponent component = (JComponent)prop;
				Border savedBorder=(Border)component.getClientProperty(CLIENT_PROPS.BORDER);
				component.setEnabled(false);
			}
		}
	}
}
