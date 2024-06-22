public class DoubleNode<E> {

    private E element;
    private DoubleNode<E> previous;
    private DoubleNode<E> next;

    public DoubleNode() {
        this(null);
    }

    public DoubleNode(E element) {
        this(element, null, null);
    }

    public DoubleNode(
            E element,
            DoubleNode<E> previous,
            DoubleNode<E> next
    ) {
        this.element = element;
        this.setPrevious(previous);
        this.setNext(next);
    }

    public E getElement() {
        return this.element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public DoubleNode<E> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleNode<E> previous) {
        this.previous = previous;
    }

    public DoubleNode<E> getNext() {
        return this.next;
    }

    public void setNext(DoubleNode<E> next) {
        this.next = next;
    }

}
