public class Key implements Comparable<Key> {
  String label;
  int type;

  public Key(String theLabel, int theType) {
    label = theLabel.toLowerCase();
    type = theType;
  }

  public String getLabel() {
    return label;
  }

  public int compareTo(Key other) {
    // Compare labels lexicographically
    int labelComparison = this.label.compareTo(other.label);
    if (labelComparison != 0) {
      return labelComparison; // If labels are different, return the comparison result
    }

    // If labels are equal, compare types
    return Integer.compare(this.type, other.type);
  }

}