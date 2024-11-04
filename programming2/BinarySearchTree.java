public class BinarySearchTree {
  BSTNode root;

  public BinarySearchTree() {
    root = null;
  }

  public BSTNode getRoot() {
    return root;
  }

  public BSTNode get(BSTNode r, Key k) {
    if (r == null) {
      return null;
    }

    if (k.compareTo(r.getRecord().getKey()) == 0) {
      return r;
    }

    if (k.compareTo(r.getRecord().getKey()) < 0) {
      return get(r.getLeftChild(), k);
    } else {
      return get(r.getRightChild(), k);
    }
  }

  public void insert(Record d) throws DictionaryException {
    // If root is null, set root to the new node with the record
    if (root == null) {
      root = new BSTNode(d);
      return;
    }

    BSTNode newNode = new BSTNode(d);
    BSTNode current = root;
    BSTNode parent = null;

    // Check if the key already exists in the tree
    if (get(root, d.getKey()) != null) {
      throw new DictionaryException("Key already exists");
    }

    while (true) {
      parent = current;
      if (d.getKey().compareTo(current.getRecord().getKey()) < 0) {
        current = current.getLeftChild();
        if (current == null) {
          parent.setLeftChild(newNode);
          newNode.setParent(parent);
          return;
        }
      } else {
        current = current.getRightChild();
        if (current == null) {
          parent.setRightChild(newNode);
          newNode.setParent(parent);
          return;
        }
      }
    }
  }

  public void remove(BSTNode r, Key k) throws DictionaryException {
    // If the tree is empty, throw an exception
    if (root == null) {
      throw new DictionaryException("Cannot remove from an empty tree");
    }

    BSTNode node = get(r, k);
    if (node == null) {
      throw new DictionaryException("Key not found");
    }

    // Case 1: Node has no children (leaf node)
    if (node.getLeftChild() == null && node.getRightChild() == null) {
      if (node == root) {
        root = null; // Removing the root node
      } else {
        if (node == node.getParent().getLeftChild()) {
          node.getParent().setLeftChild(null);
        } else {
          node.getParent().setRightChild(null);
        }
      }
    }
    // Case 2: Node has one child
    else if (node.getLeftChild() == null || node.getRightChild() == null) {
      BSTNode child = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();
      if (node == root) {
        root = child; // If root has one child, make that child the new root
      } else {
        if (node == node.getParent().getLeftChild()) {
          node.getParent().setLeftChild(child);
        } else {
          node.getParent().setRightChild(child);
        }
      }
      child.setParent(node.getParent());
    }
    // Case 3: Node has two children
    else {
      // Find the in-order successor of the node
      BSTNode successor = successor(node, k);
      // Copy the successor's data to the node to be deleted
      node.setRecord(successor.getRecord());
      // Remove the successor node
      if (successor.getParent().getLeftChild() == successor) {
        successor.getParent().setLeftChild(successor.getRightChild());
      } else {
        successor.getParent().setRightChild(successor.getRightChild());
      }
      if (successor.getRightChild() != null) {
        successor.getRightChild().setParent(successor.getParent());
      }
    }
  }

  public BSTNode successor(BSTNode r, Key k) {
    BSTNode node = get(r, k);
    if (node == null)
      return null;

    if (node.getRightChild() != null) {
      BSTNode successor = node.getRightChild();
      while (successor.getLeftChild() != null) {
        successor = successor.getLeftChild();
      }
      return successor;
    }

    BSTNode parent = node.getParent();
    while (parent != null && node == parent.getRightChild()) {
      node = parent;
      parent = parent.getParent();
    }
    return parent;
  }

  public BSTNode predecessor(BSTNode r, Key k) {
    BSTNode node = get(r, k);
    if (node == null)
      return null;

    if (node.getLeftChild() != null) {
      BSTNode predecessor = node.getLeftChild();
      while (predecessor.getRightChild() != null) {
        predecessor = predecessor.getRightChild();
      }
      return predecessor;
    }

    BSTNode parent = node.getParent();
    while (parent != null && node == parent.getLeftChild()) {
      node = parent;
      parent = parent.getParent();
    }
    return parent;
  }

  public BSTNode largest(BSTNode r) {
    if (r == null)
      return null;
    BSTNode current = r;
    while (current.getRightChild() != null) {
      current = current.getRightChild();
    }
    return current;
  }

  public BSTNode smallest(BSTNode r) {
    if (r == null)
      return null;
    BSTNode current = r;
    while (current.getLeftChild() != null) {
      current = current.getLeftChild();
    }
    return current;
  }

}
