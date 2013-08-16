package org.aesthete.swingobjects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 03/05/13
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    public String[] value() default "ALL";
    public String errorMsg() default "Please enter a valid date in the format dd/mm/yyyy";
}
