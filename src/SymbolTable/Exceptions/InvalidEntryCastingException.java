package SymbolTable.Exceptions;

public class InvalidEntryCastingException extends Exception {
    public InvalidEntryCastingException() {
    }

    public InvalidEntryCastingException(String message) {
        super(message);
    }

    public InvalidEntryCastingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntryCastingException(Throwable cause) {
        super(cause);
    }

    public InvalidEntryCastingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
