package AST.TreeWalks.Exceptions;

public class RecursiveBlockException extends RuntimeException {

    public RecursiveBlockException() {
        super();
    }

    public RecursiveBlockException(String message) {
        super(message);
    }
}
