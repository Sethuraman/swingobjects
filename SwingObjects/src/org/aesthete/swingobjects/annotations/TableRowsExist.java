package org.aesthete.swingobjects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 13/05/13
 * Time: 9:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableRowsExist {
    public String[] value() default "ALL";
    public String errorMsg() default "Please enter some data to proceed";
}
