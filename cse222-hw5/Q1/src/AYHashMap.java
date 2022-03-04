import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class AYHashMap<K, V> extends HashMap<K, V>{

    // constructors of HashMap
    AYHashMap(){
        super();
    }

    AYHashMap(int initialCapacity){
        super(initialCapacity);
    }

    AYHashMap(int initialCapacity,float loadFactor){
        super(initialCapacity,loadFactor);
    }

    @SuppressWarnings("unchecked")
    private class MapIterator<k> implements MapIteratorInterface<k> {

        /** The cursor for traversing with iterator*/
        private int cursor;
        /** Items list of keys.For traversing back and forth */
        private final ArrayList<k> items;

        /** Default constructor */
         MapIterator(){
            cursor = 0;
            items = new ArrayList<>((Collection<? extends k>) keySet());
        }

        /**
         * Parameter constructor.Iterator starts from given key.
         * The specified index indicates the first element that would be returned by an initial call to next.
         * If the key is not in map, iterator starts from beginning.
         * @param key The starting index
         */
        MapIterator(k key){
            this();
            if (containsKey(key)) // checks that key is in the map or not
                cursor = items.indexOf(key);
        }

        /**
         * Returns the next key in the list and advances the cursor position.
         * @return The next key in the map.
         * @throws NoSuchElementException If the iterator has no next key
         */
        @Override
        public k next() throws NoSuchElementException {
            if(hasNext())
                return items.get(cursor++);
            else
                throw new NoSuchElementException("The map is empty.\n");
        }

        /**
         * The method returns True if there are still not-iterated key/s in the Map, otherwise
         * returns False.In other words, returns True if next() would return element rather than throwing exception.
         * @return True if there are still not-iterated key/s in the Map, otherwise False.
         */
        @Override
        public boolean hasNext() {
           return cursor < keySet().size();
        }

        /**
         * Returns the previous key in the map and moves the cursor position backwards.
         * It returns the last key when the iterator is at the first key.
         * @return The previous key in the map.If the cursor is in beginning position returns last key.
         * @throws NoSuchElementException If the iterator has no previous key
         */
        @Override
        public k prev() throws NoSuchElementException{
            if(size()==0)
                throw new NoSuchElementException("The map is empty.\n");
            if(cursor == 0){
                cursor = items.size();
            }
            return items.get(--cursor);
        }
    }

    /**
     * Returns a custom map iterator over the elements in this list (in proper sequence).
     * @return the MapIterator
     */
    public MapIterator<K> mapIterator(){
        return new MapIterator<>();
    }

    /**
     * Returns a custom map iterator over the elements in this list (in proper sequence).
     * The specified index indicates the first element that would be returned by an initial call to next.
     * If the key is not in map, iterator starts from beginning.
     * @param key The starting index
     * @return the MapIterator
     */
    public MapIterator<K> mapIterator(K key){
        return new MapIterator<>(key);
    }
}

