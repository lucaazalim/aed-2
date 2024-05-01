import java.util.Scanner;

public class CiframentoDeCesar {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equalsIgnoreCase("fim")) {

                System.out.println(ciframentoDeCesar(input));

            }

        }

    }

    public static String ciframentoDeCesar(String str) {
        return ciframentoDeCesar(str, 0);
    }

    public static String ciframentoDeCesar(String str, int index) {

        if (index == str.length()) {
            return "";
        }

        return (char) (str.charAt(index) + 3) + ciframentoDeCesar(str, index + 1);

    }

}
