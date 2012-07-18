package org.aesthete.swingobjects.scope;

import java.util.ArrayList;
import java.util.LinkedList;
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
	public static final LinkedList<RequestScopeObject> REQUEST_SCOPE=new LinkedList<RequestScopeObject>();

	private static RequestScopeObject getNewRequestObj(){
		RequestScopeObject scopeObj=new RequestScopeObject();
		REQUEST_SCOPE.add(scopeObj);
		return scopeObj;
	}

	public static void endOfRequest(RequestScopeObject scopeObject){
		REQUEST_SCOPE.remove(scopeObject);
	}

	public static RequestScopeObject getRequestObj(){
		if(REQUEST_SCOPE.isEmpty()) {
			return getNewRequestObj();
		}else {
			return REQUEST_SCOPE.getLast();
		}
	}

    public static RequestScopeObject startNewIteration(){
        return getNewRequestObj();
    }


	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromScope(String key) {
		return (T)getRequestObj().getObjectFromMap(key);
	}
 }
