package spellcheckergui;
// Written By: Prakash Khadka and Tyler Vankainen
/* Allows user to check the words the whole document as aone and highlights the
   incorrect words and auto correct to the nearest matched words using hash 
   table.
*/
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class HashTable<K, V> implements HashTableInterface<K,V> {
    public static final int    SIZE = 1001;
    public int                 count;
    public int                 collision;
    public int                 num;
    public K[]                 keys;
    public V[]                 values;
    public LinkedList<Integer> hash;
    boolean                    prime;

    public HashTable() {
        this(SIZE);
    }

    public HashTable(int size) {
        prime = isPrime(size);
        num    = size;
        count  = 0;
        collision = 0;
        keys   = (K[]) new Object[num];
        values = (V[]) new Object[num];
        hash   = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            keys[i]   = null;
            values[i] = null;
        }
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    @Override
    public boolean contains(K key) {
        return getValue(key) != null;
    }

    @Override
    public int hash(K key) {
        String          temp;
        int             sum;
        int             j;
        temp = (String) key;
        sum =           0;
        for (int i = 0; i < temp.length();) {
            j  = i + 4;
            if (j < temp.length()) {
                sum = sum + getChunk(temp.substring(i, j));
            } else {
                sum = sum + getChunk(temp.substring(i));
            }
            i = i + 4;
        }
        return (sum & 0x7fffffff) % num;
    }
    public int hashValue(K key) {
        String          temp;
        int             sum;
        int             j;
        temp = (String) key;
        sum =           0;
        for (int i = 0; i < temp.length();) {
            j  = i + 4;
            if (j < temp.length()) {
                sum = sum + getChunk(temp.substring(i, j));
            } else {
                sum = sum + getChunk(temp.substring(i));
            }
            i = i + 4;
        }
        return sum;
    }

    public void resize(int size) {
        HashTable<K, V>    newHashTable;
        newHashTable = new HashTable<>(size);
        for (int i = 0; i < num; i++) {
            if (keys[i] != null) {
                newHashTable.insert(keys[i], values[i]);
            }
        }
        keys        =      newHashTable.keys;
        values      =      newHashTable.values;
        num         =      newHashTable.num;
    }

    @Override
    public void insert(K key, V value) {
        int j;
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        j = 0;
        int hashValue = hash(key);
        hash.add(hashValue);
        while (keys[hashValue] != null && keys[hashValue] != key) {
            hashValue = (hashValue + 1) % num;
            if (count >= num / 2) {
                resize(2 * num);
            }
            if (j == 0) {
                collision++;
                j++;
            }
        }
        keys[hashValue] = key;
        values[hashValue] = value;
        count++;
    }

    @Override
    public V getValue(K key) {
        int hashValue;
        hashValue = hash(key);
        while (keys[hashValue] != null && !keys[hashValue].equals(key)) {
            hashValue = (hashValue + 1) % num;
        }
        return values[hashValue];
    }

    @Override
    public Queue keySet() {
        Queue<K>     queue;
        queue  = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            if (keys[i] != null) {
                queue.add(keys[i]);
            }
        }
        return queue;
    }

    @Override
    public Queue valueSet() {
        Queue<V>     queue;
        queue  = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            if (values[i] != null) {
                queue.add(values[i]);
            }
        }
        return queue;
    }

    public static int getChunk(String text) {
        char[]       charArr;
        int          sum;
        int          i;
        charArr   =  text.toLowerCase().toCharArray();
        sum       =  0;
        i         =  3;
        for (char c : charArr) {
            sum = sum + (int) Math.pow(31, i) * (c - 96);
            i   = i - 1;
        }
        return sum;
    }

    public int getMaxChain() {
        Collections.sort(hash);
        int                last;
        int                max;
        int                currentDuplicate;
        last             = hash.get(0);
        max              = 0;
        currentDuplicate = 1;
        for (int i = 1; i < hash.size(); i++) {
            if (hash.get(i) == last) {
                currentDuplicate++;
            } else {
                max = max > currentDuplicate ? max : currentDuplicate;
                currentDuplicate = 1;
            }
            last = hash.get(i);
        }
        max = max > currentDuplicate ? max : currentDuplicate;
        return max;
    }

    public int getCollision() {
        int previousNum, numDupes;
        previousNum = hash.get(0);
        numDupes    = 0;
        for (int i : hash) {
            if (i == previousNum) {
                numDupes++;
            }
            previousNum = i;
        }
        return numDupes;
    }
    public boolean isPrime(int n) {
        if (n%2==0) 		
           return false;
        else
           for(int i=3; i*i<=n; i+=2) {
              if(n%i==0)
                 return false;
           }
        return true;
    }
}
