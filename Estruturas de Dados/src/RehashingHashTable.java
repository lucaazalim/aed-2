import java.util.NoSuchElementException;

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
        return (Math.abs(key.hashCode() % this.capacity) + tries) % this.capacity;
    }

    public void put(K key, V value) {

        int tries = 0;
        int hash = this.hash(key, tries);

        while (this.table[hash] != null) {
            hash = this.hash(key, ++tries);
        }

        this.table[hash] = new Entry<>(key, value);

    }

    public V remove(K key) {

        int tries = 0;
        int hash = this.hash(key, tries);

        while (this.table[hash] != null && !this.table[hash].getKey().equals(key)) {
            hash = this.hash(key, ++tries);
        }

        if (this.table[hash] == null) {
            throw new IllegalArgumentException();
        }

        V value = this.table[hash].getValue();
        this.table[hash] = this.removed;

        return value;

    }

    public V get(K key) {

        int tries = 0;
        int hash = this.hash(key, tries);

        while (this.table[hash] != null && !this.table[hash].getKey().equals(key)) {
            hash = this.hash(key, ++tries);
        }

        if (this.table[hash] == null) {
            throw new NoSuchElementException();
        }

        return this.table[hash].getValue();

    }

    public void restore() {

        for (int i = 0; i < this.capacity; i++) {

            Entry<K, V> entry = this.table[i];

            if (entry != this.removed) {
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
