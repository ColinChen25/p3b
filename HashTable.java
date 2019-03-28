import java.util.ArrayList;


// DO ADD UNIMPLEMENTED PUBLIC METHODS FROM HashTableADT and DataStructureADT TO YOUR CLASS
// DO IMPLEMENT THE PUBLIC CONSTRUCTORS STARTED
// DO NOT ADD OTHER PUBLIC MEMBERS (fields or methods) TO YOUR CLASS
//

//
// Collision resolution is to use an arraylist of arraylist as the hash table, so when collision
// occurit would be stored in the inner arraylist allowing fast access
//
// The code masks off the sign bit (to turn the 32-bit integer into a 31-bit nonnegative integer)
// and then computing the remainder when dividing by capacity, as in modular hashing.
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  /**
   * Node as an Object to store key-value pair
   * 
   * @author Colin Mercer
   *
   * @param <K> Key
   * @param <V> Value
   */
  private class Node<K, V> {
    private K key;
    private V data;

    /**
     * Constructor for Node class, store the key and data pair
     * 
     * @param key
     * @param data
     */
    private Node(K key, V data) {
      this.key = key;
      this.data = data;
    }

    /**
     * accessor for the key
     * 
     * @return key
     */
    private K getKey() {
      return key;
    }

    /**
     * accessor for the data
     * 
     * @return data
     */
    private V getData() {
      return data;
    }
  }

  private ArrayList<ArrayList<Node<K, V>>> dataTable; // the table for storing nodes(bucket)
  private double loadFactorThreshold;
  private int capacity; // capacity of the hash table
  private int numOfKeys; // number of key-value pairs stored in this hash table



  /**
   * Default constructor with 11 table capacity and 0.75 load factor threshold
   */
  public HashTable() {
    dataTable = new ArrayList<ArrayList<Node<K, V>>>(11);
    for (int i = 0; i < 11; i++) {
      dataTable.add(null);
    }
    this.loadFactorThreshold = 0.75;
    numOfKeys = 0;
    capacity = 11;
    // printHashTable();
  }

  /**
   * Constructor that customize the initial capacity and load factor threshold
   * 
   * @param initialCapacity
   * @param loadFactorThreshold
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    dataTable = new ArrayList<ArrayList<Node<K, V>>>(initialCapacity);
    for (int i = 0; i < initialCapacity; i++) {
      dataTable.add(null);
    }
    this.loadFactorThreshold = loadFactorThreshold;
    numOfKeys = 0;
    capacity = initialCapacity;
    // printHashTable();
  }



  /**
   * The code masks off the sign bit (to turn the 32-bit integer into a 31-bit nonnegative integer)
   * and then computing the remainder when dividing by capacity, as in modular hashing.
   * 
   * @param key key need to calculated
   * @return hash code of the key
   */
  private int hash(K key) {
    // System.out.println(capacity);
    return (key.hashCode() & 0x7fffffff) % capacity;
  }

  /**
   * Private helper method for printing out the whole hash table
   */
  private void printHashTable() {
    // System.out.println("Printing hash table: (key/value), LF=" + this.getLoadFactor()
    // + " Capacity: " + this.getCapacity());
    for (int i = 0; i < capacity; i++) {
      if (dataTable.get(i) != null) {
        for (int j = 0; j < dataTable.get(i).size(); j++) {
          System.out.println("(" + dataTable.get(i).get(j).getKey() + "/"
              + dataTable.get(i).get(j).getData() + ")");
        }
      }
    }
  }

  /**
   * Private helper method for rehashing the hash table when the load factor is over the threshold
   * 
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   */
  private void rehash() throws IllegalNullKeyException, DuplicateKeyException {
    // System.out.println("Rehashing");
    int oldCapacity = capacity;
    ArrayList<ArrayList<Node<K, V>>> temp = dataTable; // save old table
    dataTable = new ArrayList<ArrayList<Node<K, V>>>(2 * capacity + 1); // create new table
    capacity = 2 * capacity + 1;
    for (int i = 0; i < capacity; i++) {// Initialize the new table
      dataTable.add(null);
    }

    numOfKeys = 0; // reset the pair count
    for (int i = 0; i < oldCapacity; i++) { // copy old value to new table using insert

      if (temp.get(i) != null) {
        for (int j = 0; j < temp.get(i).size(); j++) {
          insert(temp.get(i).get(j).getKey(), temp.get(i).get(j).getData());
        }
      }
    }
  }

  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    // System.out.println("Inserting");
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    int hash = hash(key);


    if (dataTable.get(hash) == null) { // if adding value to this location for the 1st time
      dataTable.set(hash, new ArrayList<Node<K, V>>());
      dataTable.get(hash).add(new Node<K, V>(key, value));
    } else { // Collision happened, link the new K V pair as new node under the existing node
      for (int i = 0; i < dataTable.get(hash).size(); i++) {
        if (dataTable.get(hash).get(i).getKey().equals(key)) {
          throw new DuplicateKeyException();
        }
      }
      dataTable.get(hash).add(new Node<K, V>(key, value));
    }
    numOfKeys += 1;
    // printHashTable();
    if (getLoadFactor() > getLoadFactorThreshold()) { // rehashing check
      rehash();
    }
  }

  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    // System.out.println("Removing");
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    try {
      get(key);
    } catch (KeyNotFoundException e) {
      return false;
    }

    int hash = hash(key);
    for (int i = 0; i < dataTable.get(hash).size(); i++) {
      if (dataTable.get(hash).get(i).getKey().equals(key)) {
        dataTable.get(hash).remove(i);
        numOfKeys--;
        return true;
      }
    }
    return false;
  }

  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    // System.out.println("Retreiving");
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    int hash = hash(key);
    if (dataTable.get(hash) == null) {
      throw new KeyNotFoundException();
    }
    for (int i = 0; i < dataTable.get(hash).size(); i++) {
      if (dataTable.get(hash).get(i).getKey().equals(key)) {
        return dataTable.get(hash).get(i).getData();
      }
    }

    throw new KeyNotFoundException();

  }

  @Override
  public int numKeys() {
    return numOfKeys;
  }

  @Override
  public double getLoadFactorThreshold() {
    return this.loadFactorThreshold;
  }

  @Override
  public double getLoadFactor() {
    return (double) numOfKeys / capacity;
  }

  @Override
  public int getCapacity() {
    return this.capacity;
  }

  @Override
  public int getCollisionResolution() {
    return 4; // array of array
  }



}
