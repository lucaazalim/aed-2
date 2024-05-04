import java.text.Normalizer;

public class PalindromeStack {

    public static void main(String[] args) {

        String[] texts = {
                "reviver",
                "radar",
                "arara",
                "Acaiaca",
                "A grama é amarga",
                "Ame o poema",
                "Socorram-me em Marrocos",
                "Luca",
                "Eveline",
                "Em casa de ferreiro, o espeto é de pau!"
        };

        for (String text : texts) {
            System.out.println("'" + text + "' " + (isPalindrome(text) ? "is" : "isn't") + " a palindrome.");
        }

    }

    public static boolean isPalindrome(String text) {

        text = normalize(text);
        int middleIndex = text.length() / 2;
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < middleIndex; i++) {
            stack.push(text.charAt(i));
        }

        if (text.length() % 2 != 0) {
            middleIndex++;
        }

        for (int i = middleIndex; i < text.length(); i++) {
            if (stack.pop() != text.charAt(i)) {
                return false;
            }
        }

        return true;

    }

    public static String normalize(String str) {
        return removeAccents(str).toLowerCase().replaceAll("[^a-zA-Z]", "");
    }

    public static String removeAccents(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return str;
    }

}
