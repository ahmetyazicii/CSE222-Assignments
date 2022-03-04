public class partTwo {

    /**
     * Driver function of method that takes a BinarySearchTree and checks whether the tree is avl or red black.
     */
    public static void testTreeTypeMethod() {

        System.out.println("-----------------------------------------");
        System.out.println("Testing method with AVL Tree.");
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.add(642);
        avlTree.add(75);
        avlTree.add(889);
        avlTree.add(44);
        avlTree.add(35);
        treeType(avlTree);

        System.out.println("-----------------------------------------");
        System.out.println("Testing method with Red-Black Tree.");
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.add(642);
        redBlackTree.add(75);
        redBlackTree.add(889);
        redBlackTree.add(44);
        redBlackTree.add(35);
        redBlackTree.add(97);
        treeType(redBlackTree);

        System.out.println("-----------------------------------------");
        System.out.println("Testing method with classic Binary Search Tree.");
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add(642);
        binarySearchTree.add(75);
        binarySearchTree.add(889);
        binarySearchTree.add(44);
        binarySearchTree.add(35);
        treeType(binarySearchTree);
    }

    /**
     * Method that checks whether the tree is avl or red black.
     *
     * @param binarySearchTree The tree being sought
     */
    public static void treeType(BinarySearchTree<? extends Comparable<?>> binarySearchTree) {
        if (checksAVLTree(binarySearchTree.root)) {
            System.out.println("Tree is a AVL Tree");
        } else
            System.out.println("Tree is not a AVL Tree");

        if (checksRedBlackTree(binarySearchTree)) {
            System.out.println("Tree is a Red-Black Tree");
        } else
            System.out.println("Tree is not a Red-Black Tree");

    }

    /**
     * Recursively checks for AVL Tree properties
     * Checks the difference between left and right subtree. If differences greater than 2, tree is not AVL tree.
     * @param localRoot The tree
     * @return true if the tree provide AVL Tree properties, false not.
     */
    private static boolean checksAVLTree(BinaryTree.Node<? extends Comparable<?>> localRoot) {
        if (localRoot == null)
            return true;
        int heightDiff = Math.abs(height(localRoot.left) - height(localRoot.right));
        return heightDiff <= 1 && checksAVLTree(localRoot.left) && checksAVLTree(localRoot.right);
    }

    /**
     * Recursively calculating the height of tree.
     *
     * @param localRoot The tree
     * @return height of the tree
     */
    private static int height(BinaryTree.Node<?> localRoot) {
        if (localRoot == null)
            return -1;
        int lefth = height(localRoot.left);
        int righth = height(localRoot.right);

        if (lefth > righth)
            return lefth + 1;
        else
            return righth + 1;
    }

    /**
     * Checks for Red-Black Tree properties
     * Check whether root is black or not
     * @param binarySearchTree The tree
     * @return True if the tree provide Red-Black Tree properties, false not
     */
    private static boolean checksRedBlackTree(BinarySearchTree<? extends Comparable<?>> binarySearchTree) {
        return !binarySearchTree.isRed();
    }
}
