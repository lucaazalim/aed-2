package structures;

import java.util.NoSuchElementException;
import java.util.Objects;

import structures.nodes.Node;

public class Stack<E> {

    private Node<E> top;
    private final Node<E> bottom; // always the sentinel
    private int size;

    public Stack() {
        this.top = this.bottom = new Node<>();
    }

    public boolean isEmpty() {
        return this.bottom == this.top;
    }

    public int size() {
        return this.size;
    }

    public void push(E element) {
        this.top = new Node<>(element, this.top);
        this.size++;
    }

    public void pushToBottom(E element) {

        if (this.isEmpty()) {

            this.push(element);

        } else {

            Node<E> newNode = new Node<>(element, this.bottom);
            Node<E> bottomNode = this.peekBottomNode();
            bottomNode.setNext(newNode);
            this.size++;

        }

    }

    public E pop() {
        return this.pop(0);
    }

    public E popBottom() {
        return this.pop(this.size() - 1);
    }

    public E pop(int index) {
        return this.popNode(index).getElement();
    }

    private Node<E> popNode(int index) {

        Node<E> popped = this.peekNode(index);

        if (index == 0) {

            this.top = this.top.getNext();

        } else {

            Node<E> behindPopped = this.peekNode(index - 1);
            Node<E> afterPopped = this.size() >= index + 1 ? this.peekNode(index + 1) : this.bottom;
            behindPopped.setNext(afterPopped);

        }

        this.size--;
        return popped;

    }

    public E peek() {
        return this.peek(0);
    }

    public E peekBottom() {
        return this.peekBottomNode().getElement();
    }

    public E peek(int index) {
        return this.peekNode(index).getElement();
    }

    private Node<E> peekBottomNode() {
        return this.peekNode(this.size() - 1);
    }

    private Node<E> peekNode(int index) {

        if (this.isEmpty()) {
            throw new NoSuchElementException("The stack is empty.");
        }

        Node<E> node = this.top;

        for (int i = 0; i < index; i++) {

            if (node == this.bottom) {
                throw new NoSuchElementException(
                        "Index " + index + " is invalid. Stack size is " + this.size() + ".");
            }

            node = node.getNext();

        }

        return node;

    }

    public void concatenate(Stack<? extends E> otherStack) {

        Objects.requireNonNull(otherStack);

        for (int i = 0; i < otherStack.size(); i++) {
            Node<? extends E> node = otherStack.peekNode(i);
            this.pushToBottom(node.getElement());
        }

    }

    public void reverse() {

        for (int i = 1; i < this.size(); i++) {
            E popped = this.pop(i);
            this.push(popped);
        }

    }

}
