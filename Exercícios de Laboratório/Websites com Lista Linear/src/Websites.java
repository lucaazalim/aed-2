import java.util.NoSuchElementException;
import java.util.Scanner;

public class Websites {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            VectorList<Website> websites = readWebsites(scanner);
            readSearches(scanner, websites);
            websites.print();

        }

    }

    public static VectorList<Website> readWebsites(Scanner scanner) {

        int websiteAmount = Integer.parseInt(scanner.nextLine());
        VectorList<Website> websites = new VectorList<>(websiteAmount);

        for (int i = 0; i < websiteAmount; i++) {

            String line = scanner.nextLine();
            String[] lineSplit = line.split(" - ");

            String websiteName = lineSplit[0];
            String websiteUrl = lineSplit[1];

            Website website = new Website(websiteName, websiteUrl);
            websites.add(website);

        }

        return websites;

    }

    public static void readSearches(Scanner scanner, VectorList<Website> websites) {

        String input;

        while (!(input = scanner.nextLine()).equals("FIM")) {
            search(websites, input);
        }

    }

    public static void search(VectorList<Website> websites, String websiteName) {

        Website dummyWebsite = new Website(websiteName);

        try {

            int index = websites.indexOf(dummyWebsite);
            Website website = websites.get(index);

            website.incrementSearched();
            websites.moveToFirstIndex(website);

            System.out.println(website.getUrl());

        } catch (NoSuchElementException e) {
            System.out.println("O item procurado nao foi encontrado!");
        }

    }

    public static class Website {

        private final String name, url;
        private int searched;

        public Website(String name) {
            this(name, null);
        }

        public Website(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void incrementSearched() {
            this.searched++;
        }

        @Override
        public String toString() {
            return "Nome: [" + this.name + "]\n" +
                    "Endereco: [" + this.url + "]\n" +
                    "Numero de acessos: " + this.searched;
        }

        @Override
        public boolean equals(Object other) {

            if(other instanceof Website otherWebsite) {
                return this.name.equals(otherWebsite.name);
            }

            return false;

        }

    }

    public static class VectorList<E> {

        private final E[] vector;
        private final int first; // always zero
        public int last;

        @SuppressWarnings("unchecked")
        public VectorList(int size) {
            this.vector = (E[]) new Object[size];
            this.first = this.last = 0;
        }

        public boolean isEmpty() {
            return this.first == this.last;
        }

        public boolean isFull() {
            return this.last == this.vector.length;
        }

        public E get(int index) {

            if(index < 0 || index >= this.last) {
                throw new IndexOutOfBoundsException();
            }

            return this.vector[index];

        }

        public void add(E element) {
            this.add(element, this.last);
        }

        public void add(E element, int index) {

            if(this.isFull()) {
                throw new IllegalStateException();
            }

            if(index < 0 || index > this.last) {
                throw new IndexOutOfBoundsException();
            }

            for(int i = this.last; i > index; i--) {
                this.vector[i] = this.vector[i - 1];
            }

            this.vector[index] = element;
            this.last++;

        }

        public E remove(int index) {

            E removed = this.get(index);
            this.last--;

            for(int i = index; i < this.last; i++) {
                this.vector[i] = this.vector[i + 1];
            }

            return removed;

        }

        public int indexOf(E element) {

            for(int i = this.first; i < this.last; i++) {

                if(this.vector[i].equals(element)) {
                    return i;
                }

            }

            throw new NoSuchElementException();

        }

        public void moveToFirstIndex(E element) {

            int currentIndex = this.indexOf(element);
            E removed = this.remove(currentIndex);
            this.add(removed, 0);

        }

        public void print() {
            for(int i = this.first; i < this.last; i++) {
                System.out.println(this.vector[i]);
            }
        }

    }

}
