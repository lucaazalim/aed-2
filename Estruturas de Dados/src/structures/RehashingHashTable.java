package structures;

import java.util.NoSuchElementException;

import structures.nodes.Entry;

public class RehashingHashTable<K, V> {

    private final int capacity;
    private final Entry<K, V>[] table;
    private final RemovedEntry<K, V> removed;

    public RehashingHashTable(int capacity) {
        this.capacity = capacity;
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.removed = new RemovedEntry<>();
    }

    private int hash(K key, int tries) {
        return (Math.abs(key.hashCode()) + tries) % this.capacity;
    }

    public void clear() {
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = null;
        }
    }

    public void put(K key, V value) {

        int tries = 0;
        int startingHash = this.hash(key, tries);
        int hash = startingHash;

        while (this.table[hash] != null) {
            hash = this.hash(key, ++tries);

            if (hash == startingHash) {
                throw new IllegalStateException();
            }
        }

        this.table[hash] = new Entry<>(key, value);

    }

    public V remove(K key) {

        int tries = 0;
        int startingHash = this.hash(key, tries);
        int hash = startingHash;

        while (this.table[hash] != null && !this.table[hash].getKey().equals(key)) {
            hash = this.hash(key, ++tries);

            if (hash == startingHash) {
                break;
            }
        }

        if (this.table[hash] == null) {
            throw new NoSuchElementException();
        }

        V value = this.table[hash].getValue();
        this.table[hash] = this.removed;

        return value;

    }

    public V get(K key) {

        int tries = 0;
        int startingHash = this.hash(key, tries);
        int hash = startingHash;

        while (this.table[hash] != null && !this.table[hash].getKey().equals(key)) {
            hash = this.hash(key, ++tries);

            if (hash == startingHash) {
                throw new NoSuchElementException();
            }
        }

        return this.table[hash].getValue();

    }

    public void restore() {

        Entry<K, V>[] clone = this.table.clone();
        this.clear();

        for (Entry<K, V> entry : clone) {
            if (entry != null && entry != this.removed) {
                this.put(entry.getKey(), entry.getValue());
            }
        }

    }

    private static class RemovedEntry<K, V> extends Entry<K, V> {
        public RemovedEntry() {
            super(null, null);
        }
    }

}
