public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E>{

    /** Nested class to represent a Red-Black node. */
    protected static class RedBlackNode<E> extends Node<E>{

        /** Color indicator. True if red, false if black. */
        private boolean isRed;

        /**
         * Create a RedBlackNode with the default color of red and the given item
         * @param item The item
         */
        public RedBlackNode(E item){
            super(item);
            isRed = true;
        }
        @Override
        public String toString() {
           if(isRed)
               return "Red:" + super.toString();
           else
               return "Black:" + super.toString();
        }
    }

    /**
     * add starter method
     * @param item The item being inserted
     * @return true if the object is inserted; false if the object already exists in the tree
     * @throws ClassCastException if item is not Comparable
     */
    public boolean add(E item){
        if(root == null){
            root = new RedBlackNode<>(item);
            ((RedBlackNode<E>) root).isRed = false;
            size++;
            return true;
        }
        else{
            root = add((RedBlackNode<E>)root,item);
            ((RedBlackNode<E>) root).isRed = false;
            if(addReturn)
                size++;
            return addReturn;
        }
    }

    private Node<E> add(RedBlackNode<E> localRoot,E item){
        if(item.compareTo(localRoot.data) == 0){
            // item already in the tree
            addReturn = false;
            return localRoot;
        } else if(item.compareTo(localRoot.data) < 0){
            // item < localRoot.data
            if(localRoot.left == null){
                // Create new left child.
                localRoot.left = new RedBlackNode<>(item);
                addReturn = true;
                return localRoot;
            }
            else{ // Need to search
                // check for two red children, swap colors if found
                moveBlackDown(localRoot);
                // Recursively add on the left
                localRoot.left = add((RedBlackNode<E>) localRoot.left,item);
                // see whether the left child is now red
                if (((RedBlackNode<E>) localRoot.left).isRed) {
                    if(localRoot.left.left != null && ((RedBlackNode<E>) localRoot.left.left).isRed){
                        // left-left grandchild is also red
                        // single rotation is necessary
                        ((RedBlackNode<E>) localRoot.left).isRed = false;
                        localRoot.isRed = true;
                        return rotateRight(localRoot);
                    }
                    else if(localRoot.left.right != null && ((RedBlackNode<E>) localRoot.left.right).isRed ){
                        // left-right grandchild is also red
                        // double rotation is necessary
                        localRoot.left = rotateLeft(localRoot.left);
                        ((RedBlackNode<E>) localRoot.left).isRed = false;
                        localRoot.isRed = true;
                        return rotateRight(localRoot);
                    }
                }
            }
        } else{
            // item > localRoot.data
            if(localRoot.right == null){
                // Create new right child.
                localRoot.right = new RedBlackNode<>(item);
                addReturn = true;
                return localRoot;
            }
            else{ // Need to search
                // check for two red children, swap colors if found
                moveBlackDown(localRoot);
                // Recursively add on the left
                localRoot.right = add((RedBlackNode<E>) localRoot.right,item);
                // see whether the right child is now red
                if (((RedBlackNode<E>) localRoot.right).isRed) {
                    if(localRoot.right.right != null && ((RedBlackNode<E>) localRoot.right.right).isRed){
                        // right-right grandchild is also red
                        // single rotation is necessary
                        ((RedBlackNode<E>) localRoot.right).isRed = false;
                        localRoot.isRed = true;
                        return rotateLeft(localRoot);
                    }
                    else if(localRoot.right.left != null && ((RedBlackNode<E>) localRoot.right.left).isRed ){
                        // right-left grandchild is also red
                        // double rotation is necessary
                        localRoot.right = rotateRight(localRoot.right);
                        ((RedBlackNode<E>) localRoot.right).isRed = false;
                        localRoot.isRed = true;
                        return rotateLeft(localRoot);
                    }
                }
            }
        }

        if(localRoot == root){
            localRoot.isRed = false;
        }
        return localRoot;
    }
    private void moveBlackDown(RedBlackNode<E> localRoot){
        if(localRoot.left != null && localRoot.right != null){
            if(((RedBlackNode<E>) localRoot.left).isRed && ((RedBlackNode<E>) localRoot.right).isRed){
                ((RedBlackNode<E>) localRoot.left).isRed = false;
                ((RedBlackNode<E>) localRoot.right).isRed = false;
                localRoot.isRed = true;
            }
        }
    }

    /**
     * Checks that root is red or not
     * @return True if root is red, false if root is black.
     */
    @Override
    public boolean isRed() {
        return ((RedBlackNode<E>) root).isRed;
    }


}
