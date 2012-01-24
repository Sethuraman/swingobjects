package org.aesthete.swingobjects.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aesthete.swingobjects.annotations.Action;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.apache.commons.lang.StringUtils;

public class GlobalListener implements ActionListener{

	private Object comp;
	private boolean isInited;
	private Map<String,Method> actions;
	
	public GlobalListener(Object comp) {
		this.comp=comp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action=e.getActionCommand();
		if(StringUtils.isNotEmpty(action)){
			init();
			Method m = actions.get(action);
			if(m!=null){
				try {
					m.invoke(comp, e);
				} catch (Exception exp) {
					throw new SwingObjectRunException("Error invoking action methods", exp.getCause(),
							ErrorSeverity.SEVERE, FrameFactory.class);
				}
			}
		}
		
	}

	private void init() {
		if(!isInited){
			Method[] methods = comp.getClass().getMethods();
			for(Method method : methods){
				Action a=method.getAnnotation(Action.class);
				if(a!=null){
					if(actions==null){
						actions=new HashMap<String, Method>();
					}
					for(String s : a.value()){
						actions.put(s, method);
					}
				}
			}
			isInited=true;
		}
	}

}
