package CodeGeneration.Building.Statements.Calls;

import CodeGeneration.Building.Statement;
import SymbolTableImplementation.BlockScope;

public class ProcedureCall implements Statement{
    private String procedureId;
    private CallParams callParams;

    public ProcedureCall(String procedureId) {
        if (procedureId == null) {
            throw new NullPointerException("The prod id was null ??");
        }

        this.procedureId = procedureId;
        this.callParams = new CallParams();
    }

    public ProcedureCall(String procedureId, CallParams callParams) {
        this(procedureId);
        this.callParams = callParams;
    }

    public ProcedureCall(String procedureId, Statement... paramStatements) {
        this(procedureId, new CallParams(paramStatements));
    }

    @Override
    public String toString() {
        return "this." + BlockScope.PROCEDURE_PREFIX + procedureId + callParams;
    }
}
