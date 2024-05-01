import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExpressaoMatematica {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equalsIgnoreCase("fim")) {
                System.out.println(convertToReversePolishNotation(input));
            }

        }

    }

    public static String convertToReversePolishNotation(String expression) {

        StringBuilder builder = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {

            if (ch == ' ') {
                continue;
            }

            if (Character.isLetterOrDigit(ch)) {

                builder.append(ch).append(" ");

            } else if (ch == '(') {

                stack.push(ch);

            } else if (ch == ')') {

                while (!stack.isEmpty() && stack.peek() != '(') {
                    builder.append(stack.pop()).append(" ");
                }

            } else {

                int precedence = getPrecedence(ch);

                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= precedence) {
                    builder.append(stack.pop()).append(" ");
                }

                stack.push(ch);

            }

        }

        while (!stack.isEmpty()) {
            builder.append(stack.pop()).append(" ");
        }

        return builder.toString();

    }

    public static int getPrecedence(char ch) {
        return switch (ch) {
            case '(' -> 1;
            case '+', '-' -> 2;
            case '*', '/' -> 3;
            default -> throw new IllegalArgumentException();
        };
    }

    public static class Stack<E> {

        private Node<E> head;

        public boolean isEmpty() {
            return this.head == null;
        }

        public void push(E element) {
            this.head = new Node<>(element, this.head);
        }

        public E pop() {

            E popped = this.peek();
            this.head = this.head.next();
            return popped;

        }

        public E peek() {

            if (this.isEmpty()) {
                throw new NoSuchElementException();
            }

            return this.head.element();

        }

        private record Node<E>(E element, Node<E> next) {
        }

    }

}
