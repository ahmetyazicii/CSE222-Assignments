import java.util.NoSuchElementException;

public class BSTHeapTree<E> {

    private final int limit = 7;
    private int numOccurAdd =0;

    protected static class HeapNode<E>{

        protected HeapWO<E> heap;
        protected HeapNode<E> left;
        protected HeapNode<E> right;


        public HeapNode(E data){
            heap = new HeapWO<>();
            heap.insert(data);
            left = null;
            right = null;
        }



        @Override
        public String toString() {
            return heap.toString();
        }
    }

    protected HeapNode<E> root;

    protected BSTHeapTree(){
        root = null;
    }

    protected BSTHeapTree(HeapNode<E> root){
        this.root = root;
    }


    public BSTHeapTree<E> getLeftSubTree(){
        if(root != null && root.left !=null)
            return new BSTHeapTree<>(root.left);
        else
            return null;
    }
    public BSTHeapTree<E> getRightSubTree(){
        if(root != null && root.right !=null)
            return new BSTHeapTree<>(root.right);
        else
            return null;
    }

    /**
     * finds the number of occurrence of the given item in the tree
     * @param item item that will be find the number of occurrences
     * @return number of occurrence of the given item
     */
    public int find(E item){
        return find(root,item);
    }

    @SuppressWarnings("unchecked")
    private int find(HeapNode<E> localRoot,E item){
        if(localRoot == null)
                return 0;

        if(localRoot.heap.search(item)){
            return localRoot.heap.getNumberOfOccur(item);
        }
        int comp = ((Comparable<E>) item).compareTo(localRoot.heap.peek());
        if(comp < 0){
            return find(localRoot.left,item);
        }
        else{
            return find(localRoot.right,item);
        }

    }

    /**
     * finding the mode of Tree(The mode is the value in the BSTHeapTree that occurs most frequently)
     */
    public void find_mode(){
        E value = find_mode(root);

        System.out.println("\nThe mode is "+ value + " with " + find(value) + " occurrences.");

    }

    /**
     * method for recursively find the mode
     * @param localRoot localRoot
     * @return returns the mode
     */
    private E find_mode(HeapNode<E> localRoot){
        if(localRoot == null)
            return null;

        int max = localRoot.heap.findMaxOccur();
        E rootValue = localRoot.heap.getOccurElement(max);
        E leftValue = find_mode(localRoot.left);
        E rightValue = find_mode(localRoot.right);
        if(leftValue != null && find(leftValue) > find(rootValue))
            rootValue = leftValue;

        if(rightValue != null && find(rightValue) > find(rootValue))
            rootValue = rightValue;

        return rootValue;
    }

    /**
     * add the item to the tree. If the item already is in tree increase the number of occurrences of item.Otherwise
     * add to the tree. When adding to the tree the BSTHeapTree Structure is preserving(more detail is in hw pdf)
     * @param item that will be added to the tree
     * @return returns the number of occurrences of the item after insertion
     */
    public int add(E item){
        numOccurAdd = 0;
        root = add(root,item);
        return numOccurAdd;
    }

    /**
     * recursive add method for perform add operation
     * @param localRoot localRoot
     * @param item  that will be added to the tree
     * @return return Node
     */
    @SuppressWarnings("unchecked")
    private HeapNode<E> add(HeapNode<E> localRoot,E item){
        if(localRoot == null){
            return new HeapNode<>(item);
        }
        else if(localRoot.heap.search(item) || localRoot.heap.size() < limit){
            localRoot.heap.insert(item);
            numOccurAdd = localRoot.heap.getNumberOfOccur(item);
            return localRoot;
        }
        else if( ((Comparable<E>) item).compareTo(localRoot.heap.peek()) < 0){
            localRoot.left = add(localRoot.left,item);
            return localRoot;
        }
        else {
            localRoot.right = add(localRoot.right,item);
            return localRoot;
        }

    }


    public int remove(E item){
        numOccurAdd = 0;
        root = remove(root,item);
        return numOccurAdd;

    }
    @SuppressWarnings("unchecked")
    private HeapNode<E> remove(HeapNode<E> localRoot,E item){
        try{
            if(localRoot == null){
                return null;
            }
            else if(localRoot.heap.search(item) ) {
                numOccurAdd = localRoot.heap.getNumberOfOccur(item) - 1;
                localRoot.heap.remove(item);

                if (localRoot.heap.size() == limit) {
                    return localRoot;
                } else if (localRoot.heap.size() == 0) {
                    return null;
                }
                return localRoot;
            }
            else if( ((Comparable<E>) item).compareTo(localRoot.heap.peek()) < 0){
                localRoot.left = remove(localRoot.left,item);
                return localRoot;
            }
            else {
                localRoot.right = remove(localRoot.right,item);
                return localRoot;
            }
        }
        catch (NoSuchElementException nexc){
            System.out.println(nexc +"!EXCEPTION!-No element found.\n");
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root,1,sb);
        return sb.toString();
    }

    private void preOrderTraverse(HeapNode<E> root, int depth, StringBuilder sb) {

        sb.append("-".repeat(Math.max(0, depth - 1)));
        if(root != null){
            sb.append(root);
           // sb.append("\n");
            preOrderTraverse(root.left,depth+1,sb);
            preOrderTraverse(root.right,depth+1,sb);
        }
        else
            sb.append("null\n");

    }

}
