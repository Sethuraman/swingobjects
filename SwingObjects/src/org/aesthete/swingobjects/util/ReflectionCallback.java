package org.aesthete.swingobjects.util;

import java.lang.reflect.Field;

public interface ReflectionCallback<T> {
	public boolean filter(T entity);
	public void consume(T entity);
}
