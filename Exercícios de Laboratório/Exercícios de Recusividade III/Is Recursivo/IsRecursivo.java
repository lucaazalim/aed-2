import java.util.Scanner;

public class IsRecursivo {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equalsIgnoreCase("fim")) {
                System.out.print(booleanToYesOrNo(isOnlyVowels(input)));
                System.out.print(" " + booleanToYesOrNo(isOnlyConsonants(input)));
                System.out.print(" " + booleanToYesOrNo(isPositiveInteger(input)));
                System.out.println(" " + booleanToYesOrNo(isRealNumber(input)));
            }

        }

    }

    public static boolean isRealNumber(String str) {
        return isRealNumber(str, 0, false);
    }

    public static boolean isRealNumber(String str, int index, boolean dot) {

        if (str.length() == index) {
            return true;
        }

        char charAt = str.charAt(index);

        if (charAt == ',' || charAt == '.') {
            if (dot) {
                return false;
            }

            dot = true;
        } else if (!isDigit(charAt)) {
            return false;
        }

        return isRealNumber(str, index + 1, dot);

    }

    public static boolean isPositiveInteger(String str) {
        return isPositiveInteger(str, 0);
    }

    public static boolean isPositiveInteger(String str, int index) {
        return index == str.length() || isDigit(str.charAt(index)) && isPositiveInteger(str, index + 1);
    }

    public static boolean isOnlyConsonants(String str) {
        return isOnlyConsonants(str, 0);
    }

    public static boolean isOnlyConsonants(String str, int index) {
        return index == str.length() || isConsonant(str.charAt(index)) && isOnlyConsonants(str, index + 1);
    }

    public static boolean isOnlyVowels(String str) {
        return isOnlyVowels(str, 0);
    }

    public static boolean isOnlyVowels(String str, int index) {
        return index == str.length() || isVowel(str.charAt(index)) && isOnlyVowels(str, index + 1);
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isConsonant(char c) {
        c = Character.toLowerCase(c);
        return c >= 'a' && c <= 'z' && !isVowel(c);
    }

    public static boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static String booleanToYesOrNo(boolean bool) {
        return bool ? "SIM" : "NAO";
    }

}
