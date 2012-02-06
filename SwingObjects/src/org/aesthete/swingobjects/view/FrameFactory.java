package org.aesthete.swingobjects.view;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;

public class FrameFactory {
	
	private static Map<Component,String> framesetidsmap=new ConcurrentHashMap<Component,String>();
	

	@SuppressWarnings("unchecked")
	public static <T extends Component> T getNewContainer(String framesetid,
			Class<? extends Component> clz, Object... objs) throws SwingObjectRunException{
		Component comp=null;
		try {
			comp = (Component)ConstructorUtils.invokeConstructor(clz, objs);
			framesetidsmap.put(comp, framesetid);
			registerActionlistner(comp);
		} catch (Exception e) {
			throw new SwingObjectRunException(e,ErrorSeverity.SEVERE, FrameFactory.class);
		}
		return (T)comp;
	}

	private static void registerActionlistner(Object comp) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		GlobalListener listner=new GlobalListener(comp);
		Field[] fields=comp.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			Object prop=field.get(comp);
			if(prop instanceof AbstractButton || 
					prop instanceof JComboBox || 
					prop instanceof JTextField){
				MethodUtils.invokeMethod(prop, "addActionListener", listner);
			}else if(prop instanceof Components){
				registerActionlistner(prop);
			}
		}
		
	}

	private static int frameno;

	public static synchronized String getNewFrameSetId() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmssSSSSS"
				+ frameno++);
		return sdf.format(new Date());
	}

}
