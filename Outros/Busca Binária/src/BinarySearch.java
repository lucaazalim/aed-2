import java.util.Comparator;
import java.util.Objects;

public class BinarySearch {

    public static void main(String[] args) {

        Integer[] array = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        System.out.println(binarySearch(array, 0));

    }

    public static <T extends Comparable<T>> T binarySearch(T[] array, T target) {
        return binarySearch(array, target, 0, array.length - 1);
    }

    public static <T extends Comparable<T>> T binarySearch(
            T[] array, T target, int from, int to) {

        if (from > to) {
            return null;
        }

        int middle = (from + to) / 2;
        T middleElement = array[middle];

        int comparison = Objects.compare(target, middleElement, Comparator.naturalOrder());

        if (comparison == 0) {
            return middleElement;
        }

        if (comparison < 0) {
            from = 0;
            to = middle - 1;
        } else {
            from = middle + 1;
            to = array.length - 1;
        }

        return binarySearch(array, target, from, to);

    }

}
