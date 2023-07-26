package io.gnt.bmt.commons.exceptions;


/**
 * General/Overall System Security Exception
 */
public class SystemSecurityException extends SecurityException {

    public SystemSecurityException() {
        super();
    }


    public SystemSecurityException(final String message) {
        super(message);
    }


    public SystemSecurityException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public SystemSecurityException(final Throwable cause) {
        super(cause);
    }

}