package org.aesthete.swingobjects;

import java.awt.Color;
import java.awt.Container;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import org.aesthete.swingobjects.annotations.Required;
import org.aesthete.swingobjects.annotations.ShouldBeEmpty;
import org.aesthete.swingobjects.annotations.Trim;
import org.aesthete.swingobjects.datamap.DataMapper;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectException;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.exceptions.SwingObjectsExceptions;
import org.aesthete.swingobjects.scope.RequestScope;
import org.aesthete.swingobjects.scope.RequestScopeObject;
import org.aesthete.swingobjects.util.FieldCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.aesthete.swingobjects.view.CommonUI;
import org.aesthete.swingobjects.view.Components;
import org.aesthete.swingobjects.view.FrameFactory;
import org.aesthete.swingobjects.view.validator.Validator;
import org.aesthete.swingobjects.workers.SwingWorkerInterface;
import org.apache.commons.lang3.StringUtils;

public class ActionProcessor {

	public enum CLIENT_PROPS{BORDER,ENABLED,TOOLTIP};
	boolean isError=false;
	private Set<Validator> validators;
	private RequestScopeObject scopeObj;
	private List<JComponent> fieldsOfContainer;

	private ActionProcessor() {
		scopeObj=RequestScope.getRequestObj();
		fieldsOfContainer=new ArrayList<JComponent>();
	}

	public static void processAction(Object container,SwingWorkerInterface swingworker){
		ActionProcessor processor=new ActionProcessor();
		try{
			processor.initCompsAndValidate(container,swingworker);
			if(!processor.isError) {
				DataMapper.mapData(container);

				if(!processor.performUserValidations(swingworker, processor)) {
					processor.scopeObj.setContainer(container);
					processor.scopeObj.setFieldsOfTheContainer(processor.fieldsOfContainer);
					swingworker.execute();
				}

			}else{
				CommonUI.restoreComponentsToInitialState(processor.fieldsOfContainer);
				processor.showErrorDialog();
			}
		}
		catch(Exception e){
			CommonUI.restoreComponentsToInitialState(processor.fieldsOfContainer);
			if(e instanceof SwingObjectsExceptions) {
				CommonUI.showErrorDialogForComponent((SwingObjectsExceptions)e);
			}else {
				CommonUI.showErrorDialogForComponent(new SwingObjectRunException(e,ErrorSeverity.SEVERE, FrameFactory.class));
			}
		}
	}

	private boolean performUserValidations(SwingWorkerInterface swingworker, ActionProcessor processor) {
		boolean isError=false;
		if(processor.validators!=null) {
			for(Validator validator : processor.validators) {
				boolean isOk=validator.validate(swingworker.getAction());
				if(!isOk) {
					isError=true;
					if(!validator.continueIfError(swingworker.getAction())) {
						break;
					}
				}
			}
		}
		if(isError) {
			processor.showErrorDialog();
		}
		return isError;
	}

	private void showErrorDialog() {
		if(scopeObj.getErrorObj()!=null) {
			CommonUI.showErrorDialogForComponent(scopeObj.getErrorObj());
		}else {
			CommonUI.showErrorDialogForComponent(new SwingObjectException("swingobj.commonerror", null,ErrorSeverity.ERROR, ActionProcessor.class));
		}
	}

	private void initCompsAndValidate(final Object container, final SwingWorkerInterface swingworker) throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.iterateOverFields(container.getClass(), Container.class, new FieldCallback() {
			@Override
			public boolean filter(Field field) {
				return true;
			}

			@Override
			public void consume(Field field) {
				try {
					if (Validator.class.isAssignableFrom(field.getType())) {
						if(validators==null) {
							validators=new HashSet<Validator>();
						}
						validators.add((Validator) field.get(container));
					}


					if (Components.class.isAssignableFrom(field.getType())) {
						initCompsAndValidate(field.get(container), swingworker);
					}else if(JComponent.class.isAssignableFrom(field.getType())) {
						Object prop=field.get(container);
						fieldsOfContainer.add((JComponent)prop);
						CommonUI.initComponent(prop);
						trimTexts(field, prop);
						Required reqAnno = field.getAnnotation(Required.class);
						ShouldBeEmpty empty = field.getAnnotation(ShouldBeEmpty.class);

						if (reqAnno != null || empty != null) {
							boolean isError=checkForRequired(reqAnno!=null,
									reqAnno!=null? reqAnno.errorMsg() : empty.errorMsg(),
									reqAnno!=null? reqAnno.value() : empty.value(),
											field,container,swingworker.getAction());
							if(isError) {
								ActionProcessor.this.isError=true;
							}
						}
					}
				}catch(Exception e){
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, DataMapper.class);
				}
			}
		});
	}


	private boolean checkForRequired(boolean isRequired, String msg, String[] actions,
			Field field, Object container,String action) throws IllegalArgumentException, IllegalAccessException {
		if(actions==null || actions.length==0
			||	(actions.length>0 && "ALL".equals(actions[0]))
			||  (actions.length>0 && StringUtils.isNotEmpty(action) &&	action.equals(actions[0]))){

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
					jcomponent.setBorder(BorderFactory.createLineBorder(Color.red));
					return true;
				}
			}
		}
		return false;
	}

	private void trimTexts(Field field, Object prop) {
		if(prop instanceof JTextComponent) {
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



}
