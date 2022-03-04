import java.util.Arrays;
import java.util.Random;

public class SkipList<E extends Comparable<E>> {

    static Random rand = new Random();
    private int maxLevel;
    private int maxCap;
    /** Natural Log of 2 */
    static final double LOG2 = Math.log(2.0);
    private int size;

    /** Static class to contain the data and the links */
    @SuppressWarnings("unchecked")
    protected static class SLNode<E>{
        SLNode<E>[] links;
        E data;
        /** create a node of level m */
        SLNode(int m,E data){
            links = (SLNode<E>[]) new SLNode[m];
            this.data = data;
        }
    }

    protected SLNode<E> head;

    /**
     * Default constructor with maxLevel 4.
     */
    public SkipList(){
        maxLevel = 4;
        size = 0;
        maxCap = computeMaxCap(maxLevel);
        head = new SLNode<>(maxLevel,null);
    }


    /**
     * Search for an item in the list
     * @param target The item being sought
     * @return A SLNode array which references the predecessors of the target at each level
     */
    @SuppressWarnings("unchecked")
    private SLNode<E>[] search(E target){
        SLNode<E>[] pred = (SLNode<E>[]) new SLNode[maxLevel];
        SLNode<E> current = head;
        for(int i=current.links.length-1 ; i >= 0; i--){
            while(current.links[i] != null &&
                    current.links[i].data.compareTo(target) < 0){
                current = current.links[i];
            }
            pred[i] = current;
        }
        return pred;
    }

    /**
     * Find an object in the skip-list
     * @param target The item being sought
     * @return A reference to the object in the skip-list that matches the target. If not found, null is returned.
     */
    public E find(E target){
        SLNode<E>[] pred = search(target);
        if(pred[0].links[0] != null &&
            pred[0].links[0].data.compareTo(target) == 0){
            return pred[0].links[0].data;
        }
        else {
            return null;
        }
    }

    /**
     * Inserting new item to the skip-list, if item is not already in skip-list
     * @param item The item
     * @return True if item inserted skip-list, false the item is already in skip-list
     */
    public boolean insert(E item){

        if(item == find(item))
            return false;

        SLNode<E>[] pred = search(item);

        int randomLevel = logRandom();
        SLNode<E> newNode = new SLNode<>(randomLevel,item);

        for(int i =0; i < randomLevel; ++i){
            newNode.links[i] = pred[i].links[i];
            pred[i].links[i] = newNode;
        }
        size++;
        if(size > maxCap){
            maxLevel++;
            maxCap = computeMaxCap(maxLevel);
            head.links = Arrays.copyOf(head.links,maxLevel);
        }
        return true;
    }

    /**
     * Deleting item from the skip-list.
     * @param target The item
     * @return True if item deleted from skip-list, false the item is not in skip-list
     */
    public boolean delete(E target){
        if(target != find(target))
            return false;

        SLNode<E>[] pred = search(target);

        SLNode<E> nextNode = pred[0].links[0];
        int deleteLevel = nextNode.links.length;

        for(int i =0; i < deleteLevel; ++i){
           pred[i].links[i] = nextNode.links[i];
        }

        return true;

    }

    private int logRandom(){
        int r = rand.nextInt(maxCap);
        int k = (int) (Math.log(r + 1) / LOG2);
        if(k > maxLevel -1)
            k = maxLevel - 1;
        return maxLevel - k;
    }
    private int computeMaxCap(int maxLevel){
        return (int)(Math.pow(2.0,maxLevel) - 1);
    }

    /**
     * Method for printing the skip-list nicely.
     */
    public void print(){
        SLNode<E> current = head;

        System.out.println("MaxLevel:" + maxLevel);
        while(current.links[0] != null){
            System.out.println("Level:" +current.links[0].links.length + " Data:" + current.links[0].data );
            current = current.links[0];
        }
    }

    /**
     * Returning the items in the skip-list
     * @return The size of skip-list
     */
    public int size(){
        return size;
    }


}
