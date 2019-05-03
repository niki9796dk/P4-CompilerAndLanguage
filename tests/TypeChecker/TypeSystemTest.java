package TypeChecker;

import AST.Nodes.NodeClasses.NamedNodes.RootNode;
import SymbolTableImplementation.SymbolTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeSystemTest {

    @Test
    void getSupertypeOfNode() {
        TypeSystem ts = new TypeSystem(new SymbolTable());

        assertNull(ts.getSupertypeOfNode(new RootNode(), "ignored", "ignored"));
    }

}