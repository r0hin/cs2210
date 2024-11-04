// BinarySearchTree.java, Programming Assignment 2.
// Implements a binary search tree.

public class BinarySearchTree {
  BSTNode root;

  // Creates a new binary search tree.
  public BinarySearchTree() {
    root = null;
  }

  // Returns the root of the tree.
  public BSTNode getRoot() {
    return root;
  }

  // Returns the node with the specified key.
  public BSTNode get(BSTNode r, Key k) {
    if (r == null) {
      return null;
    }

    // If the key is found, return the node
    if (k.compareTo(r.getRecord().getKey()) == 0) {
      return r;
    }

    if (k.compareTo(r.getRecord().getKey()) < 0) {
      // If the key is less than the current node's key, search the left subtree
      return get(r.getLeftChild(), k);
    } else {
      // If the key is greater than the current node's key, search the right subtree
      return get(r.getRightChild(), k);
    }
  }

  // Inserts a new record into the tree.
  // I think it is possible to do this recursively too, but the iterative approach
  // has the same time complexity O(h), where h is the height of the tree.
  public void insert(Record d) throws DictionaryException {
    // If its the first node, make it the root
    if (root == null) {
      root = new BSTNode(d);
      return;
    }

    // Create a new node
    BSTNode newNode = new BSTNode(d);
    BSTNode current = root;
    BSTNode parent = null;

    // Check if the key already exists in the tree
    if (get(root, d.getKey()) != null) {
      throw new DictionaryException("Key already exists");
    }

    // Traverse the tree to find the correct position to insert the new node
    while (true) {
      parent = current;
      if (d.getKey().compareTo(current.getRecord().getKey()) < 0) {
        // If the key is less than the current node's key, go left
        current = current.getLeftChild();
        if (current == null) {
          parent.setLeftChild(newNode);
          newNode.setParent(parent);
          return;
        }
      } else {
        // If the key is greater than the current node's key, go right
        current = current.getRightChild();
        if (current == null) {
          parent.setRightChild(newNode);
          newNode.setParent(parent);
          return;
        }
      }
    }
  }

  // Removes the node with the specified key from the tree.
  public void remove(BSTNode r, Key k) throws DictionaryException {
    if (root == null) {
      // If the tree is empty, throw an exception
      throw new DictionaryException("Cannot remove from an empty tree");
    }

    // Find the node to be deleted
    BSTNode node = get(r, k);

    // If the key is not found, throw an exception
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

  // Returns the next node in the in-order traversal of the tree.
  public BSTNode successor(BSTNode r, Key k) {
    // Find the node with the specified key
    BSTNode node = get(r, k);

    // If the key is not found, return null
    if (node == null)
      return null;

    // If the node has a right child, the successor is the leftmost node in the
    // right subtree
    if (node.getRightChild() != null) {
      BSTNode successor = node.getRightChild();
      while (successor.getLeftChild() != null) {
        successor = successor.getLeftChild();
      }
      return successor;
    }

    // If the node has no right child, the successor is the lowest ancestor of the
    // node
    BSTNode parent = node.getParent();
    while (parent != null && node == parent.getRightChild()) {
      node = parent;
      parent = parent.getParent();
    }
    return parent;
  }

  // Returns the previous node in the in-order traversal of the tree.
  public BSTNode predecessor(BSTNode r, Key k) {
    // Find the node with the specified key
    BSTNode node = get(r, k);

    // If the key is not found, return null
    if (node == null)
      return null;

    // If the node has a left child, the predecessor is the rightmost node in the
    // left subtree
    if (node.getLeftChild() != null) {
      BSTNode predecessor = node.getLeftChild();
      while (predecessor.getRightChild() != null) {
        predecessor = predecessor.getRightChild();
      }
      return predecessor;
    }

    // If the node has no left child, the predecessor is the lowest ancestor of the
    // node
    BSTNode parent = node.getParent();
    while (parent != null && node == parent.getLeftChild()) {
      node = parent;
      parent = parent.getParent();
    }
    return parent;
  }

  // Returns the node with the largest key in the tree.
  public BSTNode largest(BSTNode r) {
    if (r == null)
      return null;

    // Find the rightmost node, because it's a search tree
    BSTNode current = r;
    while (current.getRightChild() != null) {
      current = current.getRightChild();
    }

    return current;
  }

  public BSTNode smallest(BSTNode r) {
    if (r == null)
      return null;

    // Find the leftmost node, because it's a search tree
    BSTNode current = r;
    while (current.getLeftChild() != null) {
      current = current.getLeftChild();
    }
    return current;
  }

}
