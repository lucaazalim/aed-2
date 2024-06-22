import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class ComparableSorting {

    public static <T extends Comparable<T>> void selectionSort(T[] array) {

        int smallestIndex;

        for (int ref = 0; ref < array.length - 1; ref++) {

            smallestIndex = ref;

            for (int j = ref + 1; j < array.length; j++) {

                if (array[j].compareTo(array[smallestIndex]) < 0) {
                    smallestIndex = j;
                }

            }

            swap(array, ref, smallestIndex);

        }

    }

    public static <T extends Comparable<T>> void insertionSort(T[] array) {

        for (int ref = 1; ref < array.length; ref++) {

            T refValue = array[ref];
            int i = ref - 1;

            while (i >= 0 && array[i].compareTo(refValue) > 0) {
                array[i + 1] = array[i];
                i--;
            }

            array[i + 1] = refValue;

        }

    }

    public static <T extends Comparable<T>> void bubbleSort(T[] array) {

        for (int ref = array.length - 1; ref > 0; ref--) {

            boolean changed = false;

            for (int i = 0; i < ref; i++) {

                T next = array[i + 1];

                if (array[i].compareTo(next) > 0) {

                    swap(array, i, i + 1);
                    changed = true;

                }

            }

            if (!changed) {
                return;
            }

        }

    }

    public static <T extends Comparable<T>> void mergeSort(Class<T> clazz, T[] array) {
        mergeSort(clazz, array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void mergeSort(Class<T> clazz, T[] array, int from, int to) {

        if (from < to) {

            int middle = (from + to) / 2;

            mergeSort(clazz, array, from, middle);
            mergeSort(clazz, array, middle + 1, to);

            merge(clazz, array, from, middle, to);

        }

    }

    private static <T extends Comparable<T>> void merge(Class<T> clazz, T[] array, int from, int middle, int to) {

        T[] v1 = (T[]) Array.newInstance(clazz, middle - from + 1);
        T[] v2 = (T[]) Array.newInstance(clazz, to - middle);

        for(int i = 0; i < v1.length; i++) {
            v1[i] = array[from + i];
        }

        for(int j = 0; j < v2.length; j++) {
            v2[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0, k = from;

        for (; i < v1.length && j < v2.length; k++) {

            if (v1[i].compareTo(v2[j]) < 0) {
                array[k] = v1[i++];
            } else {
                array[k] = v2[j++];
            }

        }

        if(i == v1.length) {
            while(k <= to) {
                array[k++] = v2[j++];
            }
        } else {
            while(k <= to) {
                array[k++] = v1[i++];
            }
        }

    }

    public static <T extends Comparable<T>> void heapSort(Class<T> clazz, T[] vector) {

        T[] heap = (T[]) Array.newInstance(clazz, vector.length + 1);

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

    private static <T extends Comparable<T>> void restoreHeap(T[] heap, int i, int heapSize) {

        int largest = i;
        int left = 2 * i;
        int right = 2 * i + 1;

        if (left <= heapSize && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }

        if (right <= heapSize && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(heap, i, largest);
            restoreHeap(heap, largest, heapSize);
        }
    }

    public static <T extends Comparable<T>> void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static <T extends Comparable<T>> void quickSort(T[] array, int left, int right) {

        if (left < right){
            int part = partition(array, left, right);
            quickSort(array, left, part - 1);
            quickSort(array, part + 1, right);
        }

    }

    private static <T extends Comparable<T>> int partition(T[] array, int from, int to) {

        T pivot = array[to];
        int part = from - 1;

        for (int i = from; i < to; i++) {
            if (array[i].compareTo(pivot) < 0) {
                part++;
                swap(array, part, i);
            }
        }

        part++;
        swap(array, part, to);

        return (part);

    }

    public static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}