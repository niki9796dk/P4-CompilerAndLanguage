package ScopeChecker.Exceptions;

public class NoSuchChannelDeclaredException extends ScopeBoundsViolationException {

    public NoSuchChannelDeclaredException() {
        super();
    }

    public NoSuchChannelDeclaredException(String message) {
        super(message);
    }
}