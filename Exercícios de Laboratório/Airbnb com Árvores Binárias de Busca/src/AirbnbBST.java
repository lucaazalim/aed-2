import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AirbnbBST {

    private static final Path DATA_PATH = Path.of("/tmp/dados_airbnb.txt");

    public static void main(String[] args) throws IOException {

        Map<Integer, Accommodation> accommodationMap = readData();

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equals("FIM")) {

                BinarySearchTree<Accommodation> binarySearchTree = new BinarySearchTree<>();
                int accommodations = Integer.parseInt(input);

                for (int i = 0; i < accommodations; i++) {

                    int roomId = Integer.parseInt(scanner.nextLine());
                    Accommodation accommodation = accommodationMap.get(roomId);
                    binarySearchTree.add(accommodation);

                }

                System.out.println("Numero de folhas da arvore: " + binarySearchTree.getLeaves());
                System.out.println("Numero de acomodacoes presentes na arvore: " + binarySearchTree.getSize());
                System.out.println("Altura da arvore: " + binarySearchTree.getHeight());

            }

        }

    }

    public static Map<Integer, Accommodation> readData() throws IOException {

        Map<Integer, Accommodation> data = new HashMap<>();
        String fileContent = Files.readString(DATA_PATH);
        String[] lines = fileContent.split("\n");

        for (int i = 1; i < lines.length; i++) {

            String[] fields = lines[i].split("\t");

            Accommodation accommodation = new Accommodation(
                    Integer.parseInt(fields[0]), Integer.parseInt(fields[1]),
                    fields[2], fields[3], fields[4], fields[5],
                    Integer.parseInt(fields[6]), Double.parseDouble(fields[7]),
                    Integer.parseInt(fields[8]), Double.parseDouble(fields[9]),
                    Double.parseDouble(fields[10]), fields[11].trim());

            data.put(accommodation.getRoomId(), accommodation);

        }

        return data;

    }

    public static class Accommodation implements Comparable<Accommodation> {

        private int roomId;
        private int hostId;
        private String roomType;
        private String country;
        private String city;
        private String neighbourhood;
        private int reviews;
        private double overallSatisfaction;
        private int accommodates;
        private double bedrooms;
        private double price;
        private String propertyType;

        public Accommodation(int roomId, int hostId, String roomType, String country, String city, String neighbourhood,
                int reviews, double overallSatisfaction, int accommodates, double bedrooms, double price,
                String propertyType) {
            this.roomId = roomId;
            this.hostId = hostId;
            this.roomType = roomType;
            this.country = country;
            this.city = city;
            this.neighbourhood = neighbourhood;
            this.reviews = reviews;
            this.overallSatisfaction = overallSatisfaction;
            this.accommodates = accommodates;
            this.bedrooms = bedrooms;
            this.price = price;
            this.propertyType = propertyType;
        }

        public Accommodation() {
            this.roomId = 0;
            this.hostId = 0;
            this.roomType = "";
            this.country = "";
            this.city = "";
            this.neighbourhood = "";
            this.reviews = 0;
            this.overallSatisfaction = 0;
            this.accommodates = 0;
            this.bedrooms = 0;
            this.price = 0;
            this.propertyType = "";
        }

        public String toString() {
            return "[" + roomId + " ## " + hostId + " ## " + roomType
                    + " ## " + country + " ## " + city + " ## " + neighbourhood
                    + " ## " + reviews + " ## " + overallSatisfaction + " ## "
                    + accommodates + " ## " + bedrooms + " ## " + price + " ## "
                    + propertyType + "]";
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public int getHostId() {
            return hostId;
        }

        public void setHostId(int hostId) {
            this.hostId = hostId;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(String neighbourhood) {
            this.neighbourhood = neighbourhood;
        }

        public int getReviews() {
            return reviews;
        }

        public void setReviews(int reviews) {
            this.reviews = reviews;
        }

        public double getOverallSatisfaction() {
            return overallSatisfaction;
        }

        public void setOverallSatisfaction(double overallSatisfaction) {
            this.overallSatisfaction = overallSatisfaction;
        }

        public int getAccommodates() {
            return accommodates;
        }

        public void setAccommodates(int accommodates) {
            this.accommodates = accommodates;
        }

        public double getBedrooms() {
            return bedrooms;
        }

        public void setBedrooms(double bedrooms) {
            this.bedrooms = bedrooms;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        @Override
        public int compareTo(Accommodation other) {
            return Comparator.comparing(Accommodation::getCountry)
                    .thenComparing(Accommodation::getCity)
                    .thenComparing(Accommodation::getNeighbourhood)
                    .thenComparing(Accommodation::getRoomId)
                    .compare(this, other);
        }

    }

    public static class BinarySearchTree<E extends Comparable<E>> {

        private Node<E> root;

        public boolean isEmpty() {
            return this.root == null;
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

        private Node<E> add(E element, Node<E> node) {

            if (node == null) {
                return new Node<>(element);
            }

            int comparison = element.compareTo(node.getElement());

            if (comparison == 0) {
                throw new IllegalArgumentException();
            }

            if (comparison < 0) {
                node.setLeft(add(element, node.getLeft()));
            } else {
                node.setRight(add(element, node.getRight()));
            }

            return node;

        }

        public int getLeaves() {
            return this.getLeaves(this.root);
        }

        private int getLeaves(Node<E> node) {

            if (node == null) {
                return 0;
            }

            if (node.isLeaf()) {
                return 1;
            }

            return this.getLeaves(node.getLeft()) + this.getLeaves(node.getRight());

        }

        public int getSize() {
            return this.getSize(this.root);
        }

        private int getSize(Node<E> node) {

            if (node == null) {
                return 0;
            }

            return this.getSize(node.getLeft()) + this.getSize(node.getRight()) + 1;

        }

        public int getHeight() {
            return this.getHeight(this.root);
        }

        private int getHeight(Node<E> node) {

            if (node == null) {
                return -1;
            }

            int leftHeight = this.getHeight(node.getLeft());
            int rightHeight = this.getHeight(node.getRight());

            return Math.max(leftHeight, rightHeight) + 1;

        }

    }

    public static class Node<T extends Comparable<T>> {

        private T element;
        private Node<T> left, right;

        public Node(T element, Node<T> left, Node<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
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

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

    }

}
