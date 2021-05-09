package experiment2;

import java.util.*;

public class AStarSingle {
    TheMap mMap;
    public List<Pos> search(TheMap map) {
        mMap = map;
        Pos src = map.source;
        Pos tar = map.target;
        Map<Pos, Double> poses = new HashMap<>();
        Map<Pos, List<Pos>> roads = new HashMap<>();
        List<Pos> initRoad = new ArrayList<>();
        initRoad.add(src);
        roads.put(src, initRoad);
        while (getEquals(mMap.target, poses) == null) {
            List<Pos> avails = map.avails(src);
            for (Pos pos : avails) {
                double fScore = calF(pos);
                Pos oldPos = getEquals(pos, poses);
                // 拦截更新--当新的代价比原来的高时，不进行更新
                if (oldPos != null && oldPos.cos < pos.cos) continue;
                poses.put(pos, fScore);
                List<Pos> newRoad = new ArrayList<>(roads.get(src));
                newRoad.add(pos);
                roads.put(pos, newRoad);
            }
            src = findLowestPos(poses);
        }
        return roads.get(getEquals(mMap.target, poses));
    }

    private Pos getEquals(Pos target, Map<Pos, Double> poses) {
        for (Map.Entry<Pos, Double> entry : poses.entrySet()) {
            if (entry.getKey().equals(target)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public static void main(String[] args){
        AStarSingle aStarSingle = new AStarSingle();
        TheMap map = new TheMap();
        // map1和map2采用不同的格式，因此读入方式不同，分别为TheMap#loadMap1, TheMap#loadMap2
        //String filepath = "map/map1.txt";
        //map.loadMap1(filepath);
        String filepath2 = "map/map2.txt";
        map.loadMap2(filepath2);
        List<Pos> resList = aStarSingle.search(map);
        new VisualizeControl().drawMapAndRoad(map, resList);
        System.out.println("The Best Path = " + resList.toString());
    }

    private Pos findLowestPos(Map<Pos, Double> tm) {
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
        return pos.cos;
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
