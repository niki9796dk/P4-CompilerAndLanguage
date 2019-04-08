package SymbolTableImplementation;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import Enums.AnsiColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class VariableEntryTest {

    NamedIdNode n;
    VariableEntry v;

    @BeforeEach
    void BeforeEach() {
        n = new BuildNode("id");
        v = new VariableEntry(n);
        v.getSubType(1);
    }

    @Test
    void getId() {
        assertEquals("id", v.getId());
    }


    @Test
    void getSuperType() {
        assertSame(v.getSuperType(), NodeEnum.BUILD);
    }

    @Test
    void subTypes() {
        NamedIdNode a = new BuildNode("a");
        NamedIdNode b = new BuildNode("b");
        a.setNumber(10);
        b.setNumber(30);
        v.setSubType(a);
        v.setSubType(b);

        assertSame(null, v.getSubType(1));
        assertSame(a, v.getSubType(10));
        assertSame(a, v.getSubType(11));
        assertSame(a, v.getSubType(29));
        assertSame(b, v.getSubType(30));
        assertSame(b, v.getSubType(31));
    }

    @Test
    void getNode() {
        assertSame(v.getNode(), n);
    }

    @Test
    void toString000() {
        assertEquals(v.toString(),
                "\t\t\tEntry: " + AnsiColor.YELLOW + v.getId() + AnsiColor.RESET
                        + " | SuperType: " + AnsiColor.PURPLE + v.getSuperType() + AnsiColor.RESET
                        + " | Node: " + AnsiColor.PURPLE + v.getNode() + AnsiColor.RESET
                        + "\n" + "\t\t\t\t\t"
                        + "Subtype: "
                        + AnsiColor.RED + "NONE" + AnsiColor.RESET
                        + " | Node: "
                        + AnsiColor.RED + "NONE" + AnsiColor.RESET
                        + "\n");
        v.setSubType(new BuildNode("buildNode"));
        assertEquals(AnsiColor.removeColor(v.toString()),
                "\t\t\tEntry: id | SuperType: BUILD | Node: [-1] Build id\n" +
                        "\t\t\t\t\tSubtype: buildNode | Node: [-1] Build buildNode\n"
        );
    }
}