package TypeChecker.Exceptions;

public class ParamsSizeInconsistencyException extends TypeInconsistencyException {
    public ParamsSizeInconsistencyException() {
        super();
    }

    public ParamsSizeInconsistencyException(String message) {
        super(message);
    }

    public ParamsSizeInconsistencyException(Throwable cause) {
        super(cause);
    }
}
