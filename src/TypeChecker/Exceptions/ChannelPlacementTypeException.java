package TypeChecker.Exceptions;

public class ChannelPlacementTypeException extends TypeInconsistencyException {
    public ChannelPlacementTypeException() {
        super();
    }

    public ChannelPlacementTypeException(String message) {
        super(message);
    }

    public ChannelPlacementTypeException(Throwable cause) {
        super(cause);
    }
}
