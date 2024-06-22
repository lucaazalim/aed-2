import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ContagemDeOperacoes {

    public static void main(String[] args) {

        List<Integer> numbers = generateNumbers();
        runTests(numbers);

    }

    /**
     * How many times should the program run each test for each
     * value of 'n' before calculating the average elapsed time.
     */
    private static final int TOTAL_SAMPLES = 5;

    /**
     * How many loops should each test run (aka value of 'n').
     */
    private static final int TOTAL_LOOPS = 50;

    /**
     * Stores the consumers for each test that is going to be evaluated.
     */
    private static final Map<String, Consumer<Integer>> TESTS = new LinkedHashMap<>() {
        {
            this.put("RevisaoRecursividade A", n -> {

                int b = 1;

                for (int i = 0; i <= n; i += 2) {
                    b *= 3;
                }

            });

            this.put("RevisaoRecursividade B", n -> {

                int a = 1;

                for (int i = n + 1; i > 0; i /= 2) {
                    a *= 2;
                }

            });

        }
    };

    /**
     * Runs each test for each value of 'n' and prints the results.
     * The elapsed time is calculated as the average of the elapsed time
     * for each test, after removing the smallest and the greatest elapsed time.
     *
     * @param numbers the list of integers to be used as the value of 'n'.
     */
    public static void runTests(List<Integer> numbers) {

        TESTS.forEach((name, consumer) -> {

            System.out.println("Results of '" + name + "'");
            System.out.println("Loops (n);Avg. Elapsed Time (ns)");

            for (int n : numbers) {

                List<Long> elapsedTimes = new ArrayList<>();

                // Runs the test x times and stores the elapsed time for each one
                for (int s = 0; s < TOTAL_SAMPLES; s++) {
                    long start = System.nanoTime();
                    consumer.accept(n);
                    elapsedTimes.add(System.nanoTime() - start);
                }

                // Removes both the smallest and the greatest elapsed time
                Collections.sort(elapsedTimes);
                elapsedTimes.remove(0);
                elapsedTimes.remove(TOTAL_SAMPLES - 2);

                // Calculates the average elapsed time
                long average = (long) elapsedTimes.stream()
                        .mapToLong(Long::valueOf)
                        .average()
                        .orElseThrow();

                // Prints the result of the test
                System.out.println(n + ";" + average);

            }

            System.out.println();

        });

    }

    /**
     * Generates an ascending list of positive integers following a specific pattern. The
     * list will include powers of two and also the average between each pair of powers.
     *
     * @return the list of integers.
     */
    public static List<Integer> generateNumbers() {

        int offset = 5;
        List<Integer> numbers = new ArrayList<>();

        for (int i = offset; i < TOTAL_LOOPS / 2 + offset; i++) {
            int pow = (int) Math.pow(2, i);
            numbers.add(pow);
            numbers.add((int) (pow + Math.pow(2, i + 1)) / 2);
        }

        return numbers;

    }

}