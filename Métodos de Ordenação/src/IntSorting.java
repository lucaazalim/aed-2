import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@SuppressWarnings("ALL")
public class IntSorting {

    private static final Map<String, Consumer<int[]>> SORTING_METHODS = new HashMap<>() {
        {
            this.put("Selection", IntSorting::selectionSort);
            this.put("Insertion", IntSorting::insertionSort);
            this.put("Bubble", IntSorting::bubbleSort);
            this.put("Merge", IntSorting::mergeSort);
            this.put("Heap", IntSorting::heapSort);
            this.put("Quick", IntSorting::quickSort);
        }
    };

    private static final int ARRAY_SIZE = 50;

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(IntStream.range(0, ARRAY_SIZE).boxed().toList());
        Collections.shuffle(list);

        System.out.println("Array to be sorted: " + list);

        int[] arrayToBeSorted = list.stream().mapToInt(i -> i).toArray();
        int[] expectedArray = IntStream.range(0, ARRAY_SIZE).toArray();

        for (Map.Entry<String, Consumer<int[]>> entry : SORTING_METHODS.entrySet()) {

            int[] copy = Arrays.copyOf(arrayToBeSorted, arrayToBeSorted.length);
            entry.getValue().accept(copy);

            if (!Arrays.equals(copy, expectedArray)) {
                throw new RuntimeException(
                        "Sorting method " + entry.getKey() + " is not working as expected: " + Arrays.toString(copy));
            }

        }

        System.out.println("All sorting methods are working as expected!");

    }

    public static void selectionSort(int[] array) {

        int smallestIndex;

        for (int ref = 0; ref < array.length - 1; ref++) {

            smallestIndex = ref;

            for (int j = ref + 1; j < array.length; j++) {

                if (array[j] < array[smallestIndex]) {
                    smallestIndex = j;
                }

            }

            swap(array, ref, smallestIndex);

        }

    }

    public static void insertionSort(int[] array) {

        for (int ref = 1; ref < array.length; ref++) {

            int refValue = array[ref];
            int i = ref - 1;

            while (i >= 0 && array[i] > refValue) {
                array[i + 1] = array[i];
                i--;
            }

            array[i + 1] = refValue;

        }

    }

    public static void bubbleSort(int[] array) {

        for (int ref = array.length - 1; ref > 0; ref--) {

            boolean changed = false;

            for (int i = 0; i < ref; i++) {

                int next = array[i + 1];

                if (array[i] > next) {

                    swap(array, i, i + 1);
                    changed = true;

                }

            }

            if (!changed) {
                return;
            }

        }

    }

    public static void mergeSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    public static void mergeSort(int[] array, int from, int to) {

        if (from < to) {

            int middle = (from + to) / 2;

            mergeSort(array, from, middle);
            mergeSort(array, middle + 1, to);

            merge(array, from, middle, to);

        }

    }

    public static void merge(int[] array, int from, int middle, int to) {

        int[] v1 = new int[middle - from + 1];
        int[] v2 = new int[to - middle];

        for (int i = 0; i < v1.length; i++) {
            v1[i] = array[from + i];
        }

        for (int j = 0; j < v2.length; j++) {
            v2[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0, k = from;

        for (; i < v1.length && j < v2.length; k++) {

            if (v1[i] < v2[j]) {
                array[k] = v1[i++];
            } else {
                array[k] = v2[j++];
            }

        }

        if (i == v1.length) {
            while (k <= to) {
                array[k++] = v2[j++];
            }
        } else {
            while (k <= to) {
                array[k++] = v1[i++];
            }
        }

    }

    public static void heapSort(int[] vector) {

        int[] heap = new int[vector.length + 1];

        for (int i = 0; i < vector.length; i++) {
            heap[i + 1] = vector[i];
        }

        for (int i = vector.length / 2; i >= 1; i--) {
            restoreHeap(heap, i, vector.length);
        }

        int heapSize = vector.length;

        while (heapSize > 1) {
            swap(heap, 1, heapSize--);
            restoreHeap(heap, 1, heapSize);
        }

        for (int i = 0; i < vector.length; i++) {
            vector[i] = heap[i + 1];
        }

    }

    public static void restoreHeap(int[] heap, int i, int heapSize) {

        int largest = i;
        int left = 2 * i;
        int right = 2 * i + 1;

        if (left <= heapSize && heap[left] > heap[largest]) {
            largest = left;
        }

        if (right <= heapSize && heap[right] > heap[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(heap, i, largest);
            restoreHeap(heap, largest, heapSize);
        }
    }

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int left, int right) {

        if (left < right) {
            int part = partition(array, left, right);
            quickSort(array, left, part - 1);
            quickSort(array, part + 1, right);
        }

    }

    private static int partition(int[] array, int from, int to) {

        int pivot = array[to];
        int part = from - 1;

        for (int i = from; i < to; i++) {
            if (array[i] < pivot) {
                part++;
                swap(array, part, i);
            }
        }

        part++;
        swap(array, part, to);

        return (part);

    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
