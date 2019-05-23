package CodeGeneration.Building.Statements.Calls;

import CodeGeneration.Building.Statement;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.Scope;

public class ProcedureCall implements Statement{

    // Fields:
    private String procedureId;
    private CallParams callParams;

    // Constructors:
    public ProcedureCall(Scope scope, String procedureId) {
        if (procedureId == null) {
            throw new NullPointerException("The prod id was null ??");
        }

        this.procedureId = procedureId;
        this.callParams = new CallParams(scope);
    }

    public ProcedureCall(Scope scope, String procedureId, CallParams callParams) {
        this(scope, procedureId);
        this.callParams = callParams;
    }

    public ProcedureCall(Scope scope, String procedureId, Statement... paramStatements) {
        this(scope, procedureId, new CallParams(scope, paramStatements));
    }

    @Override
    public String toString() {
        return "this." + BlockScope.PROCEDURE_PREFIX + procedureId + callParams;
    }
}
