import java.util.Random;
import java.util.Scanner;

public class AlteracaoAleatoria {

    public static Random RANDOM = new Random(4);

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equalsIgnoreCase("fim")) {
                System.out.println(alteracaoAleatoria(input));
            }

        }

    }

    public static String alteracaoAleatoria(String str) {
        return alteracaoAleatoria(str, 0, letraAleatoria(), letraAleatoria());
    }

    public static String alteracaoAleatoria(String str, int index, char from, char to) {

        if (str.length() == index) {
            return "";
        }

        char c = str.charAt(index);

        return (c == from ? to : c) + alteracaoAleatoria(str, index + 1, from, to);

    }

    public static char letraAleatoria() {
        return (char) ('a' + Math.abs(RANDOM.nextInt() % 26));
    }

}
