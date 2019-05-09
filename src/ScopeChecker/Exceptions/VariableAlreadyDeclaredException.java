package ScopeChecker.Exceptions;

public class VariableAlreadyDeclaredException extends ScopeBoundsViolationException  {
    public VariableAlreadyDeclaredException() {
        super();
    }

    public VariableAlreadyDeclaredException(String message) {
        super(message);
    }
}
