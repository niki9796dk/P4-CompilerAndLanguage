package AutoGen;

import AST.*;
import java.io.*;
import java_cup.runtime.*;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java_cup.runtime.XMLElement;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.XMLElement;

class TemporaryNode extends AbstractNode {

    private String s;

    public TemporaryNode(String s) {
        this.s = s;
    }

    public String getName() { return s; }

}

public class MainParse {
    public static boolean isTest = false;

    public static void main(String args[]) throws Exception {

        parseFile(args[0]);
        //testTree();

    }

    public static boolean parseFile(String path) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        // create a buffering scanner wrapper
        ScannerBuffer lexer = new ScannerBuffer(new AutoGen.Lexer(new BufferedReader(new FileReader(path)),csf));

        // start parsing
        AutoGen.Parser p = new AutoGen.Parser(lexer,csf);
        //System.out.println("Parser runs: ");
        AutoGen.Parser.newScope();
        p.parse();

        return true;
    }

    public static void testTree() {
        AbstractNode b = new TemporaryNode("block1");
        AbstractNode bs = new TemporaryNode("block2");
        AbstractNode prog = new TemporaryNode("Program").adoptChildren(bs);
        prog.makeSibling(bs);
        System.out.println("\nAST\n");
        prog.walkTree(new PrintTree(System.out));
    }
}