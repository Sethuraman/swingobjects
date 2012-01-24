/*
 * and open the template in the editor.
 */

package org.aesthete.swingobjects.exceptions;


/**
 *
 * @author U0117768
 */
public enum ErrorSeverity {
    SEVERE(4),ERROR(3),WARNING(2),INFO(1);
    
    private int cardinal;
    private ErrorSeverity(int cardinal) {
    	this.cardinal=cardinal;
    }
    public boolean isGreatherThan(ErrorSeverity input) {
    	return this.cardinal>input.cardinal;
    }
}
