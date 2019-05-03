package SemanticAnalysis.Exceptions;

public class MultipleEntriesToChannelException extends RuntimeException {

    public MultipleEntriesToChannelException() { super(); }

    public MultipleEntriesToChannelException(String message) {
        super(message);
    }
}
