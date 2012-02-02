package org.aesthete.swingobjects.scope;

import java.util.HashMap;
import java.util.Map;

/**
 * You would use this object to store data that needs
 * to be transferred to a model from the view and then back again.
 *
 * The RequestScopeObject will be placed in a stack which we will use to collect
 * the various request scope objects. Typically, you would use a single RequestScopeObject
 * for a call from the view to the model and then back. If there are nested calls where this
 * cycle needs to be repeated then additional RequestScopeObjects will be placed on the stack
 * and popped out.
 *
 * Typically, the lifecycle of a RequestScopeObject would be as follows:
 * <ul>
 * 	<li>Create a RequestScopeObject in the actionPerformed() (for example) method of the view element.
 * This gets added as the top level element in the stack.</li>
 *  <li>GUI element values can be added to the requestMap or set as the scopeObj or both.</li>
 *  <li>These values are used in the model</li>
 *  <li>More values can be added to the object in the model</li>
 *  <li>Once the access comes back to the view (Connectors) these values can be accessed and the result shown to the user</li>
 *  <li>The stack is popped at this time and the RequestScopeObject dies</li>
 * </ul>
 *
 * @author sethu
 */
public class RequestScopeObject {

	private Object scopeObj;
	private Map<String,Object> requestMap=new HashMap<String,Object>();

	/**
	 * You will never need to directly call the constructor.
	 * Call the method {@link RequestScope#getNewRequestObj()} instead
	 *
	 */
	protected RequestScopeObject(){

	}

	public Object getScopeObj() {
		return scopeObj;
	}
	public void setScopeObj(Object scopeObj) {
		this.scopeObj = scopeObj;
	}
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}
	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public Object getObjectFromMap(String key){
		return requestMap.get(key);
	}

	public void putObjectInMap(String key,Object obj){
		requestMap.put(key,obj);
	}
}
