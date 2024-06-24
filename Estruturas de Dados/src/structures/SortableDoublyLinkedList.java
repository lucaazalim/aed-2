package structures;

import structures.nodes.DoubleNode;

public class SortableDoublyLinkedList<E extends Comparable<E>> extends DoublyLinkedList<E> {

    public void sort() {
        this.quickSort(this.getFirst().getNext(), this.getLast());
    }

    private void quickSort(DoubleNode<E> from, DoubleNode<E> to) {

        if (from != null && to != null && from != to && from != to.getNext()) {

            DoubleNode<E> part = partition(from, to);
            quickSort(from, part.getPrevious());
            quickSort(part.getNext(), to);

        }

    }

    private DoubleNode<E> partition(DoubleNode<E> from, DoubleNode<E> to) {

        DoubleNode<E> part = from.getPrevious(), currentNode = from;

        while (currentNode != to) {

            int comparison = currentNode.getElement().compareTo(to.getElement());

            if (comparison < 0) {
                part = part.getNext();
                this.swap(part, currentNode);
            }

            currentNode = currentNode.getNext();

        }

        part = part.getNext();
        swap(part, to);

        return part;

    }

}