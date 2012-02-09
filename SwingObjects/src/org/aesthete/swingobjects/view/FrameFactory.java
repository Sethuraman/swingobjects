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
import org.aesthete.swingobjects.util.FieldCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;

/**
 * It all starts here. You will use the FrameFactory, to instantiate your Dialogs or Frames that you wish to show on screen.
 * To create a new Frame you will have to call {@link FrameFactory#getNewContainer(String, Class, Object...)}
 * <pre>
 *        MyFirstFrame frame=FrameFactory.getNewContainer("myfirstframe",MyFirstFrame.class,<constructor args for MyFirstFrame>);
 * </pre>
 *
 *
 * @author sethu
 *
 */
public class FrameFactory {

	private static Map<Component,String> framesetidsmap=new ConcurrentHashMap<Component,String>();


	@SuppressWarnings("unchecked")
	public static <T extends Component> T getNewContainer(String framesetid,
			Class<? extends Component> clz, Object... objs) throws SwingObjectRunException{
		Component comp=null;
		try {
			comp = (Component)ConstructorUtils.invokeConstructor(clz, objs);
			framesetidsmap.put(comp, framesetid);
			registerActionlistener(comp);
		} catch (Exception e) {
			throw new SwingObjectRunException(e,ErrorSeverity.SEVERE, FrameFactory.class);
		}
		return (T)comp;
	}


	private static void registerActionlistener(final Object comp) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		final GlobalListener listener=new GlobalListener(comp);
		ReflectionUtils.iterateOverFields(comp.getClass(), null, new FieldCallback() {
			private Object prop;
			@Override
			public boolean filter(Field field) {
				try{
					prop=field.get(comp);
					if(Components.class.isAssignableFrom(field.getType())) {
						registerActionlistener(field.get(comp));
					}else if(prop instanceof AbstractButton ||
							prop instanceof JComboBox ||
							prop instanceof JTextField) {
						return true;
					}

				}catch(Exception e){
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
				}
				return false;
			}

			@Override
			public void consume(Field field) {
				try {
					MethodUtils.invokeMethod(prop, "addActionListener", listener);
				} catch (Exception e) {
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, FrameFactory.class);
				}
			}
		});
	}

	private static int frameno;

	public static synchronized String getNewFrameSetId() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmssSSSSS"
				+ frameno++);
		return sdf.format(new Date());
	}

}
