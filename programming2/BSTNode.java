public class BSTNode {
  Record data;
  BSTNode leftChild;
  BSTNode rightChild;
  BSTNode parent;

  public BSTNode(Record item) {
    data = item;
  }

  public Record getRecord() {
    return data;
  }

  public void setRecord(Record d) {
    data = d;
  }

  public BSTNode getLeftChild() {
    return leftChild;
  }

  public BSTNode getRightChild() {
    return rightChild;
  }

  public BSTNode getParent() {
    return parent;
  }

  public void setLeftChild(BSTNode left) {
    leftChild = left;
  }

  public void setRightChild(BSTNode right) {
    rightChild = right;
  }

  public void setParent(BSTNode p) {
    parent = p;
  }

  public boolean isLeaf() {
    return leftChild == null && rightChild == null;
  }
}
