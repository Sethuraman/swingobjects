package org.aesthete.swingobjects.datamap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.LazyDynaBean;

public class SwingObjData extends LazyDynaBean {

	private Map<String, Boolean> valueChanged=new ConcurrentHashMap<String, Boolean>();

	private boolean isDataChanged;

	public boolean isDataChanged() {
		return isDataChanged;
	}

	public void setDataChanged(boolean isDataChanged) {
		this.isDataChanged = isDataChanged;
	}

	@Override
	public void set(String name, int index, Object value) {
		markChanged(name+index,value);
		super.set(name, index, value);
	}

	/**
	 * Make sure you call this method if in case your value has changed. You need to call this method if you need your GUi to be automatically
	 * updated with the values you set here.
	 * To prevent unnecessarily setting the same data onto a Component and triggering a repaint, the framework, will only set the data into the
	 * Component if the flag associated with this 'name' parameter is set to true. Hence if you are working with the value Object, then after
	 * you finish, call this method to set the value back. This will trigger the flag to be set to true.
	 *
	 *  @see SwingObjData#markChanged(String, Object)
	 */
	@Override
	public void set(String name, Object value) {
		markChanged(name,value);
		super.set(name, value);
	}

	@Override
	public void set(String name, String key, Object value) {
		markChanged(name+"("+key+")",value);
		super.set(name, key, value);
	}


	public void setUnchanged(String name, int index, Object value) {
		super.set(name, index, value);
	}

	public void setUnchanged(String name, Object value) {
		super.set(name, value);
	}

	public void setUnchanged(String name, String key, Object value) {
		super.set(name, key, value);
	}

	private void markChanged(String name, Object value) {
		Object cachedValue=get(name);
		if(cachedValue!=null) {
			if(!cachedValue.equals(value)) {
				isDataChanged=true;
				valueChanged.put(name,true);
			}
		}
	}

	/**
	 * Dont call this method. Call this one instead:
	 * @see SwingObjData#getValue()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Object get(String name) {
		return super.get(name);
	}

	/**
	 * Dont call this method. Call this one instead:
	 * @see SwingObjData#getValue()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Object get(String name, int index) {
		return super.get(name, index);
	}

	/**
	 * Dont call this method. Call this one instead:
	 * @see SwingObjData#getValue()
	 * @deprecated
	 */
	@Override
	public Object get(String name, String key) {
		return super.get(name, key);
	}

	public DataWrapper getValue(String key) {
		Object value = super.get(key);
		if(value instanceof DataWrapper){
			return (DataWrapper)value;
		}else{
			return new DataWrapper(value);
		}
	}

	public DataWrapper getValue(String key, int index) {
		Object value = super.get(key,index);
		if(value instanceof DataWrapper){
			return (DataWrapper)value;
		}else{
			return new DataWrapper(value);
		}
	}

	public DataWrapper getValue(String name, String key) {
		Object value = super.get(name,key);
		if(value instanceof DataWrapper){
			return (DataWrapper)value;
		}else{
			return new DataWrapper(value);
		}
	}

	public boolean isChanged(String key) {
		return valueChanged.get(key);
	}
}
