package org.aesthete.swingobjects.scope;

import java.util.Stack;

/**
 * This class needs to be used for placing objects in a 'Request' scope. Its analagous
 * to the java servlet request scope.
 *
 * This method has no thread safety mechanisms for the moment. Please ensure
 * to manage threading related issues if you have multiple threads trying to use the scope
 * in your application.
 *
 *  Based on the usage of this class this class might be changed later on to support multi threading
 * @see RequestScopeObject
 * @author sethu
 *
 */
public class RequestScope {
	public static final Stack<RequestScopeObject> REQUEST_SCOPE=new Stack<RequestScopeObject>();

	private static RequestScopeObject getNewRequestObj(){
		RequestScopeObject scopeObj=new RequestScopeObject();
		REQUEST_SCOPE.add(scopeObj);
		return scopeObj;
	}

	public static RequestScopeObject endOfRequest(){
		return REQUEST_SCOPE.pop();
	}

	public static RequestScopeObject getRequestObj(){
		if(REQUEST_SCOPE.isEmpty()) {
			return getNewRequestObj();
		}else {
			return REQUEST_SCOPE.peek();
		}
	}

    /**
     * This method needs to be called if you recursing another level of Model and then Connector.
     * This will typically happen, when at the Connector you show up a screen and depending
     * on the user selection, you want to call another model and start another request response cycle.
     * In that case the current stack contents have to be pushed down one more level.
     */
    public static void pushDownOneLevel(){
        getNewRequestObj();
    }

	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromScope(String key) {
		return (T)getRequestObj().getObjectFromMap(key);
	}
 }
