import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlgebraBooleana {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (!(input = scanner.nextLine()).equals("0")) {
                System.out.println(evaluate(input) ? 1 : 0);
            }

        }

    }

    public static boolean evaluate(String input) {

        String[] spaceSplit = input.toLowerCase().split(" ");
        int variablesAmount = Integer.parseInt(spaceSplit[0]);
        boolean[] values = new boolean[variablesAmount];

        for (int i = 0; i < values.length; i++) {
            int value = Integer.parseInt(spaceSplit[i + 1]);
            values[i] = value == 1;
        }

        StringBuilder expression = new StringBuilder();

        for (int i = values.length + 1; i < spaceSplit.length; i++) {
            expression.append(spaceSplit[i]);
        }

        return recursivelyEvaluate(expression.toString(), values);

    }

    public static boolean recursivelyEvaluate(String expression, boolean[] variables) {

        if (expression.length() == 1) {
            return variables[expression.charAt(0) - 'a'];
        }

        int startIndex = expression.indexOf('(');
        int endIndex = expression.lastIndexOf(')');

        String function = expression.substring(0, startIndex);
        String functionParameters = expression.substring(startIndex + 1, endIndex);
        List<String> parameters = extractParameters(functionParameters);

        if (function.equals("not")) {

            if (parameters.size() != 1) {
                throw new SyntaxException("You must provide at least one parameter to the not() function.");
            }

            return !recursivelyEvaluate(parameters.get(0), variables);

        } else {

            if (parameters.size() < 2) {
                throw new SyntaxException(
                        "You must provide at least two parameters to the " + function + "() function.");
            }

            if (function.equals("and")) {

                for (String parameter : parameters) {
                    if (!recursivelyEvaluate(parameter, variables)) {
                        return false;
                    }
                }

                return true;

            } else if (function.equals("or")) {

                for (String parameter : parameters) {
                    if (recursivelyEvaluate(parameter, variables)) {
                        return true;
                    }
                }

                return false;

            } else {

                throw new SyntaxException("The function " + function + "() does not exist.");

            }

        }

    }

    private static List<String> extractParameters(String expression) {

        List<String> parameters = new ArrayList<>();
        StringBuilder parameter = new StringBuilder();

        int depth = 0;

        for (char c : expression.toCharArray()) {

            if (c == ',' && depth == 0) {

                parameters.add(parameter.toString());
                parameter.setLength(0);

            } else {

                parameter.append(c);

                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                }

            }
        }

        parameters.add(parameter.toString());
        return parameters;

    }

    public static class SyntaxException extends RuntimeException {
        public SyntaxException(String message) {
            super(message);
        }
    }

}
