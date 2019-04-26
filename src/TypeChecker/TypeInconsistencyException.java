package TypeChecker;

public class TypeInconsistencyException extends RuntimeException {
    public TypeInconsistencyException() {
        super();
    }

    public TypeInconsistencyException(String message) {
        super(message);
    }
}
