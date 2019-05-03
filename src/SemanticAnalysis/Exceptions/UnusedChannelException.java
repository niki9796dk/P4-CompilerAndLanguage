package SemanticAnalysis.Exceptions;

public class UnusedChannelException extends Exception {

    public UnusedChannelException() { super(); }

    public UnusedChannelException(String message) {
        super(message);
    }
}
