package structures;

public class Test {

    public static void main(String[] args) {

        ReservedAreaHashTable<String, Integer> hashTable = new ReservedAreaHashTable<>(2, 1);

        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.put("A", 3);

        System.out.println(hashTable.get("A"));

    }

    public static class Key {

        private final String value;

        public Key(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }

            Key key = (Key) obj;

            return this.value.equals(key.value);
        }
    }

}
