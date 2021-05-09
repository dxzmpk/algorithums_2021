package experiment4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MergeSort {

    public static void main(String[] args){
        int[] s = new int[] {4,1,2,3,5, 10, 9, 7, 6, 4};
        new MergeSort().mergesort(s, 0, s.length - 1);
        System.out.println(Arrays.toString(s));
    }

    void mergesort(int[] s, int low, int high)
    {
        int i; /* counter */
        int middle; /* index of middle element */
        if (low < high) {
            middle = (low+high)/2;
            mergesort(s,low,middle);
            mergesort(s,middle+1,high);
            merge(s, low, middle, high);
        }
    }

    private void merge(int[] s, int low, int middle, int high) {
        int i; // counter
        Queue<Integer> buffer1, buffer2;
        buffer1 = new LinkedList<>();
        buffer2 = new LinkedList<>();
        for (i = low; i <= middle; i++) {
            buffer1.offer(s[i]);
        }
        for (i = middle + 1; i <= high; i++) {
            buffer2.offer(s[i]);
        }
        i = low;
        while ((!buffer1.isEmpty()) && (!buffer2.isEmpty())) {
            if (buffer1.peek() <= buffer2.peek()) { // equals keeps stability
                s[i++] = buffer1.poll();
            } else {
                s[i++] = buffer2.poll();
            }
        }
        while (!buffer1.isEmpty()) {
            s[i++] = buffer1.poll();
        }
        while (!buffer2.isEmpty()) {
            s[i++] = buffer2.poll();
        }
    }


}
