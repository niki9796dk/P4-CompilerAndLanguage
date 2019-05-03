package SemanticAnalysis.Exceptions;

public class UnusedChannelException extends RuntimeException {

    public UnusedChannelException() { super(); }

    public UnusedChannelException(String message) {
        super(message);
    }
}
