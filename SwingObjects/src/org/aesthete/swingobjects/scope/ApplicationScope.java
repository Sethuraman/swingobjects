package org.aesthete.swingobjects.scope;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 09/09/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationScope {

    private static ConcurrentHashMap<String, Object> appScope=new ConcurrentHashMap<String, Object>();

    public static <$ReturnType> $ReturnType getObjectFromScope(String key){
        return ($ReturnType)appScope.get(key);
    }

    public static void putObjectInScope(String key, Object value){
        appScope.put(key, value);
    }
}
