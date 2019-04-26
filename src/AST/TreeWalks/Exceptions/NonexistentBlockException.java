package AST.TreeWalks.Exceptions;

public class NonexistentBlockException extends ScopeBoundsViolationException{
    private static final String DEFAULT_ERROR_MESSAGE = "Attempted Draw/Build of nonexistent block";

    public NonexistentBlockException() {
        super(DEFAULT_ERROR_MESSAGE + ".");
    }

    public NonexistentBlockException(String message) {
        super( DEFAULT_ERROR_MESSAGE + ": " + message);
    }
}
