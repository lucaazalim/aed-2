import java.util.NoSuchElementException;
import java.util.Scanner;

public class ParkingLot {

    private static final Queue<Vehicle> parkingLot = new Queue<>();

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equals("FIM")) {
                processOperation(input);
            }

            while (!parkingLot.isEmpty()) {
                System.out.println(parkingLot.unqueue());
            }

        }

    }

    public static void processOperation(String input) {

        char operation = input.charAt(0);

        String plate = input.substring(2);
        Vehicle vehicle = new Vehicle(plate);

        switch (operation) {
            case 'C' -> {

                parkingLot.queue(vehicle);
                System.out.println("Carro de placa " + vehicle + " entrou no estacionamento.");

            }
            case 'P' -> {

                Queue<Vehicle> temporaryQueue = new Queue<>();
                Vehicle foundVehicle = null;

                while (!parkingLot.isEmpty()) {

                    Vehicle currentVehicle = parkingLot.unqueue();

                    if (currentVehicle.equals(vehicle)) {

                        foundVehicle = currentVehicle;

                    } else {

                        temporaryQueue.queue(currentVehicle);

                        if (foundVehicle == null) {
                            currentVehicle.incrementManeuvers();
                        }

                    }

                }

                if (foundVehicle == null) {

                    System.out.println("Carro nao encontrado!");

                } else {

                    System.out.println("Carro de placa " + foundVehicle + " saiu do estacionamento.");
                    System.out.println("Esse carro foi manobrado " + foundVehicle.getManeuvers() + " vezes.");

                }

                while (!temporaryQueue.isEmpty()) {
                    parkingLot.queue(temporaryQueue.unqueue());
                }

            }
        }

    }

    public static class Vehicle {

        private final String plate;
        private int maneuvers;

        public Vehicle(String plate) {
            this.plate = plate;
        }

        public int getManeuvers() {
            return this.maneuvers;
        }

        public void incrementManeuvers() {
            this.maneuvers++;
        }

        @Override
        public boolean equals(Object other) {

            if (other instanceof Vehicle otherVehicle) {
                return this.plate.equals(otherVehicle.plate);
            }

            return false;

        }

        @Override
        public String toString() {
            return this.plate;
        }

    }

    public static class Queue<E> {

        private Node<E> front, back; // front is always the sentinel.

        public Queue() {
            Node<E> sentinel = new Node<>();
            this.front = this.back = sentinel;
        }

        public boolean isEmpty() {
            return this.front == this.back;
        }

        public void queue(E element) {

            Node<E> newNode = new Node<>(element);
            this.back.setNext(newNode);
            this.back = newNode;

        }

        public E unqueue() {

            E element = this.peek();
            Node<E> first = this.front.getNext();

            this.front.setNext(first.getNext());

            if (first == this.back) { // or first.getNext() == null
                this.front = this.back;
            }

            return element;

        }

        public E peek() {

            if (this.isEmpty()) {
                throw new NoSuchElementException("The queue is empty.");
            }

            return this.front.getNext().getElement();

        }

    }

    public static class Node<E> {

        private final E element;
        private Node<E> next = null;

        public Node() {
            this(null);
        }

        public Node(E element) {
            this(element, null);
        }

        public Node(E element, Node<E> next) {
            this.element = element;
            this.setNext(next);
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

}
