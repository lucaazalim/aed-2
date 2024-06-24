package structures;

import java.util.NoSuchElementException;
import java.util.Objects;

import structures.nodes.Node;

public class LinkedList<E> {

    private final Node<E> first; // always the sentinel
    private Node<E> last;
    private int size;

    public LinkedList() {
        Node<E> sentinel = new Node<>();
        this.first = this.last = sentinel;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.first == this.last;
    }

    public int getSize() {
        return this.size;
    }

    public E get(int index) {

        if (index >= this.size) {
            throw new NoSuchElementException();
        }

        Node<E> currentNode = this.first.getNext();

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode.getElement();

    }

    public void add(E newElement) {
        this.add(newElement, this.size);
    }

    public void add(E newElement, int index) {

        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> predecessor = this.first;

        for (int i = 0; i < index; i++) {
            predecessor = predecessor.getNext();
        }

        Node<E> successor = predecessor.getNext();
        Node<E> newNode = new Node<>(newElement);

        predecessor.setNext(newNode);
        newNode.setNext(successor);

        if (index == this.size) {
            this.last = newNode;
        }

        this.size++;

    }

    public E removeFirst() {
        return this.remove(0);
    }

    public E remove(int index) {

        this.checkIndex(index);

        Node<E> predecessor = this.first;

        for (int i = 0; i < index; i++) {
            predecessor = predecessor.getNext();
        }

        Node<E> removed = predecessor.getNext();
        Node<E> successor = removed.getNext();

        predecessor.setNext(successor);

        if (removed == this.last) {
            this.last = predecessor;
        }

        this.size--;

        return removed.getElement();

    }

    public E remove(E element) {

        Node<E> predecessor = this.first;

        for (int i = 0; i < this.size; i++) {

            E successorElement = predecessor.getNext().getElement();

            if (Objects.equals(successorElement, element)) {
                break;
            }

            predecessor = predecessor.getNext();

        }

        if (predecessor == this.last) {
            throw new NoSuchElementException();
        }

        Node<E> removed = predecessor.getNext();
        Node<E> successor = removed.getNext();

        predecessor.setNext(successor);

        if (removed == this.last) {
            this.last = predecessor;
        }

        this.size--;

        return removed.getElement();

    }

    public void swap(E aElement, E bElement) {

        Node<E> aNode = null, bNode = null, currentNode = this.first;

        while ((aNode == null || bNode == null) && (currentNode = currentNode.getNext()) != null) {

            E currentElement = currentNode.getElement();

            if (aNode == null && Objects.equals(aElement, currentElement)) {
                aNode = currentNode;
            }

            if (bNode == null && Objects.equals(bElement, currentElement)) {
                bNode = currentNode;
            }

        }

        if (aNode == null || bNode == null) {
            throw new NoSuchElementException();
        }

        E previousAElement = aNode.getElement();

        aNode.setElement(bNode.getElement());
        bNode.setElement(previousAElement);

    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void print() {

        Node<E> currentElement = this.first;

        for (int i = 0; i < this.size; i++) {
            currentElement = currentElement.getNext();
            System.out.println(currentElement.getElement());
        }

    }

}
