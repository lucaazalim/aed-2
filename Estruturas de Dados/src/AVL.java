public class AVL<E extends Comparable<E>> extends BinarySearchTree<E> {

    @Override
    protected TreeNode<E> add(E element, TreeNode<E> treeNode) {
        return this.balance(super.add(element, treeNode));
    }

    @Override
    protected TreeNode<E> remove(E element, TreeNode<E> treeNode) {
        return this.balance(super.remove(element, treeNode));
    }

    private TreeNode<E> balance(TreeNode<E> treeNode) {

        if (treeNode != null) {

            int balanceFactor = treeNode.getBalanceFactor();
            int childBalanceFactor;

            if (balanceFactor > 1) {

                childBalanceFactor = treeNode.getLeft().getBalanceFactor();

                // Double rotation
                if (childBalanceFactor == -1) {
                    treeNode.setLeft(this.rotateLeft(treeNode.getLeft()));
                }

                treeNode = this.rotateRight(treeNode);

            } else if (balanceFactor < -1) {

                childBalanceFactor = treeNode.getRight().getBalanceFactor();

                if (childBalanceFactor == 1) {
                    treeNode.setRight(this.rotateRight(treeNode.getRight()));
                }

                treeNode = this.rotateLeft(treeNode);

            } else {
                treeNode.updateHeight();
            }

        }

        return treeNode;

    }

    private TreeNode<E> rotateLeft(TreeNode<E> treeNode) {

        TreeNode<E> rightChild = treeNode.getRight();
        TreeNode<E> leftChildOfRightChild = rightChild.getLeft();

        rightChild.setLeft(treeNode);
        treeNode.setRight(leftChildOfRightChild);

        treeNode.updateHeight();
        rightChild.updateHeight();

        return rightChild;

    }

    private TreeNode<E> rotateRight(TreeNode<E> treeNode) {

        TreeNode<E> leftChild = treeNode.getLeft();
        TreeNode<E> rightChildOfLeftChild = leftChild.getRight();

        leftChild.setRight(treeNode);
        treeNode.setLeft(rightChildOfLeftChild);

        treeNode.updateHeight();
        leftChild.updateHeight();

        return leftChild;

    }

}
