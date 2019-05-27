package CodeGeneration.Building.Statements.Assignments;

import AST.Enums.NodeEnum;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Selectors.Selector;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;
import SymbolTableImplementation.Scope;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignBuildTest {

    // Fields:
    private AssignBuild assignBuild01;
    private AssignBuild assignBuild02;

    // Constants:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        Scope scope = new Scope(DEFAULT_ID, new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1)));
        scope.setVariable(new BlockNode(DEFAULT_ID, new ComplexSymbolFactory.Location(-1, -1)));

        Statement statement = new Selector(DEFAULT_ID);
        this.assignBuild01 = new AssignBuild(DEFAULT_ID, statement);
        this.assignBuild02 = new AssignBuild(scope, DEFAULT_ID, null);
    }

    @Test
    void setType01() {
        this.assignBuild01.setType(JavaTypes.BLOCK);
        assertTrue(true);
    }

    @Test
    void setType02() {
        this.assignBuild01.setType(NodeEnum.BLOCK_TYPE);
        assertTrue(true);
    }

    @Test
    void setType03() {
        this.assignBuild01.setType(NodeEnum.OPERATION_TYPE);
        assertTrue(true);
    }

    @Test
    void setType04() {
        this.assignBuild01.setType(NodeEnum.SOURCE_TYPE);
        assertTrue(true);
    }

    @Test
    void toString01() {
        assertNotNull(this.assignBuild01.toString());
    }

    @Test
    void toString02() {
        this.assignBuild01.setType(JavaTypes.BLOCK);
        assertNotNull(this.assignBuild01.toString());
    }

    @Test
    void toString03() { // InitBuild == null
        assertThrows(NullPointerException.class, () -> {
            this.assignBuild02.toString();
        });
    }
}