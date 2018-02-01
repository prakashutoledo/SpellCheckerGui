package spellcheckergui;
// Written By: Prakash Khadka and Tyler Vankainen
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import java.util.Queue;
public interface HashTableInterface<K,V> {
    int count();
    boolean isEmpty();
    boolean contains(K key);
    int hash(K key);
    void insert(K key, V value);
    V getValue(K key);
    Queue keySet();
    Queue valueSet();
}
