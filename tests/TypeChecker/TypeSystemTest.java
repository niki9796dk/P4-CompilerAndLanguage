package TypeChecker;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.ProcedureNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SizeTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.RootNode;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeSystemTest {

    // Field:
    private SymbolTableInterface symbolTableInterface;
    private TypeSystem typeSystem;
    private BlockNode blockNode;
    private ProcedureNode procedureNode;

    // Constants:
    private static final String BLOCK_ID_01 = "block_01";
    private static final String PROC_ID_01 = "proc_01";

    @BeforeEach
    void beforeEach() {
        this.symbolTableInterface = new SymbolTable();
        this.blockNode = new BlockNode(BLOCK_ID_01);
        this.symbolTableInterface.openBlockScope(blockNode);
        this.procedureNode = new ProcedureNode(PROC_ID_01);
        this.symbolTableInterface.openSubScope(procedureNode);
        this.typeSystem = new TypeSystem(symbolTableInterface);
    }

    @Test
    void getSymbolTable() {
        assertTrue(this.typeSystem.getSymbolTable() instanceof SymbolTable);
    }

    @Test
    void getBlock() {
        assertEquals(this.blockNode, this.typeSystem.getBlock(BLOCK_ID_01));
    }

    @Test
    void getProcedure() {
        assertEquals(this.procedureNode, this.typeSystem.getProcedure(BLOCK_ID_01, PROC_ID_01));
    }

    @Test
    void getSubTypeOfNode01() {
        RootNode rootNode = new RootNode();
        assertNull(this.typeSystem.getSubTypeOfNode(rootNode, "", ""));
    }

    @Test
    void getSubTypeOfNode02() {
        SizeTypeNode sizeTypeNode = new SizeTypeNode("id");
        assertNull(this.typeSystem.getSubTypeOfNode(sizeTypeNode, "", ""));
    }

    @Test
    void getSubTypeOfNode03() {
        MyInChannelNode myInChannelNode = new MyInChannelNode("id");
        assertNull(this.typeSystem.getSubTypeOfNode(myInChannelNode, "", ""));
    }
}