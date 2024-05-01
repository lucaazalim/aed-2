import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class TPMerge {

    private static final int REG_NUMBER = 818574;
    private static final Path DATA_PATH = Path.of("/tmp/dados_airbnb.txt");
    private static final Path LOG_PATH = Path.of(REG_NUMBER + "_mergesort.txt");
    private static final Comparator<Accommodation> COMPARATOR = Comparator
            .comparing(Accommodation::getHostId)
            .thenComparing(Accommodation::getRoomId);

    private static int comparisons = 0, moves = 0;

    public static void main(String[] args) throws CloneNotSupportedException, IOException {

        Accommodation[] allAccommodations = readData();

        try (Scanner scanner = new Scanner(System.in)) {

            int amount = Integer.parseInt(scanner.nextLine());
            Accommodation[] selectedAccommodations = new Accommodation[amount];

            for (int i = 0; i < amount; i++) {

                int accommodationId = Integer.parseInt(scanner.nextLine());
                selectedAccommodations[i] = Arrays.stream(allAccommodations)
                        .filter(accommodation -> accommodation.getRoomId() == accommodationId)
                        .findFirst()
                        .orElse(null);

            }

            long start = System.currentTimeMillis();
            mergeSort(selectedAccommodations);
            long elapsed = System.currentTimeMillis() - start;

            for (Accommodation accommodation : selectedAccommodations) {
                accommodation.print();
            }

            writeLogFile(elapsed);

        }

    }

    public static Accommodation[] readData() throws IOException {

        String fileContent = Files.readString(DATA_PATH);
        String[] lines = fileContent.split("\n");
        Accommodation[] accommodations = new Accommodation[lines.length - 1];

        for (int i = 1; i < lines.length; i++) {

            String[] fields = lines[i].split("\t");

            Accommodation accommodation = new Accommodation(
                    Integer.parseInt(fields[0]), Integer.parseInt(fields[1]),
                    fields[2], fields[3], fields[4], fields[5],
                    Integer.parseInt(fields[6]), Double.parseDouble(fields[7]),
                    Integer.parseInt(fields[8]), Double.parseDouble(fields[9]),
                    Double.parseDouble(fields[10]), fields[11].trim());

            accommodations[i - 1] = accommodation;

        }

        return accommodations;

    }

    public static void writeLogFile(long executionTime) throws IOException {

        String log = REG_NUMBER + "\t" + executionTime + "\t" + comparisons + "\t" + moves;
        Files.writeString(LOG_PATH, log, StandardCharsets.UTF_8, StandardOpenOption.CREATE);

    }

    public static void mergeSort(Accommodation[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    public static void mergeSort(Accommodation[] array, int from, int to) {

        if (from < to) {

            int middle = (from + to) / 2;

            mergeSort(array, from, middle);
            mergeSort(array, middle + 1, to);

            merge(array, from, middle, to);

        }

    }

    public static void merge(Accommodation[] array, int from, int middle, int to) {

        Accommodation[] v1 = new Accommodation[middle - from + 1];
        Accommodation[] v2 = new Accommodation[to - middle];

        for (int i = 0; i < v1.length; i++) {
            v1[i] = array[from + i];
        }

        for (int j = 0; j < v2.length; j++) {
            v2[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0, k = from;

        for (; i < v1.length && j < v2.length; k++) {

            if (v1[i].compareTo(v2[j]) < 0) {
                array[k] = v1[i++];
            } else {
                array[k] = v2[j++];
            }

            moves++;

        }

        if (i == v1.length) {
            while (k <= to) {
                array[k++] = v2[j++];
            }
        } else {
            while (k <= to) {
                array[k++] = v1[i++];
            }
        }

    }

    public static class Accommodation implements Cloneable, Comparable<Accommodation> {

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

        public void print() {
            System.out.println("[" + roomId + " ## " + hostId + " ## " + roomType
                    + " ## " + country + " ## " + city + " ## " + neighbourhood
                    + " ## " + reviews + " ## " + overallSatisfaction + " ## "
                    + accommodates + " ## " + bedrooms + " ## " + price + " ## "
                    + propertyType + "]");
        }

        public static Accommodation read() {

            try (Scanner scanner = new Scanner(System.in)) {

                System.out.println("Please provide the room ID: ");
                int roomId = Integer.parseInt(scanner.nextLine());

                System.out.println("Please provide the host ID: ");
                int hostId = Integer.parseInt(scanner.nextLine());

                System.out.println("Please provide the room type: ");
                String roomType = scanner.nextLine();

                System.out.println("Please provide the country: ");
                String country = scanner.nextLine();

                System.out.println("Please provide the city: ");
                String city = scanner.nextLine();

                System.out.println("Please provide the neighbourhood: ");
                String neighbourhood = scanner.nextLine();

                System.out.println("Please provide the number of reviews: ");
                int reviews = Integer.parseInt(scanner.nextLine());

                System.out.println("Please provide the overall satisfaction: ");
                double overallSatisfaction = Double.parseDouble(scanner.nextLine());

                System.out.println("Please provide the number of accommodates: ");
                int accommodates = Integer.parseInt(scanner.nextLine());

                System.out.println("Please provide the number of bedrooms: ");
                double bedrooms = Double.parseDouble(scanner.nextLine());

                System.out.println("Please provide the price: ");
                double price = Double.parseDouble(scanner.nextLine());

                System.out.println("Please provide the property type: ");
                String propertyType = scanner.nextLine();

                return new Accommodation(roomId, hostId, roomType, country, city,
                        neighbourhood, reviews, overallSatisfaction, accommodates,
                        bedrooms, price, propertyType);

            }

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
        public Accommodation clone() throws CloneNotSupportedException {
            return (Accommodation) super.clone();
        }

        @Override
        public int compareTo(Accommodation other) {

            comparisons++;
            return COMPARATOR.compare(this, other);

        }

    }

}
