package org.aesthete.swingobjects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 08/09/13
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmBeforeDoing {
    public String[] actionCommand() default "ALL";
    public String message();
}
