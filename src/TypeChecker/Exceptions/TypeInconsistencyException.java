package TypeChecker.Exceptions;

public class TypeInconsistencyException extends RuntimeException {
    public TypeInconsistencyException() {
        super();
    }

    public TypeInconsistencyException(String message) {
        super(message);
    }

    public TypeInconsistencyException(Throwable cause) {
        super(cause);
    }
}
