// Key.java, Programming Assignment 2.
// Holds a key with a label and type.

public class Key implements Comparable<Key> {
  String label;
  int type;

  // Creates a new key with the specified label and type.
  public Key(String theLabel, int theType) {
    label = theLabel.toLowerCase();
    type = theType;
  }

  // Returns the label of the key.
  public String getLabel() {
    return label;
  }

  // Returns the type of the key.
  public int getType() {
    return type;
  }

  // Compare labels lexicographically
  public int compareTo(Key other) {
    int labelComparison = this.label.compareTo(other.label);
    if (labelComparison != 0) {
      // If labels are different, return the comparison result
      return labelComparison;
    }

    // If labels are equal, compare types
    return Integer.compare(this.type, other.type);
  }

}