public class RevisaoRecursividade {

    public static void main(String[] args) {
        System.out.println("Exercício 1: " + multiply(5, 3)); // 15
        System.out.println("Exercício 2: " + divide(15, 3)); // 5
        System.out.println("Exercício 3: " + search(new int[] { 10, 20, 30, 40, 50 }, 30)); // 2
        System.out.println("Exercício 4: " + calculateC(5)); // 0.5403025793650793
    }

    public static double calculateC(int n) {
        return n == 0 ? 1 : 1D / factorial(n * 2) * (n % 2 == 0 ? 1 : -1) + calculateC(n - 1);
    }

    public static int factorial(int n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }

    public static int search(int[] vector, int value) {
        return search(vector, 0, value);
    }

    public static int search(int[] vector, int index, int value) {

        if (index > vector.length) {
            return -1;
        }

        if (vector[index] == value) {
            return index;
        }

        return search(vector, index + 1, value);

    }

    public static int divide(int a, int b) {

        if (b == 0) {
            throw new ArithmeticException();
        }

        if (a < b || a == 0) {
            return 0;
        }

        return 1 + divide(a - b, b);

    }

    public static int multiply(int a, int b) {

        System.out.println(a + ", " + b);

        if (b == 1) {
            return a;
        }

        if (a == 0 || b == 0) {
            return 0;
        }

        return a + multiply(a, b - 1);

    }

}
