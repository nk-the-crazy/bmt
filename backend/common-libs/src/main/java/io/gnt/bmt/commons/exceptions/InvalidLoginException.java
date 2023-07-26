package io.gnt.bmt.commons.exceptions;


public class InvalidLoginException extends SystemSecurityException {


    public InvalidLoginException() {
        super();
    }


    public InvalidLoginException(final String message) {
        super(message);
    }


    public InvalidLoginException(final String message, final Throwable cause) {
        super(message, cause);
    }


    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

}