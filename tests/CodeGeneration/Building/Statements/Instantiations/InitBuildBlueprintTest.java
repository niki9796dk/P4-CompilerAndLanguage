package CodeGeneration.Building.Statements.Instantiations;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.Building.Statements.Calls.CallParams;
import SymbolTableImplementation.Scope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitBuildBlueprintTest {

    // Fields:
    private InitBuildBlueprint initBuildBlueprint;

    // Constant:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        Scope scope = new Scope(DEFAULT_ID, new BlockNode(DEFAULT_ID));
        this.initBuildBlueprint = new InitBuildBlueprint(DEFAULT_ID, new CallParams(scope));
    }

    @Test
    void toString1() {
        assertNotNull(this.initBuildBlueprint.toString());
    }
}