package org.aesthete.swingobjects.exceptions;

import org.apache.log4j.Logger;

public class SwingObjectRunException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SwingObjectRunException(String message, Throwable e, ErrorSeverity errorSeverity,Class<?> clz){
		super(message,e);
		Logger logger=Logger.getLogger(clz);
		logger.error(message, e);
	}
}
