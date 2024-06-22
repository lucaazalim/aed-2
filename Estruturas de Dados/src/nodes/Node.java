public class Node<T> {

    private T element;
    private Node<T> next;

    public Node() {
        this(null);
    }

    public Node(T element) {
        this(element, null);
    }

    public Node(T element, Node<T> next) {
        this.setElement(element);
        this.setNext(next);
    }

    public T getElement() {
        return this.element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

}
