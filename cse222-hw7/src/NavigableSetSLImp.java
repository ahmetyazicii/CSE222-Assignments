import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class NavigableSetSLImp<E extends Comparable<E>> implements NavigableSetSL<E>{

    /** Data structure for store the values. */
    private final SkipList<E> skipList;

    /** Default constructor */
    public NavigableSetSLImp(){
        skipList = new SkipList<>();
    }

    /**
     * Inserting new item to the set, if item is not already in set
     * @param item The item
     * @return True if item inserted set, false the item is already in set
     */
    @Override
    public boolean insert(E item) {
        return skipList.insert(item);
    }

    /**
     * Deleting item from the set.
     * @param target The item
     * @return True if item deleted form set, false the item is not in the set
     */
    @Override
    public boolean delete(E target) {
        return skipList.delete(target);
    }

    /**
     * Returns an iterator over the elements in this set, in descending order.
     * @return an iterator over the elements in this set, in descending order
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<>() {

            private final ArrayList<E> items = new ArrayList<>();
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                if(items.size() == 0)
                    fillListDescendingOrder(skipList, items);
                return (cursor < items.size());
            }

            @Override
            public E next() {
                if(items.size() == 0)
                    fillListDescendingOrder(skipList, items);
                if(hasNext())
                    return items.get(cursor++);
                else
                    throw new NoSuchElementException("The set is empty.\n");
            }

            private void fillListDescendingOrder(SkipList<E> skipList,ArrayList<E> items){
                Stack<E> stack = new Stack<>();

                SkipList.SLNode<E> current = skipList.head;
                while(current.links[0] != null){
                    stack.push(current.links[0].data);
                    current = current.links[0];
                }
                int size = stack.size();
                for(int i=0;i< size;++i)
                    items.add(stack.pop());

            }

        };
    }

    /**
     * toString() method for print out the set nicely. toString() method uses descendingIterator for iterate over set.
     * The set printing descending order.
     * @return String representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");

        Iterator<E> iterator = this.descendingIterator();
        while (iterator.hasNext()) {
            E item = iterator.next();
            sb.append(item);
            if(iterator.hasNext())
                sb.append(",");
        }

        sb.append(" ]");
        return  sb.toString();
    }
}
