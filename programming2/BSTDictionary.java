// Use class BinarySearchTree to implement the BSTDictionary class
public class BSTDictionary implements BSTDictionaryADT {
  BinarySearchTree bst;

  public BSTDictionary() {
    bst = new BinarySearchTree();
  }

  public Record get(Key k) {
    if (bst.get(bst.getRoot(), k) == null) {
      return null;
    }
    return bst.get(bst.getRoot(), k).getRecord();
  }

  public void put(Record r) throws DictionaryException {
    bst.insert(r);
  }

  public void remove(Key k) throws DictionaryException {
    bst.remove(bst.getRoot(), k);
  }

  public Record successor(Key k) {
    if (bst.successor(bst.getRoot(), k) == null) {
      return null;
    }

    return bst.successor(bst.getRoot(), k).getRecord();
  }

  public Record predecessor(Key k) {
    if (bst.predecessor(bst.getRoot(), k) == null) {
      return null;
    }
    return bst.predecessor(bst.getRoot(), k).getRecord();
  }

  public Record smallest() {
    return bst.smallest(bst.getRoot()).getRecord();
  };

  public Record largest() {
    return bst.largest(bst.getRoot()).getRecord();
  }

}