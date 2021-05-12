package experiment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TheMap {

    int[][] map;
    // 1: 代表灰色
    Pos source;
    Pos target;
    static double SQRT2 = Math.sqrt(2);

    /**
     * read the map into a 2-dim array
     * @param filename
     * @return
     */
    public void loadMap1(String filename) {
        filename = "E:\\elipseProject\\algorithums_2021\\src\\main\\java\\experiment2\\" + filename;
        ArrayList<String> mapStrings = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                mapStrings.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int len = mapStrings.size();
        map = new int[17][14];

        String[] sourceString = mapStrings.get(0).split(" ");
        source = new Pos(s2Int(sourceString[0]), s2Int(sourceString[1]));
        String[] targetString = mapStrings.get(1).split(" ");
        target = new Pos(s2Int(targetString[0]), s2Int(targetString[1]));
        // 读入地图 0--普通地形 1--障碍 2--溪流 4--沙漠
        for (int i = 2; i < len; i++) {
            String[] s = mapStrings.get(i).split(" ");
            Integer cost = s2Int(s[2]);
            if (cost == 1) {
                cost = Integer.MAX_VALUE;
            }
            map[s2Int(s[0])][s2Int(s[1])] = cost;
        }
        System.out.println("Map loaded");
    }

    public void loadMap2(String filename) {
        filename = "E:\\elipseProject\\algorithums_2021\\src\\main\\java\\experiment2\\" + filename;
        ArrayList<String> mapStrings = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                mapStrings.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int len = mapStrings.size();
        map = new int[40][20];
        String[] sourceString = mapStrings.get(0).split(" ");
        source = new Pos(s2Int(sourceString[0]) - 1, 20 - s2Int(sourceString[1]));
        String[] targetString = mapStrings.get(1).split(" ");
        target = new Pos(s2Int(targetString[0]) - 1, 20 - s2Int(targetString[1]));
        // 读入地图 0--普通地形 1--障碍 2--溪流 4--沙漠
        int cost = Integer.MAX_VALUE;
        for (int i = 2; i < len; i++) {
            String[] s = mapStrings.get(i).split(" ");
            if (s2Int(s[0]) == 0 && s2Int(s[1]) == 0) {
                if (cost == Integer.MAX_VALUE) {
                    cost = 4;
                    continue;
                }
                cost = 2;
                continue;
            }
            map[s2Int(s[0]) - 1][20 - s2Int(s[1])] = cost;
        }
        System.out.println("Map loaded");
    }

    int s2Int(String s) {
        return Integer.parseInt(s);
    }

    public static void main(String[] args){
        new TheMap().loadMap1("map/map1.txt");
    }

    public List<Pos> avails(Pos src) {
        List<Pos> avails = new ArrayList<>();
        for (int out = -1; out <= 1; out++) {
            for (int in = -1; in <= 1; in++) {
                if (!(in == 0 && out == 0)) {
                    Pos cur = src.move(out, in);
                    if (in * out != 0) {
                        cur.gcos = src.gcos + Math.sqrt(2);
                    } else {
                        cur.gcos = src.gcos + 1;
                    }

                    // 如果当前节点在地图中， 则加入可达到的节点
                    if (inMap(cur)) {
                        // 不应加入不可到达的节点
                        if (map[cur.x][cur.y] == Integer.MAX_VALUE) {
                            continue;
                        }
                        // 首先加上代价
                        cur.gcos += map[cur.x][cur.y];
                        avails.add(cur);
                    }
                }
            }
        }
        return avails;
    }

    public boolean inMap(Pos pos) {
        if (pos.x >= 0 && pos.x <= map.length - 1) {
            if (pos.y >= 0 && pos.y <= map[0].length - 1) {
                return true;
            }
        }
        return false;
    }
}
