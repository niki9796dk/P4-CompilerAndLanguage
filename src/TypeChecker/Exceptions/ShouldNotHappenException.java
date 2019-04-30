package TypeChecker.Exceptions;

public class ShouldNotHappenException extends RuntimeException {
    public ShouldNotHappenException() {
        super();
    }

    public ShouldNotHappenException(String message) {
        super("SHOULT NOT HAPPEN HERE!! - " + message);
    }
}
