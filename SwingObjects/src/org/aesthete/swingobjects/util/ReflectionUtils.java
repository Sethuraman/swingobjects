package org.aesthete.swingobjects.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
	public static void iterateOverFields(Class<?> clz,Class<?> goUpto,ReflectionCallback<Field> callback) {
		for(Class<?> c=clz; c!=null && (goUpto==null || goUpto.isAssignableFrom(c)) ; c=c.getSuperclass()) {
			for(Field f : c.getDeclaredFields()) {
				f.setAccessible(true);
				if(callback.filter(f)) {
					callback.consume(f);
				}
			}
		}
	}

    public static void iterateOverMethods(Class<?> clz,Class<?> goUpto,ReflectionCallback<Method> callback) {
        for(Class<?> c=clz; c!=null && (goUpto==null || goUpto.isAssignableFrom(c)) ; c=c.getSuperclass()) {
            for(Method m : c.getDeclaredMethods()) {
                m.setAccessible(true);
                if(callback.filter(m)) {
                    callback.consume(m);
                }
            }
        }
    }

}

