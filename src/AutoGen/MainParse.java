package AutoGen;

import java.io.*;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.TreeWalks.*;
import SymbolTableImplementation.SymbolTableInterface;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;


public class MainParse {
    public static boolean isTest = false;

    public static void main(String args[]) throws Exception {

        if (args.length != 1) {
            //parseFile("data/input");
            parseFile("data/input");
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

        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();

        prog.walkTree(symbolTableVisitor);

        SymbolTableInterface symbolTableInterface = symbolTableVisitor.getSymbolTableInterface();

        System.out.println(symbolTableInterface.toString());

        System.out.println("\n");

        prog.walkTree(new PrintTree(System.out));

        prog.walkTree(new ScopeCheckerVisitor(symbolTableInterface));

        prog.walkTree(new TypeCheckerVisitor(symbolTableInterface));

        return true;
    }
}