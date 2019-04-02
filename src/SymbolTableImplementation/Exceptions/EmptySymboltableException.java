package SymbolTableImplementation.Exceptions;

import SymbolTableImplementation.SymbolTable;

public class EmptySymboltableException extends RuntimeException {

    public EmptySymboltableException() {
    }

    public EmptySymboltableException(String message) {
        super(message);
    }

    public EmptySymboltableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptySymboltableException(Throwable cause) {
        super(cause);
    }

    public EmptySymboltableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
