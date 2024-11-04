public class Record {
  Key key;
  String data;

  public Record(Key k, String theData) {
    key = k;
    data = theData;
  }

  public Key getKey() {
    return key;
  }

  public String getDataItem() {
    return data;
  }
}
