/*
 * and open the template in the editor.
 */

package org.aesthete.swingobjects.exceptions;

import java.util.logging.Level;

/**
 * 
 * @author U0117768
 */
public enum ErrorSeverity {
	SEVERE(4), ERROR(3), WARNING(2), INFO(1);

	private int cardinal;

	private ErrorSeverity(int cardinal) {
		this.cardinal = cardinal;
	}

	public boolean isGreatherThan(ErrorSeverity input) {
		return this.cardinal > input.cardinal;
	}

	public Level getJavaLoggingLevel() {
		switch (this) {
		case SEVERE:
			return Level.SEVERE;
		case ERROR:
			return Level.WARNING;
		case WARNING:
			return Level.WARNING;
		case INFO:
			return Level.WARNING;
		}
		return null;
	}
}
