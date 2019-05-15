package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableEntryOrDefaultTest {

    // Fields:
    private VariableEntryOrDefault variableEntryOrDefault;
    private DummyNode dummyNode;

    @BeforeEach
    void beforeEach() {
        this.dummyNode = new DummyNode();
        this.variableEntryOrDefault = new VariableEntryOrDefault(this.dummyNode);
    }

    @Test
    void setDefaultSubtype() {
        ParamsNode paramsNode = new ParamsNode();
        assertThrows(IllegalArgumentException.class, () -> {
            this.variableEntryOrDefault.setDefaultSubtype(paramsNode);
        });
    }

    @Test
    void toString01() {
        assertNotNull(this.variableEntryOrDefault.toString());
    }
}