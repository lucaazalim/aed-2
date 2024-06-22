public class Queue<E> implements Cloneable {

    private final Node<E> front; // always the sentinel
    private Node<E> back;
    private int size;

    public Queue() {
        Node<E> sentinel = new Node<>();
        this.front = this.back = sentinel;
    }

    public boolean isEmpty() {
        return this.front == this.back;
    }

    public int size() {
        return this.size;
    }

    public void queue(E element) {

        Node<E> newNode = new Node<>(element);
        this.back.setNext(newNode);
        this.back = newNode;
        this.size++;

    }

    public E dequeue() {
        return this.dequeue(0);
    }

    public E dequeue(int index) {

        Node<E> node = this.peekNode(index);
        Node<E> beforeDequeued = index == 0 ? this.front : this.peekNode(index - 1);

        beforeDequeued.setNext(node.getNext());

        if(node == this.back) {
            this.back = beforeDequeued;
        }

        this.size--;
        return node.getElement();

    }

    public E peek() {
        return this.peek(0);
    }

    public E peek(int index) {
        return this.peekNode(index).getElement();
    }

    private Node<E> peekNode(int index) {

        if (this.isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }

        Node<E> node = this.front.getNext();

        for (int i = 0; i < index; i++) {

            if (node == this.back) {
                throw new NoSuchElementException(
                        "Index " + index + " is invalid. Queue size is " + this.size() + "."
                );
            }

            node = node.getNext();

        }

        return node;

    }

    public void concatenate(Queue<? extends E> otherQueue) {

        Objects.requireNonNull(otherQueue);

        for (int i = 0; i < otherQueue.size(); i++) {
            this.queue(otherQueue.peek(i));
        }

    }

    public boolean contains(E element) {

        for (int i = 0; i < this.size(); i++) {

            E currentElement = this.peek(i);

            if (currentElement.equals(element)) {
                return true;
            }

        }

        return false;

    }

    public int elementsInFrontOf(E element) {

        for (int i = 0; i < this.size(); i++) {

            E currentElement = this.peek(i);

            if (currentElement.equals(element)) {
                return i;
            }

        }

        return 0;

    }

    public Queue<E> dequeueEvenIndexesElements() {

        Queue<E> evenIndexes = new Queue<>();

        for (int i = 0; i < this.size(); i++) {
            evenIndexes.queue(this.dequeue(i));
        }

        return evenIndexes;

    }

    @Override
    public Queue<E> clone() {

        Queue<E> newQueue = new Queue<>();

        for (int i = 0; i < this.size(); i++) {

            E currentElement = this.peek(i);
            newQueue.queue(currentElement);

        }

        return newQueue;

    }

}
