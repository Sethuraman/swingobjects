package org.aesthete.swingobjects.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 15/05/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidDateException extends SwingObjectRunException{

    public InvalidDateException(String dateInput, Class<?> clz) {
        super("date.incorrect", null, ErrorSeverity.ERROR, clz, dateInput);
    }

    public InvalidDateException(String dateInput, Throwable e, Class<?> clz) {
        super("date.incorrect", e, ErrorSeverity.ERROR, clz, dateInput);
    }
}
