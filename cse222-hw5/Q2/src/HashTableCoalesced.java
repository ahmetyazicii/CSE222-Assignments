import java.util.NoSuchElementException;

public class HashTableCoalesced<K,V> implements KWHashMap<K,V> {

    private static class Entry<K,V> {
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

    }
    private static class Node<K,V>{

        private Entry<K,V> pair;
        private Node<K,V> next;
        private int hashValue;

        public Node(Entry<K,V> entry,int hashValue){
            pair = entry;
            next = null;
            this.hashValue = hashValue;
        }

        public Node<K,V> replace(Node<K,V> other){
            if(other == null)
                return null;
            else{
                this.pair = other.pair;
                this.next = other.next;
                return this;
            }
        }

    }

    private Node<K,V>[] table;
    private int CAPACITY = 101;
    private static final double LOAD_THRESHOLD = 0.75;
    private  int numKeys;


    // Default constructor
    @SuppressWarnings("unchecked")
    public HashTableCoalesced(){
        table = new Node[CAPACITY];
        numKeys = 0;
    }

    @SuppressWarnings("unchecked")
    public HashTableCoalesced(int capacity){
        this.CAPACITY = capacity;
        table = new Node[CAPACITY];
        numKeys = 0;
    }

    /**
     * Method for getting value from table(simply get method)
     * @param key The key being sought
     * @return The value associated with this key if found, otherwise null
     */
    @Override
    public V get(Object key) throws NoSuchElementException{
        if(isEmpty())
            throw new NoSuchElementException("The Table is Empty");

        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null) {   // when key is not in the table
            return null;
        }

        // Search the list at table[index] to find the key
        Node<K,V> traverse = table[index];

        //If the first index equals key(assume no collision)
        if(traverse.pair.getKey().equals(key)){
            return traverse.pair.getValue();
        }

        // When collision happen
        while(traverse.next!= null){
            // Replace value for this key and returns the old one
            if(traverse.pair.getKey().equals(key)){
                return traverse.pair.getValue();
            }
            traverse = traverse.next;
        }

        // when key is not in the table
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
    public V put(K key, V value) {
        int initialIndex = key.hashCode() % table.length;
        if (initialIndex < 0)
            initialIndex += table.length;
        if (table[initialIndex] == null) {  // when key is not in the table
            // Create a new LinkedList at table[index]
            table[initialIndex] = new Node<>(new Entry<>(key,value),initialIndex);
        }
        else{
            // Search the list at table[index] to find the key
            Node<K,V> traverse = table[initialIndex];
            while(traverse.next!= null){
                // Replace value for this key and returns the old one
                if(traverse.pair.getKey().equals(key)){
                    V oldValue = traverse.pair.getValue();
                    traverse.pair.setValue(value);
                    return oldValue;
                }
                traverse = traverse.next;

            }
            // The key is not in the table, add new item
            int quadraticIndex = find(key);
            table[quadraticIndex] = new Node<>(new Entry<>(key,value),quadraticIndex);
            traverse.next = table[quadraticIndex];
        }

        numKeys++;

        // Check whether rehash is needed
        double loadFactor = (double) (numKeys) / table.length;
        if(loadFactor > LOAD_THRESHOLD )
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

        Node<K,V> traverse = table[index];
        // Deleting entry that has no next
        if(traverse.next == null ){
            if(traverse.pair.getKey().equals(key)) {
                V oldValue = traverse.pair.getValue();
                table[index] = null;
                numKeys--;
                return oldValue;
            }
        }
        else{
            do {
                if (traverse.next.pair.getKey().equals(key)) {
                    Node<K, V> temp = traverse.next;
                    V oldValue = temp.pair.getValue();
                    if (temp.next == null)
                        table[traverse.hashValue].next = null;
                    table[temp.hashValue] = temp.replace(temp.next);
                    return oldValue;
                }
                traverse = traverse.next;
            } while (traverse.next != null);
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
     * Finds either the target key or the first empty slot in the search chain
     * using quadratic probing
     * pre: The table is a prime(odd) number and it is never more than half full
     * @param key The key of the target object
     * @return The position of the target or the first empty slot(as a quadratic) if the target is not in the table
     */
    private int find(Object key){
        // Calculate the starting index
        int probeNum = -1;
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;

        // Increment index quadratically until an empty slot is reached or the key is found
        while( (table[index] != null) && (!key.equals(table[index].pair.getKey()))){
            probeNum += 2;
            index = (index + probeNum) % table.length;
            // Check for wraparound
            if(index >= table.length)
                index = 0;
        }
        return index;
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
        Node<K,V>[] oldTable = table;
        // Double capacity of this table
        table = new Node[2 * oldTable.length +1];

        // Reinsert all items in oldTable into expanded table
        numKeys = 0;
        for (Node<K, V> kvNode : oldTable) {
            if ((kvNode != null)) {
                put(kvNode.pair.getKey(), kvNode.pair.getValue());
            }
        }
    }

    @Override
    public String toString() {

        for(int i=0;i< table.length;++i){
            if(table[i] != null){
                System.out.print("Hash: " + table[i].hashValue
                                    +" |Key: " + table[i].pair.getKey()
                                    +" Value: " + table[i].pair.getValue());
                if(table[i].next == null)
                    System.out.print(" |Next = null\n");
                else
                    System.out.print(" |Next: " + table[i].next.hashValue + "\n");

            }
            else
                System.out.print("Hash: " + i +" |Key = null Value = null | Next = null\n");
        }

        return "";
    }
}
