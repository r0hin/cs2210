// BSTDictionary.java, Programming Assignment 2.
// Implements a dictionary using a binary search tree. Extends the provided BSTDictionaryADT interface.

public class BSTDictionary implements BSTDictionaryADT {
  BinarySearchTree bst;

  // Creates a new binary search tree dictionary.
  public BSTDictionary() {
    bst = new BinarySearchTree();
  }

  // Returns the root of the tree.
  public Record get(Key k) {
    // If the key is not found, return null
    if (bst.get(bst.getRoot(), k) == null) {
      return null;
    }

    // Return the record of the node with the specified key
    return bst.get(bst.getRoot(), k).getRecord();
  }

  // Inserts a new record into the tree.
  public void put(Record r) throws DictionaryException {
    bst.insert(r);
  }

  // Removes the record with the specified key from the tree.
  public void remove(Key k) throws DictionaryException {
    bst.remove(bst.getRoot(), k);
  }

  // Returns the successor of the specified key.
  public Record successor(Key k) {
    // If the successor is not found, return null
    if (bst.successor(bst.getRoot(), k) == null) {
      return null;
    }

    // Return the record of the successor of the specified key
    return bst.successor(bst.getRoot(), k).getRecord();
  }

  // Returns the predecessor of the specified key.
  public Record predecessor(Key k) {
    // If the predecessor is not found, return null
    if (bst.predecessor(bst.getRoot(), k) == null) {
      return null;
    }

    // Return the record of the predecessor of the specified key
    return bst.predecessor(bst.getRoot(), k).getRecord();
  }

  // Returns the smallest record in the tree.
  public Record smallest() {
    return bst.smallest(bst.getRoot()).getRecord();
  };

  // Returns the largest record in the tree.
  public Record largest() {
    return bst.largest(bst.getRoot()).getRecord();
  }

}