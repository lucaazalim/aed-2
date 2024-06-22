import java.util.function.Consumer;

public class TreeNode<T extends Comparable<T>> {

    private T element;
    private TreeNode<T> left, right;
    private int height;

    public TreeNode(T element, TreeNode<T> left, TreeNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    public TreeNode(T element) {
        this(element, null, null);
    }

    public TreeNode() {
        this(null, null, null);
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    public int getGrade() {

        if (this.left == null) {

            if (this.right == null) {
                return 0;
            }

            return 1;

        } else {

            if (this.right == null) {
                return 1;
            }

            return 2;

        }

    }

    public boolean isLeaf() {
        return this.getGrade() == 0;
    }

    public int getLeaves() {

        if (this.isLeaf()) {
            return 1;
        }

        int leaves = 0;

        if (this.left != null) {
            leaves += this.left.getLeaves();
        }

        if (this.right != null) {
            leaves += this.right.getLeaves();
        }

        return leaves;

    }

    public int getHeight() {
        return this.height;
    }

    private static int getChildHeight(TreeNode<?> treeNode) {
        return treeNode == null ? -1 : treeNode.getHeight();
    }

    public void updateHeight() {

        int leftHeight = getChildHeight(this.left);
        int rightHeight = getChildHeight(this.right);

        this.height = Math.max(leftHeight, rightHeight) + 1;

    }

    public int getSize() {

        int size = 0;

        if (this.left != null) {
            size += this.left.getSize();
        }

        if (this.right != null) {
            size += this.right.getSize();
            ;
        }

        return size + 1;

    }

    public void walkOrdered(Consumer<T> consumer) {

        if (this.left != null) {
            this.left.walkOrdered(consumer);
        }

        consumer.accept(this.element);

        if (this.right != null) {
            this.right.walkOrdered(consumer);
        }

    }

    public int getBalanceFactor() {
        return getChildHeight(this.left) - getChildHeight(this.right);
    }

}
