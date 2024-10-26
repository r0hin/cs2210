// Purpose: Implements a hash table with separate chaining for the DictionaryADT interface

import java.util.LinkedList;

public class HashDictionary implements DictionaryADT {
  private LinkedList<Data>[] table; // Array of linked lists for separate chaining
  private int numRecords; // Number of records in the dictionary

  @SuppressWarnings("unchecked")
  public HashDictionary(int size) {
    this.numRecords = 0;
    table = new LinkedList[size];
    for (int i = 0; i < size; i++) {
      table[i] = new LinkedList<>();
    }
  }

  public int polynomialHash(String str) {
    long hash = 0;

    int[] primes = { 131, 137, }; // Different primes for each re-hash
    int mod = 2147483647; // Large prime modulus

    // Initial hashing loop
    for (int i = 0; i < str.length(); i++) {
      hash = (hash * primes[0] + str.charAt(i)) % mod;
    }

    // Re-hash up to `n` times with different primes
    for (int i = 1; i < primes.length; i++) {
      hash = (hash * primes[i] + hash) % mod;
    }

    return (int) (hash & 0x7FFFFFFF); // Ensures non-negative result
  }

  // Polynomial hash function
  private int h(String config) {
    return polynomialHash(config) % table.length;
  }

  public int put(Data pair) throws DictionaryException {
    int hashIndex = h(pair.getConfiguration());
    LinkedList<Data> chain = table[hashIndex];
    // Check if the slot already has a record with the same configuration
    for (Data data : chain) {
      if (data.getConfiguration().equals(pair.getConfiguration())) {
        throw new DictionaryException();
      }
    }
    // No duplicate found, so add the new record
    chain.add(pair);
    numRecords++;
    // Check if a collision occurred
    return chain.size() > 1 ? 1 : 0; // Return 1 if there was a collision, else 0
  }

  // Removes a Data pair by config
  public void remove(String config) throws DictionaryException {
    int hashIndex = h(config);
    LinkedList<Data> chain = table[hashIndex];

    for (Data data : chain) {
      if (data.getConfiguration().equals(config)) {
        chain.remove(data);
        numRecords--;
        return;
      }
    }
    throw new DictionaryException();
  }

  // Retrieves the score associated with a config
  public int get(String config) {
    int hashIndex = h(config);
    LinkedList<Data> chain = table[hashIndex];

    for (Data data : chain) {
      if (data.getConfiguration().equals(config)) {
        return data.getScore();
      }
    }
    return -1; // Return -1 if the config is not found
  }

  // Returns the number of records in the dictionary
  public int numRecords() {
    return numRecords;
  }

}
