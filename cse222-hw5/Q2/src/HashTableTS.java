import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class HashTableTS<K extends Comparable<K>,V extends Comparable<V>> implements KWHashMap<K,V>{

    private static class Entry<K extends Comparable<K>,V extends Comparable<V>> implements Comparable<Entry<K,V>>{
        /** The key */
        private K key;
        /** The value */
        private V value;

        /** Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key,V value){
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key
         * @return The key
         */
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value
         * @return The value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value and returns the old one
         * @param value The new value
         * @return The old value
         */
        public V setValue(V value) {
            V oldValue = getValue();
            this.value = value;
            return oldValue;
        }

        @Override
        public int compareTo(Entry<K, V> o) {
            return this.getKey().compareTo(o.getKey());
        }
    }

    /** The table */
    private TreeSet<Entry<K,V>>[] table;
    /** The total number of keys in table */
    private int numKeys;
    /** The capacity */
    private int CAPACITY = 101;
    /** The max load factor */
    private static final double LOAD_THRESHOLD = 3.0;

    // Constructor
    @SuppressWarnings("unchecked")
    public HashTableTS(){
        table = new TreeSet[CAPACITY];
        numKeys = 0;
    }
    @SuppressWarnings("unchecked")
    public HashTableTS(int capacity){
        this.CAPACITY = capacity;
        table = new TreeSet[CAPACITY];
        numKeys = 0;
    }


    /**
     * Method for getting value from table(simply get method)
     * @param key The key being sought
     * @return The value associated with this key if found, otherwise null
     */
    @Override
    public V get(Object key) throws NoSuchElementException {
        if(isEmpty())
            throw new NoSuchElementException("The Table is Empty");

        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null)   // when key is not in the table
            return null;

        // Search the list at table[index] to find the key
        for (Entry<K,V> nextItem : table[index]){
            if (nextItem.getKey().equals(key))
                return  nextItem.getValue();
        }

        return null;
    }

    /**
     * Checks whether is table empty or not.
     * @return Returns true if table contains no key-value mappings, otherwise false.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method for putting value into table(simply put or insert method)
     * @param key The key of value
     * @param value The value will be inserted to the table
     * @return The old value of associated with this key if found, otherwise null
     */
    @Override
    public V put(K key, V value){
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null) {  // when key is not in the table
            // Create a new LinkedList at table[index]
            table[index] = new TreeSet<>();
        }

        // Search the list at table[index] to find the key
        for (Entry<K,V> nextItem : table[index]){
            if (nextItem.getKey().equals(key)) {
                // Replace value for this key and returns the old one
                V oldValue = nextItem.getValue();
                nextItem.setValue(value);
                return oldValue;
            }
        }

        // The key is not in the table, add new item
        table[index].add(new Entry<>(key, value));
        numKeys++;

        if(numKeys > (LOAD_THRESHOLD * table.length))
            rehash();

        return null;
    }

    /**
     * Method for removing the mapping for given key from the table(simply remove method)
     * @param key The key that will be removed from table
     * @return The old value of associated with this key if found, otherwise null
     */
    @Override
    public V remove(Object key) throws NoSuchElementException{
        if(isEmpty())
            throw new NoSuchElementException("The Table is Empty");
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null) {  // when key is not in the table
            return null;
        }

        // Search the list at table[index] to find the key
        TreeSet<Entry<K, V>> entries = table[index];
        Iterator<Entry<K, V>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            // When search successful remove entry and decrement numKeys.Then return the removed value
            Entry<K,V> nextITem = iterator.next();
            if (nextITem.getKey().equals(key)) {
                V oldValue = nextITem.getValue();
                iterator.remove();
                numKeys--;
                if(table[index].size() == 0)
                    table[index] = null;
                return oldValue;
            }
        }
        return null;
    }

    /**
     * Returns the size of the table
     * @return The size of Table;
     */
    @Override
    public int size() {
        return numKeys;
    }

    /**
     * Expands table size when loadFactor exceeds LOAD_THRESHOLD
     * post: The size of the table is doubled and is an odd integer( 2 *size +1 ).
     *       Each nonempty entry from the original table is reinserted into the expanded table.
     *       The value of numKeys is reset to the number of items actually inserted.
     */
    @SuppressWarnings("unchecked")
    private void rehash(){
        // Save a reference to oldTable
        TreeSet<Entry<K, V>>[] oldTable = table;
        // Double capacity of this table
        table = new TreeSet[2 * oldTable.length +1];

        // Reinsert all items in oldTable into expanded table
        numKeys = 0;
        for (TreeSet<Entry<K, V>> entries : oldTable) {
            if ((entries != null)) {
                for (Entry<K, V> nextItem : entries) {
                    put(nextItem.getKey(),nextItem.getValue());
                }
            }
        }
    }

    /**
     * toString method for printing key-value pairs.
     * @return string
     */
    @Override
    public String toString() {
        for (TreeSet<Entry<K, V>> entries : table) {
            if ((entries != null)) {
                for (Entry<K, V> nextItem : entries) {
                    System.out.print("Key: " + nextItem.getKey() + " Value: " + nextItem.getValue() + " --- ");
                }
                System.out.print("null");
                System.out.println();
            }
        }
        return "";
    }

}
