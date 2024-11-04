// Record.java, Programming Assignment 2.
// Holds a record with a key and data item.

public class Record {
  private Key key;
  private String data;

  // Creates a new record with the specified key and data item.
  public Record(Key k, String theData) {
    key = k;
    data = theData;
  }

  // Returns the key of the record.
  public Key getKey() {
    return key;
  }

  // Returns the data item of the record.
  public String getDataItem() {
    return data;
  }
}
