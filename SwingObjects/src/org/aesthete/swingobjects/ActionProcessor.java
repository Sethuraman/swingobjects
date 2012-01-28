package org.aesthete.swingobjects;

import java.awt.Cursor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.annotations.Trim;
import org.aesthete.swingobjects.annotations.Validate;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.view.Components;
import org.aesthete.swingobjects.view.FrameFactory;
import org.aesthete.swingobjects.workers.CommonSwingWorker;

public class ActionProcessor {

	public enum CLIENT_PROPS{BORDER,ENABLED};

	public static void processAction(Object container,CommonSwingWorker swingworker){
		try{
			initCompsBeforeAction(container);
			if(performUIValidations(container,swingworker)) {

			}
		}catch(Exception e){
			throw new SwingObjectRunException("Error processing action", e,
					ErrorSeverity.SEVERE, FrameFactory.class);
		}
	}


	private static boolean performUIValidations(Object container, CommonSwingWorker swingworker) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields=container.getClass().getDeclaredFields();
		List<Field> fieldsWithValAnnos=new ArrayList<Field>();
		for(Field field:fields){
			field.setAccessible(true);
			Object prop=field.get(container);
			if(prop instanceof Components) {
				return ((Components)prop).validate(swingworker.getAction());
			}else if(field.getAnnotation(Validate.class)!=null) {
				fieldsWithValAnnos.add(field);
			}
		}
		return false;
	}


	private static void initCompsBeforeAction(Object comp) throws IllegalArgumentException, IllegalAccessException{
		Field[] fields=comp.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			Object prop=field.get(comp);
			if(JComponent.class.isAssignableFrom(field.getClass())){
				initComponent(prop);
				trimTexts(field, prop);
			}
		}
	}


	private static void trimTexts(Field field, Object prop) {
		if(JTextComponent.class.isAssignableFrom(field.getClass())) {
			JTextComponent txtComp=(JTextComponent)prop;

			Trim trim=field.getAnnotation(Trim.class);
			if(trim!=null) {
				if(trim.value()==YesNo.YES) {
					txtComp.setText(txtComp.getText()==null?null:txtComp.getText().trim());
				}
			}else if("true".equals(SwingObjProps.getProperty("guielements.texttrim"))){
				txtComp.setText(txtComp.getText()==null?null:txtComp.getText().trim());
			}

		}
	}


	private static void initComponent(Object prop) {
		JComponent component = (JComponent)prop;
		Border savedBorder=(Border)component.getClientProperty(CLIENT_PROPS.BORDER);
		if(savedBorder==null){
		    component.putClientProperty(CLIENT_PROPS.BORDER,component.getBorder());
		}else{
		    component.setBorder(savedBorder);
		}

		Boolean isEnabled=(Boolean)component.getClientProperty(CLIENT_PROPS.ENABLED);
		if(isEnabled==null) {
			component.putClientProperty(CLIENT_PROPS.ENABLED, component.isEnabled());
		}

		component.setEnabled(false);

		component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
