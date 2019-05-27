package CodeGeneration.Building.Statements.Connections;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.Building.Statements.Selectors.Selector;
import SymbolTableImplementation.Scope;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GroupChainTest {

    // Fields:
    private GroupChain groupChain;

    // Constants:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        Scope scope = new Scope(DEFAULT_ID, new BlockNode(DEFAULT_ID, new ComplexSymbolFactory.Location(-1, -1)));
        this.groupChain = new GroupChain(scope, new Selector(DEFAULT_ID), new ArrayList<>());
    }

    @Test
    void toString1() {
        assertNotNull(this.groupChain.toString());
    }
}