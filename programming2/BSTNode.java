// BSTNode.java, Programming Assignment 2.
// Implements a binary search tree node.

public class BSTNode {
  Record data;
  BSTNode leftChild;
  BSTNode rightChild;
  BSTNode parent;

  // Creates a new node with the specified record.
  public BSTNode(Record item) {
    data = item;
  }

  // Returns the record of the node.
  public Record getRecord() {
    return data;
  }

  // Sets the record of the node.
  public void setRecord(Record d) {
    data = d;
  }

  // Returns the left child of the node.
  public BSTNode getLeftChild() {
    return leftChild;
  }

  // Returns the right child of the node.
  public BSTNode getRightChild() {
    return rightChild;
  }

  // Returns the parent of the node.
  public BSTNode getParent() {
    return parent;
  }

  // Sets the left child of the node.
  public void setLeftChild(BSTNode left) {
    leftChild = left;
  }

  // Sets the right child of the node.
  public void setRightChild(BSTNode right) {
    rightChild = right;
  }

  // Sets the parent of the node.
  public void setParent(BSTNode p) {
    parent = p;
  }

  // Returns true if the node is a leaf node.
  public boolean isLeaf() {
    return leftChild == null && rightChild == null;
  }
}
