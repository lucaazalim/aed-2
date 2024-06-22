import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class BinarySearchTree<E extends Comparable<E>> {

    private TreeNode<E> root;

    public boolean isEmpty() {
        return this.root == null;
    }

    public DoublyLinkedList<E> values() {
        DoublyLinkedList<E> values = new DoublyLinkedList<>();
        this.walkOrdered(values::add);
        return values;
    }

    public E find(E element) {
        return this.find(element, this.root).getElement();
    }

    private TreeNode<E> find(E element, TreeNode<E> treeNode) {

        if (treeNode == null) {
            throw new NoSuchElementException();
        }

        int comparison = element.compareTo(treeNode.getElement());

        if (comparison == 0) {
            return treeNode;
        }

        if (comparison < 0) {
            return this.find(element, treeNode.getLeft());
        }

        return this.find(element, treeNode.getRight());

    }

    public void add(E element) {
        this.root = this.add(element, this.root);
    }

    protected TreeNode<E> add(E element, TreeNode<E> treeNode) {

        if (treeNode == null) {
            return new TreeNode<>(element);
        }

        int comparison = element.compareTo(treeNode.getElement());

        if (comparison == 0) {
            throw new IllegalArgumentException();
        }

        if (comparison < 0) {
            treeNode.setLeft(this.add(element, treeNode.getLeft()));
        } else {
            treeNode.setRight(this.add(element, treeNode.getRight()));
        }

        return treeNode;

    }

    public void remove(E element) {
        this.root = this.remove(element, this.root);
    }

    protected TreeNode<E> remove(E element, TreeNode<E> treeNode) {

        if (treeNode == null) {
            throw new NoSuchElementException();
        }

        int comparison = element.compareTo(treeNode.getElement());

        if (comparison == 0) {

            if (treeNode.getLeft() == null) {
                return treeNode.getRight();
            }

            if (treeNode.getRight() == null) {
                return treeNode.getLeft();
            }

            treeNode.setLeft(swapWithPredecessor(treeNode));

        } else if (comparison > 0) {
            treeNode.setRight(remove(element, treeNode.getRight()));
        } else {
            treeNode.setLeft(remove(element, treeNode.getLeft()));
        }

        return treeNode;

    }

    private TreeNode<E> swapWithPredecessor(TreeNode<E> treeNode) {
        return this.swapWithPredecessor(treeNode, treeNode.getLeft());
    }

    private TreeNode<E> swapWithPredecessor(TreeNode<E> treeNode, TreeNode<E> left) {

        if (left.getRight() == null) {
            treeNode.setElement(left.getElement());
            left = left.getLeft();
        } else {
            left.setRight(swapWithPredecessor(treeNode, left.getRight()));
        }

        return left;

    }

    public int getLeaves() {
        return this.root == null ? 0 : this.root.getLeaves();
    }

    public int getHeight() {
        return this.root == null ? -1 : this.root.getHeight();
    }

    public int getSize() {
        return this.root == null ? 0 : this.root.getSize();
    }

    public E getMinimum() {

        if (this.root == null) {
            throw new NoSuchElementException();
        }

        return this.root.getMinimum();

    }

    public E getMaximum() {

        if (this.root == null) {
            throw new NoSuchElementException();
        }

        return this.root.getMaximum();

    }

    public void walkPreOrdered(Consumer<E> consumer) {
        if (this.root != null) {
            this.root.walkPreOrdered(consumer);
        }
    }

    public void walkOrdered(Consumer<E> consumer) {
        if (this.root != null) {
            this.root.walkOrdered(consumer);
        }
    }

    public void walkReverseOrdered(Consumer<E> consumer) {
        if (this.root != null) {
            this.root.walkReverseOrdered(consumer);
        }
    }

    public void walkPostOrdered(Consumer<E> consumer) {
        if (this.root != null) {
            this.root.walkPostOrdered(consumer);
        }
    }

    public BinarySearchTree<E> subtreeFrom(E element) {

        TreeNode<E> treeNode = this.find(element, this.root);
        BinarySearchTree<E> subtree = new BinarySearchTree<>();

        if (treeNode.getRight() != null) {
            subtree.root = treeNode.getRight().clone();
        }

        subtree.add(element);

        return subtree;

    }

    public boolean isRoot(E element) {
        return Objects.equals(element, this.root.getElement());
    }

    public E getPredecessor(E element) {

        TreeNode<E> treeNode = this.find(element, this.root);

        if (treeNode.getLeft() == null) {
            throw new NoSuchElementException();
        }

        return treeNode.getLeft().getMaximum();

    }

    public E getSuccessor(E element) {

        TreeNode<E> treeNode = this.find(element, this.root);

        if (treeNode.getRight() == null) {
            throw new NoSuchElementException();
        }

        return treeNode.getRight().getMinimum();

    }

    public BinarySearchTree<E> clone() {
        BinarySearchTree<E> clone = new BinarySearchTree<>();
        clone.root = this.root.clone();
        return clone;
    }

}