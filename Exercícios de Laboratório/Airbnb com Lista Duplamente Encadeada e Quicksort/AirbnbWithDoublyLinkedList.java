import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AirbnbWithDoublyLinkedList {

    private static final Path DATA_PATH = Path.of("/tmp/dados_airbnb.txt");

    public static void main(String[] args) throws IOException {

        Map<Integer, Accommodation> accomodationsMap = readData();
        SortableDoublyLinkedList<Accommodation> accommodationList = new SortableDoublyLinkedList<>();

        try (Scanner scanner = new Scanner(System.in)) {

            int amount = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < amount; i++) {
                int roomId = Integer.parseInt(scanner.nextLine());
                accommodationList.add(accomodationsMap.get(roomId));
            }

        }

        accommodationList.sort();

        for (int i = 0; i < accommodationList.getSize(); i++) {
            System.out.printf("[%d] %s%n", i, accommodationList.get(i));
        }

    }

    public static class SortableDoublyLinkedList<E extends Comparable<E>> extends DoublyLinkedList<E> {

        public void sort() {
            this.quickSort(this.getFirst().getNext(), this.getLast());
        }

        private void quickSort(DoubleNode<E> from, DoubleNode<E> to) {

            if (from != null && to != null && from != to && from != to.getNext()) {

                DoubleNode<E> part = partition(from, to);
                quickSort(from, part.getPrevious());
                quickSort(part.getNext(), to);

            }

        }

        private DoubleNode<E> partition(DoubleNode<E> from, DoubleNode<E> to) {

            DoubleNode<E> part = from.getPrevious(), currentNode = from;

            while (currentNode != to) {

                int comparison = currentNode.getElement().compareTo(to.getElement());

                if (comparison < 0) {
                    part = part.getNext();
                    this.swap(part, currentNode);
                }

                currentNode = currentNode.getNext();

            }

            part = part.getNext();
            swap(part, to);

            return part;

        }

    }

    public static class DoublyLinkedList<E> {

        private final DoubleNode<E> first; // always the sentinel
        private DoubleNode<E> last;
        private int size;

        public DoublyLinkedList() {
            DoubleNode<E> sentinel = new DoubleNode<>();
            this.first = this.last = sentinel;
            this.size = 0;
        }

        protected DoubleNode<E> getFirst() {
            return this.first;
        }

        protected DoubleNode<E> getLast() {
            return this.last;
        }

        public boolean isEmpty() {
            return this.first == this.last;
        }

        public int getSize() {
            return this.size;
        }

        public E get(int index) {
            return getNode(index).getElement();
        }

        protected DoubleNode<E> getNode(int index) {

            DoubleNode<E> node;

            if (index < (this.size >> 1)) {
                node = this.first.getNext();
                for (int i = 0; i < index; i++) {
                    node = node.getNext();
                }
            } else {
                node = this.last;
                for (int i = this.size - 1; i > index; i--) {
                    node = node.getPrevious();
                }
            }

            return node;

        }

        public void add(E element) {

            DoubleNode<E> newNode = new DoubleNode<>(element, this.last, null);

            this.last.setNext(newNode);
            this.last = newNode;
            this.size++;

        }

        public E removeLast() {

            if (this.isEmpty()) {
                throw new IllegalStateException();
            }

            E removed = this.last.getElement();
            DoubleNode<E> previous = this.last.getPrevious();

            previous.setNext(null);
            this.last = previous;
            this.size--;

            return removed;

        }

        public void swap(DoubleNode<E> from, DoubleNode<E> to) {

            E toElement = to.getElement();

            to.setElement(from.getElement());
            from.setElement(toElement);

        }

    }

    public static class DoubleNode<E> {

        private E element;
        private DoubleNode<E> previous;
        private DoubleNode<E> next;

        public DoubleNode() {
            this(null);
        }

        public DoubleNode(E element) {
            this(element, null, null);
        }

        public DoubleNode(
                E element,
                DoubleNode<E> previous,
                DoubleNode<E> next) {
            this.element = element;
            this.setPrevious(previous);
            this.setNext(next);
        }

        public E getElement() {
            return this.element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public DoubleNode<E> getPrevious() {
            return previous;
        }

        public void setPrevious(DoubleNode<E> previous) {
            this.previous = previous;
        }

        public DoubleNode<E> getNext() {
            return this.next;
        }

        public void setNext(DoubleNode<E> next) {
            this.next = next;
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
            return Comparator.comparing(Accommodation::getPrice)
                    .thenComparing(Accommodation::getRoomType)
                    .thenComparing(Accommodation::getRoomId)
                    .compare(this, other);
        }

    }

}
