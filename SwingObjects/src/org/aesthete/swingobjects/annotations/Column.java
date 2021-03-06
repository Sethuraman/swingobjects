package org.aesthete.swingobjects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	public int index();
	public String name() default "COLUMN_NAME_USE_KEY";
	public String key() default "";
	public boolean editable() default false;
	public Class<?> type() default Class.class;
}