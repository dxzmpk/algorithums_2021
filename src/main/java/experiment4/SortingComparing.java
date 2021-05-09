package experiment4;


import time.Runner;
import time.TimeEvaluater;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SortingComparing {
    public static void main(String[] args){
        TimeEvaluater evaluater = new TimeEvaluater();
        Random random = new Random(1);

        final int TEST_SIZE = (int) 1e6;

        for (int out = 0; out < 11; out++) {
            int repeatNum = (int) (out * 10 * 0.01 * TEST_SIZE);
            System.out.println("重复比例 = " + out * 10 + "%");
            // 先生成不重复的数字
            int nonRepeat = TEST_SIZE - repeatNum;
            int[] systemArray = new int[TEST_SIZE];
            int[] quickArray;
            for (int i = 0; i < nonRepeat; i++) {
                systemArray[i] = i;
            }
            // 生成重复的数字
            for (int i = nonRepeat; i < TEST_SIZE; i++) {
                systemArray[i] = nonRepeat / 2;
            }
            List<Integer> helper = Arrays.stream(systemArray).boxed().collect(Collectors.toList());
            Collections.shuffle(helper);
            systemArray = helper.stream().mapToInt(x -> x).toArray();
            quickArray = systemArray.clone();
            writeToFile("data_repeat_" + out*10 + "%.txt", Arrays.toString(systemArray));
            int[] finalSystemArray = systemArray;
            evaluater.testAndPrint(new Runner() {
                @Override
                public void run() {
                    Arrays.sort(finalSystemArray);
                }
            }, "System");

            int[] finalQuickArray = quickArray;
            evaluater.testAndPrint(new Runner() {
                @Override
                public void run() {
                    new QuickSort().quickSort(finalQuickArray, 0, TEST_SIZE-1);
                }
            }, "QuickSort");
        }

    }

    private static void writeToFile(String filepath, String val) {
        File file = new File("E:\\elipseProject\\algorithums_2021\\src\\main\\java\\experiment4\\dataset\\" + filepath);
        PrintStream ps = null;
        try {
            file.createNewFile();
            ps = new PrintStream(new FileOutputStream(file));
            ps.println(val);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(ps).close();
        }
    }
}
