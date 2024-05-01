import java.util.Arrays;

public class Combinador {

    public static void main(String[] args) {

        int[] v1 = { 1, 2, 3, 4, 5 };
        int[] v2 = { 3, 4, 5, 6, 7 };
        int[] v3 = { 0, 11 };

        int[] v = combineSortedVectors(v1, v2, v3);

        System.out.println(Arrays.toString(v));

    }

    public static int[] combineSortedVectors(int[] v1, int[] v2, int[] v3) {

        int[] v = new int[v1.length + v2.length + v3.length];
        int p = 0, p1 = 0, p2 = 0, p3 = 0;

        while (p1 < v1.length && p2 < v2.length && p3 < v3.length) {

            if (v1[p1] <= v2[p2] && v1[p1] <= v3[p3]) {
                v[p++] = v1[p1++];
            } else if (v2[p2] <= v3[p3] && v2[p2] <= v3[p3]) {
                v[p++] = v2[p2++];
            } else {
                v[p++] = v3[p3++];
            }

        }

        while (p1 < v1.length) {
            v[p++] = v1[p1++];
        }

        while (p2 < v2.length) {
            v[p++] = v2[p2++];
        }

        while (p3 < v3.length) {
            v[p++] = v3[p3++];
        }

        return v;

    }

}
