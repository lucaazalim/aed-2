public class RerashingHashTable<K, V> {

    private final int capacity;
    private final Entry<K, V>[] table;

    public RerashingHashTable(int capacity) {
        this.capacity = capacity;
        this.table = (Entry<K, V>[]) new Entry[capacity];
    }

    // Soon!

}
