package structures;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

import structures.nodes.Entry;

public class DoublyLinkedListHashTable<K extends Comparable<K>, V> {

    private final int capacity;
    private final DoublyLinkedList<Entry<K, V>>[] table;

    @SuppressWarnings("unchecked")
    public DoublyLinkedListHashTable(int capacity) {

        this.capacity = capacity;
        this.table = (DoublyLinkedList<Entry<K, V>>[]) new DoublyLinkedList[capacity];

        for (int i = 0; i < capacity; i++) {
            this.table[i] = new DoublyLinkedList<>();
        }

    }

    public boolean isEmpty() {
        return Stream.of(this.table).allMatch(DoublyLinkedList::isEmpty);
    }

    public int getSize() {
        return Stream.of(this.table).mapToInt(DoublyLinkedList::getSize).sum();
    }

    private DoublyLinkedList<Entry<K, V>> getList(K key) {
        int hash = this.hash(key);
        return table[hash];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % this.capacity);
    }

    public void put(K key, V value) {

        DoublyLinkedList<Entry<K, V>> list = this.getList(key);
        Entry<K, V> entry = new Entry<>(key, value);

        if (list.contains(entry)) {
            throw new IllegalArgumentException();
        }

        list.add(new Entry<>(key, value));

    }

    public void replace(K key, V value) {

        DoublyLinkedList<Entry<K, V>> list = this.getList(key);
        Entry<K, V> entry = new Entry<>(key, value);

        if (list.contains(entry)) {
            list.remove(entry);
        }

        list.add(new Entry<>(key, value));

    }

    public V get(K key) {

        DoublyLinkedList<Entry<K, V>> list = this.getList(key);
        Entry<K, V> entry = list.get(new Entry<>(key));

        if (entry == null) {
            throw new NoSuchElementException();
        }

        return entry.getValue();

    }

    public boolean containsValue(V value) {
        return Stream.of(this.table).anyMatch(list -> list.contains(entry -> Objects.equals(value, entry.getValue())));
    }

    public DoublyLinkedList<Entry<K, V>> getEntries() {
        DoublyLinkedList<Entry<K, V>> entries = new DoublyLinkedList<>();
        Stream.of(this.table).forEach(entries::addAll);
        return entries;
    }

    public DoublyLinkedList<K> getKeys() {
        DoublyLinkedList<K> keys = new DoublyLinkedList<>();
        Stream.of(this.table).forEach(list -> list.walk(entry -> keys.add(entry.getKey())));
        return keys;
    }

    public DoublyLinkedList<V> getValues() {
        DoublyLinkedList<V> values = new DoublyLinkedList<>();
        Stream.of(this.table).forEach(list -> list.walk(entry -> values.add(entry.getValue())));
        return values;
    }

}
