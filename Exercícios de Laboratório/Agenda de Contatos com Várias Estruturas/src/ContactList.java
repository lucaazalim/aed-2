import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ContactList {

    public static void main(String[] args) {

        HashTable<String, Contact> contactList = new HashTable<>(1000);

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equalsIgnoreCase("fim")) {

                String[] split = input.split(", ");
                Contact contact = new Contact(
                        split[0],
                        split[1],
                        split[2],
                        split[3]);

                contactList.put(contact.getName(), contact);

            }

            int searches = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < searches; i++) {

                String contactName = scanner.nextLine();
                Contact contact = contactList.get(contactName);

                System.out.println(contact);

            }

            System.out.println("Contatos na agenda:");
            contactList.printSorted();

        }

    }

    public static class Contact {

        private final String name, phoneNumber, email, address;

        public Contact(String name, String phoneNumber, String email, String address) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Nome: " + this.name + "\n" +
                    "Telefone: " + this.phoneNumber + "\n" +
                    "E-mail: " + this.email + "\n" +
                    "Endereco: " + this.address;
        }
    }

    public static class HashTable<K extends Comparable<K>, V> {

        private final int capacity;
        private final AVL<Entry<K, V>>[] table;

        @SuppressWarnings("unchecked")
        public HashTable(int capacity) {

            this.capacity = capacity;
            this.table = (AVL<Entry<K, V>>[]) new AVL[capacity];

            for (int i = 0; i < capacity; i++) {
                this.table[i] = new AVL<>();
            }

        }

        private AVL<Entry<K, V>> getTree(K key) {
            int hash = this.hash(key);
            return this.table[hash];
        }

        private int hash(K key) {
            return Math.abs(key.hashCode() % this.capacity);
        }

        public void put(K key, V value) {

            AVL<Entry<K, V>> tree = this.getTree(key);
            tree.add(new Entry<>(key, value));

        }

        public V get(K key) {

            AVL<Entry<K, V>> tree = this.getTree(key);
            Entry<K, V> entry = tree.find(new Entry<>(key));

            if (entry == null) {
                throw new NoSuchElementException();
            }

            return entry.getValue();

        }

        public void printSorted() {

            SortableDoublyLinkedList<Entry<K, V>> sortableValues = new SortableDoublyLinkedList<>();
            Stream.of(this.table).forEach(tree -> tree.walkOrdered(sortableValues::add));
            sortableValues.sort();
            sortableValues.walk(entry -> System.out.println(entry.getValue()));

        }

    }

    public static class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {

        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry(K key) {
            this(key, null);
        }

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }

        @Override
        public boolean equals(Object other) {

            if (this == other) {
                return true;
            }

            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Entry<?, ?> entry = (Entry<?, ?>) other;
            return this.key.equals(entry.getKey());

        }

        @Override
        public int compareTo(Entry<K, V> other) {
            return this.key.compareTo(other.getKey());
        }

    }

    public static class AVL<E extends Comparable<E>> extends BinarySearchTree<E> {

        @Override
        protected Node<E> add(E element, Node<E> node) {
            return this.balance(super.add(element, node));
        }

        @Override
        protected Node<E> remove(E element, Node<E> node) {
            return this.balance(super.remove(element, node));
        }

        private Node<E> balance(Node<E> node) {

            if (node != null) {

                int balanceFactor = node.getBalanceFactor();
                int childBalanceFactor;

                if (balanceFactor > 1) {

                    childBalanceFactor = node.getLeft().getBalanceFactor();

                    // Double rotation
                    if (childBalanceFactor == -1) {
                        node.setLeft(this.rotateLeft(node.getLeft()));
                    }

                    node = this.rotateRight(node);

                } else if (balanceFactor < -1) {

                    childBalanceFactor = node.getRight().getBalanceFactor();

                    if (childBalanceFactor == 1) {
                        node.setRight(this.rotateRight(node.getRight()));
                    }

                    node = this.rotateLeft(node);

                } else {
                    node.updateHeight();
                }

            }

            return node;

        }

        private Node<E> rotateLeft(Node<E> node) {

            Node<E> rightChild = node.getRight();
            Node<E> leftChildOfRightChild = rightChild.getLeft();

            rightChild.setLeft(node);
            node.setRight(leftChildOfRightChild);

            node.updateHeight();
            rightChild.updateHeight();

            return rightChild;

        }

        private Node<E> rotateRight(Node<E> node) {

            Node<E> leftChild = node.getLeft();
            Node<E> rightChildOfLeftChild = leftChild.getRight();

            leftChild.setRight(node);
            node.setLeft(rightChildOfLeftChild);

            node.updateHeight();
            leftChild.updateHeight();

            return leftChild;

        }

    }

    public static class BinarySearchTree<E extends Comparable<E>> {

        private Node<E> root;

        public boolean isEmpty() {
            return this.root == null;
        }

        public DoublyLinkedList<E> values() {
            DoublyLinkedList<E> values = new DoublyLinkedList<>();
            this.walkOrdered(values::add);
            return values;
        }

        public E find(E element) {
            return this.find(element, this.root);
        }

        private E find(E element, Node<E> node) {

            if (node == null) {
                throw new NoSuchElementException();
            }

            int comparison = element.compareTo(node.getElement());

            if (comparison == 0) {
                return node.getElement();
            }

            if (comparison < 0) {
                return this.find(element, node.getLeft());
            }

            return this.find(element, node.getRight());

        }

        public void add(E element) {
            this.root = this.add(element, this.root);
        }

        protected Node<E> add(E element, Node<E> node) {

            if (node == null) {
                return new Node<>(element);
            }

            int comparison = element.compareTo(node.getElement());

            if (comparison == 0) {
                throw new IllegalArgumentException();
            }

            if (comparison < 0) {
                node.setLeft(this.add(element, node.getLeft()));
            } else {
                node.setRight(this.add(element, node.getRight()));
            }

            return node;

        }

        public void remove(E element) {
            this.root = this.remove(element, this.root);
        }

        protected Node<E> remove(E element, Node<E> node) {

            if (node == null) {
                throw new NoSuchElementException();
            }

            int comparison = element.compareTo(node.getElement());

            if (comparison == 0) {

                if (node.getLeft() == null) {
                    return node.getRight();
                }

                if (node.getRight() == null) {
                    return node.getLeft();
                }

                node.setLeft(swapWithPredecessor(node));

            } else if (comparison > 0) {
                node.setRight(remove(element, node.getRight()));
            } else {
                node.setLeft(remove(element, node.getLeft()));
            }

            return node;

        }

        private Node<E> swapWithPredecessor(Node<E> node) {
            return this.swapWithPredecessor(node, node.getLeft());
        }

        private Node<E> swapWithPredecessor(Node<E> node, Node<E> left) {

            if (left.getRight() == null) {
                node.setElement(left.getElement());
                left = left.getLeft();
            } else {
                left.setRight(swapWithPredecessor(node, left.getRight()));
            }

            return left;

        }

        public void walkOrdered(Consumer<E> consumer) {
            if (this.root != null) {
                this.root.walkOrdered(consumer);
            }
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

    }

    public static class Node<T extends Comparable<T>> {

        private T element;
        private Node<T> left, right;
        private int height;

        public Node(T element, Node<T> left, Node<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        public Node(T element) {
            this(element, null, null);
        }

        public Node() {
            this(null, null, null);
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
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

        private static int getChildHeight(Node<?> node) {
            return node == null ? -1 : node.getHeight();
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

    public static class DoublyLinkedList<E> {

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

        public void add(E element) {

            DoubleNode<E> newNode = new DoubleNode<>(element, this.last, null);

            this.last.setNext(newNode);
            this.last = newNode;
            this.size++;

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

            DoubleNode<E> node = this.first.getNext();

            while (node != null) {
                consumer.accept(node.getElement());
                node = node.getNext();
            }

        }

    }

    public static class DoubleNode<E> {

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
                DoubleNode<E> next) {
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

    public static class SortableDoublyLinkedList<E extends Comparable<E>> extends DoublyLinkedList<E> {

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

}
