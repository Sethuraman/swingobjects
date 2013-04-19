package org.aesthete.swingobjects.util;

import java.lang.reflect.Field;

public interface ReflectionCallback<T> {
	public boolean filter(T entity) throws IllegalAccessException;
	public void consume(T entity);
}
