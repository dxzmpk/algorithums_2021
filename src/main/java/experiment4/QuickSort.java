package experiment4;

import java.util.Arrays;
import java.util.Random;

/**
 * Such partitioning buys us two things. First, the pivot element p ends up in
 * the exact array position it will reside in the the final sorted order. Second, after
 * partitioning no element flops to the other side in the final sorted order. Thus we
 * can now sort the elements to the left and the right of the pivot independently! This
 * gives us a recursive sorting algorithm, since we can use the partitioning approach to
 * sort each subproblem. The algorithm must be correct since each element ultimately
 * ends up in the proper position:
 */
public class QuickSort {
    static MergeSort mergeSort = new MergeSort();
    public static void main(String[] args){
        int[] s = new int[] {4,1,2,3,5, 10, 9, 7, 6, 4};
        new QuickSort().quickSort(s, 0, s.length - 1);
        System.out.println(Arrays.toString(s));
    }

    public void quickSort(int[] s, int l, int h) {
        int p;
        if (h - l > 1e6) {
            p = parition(s, l, h);
            quickSort(s,l,p-1);
            quickSort(s,p+1,h);
        } else {
            mergeSort.mergesort(s, l, h);
        }

    }

    private int parition(int[] s, int l, int h) {
        int i; /* counter */
        int p; /* pivot element index */
        int firsthigh; /* next position to store lowest num */

        Random random = new Random(1);
        p = l + random.nextInt(h - l + 1);
        swap(s, p, h);
        p = h;
        firsthigh = l;
        for (i = l; i < h; i ++) {
            if (s[i] < s[p]) {
                swap(s, i, firsthigh);
                firsthigh ++;
            }
        }
        swap(s, p , firsthigh);
        return firsthigh;
    }

    private void swap(int[] s, int i, int i1) {
        int temp = s[i];
        s[i] = s[i1];
        s[i1] = temp;
    }
}
