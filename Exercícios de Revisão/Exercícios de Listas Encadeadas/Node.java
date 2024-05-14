public class Node<E> {

    private E element; // modificador final removido devido ao método swap() (ou trocar()).
    private Node<E> next;

    public Node() {
        this(null);
    }

    public Node(E element) {
        this(element, null);
    }

    public Node(E element, Node<E> next) {
        this.setElement(element);
        this.setNext(next);
    }

    public E getElement() {
        return this.element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNext() {
        return this.next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

}
