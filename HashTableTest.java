
import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Random;



/**
 * JUnit test class for HashTable class
 * 
 * @author Colin Mercer
 *
 */
public class HashTableTest {


  @BeforeEach
  public void setUp() throws Exception {

  }


  @AfterEach
  public void tearDown() throws Exception {

  }

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  // TODO add your own tests of your implementation
  /**
   * Insert 1 KV pair and check number of keys
   */
  @Test
  public void test002_insert_one_pair() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    try {
      htIntegerKey.insert(1, "Hello");
      if (htIntegerKey.numKeys() != 1) {
        fail("after insert there should be 1 entry");
      }
    } catch (IllegalNullKeyException e) {
      fail("Illegal Null Key exception");
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      fail("Duplicate KEy exception");
      e.printStackTrace();
    }
  }

  /**
   * Insert 10 KV pair and check number of keys
   */
  @Test
  public void test003_insert_multiple_key() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    try {
      htIntegerKey.insert(1, "Hello");
      htIntegerKey.insert(2, "Hello");
      htIntegerKey.insert(3, "Hello");
      htIntegerKey.insert(4, "Hello");
      htIntegerKey.insert(5, "Hello");
      htIntegerKey.insert(6, "Hello");
      htIntegerKey.insert(7, "Hello");
      htIntegerKey.insert(8, "Hello");
      htIntegerKey.insert(9, "Hello");
      htIntegerKey.insert(10, "Hello");
      if (htIntegerKey.numKeys() != 10) {
        fail("after insert there should be 10 entry");
      }
    } catch (IllegalNullKeyException e) {
      fail("Illegal Null Key exception");
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      fail("Duplicate KEy exception");
      e.printStackTrace();
    }
  }

  /**
   * try to get a spicific value
   */
  @Test
  public void test004_get_after_insert() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    try {
      htIntegerKey.insert(1, "Hell No");
      htIntegerKey.insert(2, "Hello");
      htIntegerKey.insert(3, "Hello");
      htIntegerKey.insert(4, "Hello");
      htIntegerKey.insert(5, "Hello");
      htIntegerKey.insert(6, "Hello");
      htIntegerKey.insert(7, "Hello");
      htIntegerKey.insert(8, "Hello");
      htIntegerKey.insert(9, "Hello");
      htIntegerKey.insert(10, "Hello");
      if (htIntegerKey.numKeys() != 10) {
        fail("after insert there should be 10 entry");
      }
      if (!htIntegerKey.get(1).equals("Hell No")) {
        fail("failed to get the 1st pair inserted with key 1");
      }
    } catch (IllegalNullKeyException e) {
      fail("Illegal Null Key exception");
      e.printStackTrace();
    } catch (Exception e) {
      fail("SOmething is wrong");
      e.printStackTrace();
    }
  }


  public void test005_load_factor_check() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>(10, 0.75);
    try {
      htIntegerKey.insert(1, "Hell No");
      htIntegerKey.insert(2, "Hello");
      if (htIntegerKey.getLoadFactor() - 0.2 >= 0.01) {
        fail("load factor should be 2/10=0.2, which it is not");
      }
      htIntegerKey.insert(3, "Hello");
      htIntegerKey.insert(4, "Hello");
      if (htIntegerKey.getLoadFactor() - 0.4 >= 0.01) {
        fail("load factor should be 4/10=0.4, which it is not");
      }
      htIntegerKey.insert(5, "Hello");
      htIntegerKey.insert(6, "Hello");
      if (htIntegerKey.getLoadFactor() - 0.6 >= 0.01) {
        fail("load factor should be 6/10=0.6, which it is not");
      }
      htIntegerKey.insert(7, "Hello");
      htIntegerKey.insert(8, "Hello");
      htIntegerKey.insert(9, "Hello");
      htIntegerKey.insert(10, "Hello");
      if (htIntegerKey.numKeys() != 10) {
        fail("after insert there should be 10 entry");
      }
      if (!htIntegerKey.get(1).equals("Hell No")) {
        fail("failed to get the 1st pair inserted with key 1");
      }
    } catch (IllegalNullKeyException e) {
      fail("Illegal Null Key exception");
      e.printStackTrace();
    } catch (Exception e) {
      fail("SOmething is wrong");
      e.printStackTrace();
    }
  }

  /**
   * Insert half million KV pair and check number of keys
   */
  @Test
  public void test0666_insert_500K_key() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    try {
      for (int i = 0; i < 500000; i++) {
        htIntegerKey.insert(i, null);
      }
      if (htIntegerKey.numKeys() != 500000) {
        fail("after insert there should be 500000 entry");
      }
    } catch (IllegalNullKeyException e) {
      fail("Illegal Null Key exception");
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      fail("Duplicate KEy exception");
      e.printStackTrace();
    }
  }

  /**
   * Insert a fuck tons then delete, check num of key after that
   */
  @Test
  public void test007_delete_test() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    try {
      for (int i = 0; i < 99999; i++) {
        htIntegerKey.insert(i, null);
      }
      htIntegerKey.insert(100000, "Hello");

      for (int i = 0; i < 50000; i++) {
        htIntegerKey.remove(i);
      }

      if (htIntegerKey.numKeys() != 50000) {
        fail("after insert there should be 50000 entry");
      }
      if (!htIntegerKey.get(100000).equals("Hello")) {
        fail(
            "after insert 100000 times and delete the 0~50000 entry, the 100000th entry should still be accessable, but its not");
      }
    } catch (IllegalNullKeyException e) {
      fail("Illegal Null Key exception");
      e.printStackTrace();
    } catch (DuplicateKeyException e) {
      fail("Duplicate KEy exception");
      e.printStackTrace();
    } catch (KeyNotFoundException e) {
      fail("Key not found");
    }
  }
}
