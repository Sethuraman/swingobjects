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

	public DataWrapper getValue(String key) {
		return new DataWrapper(get(key));
	}
}
