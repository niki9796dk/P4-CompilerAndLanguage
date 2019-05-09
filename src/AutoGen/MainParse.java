package AutoGen;

import java.io.*;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.TreeWalks.*;
import AST.Visitor;
import SymbolTableImplementation.SymbolTable;
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

        SymbolTable symbolTable = (SymbolTable) symbolTableVisitor.getSymbolTableInterface();

        System.out.println(symbolTable.toString());

        System.out.println("\n");

        prog.walkTree(new PrintTree(System.out));

        prog.walkTree(new PreRecursiveVisitor(symbolTable));

        //prog.walkTree(new TypeCheckerVisitor(symbolTable));

        new RecursiveVisitor(symbolTable, new TypeCheckerVisitor(symbolTable)).startRecursiveWalk();

        /*
        prog.walkTree(new ScopeCheckerVisitor(symbolTable));

        prog.walkTree(new TypeCheckerVisitor(symbolTable));

        prog.walkTree(new SemanticAnalysisVisitor(symbolTable));
        */

        return true;
    }
}