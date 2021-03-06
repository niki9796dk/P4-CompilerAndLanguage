package TypeChecker;

import AST.Enums.NodeEnum;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.RootNode;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import java_cup.runtime.ComplexSymbolFactory;
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
        this.blockNode = new BlockNode(BLOCK_ID_01, new ComplexSymbolFactory.Location(-1, -1));
        this.blockNode.setNumber(1);
        this.symbolTableInterface.openBlockScope(blockNode);
        this.procedureNode = new ProcedureNode(PROC_ID_01, new ComplexSymbolFactory.Location(-1, -1));
        this.procedureNode.setNumber(2);
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
        SizeTypeNode sizeTypeNode = new SizeTypeNode("id", new ComplexSymbolFactory.Location(-1, -1));
        assertNull(this.typeSystem.getSubTypeOfNode(sizeTypeNode, "", ""));
    }

    @Test
    void getSubTypeOfNode03() {
        MyInChannelNode myInChannelNode = new MyInChannelNode("id", new ComplexSymbolFactory.Location(-1, -1));
        assertEquals("CHANNEL_IN_MY", this.typeSystem.getSubTypeOfNode(myInChannelNode, "", ""));
    }

    @Test
    void getSubTypeOfNode04() {
        SizeNode sizeNode = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));
        assertEquals("SIZE_TYPE", this.typeSystem.getSubTypeOfNode(sizeNode, "", ""));
    }

    @Test
    void getSubTypeOfNode05() {
        DrawNode drawNode = new DrawNode("id", new ComplexSymbolFactory.Location(-1, -1));
        assertEquals("id", this.typeSystem.getSubTypeOfNode(drawNode, "", ""));
    }

    @Test
    void getSuperTypeOfNode01() {
        RootNode rootNode = new RootNode();
        assertNull(typeSystem.getSuperTypeOfNode(rootNode, "", ""));
    }

    @Test
    void getSuperTypeOfNode02() {
        MyInChannelNode myInChannelNode = new MyInChannelNode("id", new ComplexSymbolFactory.Location(-1, -1));
        assertEquals(NodeEnum.CHANNEL_IN_MY, this.typeSystem.getSuperTypeOfNode(myInChannelNode, "", ""));
    }
}