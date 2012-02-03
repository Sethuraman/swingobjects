package org.aesthete.swingobjects.util;

import java.lang.reflect.Field;

public class ReflectionUtils {
	public static void iterateOverFields(Class<?> clz,Class<?> goUpto,FieldCallback callback) {
		for(Class<?> c=clz; c!=null && (goUpto==null || goUpto.isAssignableFrom(c)) ; c=c.getSuperclass()) {
			for(Field f : c.getDeclaredFields()) {
				f.setAccessible(true);
				if(callback.filter(f)) {
					callback.consume(f);
				}
			}
		}
	}
}

