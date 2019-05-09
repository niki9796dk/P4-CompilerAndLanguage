package SemanticAnalysis;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode;
import AST.TreeWalks.NumberTree;
import AST.TreeWalks.SymbolTableVisitor;
import AutoGen.MainParse;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeSystem;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowCheckerTest {

    // Fields
    private String latestBlockScope;
    private String channelScope;
    private FlowChecker flowChecker;
    private SymbolTableInterface symbolTableInterface;

    // Constants
    private static final String PATH = "tests/SemanticAnalysis/FlowUnit_01";
    private static final String PATH_NEGATIVE = "tests/SemanticAnalysis/FlowUnit_Negative";

    @BeforeEach
    void beforeEach() throws Exception {
        beforeEachContent(PATH);
    }

    private void beforeEachContent(String path) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        // create a buffering scanner wrapper
        ScannerBuffer lexer = new ScannerBuffer(new AutoGen.Lexer(new BufferedReader(new FileReader(path)),csf));

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

        flowChecker = new FlowChecker(symbolTableInterface);
    }

    @Test
    void check() {
        flowChecker.getConnected().add("IN_input");
        flowChecker.getConnected().add("OUT_output");
        flowChecker.check(latestBlockScope, channelScope);
        assertTrue(true); // No exceptions
    }

    @Test
    void evaluate() throws Exception{
        beforeEachContent(PATH_NEGATIVE);
        flowChecker.getConnected().add("IN_input");
        flowChecker.getConnected().add("OUT_output");
        flowChecker.getConnected().add("IN_input");
        flowChecker.getConnected().add("OUT_output");
        assertThrows(SemanticProblemException.class, () -> flowChecker.check(latestBlockScope, channelScope));
    }

    @Test
    void getConnected() {
        flowChecker.getConnected().add("IN_input");
        flowChecker.getConnected().add("OUT_output");
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