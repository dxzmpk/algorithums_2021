package experiment2;

import java.util.*;

public class AStarSingle {
    TheMap mMap;
    public List<Pos> search(TheMap map) {
        mMap = map;
        Pos src = map.source;
        Pos tar = map.target;
        Map<Pos, Double> openMap = new HashMap<>();
        List<Pos> closeList = new ArrayList<>();
        // 因为起点必被选中，因此没有加入到open表中，直接加入到close表中
        closeList.add(src);
        Map<Pos, List<Pos>> roads = new HashMap<>();
        List<Pos> initRoad = new ArrayList<>();
        initRoad.add(src);
        roads.put(src, initRoad);
        while (!openMap.containsKey(tar)) {
            List<Pos> avails = map.avails(src);
            for (Pos pos : avails) {
                // 计算当前节点的f值
                double fScore = calF(pos);
                // 拦截更新--当closeList中已经存在时，不再添加
                if (closeList.contains(pos)) {
                    continue;
                }
                Pos oldPos = getEquals(pos, openMap);
                // 拦截更新--当新的代价比原来的高时，不进行更新
                if (oldPos!=null && oldPos.gcos < pos.gcos) continue;
                openMap.remove(oldPos);
                openMap.put(pos, fScore);
                List<Pos> newRoad = new ArrayList<>(roads.get(src));
                newRoad.add(pos);
                roads.put(pos, newRoad);
            }
            src = findAndRemoveLowestPos(openMap);
            closeList.add(src);
        }
        Double cost = getEquals(tar, openMap).gcos;
        System.out.println("Close表中数目 = " + closeList.size());
        System.out.println("Cost = " + cost);
        List<Pos> resList = roads.get(getEquals(tar, openMap));
        //for (Pos pos : closeList) {
        //    Pos tem = pos;
        //    tem.close = true;
        //    resList.add(tem);
        //}
        return resList;
    }



    private Pos getEquals(Pos target, Map<Pos, Double> poses) {
        for (Map.Entry<Pos, Double> entry : poses.entrySet()) {
            if (entry.getKey().equals(target)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        AStarSingle aStarSingle = new AStarSingle();
        TheMap map = new TheMap();
        // map1和map2采用不同的格式，因此读入方式不同，分别为TheMap#loadMap1, TheMap#loadMap2
        //String filepath = "map/map1.txt";
        //map.loadMap1(filepath);
        String filepath2 = "map/map1.txt";
        map.loadMap1(filepath2);
        List<Pos> resList = aStarSingle.search(map);
        new VisualizeControl().drawMapAndRoad(map, resList);

        //Thread.sleep(1000);
        //map.swapTarSrc();
        //List<Pos> res2 = aStarSingle.search(map);
        //new VisualizeControl().drawMapAndRoad(map, res2);
        //
        //System.out.println("The Best Path = " + resList.toString());
    }

    private Pos findAndRemoveLowestPos(Map<Pos, Double> tm) {
        double lowestF = Double.MAX_VALUE;
        Pos lowestPos = null;
        for (Map.Entry<Pos, Double> entry : tm.entrySet()) {
            if (entry.getValue() < lowestF) {
                lowestF = entry.getValue();
                lowestPos = entry.getKey();
            }
        }
        tm.remove(lowestPos);
        return lowestPos;
    }

    private double calF(Pos pos) {
        return calG(pos) + calH(pos);
    }

    private double calG(Pos pos) {
        return pos.gcos;
    }

    private double calH(Pos pos) {
        return dis(pos, mMap.target);
    }

    private double dis(Pos pos1, Pos pos2) {
        return Math.sqrt(
                Math.pow(pos1.x - pos2.x, 2) +
                        Math.pow(pos1.y - pos2.y, 2));
    }


}
