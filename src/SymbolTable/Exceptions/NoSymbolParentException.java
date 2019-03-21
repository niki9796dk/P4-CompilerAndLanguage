package SymbolTable.Exceptions;

public class NoSymbolParentException extends Exception {
    public NoSymbolParentException() {
    }

    public NoSymbolParentException(String message) {
        super(message);
    }

    public NoSymbolParentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSymbolParentException(Throwable cause) {
        super(cause);
    }

    public NoSymbolParentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
