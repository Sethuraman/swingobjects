package org.aesthete.swingobjects.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sethuram
 * Date: 15/05/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidDateException extends SwingObjectRunException{

    public InvalidDateException(Class<?> clz) {
        super("date.incorrect", clz);
    }

    public InvalidDateException(Throwable e, Class<?> clz) {
        super(e, clz);
    }
}
