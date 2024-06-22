import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DoublyLinkedList<E> {

    private final DoubleNode<E> first; // always the sentinel
    private DoubleNode<E> last;
    private int size;

    public DoublyLinkedList() {
        DoubleNode<E> sentinel = new DoubleNode<>();
        this.first = this.last = sentinel;
        this.size = 0;
    }

    protected DoubleNode<E> getFirst() {
        return this.first;
    }

    protected DoubleNode<E> getLast() {
        return this.last;
    }

    public boolean isEmpty() {
        return this.first == this.last;
    }

    public int getSize() {
        return this.size;
    }

    public E get(int index) {
        return getNode(index).getElement();
    }

    protected DoubleNode<E> getNode(int index) {

        DoubleNode<E> node;

        if (index < (this.size >> 1)) {
            node = this.first.getNext();
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
        } else {
            node = this.last;
            for (int i = this.size - 1; i > index; i--) {
                node = node.getPrevious();
            }
        }

        return node;

    }

    public E get(E element) {

        DoubleNode<E> node = this.first.getNext();

        while (node != null) {
            if (node.getElement().equals(element)) {
                return node.getElement();
            }
            node = node.getNext();
        }

        throw new NoSuchElementException();

    }

    public void add(E element) {

        DoubleNode<E> newNode = new DoubleNode<>(element, this.last, null);

        this.last.setNext(newNode);
        this.last = newNode;
        this.size++;

    }

    public void addAll(DoublyLinkedList<E> list) {

        DoubleNode<E> node = list.getFirst().getNext();

        while (node != null) {
            this.add(node.getElement());
            node = node.getNext();
        }

    }

    public E remove(E element) {

        DoubleNode<E> node = this.first.getNext();

        while (node != null) {
            if (Objects.equals(element, node.getElement())) {
                node.getPrevious().setNext(node.getNext());
                node.getNext().setPrevious(node.getPrevious());
                this.size--;
                return node.getElement();
            }
            node = node.getNext();
        }

        throw new NoSuchElementException();

    }

    public E removeLast() {

        if (this.isEmpty()) {
            throw new IllegalStateException();
        }

        E removed = this.last.getElement();
        DoubleNode<E> previous = this.last.getPrevious();

        previous.setNext(null);
        this.last = previous;
        this.size--;

        return removed;

    }

    public void swap(int indexFrom, int indexTo) {

        DoubleNode<E> nodeFrom = getNode(indexFrom);
        DoubleNode<E> nodeTo = getNode(indexTo);

        E elementFrom = getNode(indexFrom).getElement();
        E elementTo = getNode(indexTo).getElement();

        nodeFrom.setElement(elementTo);
        nodeTo.setElement(elementFrom);

    }

    public void swap(DoubleNode<E> from, DoubleNode<E> to) {

        E toElement = to.getElement();

        to.setElement(from.getElement());
        from.setElement(toElement);

    }

    public void walk(Consumer<E> consumer) {

        DoubleNode<E> node = this.first;

        while (node != null) {
            consumer.accept(node.getElement());
            node = node.getNext();
        }

    }

    public boolean contains(E element) {

        DoubleNode<E> node = this.first.getNext();

        while (node != null) {
            if (Objects.equals(element, node.getElement())) {
                return true;
            }
            node = node.getNext();
        }

        return false;

    }

    public boolean contains(Predicate<E> predicate) {

        DoubleNode<E> node = this.first.getNext();

        while (node != null) {
            if (predicate.test(node.getElement())) {
                return true;
            }
            node = node.getNext();
        }

        return false;

    }

}
