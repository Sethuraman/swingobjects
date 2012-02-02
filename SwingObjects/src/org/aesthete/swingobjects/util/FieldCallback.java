package org.aesthete.swingobjects.util;

import java.lang.reflect.Field;

public interface FieldCallback {
	public boolean filter(Field field);
	public void consume(Field field);
}
