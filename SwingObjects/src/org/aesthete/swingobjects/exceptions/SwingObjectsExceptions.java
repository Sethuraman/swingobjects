package org.aesthete.swingobjects.exceptions;

public interface SwingObjectsExceptions {

	public abstract String formatMessage();
	
	public String getDetailedMessage(boolean isBasic);

	public abstract String getErrorCode();

	public abstract ErrorSeverity getErrorSeverity();

	public abstract String getMessage();

}