package AST.Nodes.AbstractNodes.Nodes;

import AST.Nodes.AbstractNodes.Node;
import AST.Visitor;

import java.util.ArrayList;
import java.util.List;

/** All AST nodes are subclasses of this node.  This node knows how to
  * link itself with other siblings and adopt children.
  * Each node gets a node number to help identify it distinctly in an AST.
  */
public abstract class AbstractNode implements Node {
   private static int nodeNums = 0;
   private int nodeNum;
   private AbstractNode mysib;
   private AbstractNode parent;
   private AbstractNode child;
   private AbstractNode firstSib;

   public AbstractNode() {
      parent   = null;
      mysib    = null;
      firstSib = this;
      child    = null;
      nodeNum = ++nodeNums;
   }


   public AbstractNode makeSibling(AbstractNode sib) {
      if (sib == null) throw new Error("Call to makeSibling supplied null-valued parameter");

      /* appendAt.mysib = first sibling of this*/
      AbstractNode appendAt = this;
      while (appendAt.mysib != null) appendAt = appendAt.mysib;
      appendAt.mysib = sib.firstSib;


      /* ans is set to be the input's (first sibling). Then the (first sibling) of ans is set to the (first sibling) of this' (first sibling).
       * ans is iterated until it no longer has a sibling. At each iteration, the input's siblings (first sibling) is set to the this' (first sibling) */
      AbstractNode ans = sib.firstSib;
      ans.firstSib = appendAt.firstSib;
      while (ans.mysib != null) {
         ans = ans.mysib;
         ans.firstSib = appendAt.firstSib;
      }

      /* return the last sibling of the input */
      return(ans);
   }

   /** Adopt the supplied node and all of its siblings under this node */
   public AbstractNode adoptChildren(AbstractNode n) {
      if (n != null) {
         if (this.child == null) this.child = n.firstSib;
         else this.child.makeSibling(n);
      }
      for (AbstractNode c = this.child; c != null; c = c.mysib) c.parent = this;
      return this;
   }

   public AbstractNode adoptChildren(AbstractNode ... nodes) {
      for(AbstractNode n : nodes) {
         adoptChildren(n);
      }

      return this;
   }

   // Insert child in the front of the child list
   public AbstractNode adoptAsFirstChild(AbstractNode node) {
      if (this.child == null) return this.adoptChildren(node);

      AbstractNode myChildren = this.child;
      this.child = node;

      return this.adoptChildren(myChildren);
   }

   public AbstractNode orphan() {
      mysib = parent = null;
      firstSib = this;
      return this;
   }

   private List<AbstractNode> orphanChildList(List<AbstractNode> children) {
      for (AbstractNode child : children) {
         child.orphan();
      }

      return children;
   }

   public AbstractNode abandonChildren() {
      child = null;
      return this;
   }

   private List<AbstractNode> getChildrenAsList() {
      if (this.child == null) return null;

      List<AbstractNode> children = new ArrayList<>();

      for (AbstractNode currentChild = this.child; currentChild != null; currentChild = currentChild.mysib) {
         children.add(currentChild);
      }

      return children;
   }

   private void setParent(AbstractNode p) {
      this.parent = p;
   }

   public AbstractNode getParent() {
      return(parent);
   }

   public AbstractNode getSib() {
      return(mysib);
   }

   public AbstractNode getChild() {
      return(child);
   }

   public AbstractNode getFirstSib() {
      return(firstSib);
   }

   public String getName() { return ""; }
   
   public String toString() {
      return("" + getName());
   }

   public int getNodeNum() { return nodeNum; }

   private void internWalk(int level, Visitor v) {
      v.pre(level, this);

      for (AbstractNode c = child; c != null; c=c.mysib)
         c.internWalk(level+1, v);
      v.post(level, this);
   }

   public void walkTree(Visitor v) {
      internWalk(0, v);
   }

   public AbstractNode[] getNodes() {
      return null;
   }

}
