package SymbolTableImplementation.Exceptions;

public class NoSuchSymbolException extends NullPointerException {
    public NoSuchSymbolException() {
    }

    public NoSuchSymbolException(String s) {
        super(s);
    }
}
