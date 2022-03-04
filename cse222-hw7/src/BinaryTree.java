public class BinaryTree<E> {
    protected static class Node<E>{
        protected E data;
        protected Node<E> left;
        protected Node<E> right;

        public Node(E data){
            this.data = data;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    protected Node<E> root;

    public BinaryTree(){
        root = null;
    }
    protected BinaryTree(Node<E> root){
        this.root = root;
    }
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree){
        root = new Node<>(data);
        if(leftTree != null)
            root.left = leftTree.root;
        else
            root.left = null;
        if(rightTree != null)
            root.right = rightTree.root;
        else
            root.right = null;
    }

    public BinaryTree<E> getLeftSubTree(){
        if(root != null && root.left != null)
            return new BinaryTree<>(root.left);
        else
            return null;
    }
    public BinaryTree<E> getRightSubTree(){
        if(root != null && root.right != null)
            return new BinaryTree<>(root.right);
        else
            return null;
    }

    public boolean isLeaf(){
        return (root.left == null && root.right == null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root,1,sb);
        return  sb.toString();
    }
    private void toString(Node<E> node,int depth,StringBuilder sb){
        for(int i=0;i<depth;++i)
            sb.append(" ");
        if(node == null)
            sb.append("null\n");
        else {
            sb.append(node.toString());
            sb.append("\n");
            toString(node.left,depth+1,sb);
            toString(node.right,depth+1,sb);
        }
    }



}
