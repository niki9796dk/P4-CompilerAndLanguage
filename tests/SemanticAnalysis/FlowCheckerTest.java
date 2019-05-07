package SemanticAnalysis;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode;
import AST.TreeWalks.NumberTree;
import AST.TreeWalks.SymbolTableVisitor;
import AutoGen.MainParse;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeSystem;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowCheckerTest {

    // Fields
    private static String latestBlockScope;
    private static String channelScope;
    private static FlowChecker flowChecker;
    private static SymbolTableInterface symbolTableInterface;

    // Constants
    private static final String PATH = "C:\\Users\\mikel\\Desktop\\Git projects\\P4\\tests\\ExpectTrue\\AnnLayer-Shorthand_true.anol";

    @BeforeAll
    static void beforeAll() throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        // create a buffering scanner wrapper
        ScannerBuffer lexer = new ScannerBuffer(new AutoGen.Lexer(new BufferedReader(new FileReader(PATH)),csf));

        // start parsing
        AutoGen.Parser p = new AutoGen.Parser(lexer,csf);
        //System.out.println("Parser runs: ");
        AutoGen.Parser.newScope();
        Symbol symbol = p.parse();

        AbstractNode prog = p.getRootNode();

        prog.walkTree(new NumberTree());

        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();

        prog.walkTree(symbolTableVisitor);

        symbolTableInterface = symbolTableVisitor.getSymbolTableInterface();

        latestBlockScope = symbolTableInterface.getLatestBlockScope().getId();
        channelScope = symbolTableInterface.getLatestBlockScope().getChannelDeclarationScope().getId();
    }

    @Test
    void check() {
        System.out.println(flowChecker.getConnected());
        flowChecker.check(latestBlockScope, channelScope);
        assertTrue(true); // No exceptions
    }

    @Test
    void getConnected() {
        flowChecker.check(latestBlockScope, channelScope);
        List<String> expected = new ArrayList<>();
        expected.add("IN_input");
        expected.add("OUT_output");
        assertArrayEquals(expected.toArray(), flowChecker.getConnected().toArray());
    }

    @Test
    void channelPrefix01() {
        AbstractNode node = symbolTableInterface.getLatestBlockScope().getChannelDeclarationScope().getNode().getChild();

        assertEquals("OUT_", flowChecker.channelPrefix(node, latestBlockScope, channelScope));
    }

    @Test
    void channelPrefix02() {
        AbstractNode node = symbolTableInterface.getLatestBlockScope().getChannelDeclarationScope().getNode().getChild().getSib();

        String latestBlockScope = symbolTableInterface.getLatestBlockScope().getId();
        String channelScope = symbolTableInterface.getLatestBlockScope().getChannelDeclarationScope().getId();
        assertEquals("IN_", flowChecker.channelPrefix(node, latestBlockScope, channelScope));
    }
}