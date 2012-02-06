package org.aesthete.swingobjects.datamap;

import java.lang.reflect.Field;

import javax.swing.JComponent;

import org.aesthete.swingobjects.annotations.DataBeanName;
import org.aesthete.swingobjects.datamap.converters.Converter;
import org.aesthete.swingobjects.datamap.converters.ConverterUtils;
import org.aesthete.swingobjects.exceptions.ErrorSeverity;
import org.aesthete.swingobjects.exceptions.SwingObjectRunException;
import org.aesthete.swingobjects.scope.RequestScope;
import org.aesthete.swingobjects.scope.RequestScopeObject;
import org.aesthete.swingobjects.util.FieldCallback;
import org.aesthete.swingobjects.util.ReflectionUtils;
import org.aesthete.swingobjects.view.Components;
import org.aesthete.swingobjects.view.FrameFactory;

public class DataMapper {

	public static void mapData(Object container) {
		SwingObjData swingObjdata=new SwingObjData();
		try {
			DataBeanName dataClass = container.getClass().getAnnotation(
					DataBeanName.class);
			if (dataClass != null) {
				String beanName = dataClass.value();
				populateObject(container,swingObjdata);
				RequestScopeObject scopeObj=RequestScope.getRequestObj();
				scopeObj.putObjectInMap(beanName, swingObjdata);
			}
		} catch (Exception e) {
			throw new SwingObjectRunException(e,ErrorSeverity.SEVERE, FrameFactory.class);

		}
	}

	private static void populateObject(final Object container,final SwingObjData objData){
		ReflectionUtils.iterateOverFields(container.getClass(), null, new FieldCallback() {
			private boolean isJComponent;
			@Override
			public boolean filter(Field field) {
				if(JComponent.class.isAssignableFrom(field.getType())){
					isJComponent=true;
					return true;
				}else if(Components.class.isAssignableFrom(field.getType())) {
					return true;
				}
				return false;
			}

			@Override
			public void consume(Field field) throws SwingObjectRunException{
				try{
					String name=field.getName();
					if(isJComponent) {
						Converter converter=ConverterUtils.getConverter(field.getType());
						if(converter!=null) {
							objData.setUnchanged(name, converter.getDataFromViewComponent((JComponent)field.get(container)));
						}
					}else {
						SwingObjData swingObjdata=new SwingObjData();
						populateObject(field.get(container),swingObjdata);
						objData.setUnchanged(name, swingObjdata);
					}
				}catch(Exception e){
					throw new SwingObjectRunException(e, ErrorSeverity.SEVERE, DataMapper.class);
				}
			}
		});
	}
}
