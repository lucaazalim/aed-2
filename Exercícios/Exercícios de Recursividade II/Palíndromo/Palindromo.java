import java.util.Scanner;

public class Palindromo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;

        while (!(input = scanner.nextLine()).equalsIgnoreCase("fim")) {

            System.out.println(ehPalindromo(input) ? "SIM" : "NAO");

        }

    }

    public static boolean ehPalindromo(String str) {
        return ehPalindromo(str, 0);
    }

    public static boolean ehPalindromo(String str, int index) {

        if (index >= str.length() / 2) {
            return true;
        }

        return str.charAt(index) == str.charAt(str.length() - 1 - index) && ehPalindromo(str, index + 1);

    }

}
