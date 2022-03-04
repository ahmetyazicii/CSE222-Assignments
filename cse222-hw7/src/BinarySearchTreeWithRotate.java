public class BinarySearchTreeWithRotate<E extends Comparable<E>> extends BinarySearchTree<E>{

    /**
     * Method to perform a right rotation.
     * @param root The root of the binary tree to be rotated
     * @return The new root of the rotated tree
     */
    protected Node<E> rotateRight(Node<E> root){
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }

    /**
     * Method to perform a left rotation.
     * @param root The root of the binary tree to be rotated
     * @return The new root of the rotated tree
     */
    protected Node<E> rotateLeft(Node<E> root){
        Node<E> temp = root.right;
        root.right = temp.left;
        temp.left = root;
        return temp;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
