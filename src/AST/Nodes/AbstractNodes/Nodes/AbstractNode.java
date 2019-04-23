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


   /** Join the end of this sibling's list with the supplied sibling's list
    * @param sib The sibling to add to the end of the list.
    * @return The last sibling of the input parameter "sib"
    */
   @Override
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

   /**
    * Adopt the supplied node and all of its siblings under this nod
    * @param n The node to adopt.
    * @return  a reference to this object.
    */
   @Override
   public AbstractNode adoptChildren(AbstractNode n) {

      if (n != null) {
         if (this.child == null) this.child = n.firstSib;
         else this.child.makeSibling(n);
      }
      for (AbstractNode c = this.child; c != null; c = c.mysib) c.parent = this;
      return this;
   }

   /**
    * Adopt the supplied nodes and all of their siblings under this nod
    * @param nodes An array of nodes to adopt.
    * @return  a reference to this object.
    */
   public AbstractNode adoptChildren(AbstractNode ... nodes) {
      for(AbstractNode n : nodes) {
         adoptChildren(n);
      }

      return this;
   }

   /**
    * Insert child in the front of the child list
    * @param node The child to add.
    * @return  a reference to this object.
    */
   @Override
   public AbstractNode adoptAsFirstChild(AbstractNode node) {
      if (this.child == null) return this.adoptChildren(node);

      AbstractNode myChildren = this.child;
      this.child = node;

      return this.adoptChildren(myChildren);
   }

   /**
    * Turn the child into an orphan.
    * @return  a reference to this object.
    */
   @Override
   public AbstractNode orphan() {
      mysib = parent = null;
      firstSib = this;
      return this;
   }

    /**
     * Nullify the reference to the child node.
     * @return  a reference to this object.
     */
   @Override
   public AbstractNode abandonChildren() {
      child = null;
      return this;
   }

   /* private & unused
   private List<AbstractNode> getChildrenAsList() {
      if (this.child == null) return null;

      List<AbstractNode> children = new ArrayList<>();

      for (AbstractNode currentChild = this.child; currentChild != null; currentChild = currentChild.mysib) {
         children.add(currentChild);
      }

      return children;
   }
   */

    /**
     * Set the parent of the node.
     * @param p The node to set as a parent.
     */
   private void setParent(AbstractNode p) {
      this.parent = p;
   }

    /**
     * Get the parent of the node.
     * @return parent of the node.
     */
   @Override
   public AbstractNode getParent() {
      return(parent);
   }

    /**
     * Get the sibling of the node.
     * @return sibling of the node.
     */
   @Override
   public AbstractNode getSib() {
      return(mysib);
   }

    /**
     * Get the child of the node.
     * @return the child of the node.
     */
   @Override
   public AbstractNode getChild() {
      return(child);
   }

    /**
     * Get the first sibling of the node.
     * @return the first sibling of the node.
     */
   @Override
   public AbstractNode getFirstSib() {
      return(firstSib);
   }

    /**
     * Returns the empty name of the node.
     * @return an empty string.
     */
   @Override
   public String getName() { return ""; }

    /**
     * Returns the name of the node.
     * @return the nodes name as a string.
     */
   @Override
   public String toString() {
      return(getName());
   }

    /**
     * Get the number of the node, which is based on the amount of nodes created before the node,
     * @return the number of the node.
     */
   @Override
   public int getNodeNum() { return nodeNum; }

    /**
     * Internally walk through the tree of children.
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param v The visitor object to use.
     */
   private void internWalk(int printLevel, Visitor v) {
      v.pre(printLevel, this);

      for (AbstractNode c = child; c != null; c=c.mysib)
         c.internWalk(printLevel+1, v);
      v.post(printLevel, this);
   }

    /**
     * Walk the tree of child nodes using a visitor.
     * @param v the visitor object to use.
     */
   @Override
   public void walkTree(Visitor v) {
      internWalk(0, v);
   }

}
