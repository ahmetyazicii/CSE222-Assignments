import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapWO<E> extends Heap<E>{

    // Data fields
    private ArrayList<Integer> numberOfOccur;

    // default constructor
    public HeapWO(){
        super();
        numberOfOccur = new ArrayList<>();
    }

    /**
     *  Insert an item into the Heap preserving heap order and structure.
     *  If the item is in already inserted to the heap increase the number of occurrence of that item.
     *  Otherwise insert item to the Heap and make 1 to the number of occurrence of that item.
     * @param item item that will be inserted heap
     * @return if the operation success returns true, otherwise false
     */
    @Override
    public boolean insert(E item) {
        if(search(item)){
            int occur = numberOfOccur.get(searchIndexOccur(item));
            numberOfOccur.set(searchIndexOccur(item), occur+1);
        }
        else{
            numberOfOccur.add(1);
            super.insert(item);
        }
        return true;
    }

    /**
     * Removes the top of heap preserving heap order and structure.
     * If there is more than one occurrences of the top of heap, just decrease the occurrences, not remove.
     * If there is just one occurrence of the top of heap, then remove.
     * @return If occurrence is equals 1 returns the top of Heap,otherwise returns null.
     */
    @Override
    public E remove() {
        if(numberOfOccur.get(0) == 1){
            numberOfOccur.set(0,numberOfOccur.remove(numberOfOccur.size()-1));
            return super.remove();
        }
        else{
            int occur = numberOfOccur.get(0);
            numberOfOccur.set(0,occur-1);
        }
        return null;
    }

    /**
     * Same as no-parameter remove method. Only differences is this method remove the given item.
     * If there is no such item in Heap returns null.
     * @param item item that will be returned.
     * @return If occurrence is equals 1 returns the top of Heap,otherwise returns null.
     */
    @SuppressWarnings("unchecked")
    public E remove(E item) {
        if(!search(item))
            throw new NoSuchElementException("Element not found\n");
        HeapIterator<E> iter = this.iterator();
        int counter = 0;
        E nextIter =  (E)iter.next();
        while( ((Comparable<E>) item).compareTo(nextIter) !=0){
            nextIter = (E) iter.next();
            counter ++;
        }
        if(numberOfOccur.get(counter) == 1){
            iter.remove();
        }
        else{
            int occur = numberOfOccur.get(counter);
            numberOfOccur.set(counter,occur-1);
        }
        return null;
    }


    /**
     * swap the number of occurrences when swapping the values.
     * @param item1 one item index to swap
     * @param item2 other item index to swap
     */
    @Override
    protected void swap(int item1, int item2) {

        super.swap(item1, item2);
        int temp = numberOfOccur.get(item1);
        numberOfOccur.set(item1,numberOfOccur.get(item2));
        numberOfOccur.set(item2,temp);
    }


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
                lastReturned = iter.next();
                return lastReturned;
            }

            @Override
            public void remove() {
                numberOfOccur.set(searchIndexOccur(lastReturned),numberOfOccur.remove(numberOfOccur.size()-1));
                data.set(data.indexOf(lastReturned),data.remove(data.size()-1));
                preserveHeapOrder();
            }

            @Override
            public E set(E value) {
                E oldValue = data.get(data.indexOf(lastReturned));
                data.set(data.indexOf(lastReturned), value );
                preserveHeapOrder();
                return oldValue;
            }
        };
    }

    /**
     * returns the number of occurrences of given item
     * @param item item that will be found the  number of occurrences
     * @return  the number of occurrences of given item
     */
    public int getNumberOfOccur(E item) {
        return numberOfOccur.get(searchIndexOccur(item));
    }

    @Override
    protected void preserveHeapOrder() {
        super.preserveHeapOrder();
    }

    private int searchIndexOccur(E item){
        return data.indexOf(item);
    }

    public int findMaxOccur(){
        int maxOccur = numberOfOccur.get(0);

        for(int i=1;i< numberOfOccur.size();++i){
            if(numberOfOccur.get(i) > maxOccur){
                maxOccur = numberOfOccur.get((i));
            }

        }
        return maxOccur;
    }

    public E getOccurElement(int occur){
        return data.get(numberOfOccur.indexOf(occur));

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(sb);
        return sb.toString();

    }

    /**
     * printing the Heap with number of occurrences
     * @param sb StringBuilder
     */
    public void print(StringBuilder sb){
        for(int i=0;i <=size() /2;++i){
            for(int j=0; j < Math.pow(2,i) && j+ Math.pow(2,i) <= size();++j){
               sb.append(data.get(j + (int) Math.pow(2, i) - 1)).append(".").append(numberOfOccur.get(searchIndexOccur(data.get(j + (int) Math.pow(2, i) - 1)))).append(" ");
            }
           sb.append("\n");
        }
    }


}
