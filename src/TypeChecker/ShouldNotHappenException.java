package TypeChecker;

public class ShouldNotHappenException extends RuntimeException {
    public ShouldNotHappenException() {
        super();
    }

    public ShouldNotHappenException(String message) {
        super(message);
    }
}
