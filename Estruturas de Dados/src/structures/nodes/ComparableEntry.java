package structures.nodes;

public class ComparableEntry<K extends Comparable<K>, V> extends Entry<K, V>
        implements Comparable<ComparableEntry<K, V>> {

    public ComparableEntry(K key) {
        super(key);
    }

    public ComparableEntry(K key, V value) {
        super(key, value);
    }

    @Override
    public int compareTo(ComparableEntry<K, V> other) {
        return other.getKey().compareTo(this.getKey());
    }

}
