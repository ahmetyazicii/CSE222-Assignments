import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class NavigableSetAVLImp<E extends Comparable<E>> implements NavigableSetAVL<E>{

    /** Data structure for store the values. */
    private final AVLTree<E> avlTree;

    /** Default constructor */
    public NavigableSetAVLImp(){
        avlTree = new AVLTree<>();
    }

    /**
     * Inserting new item to the set, if item is not already in set
     * @param item The item
     * @return True if item inserted set, false the item is already in set
     */
    @Override
    public boolean insert(E item) {
        return avlTree.add(item);
    }

    /**
     * Returns an iterator over the elements in this set, in ascending order.
     * @return an iterator over the elements in this set, in ascending order
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private final ArrayList<E> items = new ArrayList<>();
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                if(items.size() == 0)
                    InOrderTraversal(avlTree.root, items);
                return (cursor < items.size());
            }

            @Override
            public E next() {
                if(items.size() == 0)
                    InOrderTraversal(avlTree.root, items);
                if(hasNext())
                    return items.get(cursor++);
                else
                    throw new NoSuchElementException("The set is empty.\n");
            }

            private void InOrderTraversal(BinaryTree.Node<E> node,ArrayList<E> items){
                if(node != null){
                    InOrderTraversal(node.left,items);
                    items.add(node.data);
                    InOrderTraversal(node.right,items);
                }
            }
        };
    }

    /**
     * Returns a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement.
     * @param toElement high endpoint of the returned set
     * @param inclusive true if the high endpoint is to be included in the returned view
     * @return a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement
     */
    @Override
    public NavigableSetAVL<E> headSet(E toElement, boolean inclusive) {
        NavigableSetAVL<E> headSet = new NavigableSetAVLImp<>();

        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            E item = iterator.next();
            if(item.compareTo(toElement) == 0 && inclusive)
                headSet.insert(item);
            else if(item.compareTo(toElement) < 0)
                headSet.insert(item);
        }
        return headSet;
    }

    /**
     * Returns a view of the portion of this set whose elements are greater than (or equal to, if inclusive is true) fromElement.
     * @param toElement low endpoint of the returned set
     * @param inclusive true if the low endpoint is to be included in the returned view
     * @return a view of the portion of this set whose elements are greater than or equal to fromElement
     */
    @Override
    public NavigableSetAVL<E> tailSet(E toElement, boolean inclusive) {
        NavigableSetAVL<E> tailSet = new NavigableSetAVLImp<>();

        Iterator<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E item = iterator.next();
            if(item.compareTo(toElement) == 0 && inclusive)
                tailSet.insert(item);
            else if(item.compareTo(toElement) > 0)
                tailSet.insert(item);
        }
        return tailSet;
    }

    /**
     * toString() method for print out the set nicely. toString() method uses iterator for iterate over set.
     * The set is printing ascending order.
     * @return String representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");

        Iterator<E> iterator = this.iterator();
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
