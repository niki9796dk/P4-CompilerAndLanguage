package CodeGeneration.Building.Statements.Calls;

import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.Building.Statements.Selectors.Selector;
import SymbolTableImplementation.Scope;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CallParamsTest {

    // Fields:
    private CallParams callParams01;

    // Constants:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        Scope scope = new Scope(DEFAULT_ID, new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1)));
        scope.setVariable(new BlockNode(DEFAULT_ID, new ComplexSymbolFactory.Location(-1, -1)));
        this.callParams01 = new CallParams(scope, new Selector(DEFAULT_ID));
    }

    @Test
    void asConstructorPar() {
        assertNotNull(this.callParams01.asConstructorPar());
    }

    @Test
    void toString01() {
        assertNotNull(this.callParams01.toString());
    }
}