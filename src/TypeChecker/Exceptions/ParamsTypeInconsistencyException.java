package TypeChecker.Exceptions;

public class ParamsTypeInconsistencyException extends TypeInconsistencyException {
    public ParamsTypeInconsistencyException() {
        super();
    }

    public ParamsTypeInconsistencyException(String message) {
        super(message);
    }

    public ParamsTypeInconsistencyException(Throwable cause) {
        super(cause);
    }
}
