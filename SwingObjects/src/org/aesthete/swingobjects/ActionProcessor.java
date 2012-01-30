package org.aesthete.swingobjects;

import java.awt.Cursor;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.annotations.Required;
import org.aesthete.swingobjects.annotations.ShouldBeEmpty;
import org.aesthete.swingobjects.annotations.Trim;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.view.Components;
import org.aesthete.swingobjects.view.FrameFactory;
import org.aesthete.swingobjects.workers.CommonSwingWorker;
import org.apache.commons.lang.StringUtils;

public class ActionProcessor {

	public enum CLIENT_PROPS{BORDER,ENABLED,TOOLTIP};

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
		boolean isError=false;
		for(Field field:fields){
			field.setAccessible(true);
			Object prop=field.get(container);
			if(prop instanceof Components) {
				isError=performUIValidations(prop, swingworker);
			} else {
				Required reqAnno = field.getAnnotation(Required.class);
				ShouldBeEmpty empty=field.getAnnotation(ShouldBeEmpty.class);
				if(reqAnno!=null || empty!=null) {
					isError=checkForRequired(reqAnno!=null,
							reqAnno!=null? reqAnno.errorMsg() : empty.errorMsg(),
							reqAnno!=null? reqAnno.value() : empty.value(),		
									field,container,swingworker.getAction());
				}
			}
		}
		return isError;
	}


	private static boolean checkForRequired(boolean isRequired, String msg, String[] actions,
			Field field, Object container,String action) throws IllegalArgumentException, IllegalAccessException {
		if(actions==null || actions.length==0  
			||	(actions.length>1 && "ALL".equals(actions[0]))
			||  (actions.length>1 && StringUtils.isNotEmpty(action) &&	action.equals(actions[0]))){
			
			Object fieldObj = field.get(container);
			if(fieldObj instanceof JComponent){
				JComponent jcomponent = (JComponent)fieldObj;
				boolean isError=false;
				if(fieldObj instanceof JTextComponent){
					if(isRequired && StringUtils.isEmpty(((JTextComponent)fieldObj).getText())){
						isError=true;
					}else if(!isRequired && StringUtils.isNotEmpty(((JTextComponent)fieldObj).getText())){
						isError=true;
					}
				}else if(fieldObj instanceof JComboBox){
					JComboBox cb=(JComboBox)fieldObj;
					if(isRequired && cb.getSelectedIndex()==-1){
						isError=true;
					}else if(isRequired && (cb.getSelectedItem()==null || StringUtils.isEmpty(cb.getSelectedItem().toString()))){
						isError=true;
					}else if(!isRequired && (cb.getSelectedItem()!=null && StringUtils.isNotEmpty(cb.getSelectedItem().toString()))){
						isError=true;
					}
				}else if(fieldObj instanceof JList){
					JList list=(JList)fieldObj;
					if(isRequired && list.getSelectedIndex()==-1){
						isError=true;
					}else if(isRequired && (list.getSelectedValue()==null || StringUtils.isEmpty(list.getSelectedValue().toString()))){
						isError=true;
					}else if(!isRequired && (list.getSelectedValue()!=null && StringUtils.isNotEmpty(list.getSelectedValue().toString()))){
						isError=true;
					}
				}else if(fieldObj instanceof JToggleButton){
					JToggleButton tglbtn=(JCheckBox)fieldObj;
					if(isRequired && !tglbtn.isSelected()){
						isError=true;
					}else if(!isRequired && tglbtn.isSelected()){
						isError=true;
					}
				}
				
				if(isError){
					String tooltip=jcomponent.getToolTipText();
					if(StringUtils.isEmpty(tooltip)){
						tooltip="<html>"+msg+"</html>";
					}else{
						tooltip=tooltip.replace(Pattern.quote("<html>"), 
								Matcher.quoteReplacement("<html>"+msg+"<br/>"));
					}
					jcomponent.setToolTipText(tooltip);
					return true;
				}
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
		    component.putClientProperty(CLIENT_PROPS.BORDER,
		    		component.getBorder()==null?BorderFactory.createEmptyBorder():component.getBorder());
		}else{
		    component.setBorder(savedBorder);
		}

		Boolean isEnabled=(Boolean)component.getClientProperty(CLIENT_PROPS.ENABLED);
		if(isEnabled==null) {
			component.putClientProperty(CLIENT_PROPS.ENABLED, component.isEnabled());
		}

		component.setEnabled(false);

		component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		String tooltip=(String)component.getClientProperty(CLIENT_PROPS.TOOLTIP);
		if(tooltip==null){
			component.putClientProperty(CLIENT_PROPS.TOOLTIP, component.getToolTipText()==null ? "" : component.getToolTipText());
		}else{
			component.setToolTipText(tooltip);
		}
	}
}
