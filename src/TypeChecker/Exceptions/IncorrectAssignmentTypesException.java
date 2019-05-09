package TypeChecker.Exceptions;

public class IncorrectAssignmentTypesException extends TypeInconsistencyException {
    public IncorrectAssignmentTypesException() {
        super();
    }

    public IncorrectAssignmentTypesException(String message) {
        super(message);
    }

    public IncorrectAssignmentTypesException(Throwable cause) {
        super(cause);
    }
}
