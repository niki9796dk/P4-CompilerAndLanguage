package AutoGen;

import java.io.*;

import AST.Nodes.AbstractNodes.AbstractNode;
import AST.Nodes.SpecialNodes.TemporaryNode;
import AST.TreeWalks.NumberTree;
import AST.TreeWalks.PrintTree;
import AST.TreeWalks.SymbolTableVisitor;
import SymbolTable.New.SymbolTableInterface;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;


public class MainParse {
    public static boolean isTest = false;

    public static void main(String args[]) throws Exception {

        if (args.length != 1) {
            //parseFile("data/input");
            parseFile("tests/ProductionRules/ExpectTrue/Random_True");
        } else {

            parseFile(args[0]);
        }

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
        Symbol symbol = p.parse();

        System.out.println("##################################");
        AbstractNode prog = p.getRootNode();
        prog.walkTree(new NumberTree());

        SymbolTableVisitor visitor = new SymbolTableVisitor();

        prog.walkTree(visitor);

        SymbolTableInterface symbolTableInterface = visitor.symbolTableInterface;

        System.out.println(symbolTableInterface.toString());

        //prog.walkTree(new PrintTree(System.out));

        return true;
    }

    public static void testTree() {
        AbstractNode b = new TemporaryNode("block1");
        AbstractNode bs = new TemporaryNode("block2");
        AbstractNode prog = new TemporaryNode("Program").adoptChildren(bs);
        prog.makeSibling(bs);

        System.out.println("\nAST\n");
        prog.walkTree(new NumberTree());
        prog.walkTree(new PrintTree(System.out));
    }
}