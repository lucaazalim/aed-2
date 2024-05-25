import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AirbnbWithLinkedList {

    private static final Path DATA_PATH = Path.of("/tmp/dados_airbnb.txt");

    public static void main(String[] args) throws IOException {

        Map<Integer, Accommodation> accomodationsMap = readData();
        LinkedList<Accommodation> accommodationList = new LinkedList<>();

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equals("FIM")) {

                int id = Integer.parseInt(input);

                if (!accomodationsMap.containsKey(id)) {
                    throw new NoSuchElementException("(1) Acomodação " + id + " não existe.");
                }

                Accommodation accommodation = accomodationsMap.get(id);
                accommodationList.insertFirst(accommodation);

            }

            int operations = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < operations; i++) {

                String operation = scanner.next();

                switch (operation) {

                    case "II" -> {

                        int id = Integer.parseInt(scanner.nextLine().trim());

                        if (!accomodationsMap.containsKey(id)) {
                            throw new NoSuchElementException("(2) Acomodação " + id + " não existe.");
                        }

                        Accommodation accommodation = accomodationsMap.get(id);
                        accommodationList.insertFirst(accommodation);

                    }

                    case "RF" -> {
                        Accommodation removed = accommodationList.removeLast();
                        System.out.println("(R) " + removed.getRoomId());
                    }

                    default -> throw new IllegalArgumentException("Operação inválida: " + operation);

                }

            }

            accommodationList.print();

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

    public static class LinkedList<E> {

        private final Node<E> first; // always the sentinel
        private Node<E> last;
        private int size;

        public LinkedList() {
            Node<E> sentinel = new Node<>();
            this.first = this.last = sentinel;
            this.size = 0;
        }

        public boolean isEmpty() {
            return this.first == this.last;
        }

        public void insertFirst(E newElement) {
            this.insert(newElement, 0);
        }

        public void insert(E newElement, int index) {

            if (index < 0 || index > this.size) {
                throw new IndexOutOfBoundsException();
            }

            Node<E> predecessor = this.first;

            for (int i = 0; i < index; i++) {
                predecessor = predecessor.getNext();
            }

            Node<E> successor = predecessor.getNext();
            Node<E> newNode = new Node<>(newElement);

            predecessor.setNext(newNode);
            newNode.setNext(successor);

            if (index == this.size) {
                this.last = newNode;
            }

            this.size++;

        }

        public E removeLast() {
            return this.remove(this.size - 1);
        }

        public E remove(int index) {

            if (index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException();
            }

            Node<E> predecessor = this.first;

            for (int i = 0; i < index; i++) {
                predecessor = predecessor.getNext();
            }

            Node<E> removed = predecessor.getNext();
            Node<E> successor = removed.getNext();

            predecessor.setNext(successor);

            if (removed == this.last) {
                this.last = predecessor;
            }

            this.size--;

            return removed.getElement();

        }

        public void print() {

            Node<E> currentElement = this.first;

            for (int i = 0; i < this.size; i++) {
                currentElement = currentElement.getNext();
                System.out.println("[" + i + "] " + currentElement.getElement());
            }

        }

    }

    public static class Node<E> {

        private final E element;
        private Node<E> next;

        public Node() {
            this(null);
        }

        public Node(E element) {
            this(element, null);
        }

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return this.element;
        }

        public Node<E> getNext() {
            return this.next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

    }

    public static class Accommodation implements Cloneable {

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

    }

}
