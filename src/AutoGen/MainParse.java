package AutoGen;

import java.io.*;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.TreeWalks.*;
import CompilerExceptions.CompilerException;
import Enums.AnsiColor;
import SymbolTableImplementation.SymbolTable;
import TypeChecker.TypeSystem;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;


public class MainParse {

    public static void main(String args[]) throws Exception {

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            System.out.println("\n\n");
            System.out.println(AnsiColor.RED);
            System.out.println("###########################\n");

            if (e instanceof CompilerException) {
                AbstractNode errorNode = ((CompilerException) e).getErrorNode();

                System.out.println("Error happend at " + errorNode.getLineNumber() + ":" + errorNode.getColumn() + ".\n Cause - " + e.getMessage());
            } else {
                System.out.println("Unknown error occurred:\n " + e.getMessage());
            }

            System.out.println("\n###########################");
            System.out.println(AnsiColor.RESET);

            System.exit(0);
        });

        if (args.length != 1) {
            parseFile("data" + File.separator + "input");
        } else {

            parseFile(args[0]);
        }
    }

    public static boolean parseFile(String path) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        // create a buffering scanner wrapper
        ScannerBuffer lexer = new ScannerBuffer(new AutoGen.Lexer(new BufferedReader(new FileReader(path)),csf));

        String nameOfFile = path.subSequence(path.lastIndexOf(File.separator)+1, path.length()).toString();

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

        // Create/initialize symbol table
        prog.walkTree(new PreRecursiveVisitor(symbolTable));

        // Do scope checking
        new RecursiveVisitor(symbolTable, new ScopeCheckerVisitor(symbolTable)).startRecursiveWalk();

        // Do type checking
        new RecursiveVisitor(symbolTable, new TypeCheckerVisitor(symbolTable)).startRecursiveWalk();

        // Do chain checking
        new RecursiveVisitor(symbolTable, new ChainCheckerVisitor(symbolTable)).startRecursiveWalk();

        // Do flow checking
        new RecursiveVisitor(symbolTable, new FlowCheckVisitor(symbolTable)).startRecursiveWalk();

        // Do code generation
        prog.walkTree(new CodeGenerationVisitor(symbolTable, nameOfFile));

        return true;
    }
}