public class ReservedAreaHashTable<K, V> {

    private final int capacity, reserve;
    private final Entry<K, V>[] table;

    @SuppressWarnings("unchecked")
    public ReservedAreaHashTable(int capacity, int reserve) {
        this.capacity = capacity;
        this.reserve = reserve;
        this.table = (Entry<K, V>[]) new Entry[capacity + reserve];
    }

    public int hash(K key) {
        return Math.abs(key.hashCode() % this.capacity);
    }

    public void put(K key, V value) {
        int hash = this.hash(key);

        if (this.table[hash] == null) {
            this.table[hash] = new Entry<>(key, value);
        } else {
            for (int i = this.capacity; i < this.capacity + this.reserve; i++) {
                if (this.table[i] == null) {
                    this.table[i] = new Entry<>(key, value);
                    break;
                }
            }

            throw new IllegalStateException();
        }

    }

    public V find(K key) {
        return this.table[this.findIndex(key)].getValue();
    }

    private int findIndex(K key) {

        int hash = this.hash(key);

        if (this.table[hash] != null && this.table[hash].getKey().equals(key)) {
            return hash;
        }

        for (int i = this.capacity; i < this.capacity + this.reserve; i++) {
            if (this.table[i] != null && this.table[i].getKey().equals(key)) {
                return i;
            }
        }

        throw new IllegalArgumentException();

    }

    public V remove(K key) {

        int index = this.findIndex(key);
        V value = this.table[index].getValue();
        this.table[index] = null;

        return value;

    }

}
