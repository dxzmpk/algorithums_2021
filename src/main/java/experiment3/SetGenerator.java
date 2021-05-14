package experiment3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class SetGenerator {

    Set<Integer> X = new HashSet<>();
    List<Set<Integer>> F = new ArrayList<>();

    public void generateXandF(int xSize) {
        Random random = new Random(10);
        Set<Integer> exists = new HashSet<>();
        while (xSize > 0) {
            Integer randInt = random.nextInt(100 * xSize);
            if (!X.contains(randInt)) {
                xSize --;
                X.add(randInt);
            }
        }
        outer:
        while (true) {
            Set<Integer> temp = new HashSet<>();
            // 设定每个集合都是X大小的3/4
            for (int i = 0; i < X.size()*7/8; i ++) {
                // 随机选取一个下标
                int nextIndex = random.nextInt(X.size());
                int aggregate = 0;
                for (Integer num : X) {
                    // 就选它了！它是遍历到的第nextIndex个数字
                    if (nextIndex == aggregate) {
                        temp.add(num);
                    }
                    // 累计当前计数
                    aggregate ++;
                }
            }
            exists.addAll(temp);
            F.add(temp);
            for (Integer num : X) {
                // 检测所有的X中数字，观察exists是否已经全覆盖
                if (!exists.contains(num)) {
                    continue outer;
                }
            }
            break;
        }
    }

    public static void main(String[] args){
        SetGenerator generator = new SetGenerator();
        generator.generateXandF(5000);
        int[] array = new int[] {100, 1000, 5000};
        for (int i : array) {
            generator.generateXandF(i);
            writeToFile("setnum_" + i, Arrays.toString(generator.F.toArray()));
        }
        System.out.println("Generated F num : " + generator.F.size());
    }



    private static void writeToFile(String filepath, String val) {
        File file = new File("E:\\elipseProject\\algorithums_2021\\src\\main\\java\\experiment3\\dataset\\" + filepath);
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
