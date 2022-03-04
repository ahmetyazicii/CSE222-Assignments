public class BinarySearchTree<E extends Comparable<E> > extends BinaryTree<E> implements SearchTree<E> {

    protected boolean addReturn;
    protected E deleteReturn;
    protected int size;

    public BinarySearchTree(){
        root = null;
        size = 0;
    }

    public E find(E target){
        return find(root,target);
    }
    private E find(Node<E> localRoot,E target){
        if(localRoot == null)
            return null;

        int compResult = target.compareTo(localRoot.data);
        if(compResult == 0)
            return localRoot.data;
        else if(compResult < 0)
            return find(localRoot.left,target);
        else
            return find(localRoot.right,target);

    }
    public boolean contains(E target){
        return (find(target) == target);
    }

    public boolean add(E item){
        root = add(root,item);
        if(addReturn)
            size++;
        return addReturn;
    }
    private Node<E> add(Node<E> localRoot,E item){
        if(localRoot == null){
            addReturn = true;
            return new Node<>(item);
        } else if(item.compareTo(localRoot.data) == 0){
            addReturn = false;
            return localRoot;
        } else if(item.compareTo(localRoot.data) < 0){
            localRoot.left = add(localRoot.left,item);
            return localRoot;
        } else{
            localRoot.right = add(localRoot.right,item);
            return localRoot;
        }
    }

    public E delete(E target){
        root = delete(root,target);
        return deleteReturn;
    }
    private Node<E> delete(Node<E> localRoot,E item){
        if(localRoot == null){
            deleteReturn = null;
            return null;
        }

        int compResult = item.compareTo(localRoot.data);
        if(compResult < 0) {
            localRoot.left = delete(localRoot.left,item);
            return localRoot;
        } else if(compResult > 0){
            localRoot.right = delete(localRoot.right,item);
            return localRoot;
        } else{
            deleteReturn = localRoot.data;

            if(localRoot.left == null){
                return localRoot.right;
            } else if(localRoot.right == null){
                return localRoot.left;
            } else{
                if(localRoot.left.right == null){
                    localRoot.data = localRoot.left.data;
                    localRoot.left = localRoot.left.left;
                } else{
                    localRoot.data = findLargestChild(localRoot.left);
                }
                return localRoot;
            }
        }
    }
    protected E findLargestChild(Node<E> parent){
        if(parent.right.right == null){
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        } else
            return findLargestChild(parent.right);
    }
    public boolean remove(E target){
        return (delete(target) == target);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root,1,sb);
        return sb.toString();
    }
    private void preOrderTraverse(Node<E> root, int depth, StringBuilder sb) {

        sb.append("-".repeat(Math.max(0, depth - 1)));
        if(root != null){
            sb.append(root);
            sb.append("\n");
            preOrderTraverse(root.left,depth+1,sb);
            preOrderTraverse(root.right,depth+1,sb);
        }
        else
            sb.append("null\n");

    }

    public boolean isRed(){
        return true;
    }

    public int size(){
        return size;
    }

}
