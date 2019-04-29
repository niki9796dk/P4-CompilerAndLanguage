package TypeChecker;

public class ParamsInconsistencyException extends TypeInconsistencyException {
    public ParamsInconsistencyException() {
        super();
    }

    public ParamsInconsistencyException(String message) {
        super(message);
    }
}
