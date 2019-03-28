/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    Colin Chen Andrew Kummel
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   3/28/2019
 * Version:    1.0
 * 
 * Credits:    
 * 
 * Bugs:       
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    public MyProfiler() {

      hashtable = new HashTable();
      treemap = new TreeMap();
      
    }
    
    public void insert(K key, V value) {
      try {
        hashtable.insert(key,value);
        treemap.put(key, value);
      } catch (IllegalNullKeyException e) {
       
        e.printStackTrace();
      } catch (DuplicateKeyException e) {
        
        e.printStackTrace();
      }
    }
    
    public void retrieve(K key) {
      try {
        hashtable.get(key);
        treemap.get(key);
      } catch (IllegalNullKeyException e) {
        e.printStackTrace();
      } catch (KeyNotFoundException e) {
        e.printStackTrace();
      }
      
    }
    
    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);

            
            // TODO: complete the main method. 
            // Create a profile object. 
            // For example, Profile<Integer, Integer> profile = new Profile<Integer, Integer>();
            // execute the insert method of profile as many times as numElements
            // execute the retrieve method of profile as many times as numElements
            // See, ProfileSample.java for example.
            MyProfiler<Integer,Integer> profile = new MyProfiler<Integer,Integer>();
            for(int i=0;i<numElements;i++) {
              profile.insert(i, i);
            }
            for(int i=0;i<numElements;i++) {
              profile.retrieve(i);
            }
            
        
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        }
        catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
