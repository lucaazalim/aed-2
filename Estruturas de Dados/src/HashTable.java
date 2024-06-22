import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class HashTable<K extends Comparable<K>, V> {

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
        return table[hash];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() / this.capacity);
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
        Stream.of(this.table).forEach(tree -> tree.values().walk(sortableValues::add));
        sortableValues.walk(entry -> System.out.println(entry.getValue()));

    }

}
