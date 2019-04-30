package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Visitor;

import java.io.PrintStream;


/**
 * A visitor pattern which prints the contents of the tree.
 */
public class PrintTree implements Visitor {
    PrintStream ps;

    /**
     * Construct a printTree which prints in a specific stream.
     * @param ps The stream to print to.
     */
    public PrintTree(PrintStream ps) {
        this.ps = ps;
    }

    /**
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param n The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode n) {
        String tab = "";
        for (int i = 1; i <= printLevel; ++i) tab += "  ";
        ps.println(tab + n.toString());
    }


    /**
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param a          The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode a) {
    }
}
