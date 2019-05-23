package CodeGeneration.Building.Statements.Calls;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.Building.Statements.Selectors.Selector;
import SymbolTableImplementation.Scope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcedureCallTest {

    // Fields:
    private ProcedureCall procedureCall01;
    private Scope scope;

    // Constants:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        this.scope = new Scope(DEFAULT_ID, new BlockNode(DEFAULT_ID));
        this.procedureCall01 = new ProcedureCall(this.scope, DEFAULT_ID);
        ProcedureCall procedureCall02 = new ProcedureCall(this.scope, DEFAULT_ID, new CallParams(this.scope, new Selector(DEFAULT_ID)));
        ProcedureCall procedureCall03 = new ProcedureCall(this.scope, DEFAULT_ID, new Selector(DEFAULT_ID));
    }

    @Test
    void nullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            ProcedureCall procedureCallNull = new ProcedureCall(new Scope(DEFAULT_ID, new BlockNode(DEFAULT_ID)), null);
        });
    }

    @Test
    void toString1() {
        assertNotNull(this.procedureCall01.toString());
    }
}