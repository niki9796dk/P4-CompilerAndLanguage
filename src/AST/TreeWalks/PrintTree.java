package AST.TreeWalks;
import AST.Nodes.AbstractNodes.AbstractNode;
import AST.Visitor;

import java.io.PrintStream;

public class PrintTree implements Visitor {
    PrintStream ps;
    public PrintTree(PrintStream ps) {
       this.ps = ps;
   }
    public void pre(int level,  AbstractNode n) {
       String tab = "";
       for (int i=1; i <= level; ++i) tab += "  ";
       ps.println(tab + n.toString());
    }
    public void post(int i, AbstractNode a) {}
}
