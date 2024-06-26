package structures.nodes;

public class Entry<K, V> {

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

}