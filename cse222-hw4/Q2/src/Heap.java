import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

// maxHeap
public class Heap<E> implements HeapInterface<E>  {

    // Data fields
    protected ArrayList<E> data;

    // Default constructor
    public Heap(){
        data = new ArrayList<>();
    }

    @Override
    public int size() {
        return data.size();
    }

    /**
     * Insert an item into the Heap preserving heap order and structure.
     * @param item item that will be inserted heap
     * @return if the operation success returns true, otherwise false
     */
    @Override
    public boolean insert(E item) {

        data.add(item);
        int child = data.size()-1;
        int parent = (child-1) / 2;

        while( parent >=0 && compare(data.get(parent),data.get(child)) > 0 ){
            swap(parent,child);
            child = parent;
            parent = (child - 1) / 2;

        }
        return true;
    }

    /**
     * Helper function for compare to item
     * @param left one item to compare
     * @param right other item to compare
     * @return negative int if left < right
     *         0 int if left = right
     *         positive int if left > right
     */
    @SuppressWarnings("unchecked")
    private int compare(E left,E right){

        return -((Comparable<E>) left).compareTo(right);
    }

    /**
     * Helper function for swap to value
     * @param item1 one item index to swap
     * @param item2 other item index to swap
     */
    protected void swap(int item1,int item2){
        E temp = data.get(item1);
        data.set(item1,data.get(item2));
        data.set(item2,temp);
    }

    /**
     * Removes the top of heap preserving heap order and structure.
     * @return returns the removed item.If item cannot find in heap returns null.
     */
    @Override
    public E remove() {
        if(data.isEmpty()) // when the heap is empty
            return null;

        E removeItem = data.get(0);

        if(data.size() == 1){ // when the heap has only one item to remove
            data.remove(0);
            return removeItem;
        }

        // removing first item from heap and swap the empty place with end of the heap.
        data.set(0,data.remove(data.size()-1));
        preserveHeapOrder();
        return removeItem;
    }

    /**
     * Searches the given item in the Heap.
     * @param item item to be searched
     * @return If item is in Heap return true, otherwise return false;
     */
    @Override
    public boolean search(E item) {
        return data.contains(item);
    }

    /**
     * Merge two heaps.
     * @param other the other heap that will be merged with.
     */
    @Override
    public void merge(HeapInterface<E> other) {
        int counter = other.size();
        for(int i=0;i< counter;++i)
            this.insert(other.remove());

    }

    /**
     * Removing the ith largest number from the Heap.
     * @param ith the rank of largest number
     * @return returning removed number.
     */
    @Override
    public E removeILargest(int ith) throws ArrayIndexOutOfBoundsException {

        if(ith > size())
            throw new ArrayIndexOutOfBoundsException();

        // temp Heap for storing removed items(since only can removed from root)
        Heap<E> temp = new Heap<>();

        for(int i=0;i <ith-1;++i)
            temp.insert(remove());

        E removeItem = remove();

        // reload the Heap.
        int tempCounter = temp.size();
        for(int i=0;i < tempCounter;++i)
            insert(temp.remove());

        return removeItem;
    }

    /**
     * the Heap Iterator. The methods are "hasNext() , next(), remove() , set(E)".
     * @return returning the iterator.
     */
    @Override
    public HeapIterator<E> iterator() {

        return new HeapIterator<>() {

            private Iterator<E> iter = data.iterator();
            private E lastReturned;

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public E next() {
              if(hasNext()) {
                  lastReturned = iter.next();
                  return lastReturned;
              }
               else throw new NoSuchElementException("There is no element\n");
            }

            @Override
            public void remove() throws NoSuchElementException {
                if(!hasNext())
                    throw new NoSuchElementException("There is no element\n");
                data.set(data.indexOf(lastReturned),data.remove(data.size()-1));
                preserveHeapOrder();
            }

            /**
             * set the value(value passed as parameter) of the last element returned by next()
             * set operation occur with preserve the Heap Order.
             * @param value the value that will be setted
             * @return  returns the value before set operation
             */
            @Override
            public E set(E value) {
                if(!hasNext())
                    throw new NoSuchElementException("There is no element\n");
                E oldValue = data.get(data.indexOf(lastReturned));
                data.set(data.indexOf(lastReturned), value );
                preserveHeapOrder();
                return oldValue;
            }
        };
    }

    /**
     * Helper method for preserve the Heap Order
     */
    protected void preserveHeapOrder(){
        int parent = 0;
        while(true) {
            int leftChild = 2 * parent + 1;
            if(leftChild >= data.size()){   //If there is no left child
                break;
            }
            int rightChild = leftChild +1;
            int minChild = leftChild;
            if(rightChild < data.size() && compare(data.get(leftChild), data.get(rightChild)) > 0 )
                minChild = rightChild;

            if(compare(data.get(parent), data.get(minChild) ) > 0){
                swap(parent,minChild);
                parent = minChild;
            }
            else {
                break;
            }
        }
    }

    /**
     * Method for printing the Heap
     */
    @Override
    public void show() {

        System.out.println();
        for(int i=0;i <= height() ;++i){
            for(int j=0; j < Math.pow(2,i) && j+ Math.pow(2,i) <= size();++j){
                System.out.print(data.get(j + (int) Math.pow(2, i) - 1) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * returns the height of heap
     * @return the height of Heap
     */
    private int height(){
        return (int) Math.floor(Math.log(size() / Math.log(2)) +1 );
    }

    /**
     * Method to return head of Heap
     * @return head of Heap
     */
    @Override
    public E peek() {
        return data.get(0);
    }
}
