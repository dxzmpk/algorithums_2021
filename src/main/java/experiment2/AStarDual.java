package experiment2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarDual {
    TheMap mMap;
    public List<Pos> search(TheMap map) {
        mMap = map;
        Pos src = map.source;
        Pos tar = map.target;
        Map<Pos, Double> srcOpenMap = new HashMap<>();
        Map<Pos, Double> tarOpenMap = new HashMap<>();
        // 因为起点必被选中，因此没有加入到open表中
        Map<Pos, List<Pos>> srcRoads = new HashMap<>();
        Map<Pos, List<Pos>> tarRoads = new HashMap<>();

        List<Pos> srcCloseList = new ArrayList<>();
        srcCloseList.add(src);
        List<Pos> tarCloseList = new ArrayList<>();
        tarCloseList.add(tar);
        List<Pos> initRoadSrc = new ArrayList<>();
        initRoadSrc.add(src);
        srcRoads.put(src, initRoadSrc);
        List<Pos> initRoadTar = new ArrayList<>();
        initRoadTar.add(tar);
        tarRoads.put(tar, initRoadTar);
        // 当二者openMap没有相交的时候
        while (true) {
            List<Pos> srcAvails = map.avails(src);
            List<Pos> tarAvails = map.avails(tar);
            for (Pos pos : srcAvails) {
                double fScore = calFSrc(pos);
                Pos oldPos = getEqualsKey(pos, srcOpenMap);
                // 拦截更新--当closeList中已经存在时，不再添加
                if (srcCloseList.contains(pos)) {
                    continue;
                }
                // 拦截更新--当新的代价比原来的高时，不进行更新
                if (oldPos!=null && oldPos.gcos < pos.gcos) continue;
                srcOpenMap.remove(oldPos);
                srcOpenMap.put(pos, fScore);

                List<Pos> newRoad = new ArrayList<>(srcRoads.get(src));
                newRoad.add(pos);
                srcRoads.put(pos, newRoad);
            }
            for (Pos pos : tarAvails) {
                double fScore = calFTar(pos);
                Pos oldPos = getEqualsKey(pos, tarOpenMap);
                // 拦截更新--当新的代价比原来的高时，不进行更新
                // 拦截更新--当closeList中已经存在时，不再添加
                if (tarCloseList.contains(pos)) {
                    continue;
                }
                // 拦截更新--当新的代价比原来的高时，不进行更新
                if (oldPos!=null && oldPos.gcos < pos.gcos) continue;
                tarOpenMap.remove(pos);
                tarOpenMap.put(pos, fScore);

                List<Pos> newRoad = new ArrayList<>(tarRoads.get(tar));
                newRoad.add(pos);
                tarRoads.put(pos, newRoad);
            }
            // 当找到重合点时，直接返回
            if (intercept(srcOpenMap, tarOpenMap) != null) {
                break;
            }
            src = findAndRemoveLowestPos(srcOpenMap);
            tar = findAndRemoveLowestPos(tarOpenMap);

            srcCloseList.add(src);
            tarCloseList.add(tar);
        }

        // 相交点
        Pos intercept = intercept(srcOpenMap, tarOpenMap);
        List<Pos> srcRoad = srcRoads.get(intercept);
        List<Pos> tarRoad = tarRoads.get(intercept);
        List<Pos> resRoad = new ArrayList<>();
        for (int i = 0; i < srcRoad.size(); i++) {
            if (srcRoad.get(i).equals(intercept)) {
                break;
            }
            Pos tem = srcRoad.get(i);
            tem.left = true;
            resRoad.add(tem);
        }
        boolean recordFlag = false;
        for (int i = tarRoad.size() - 1; i >= 0; i--) {
            if (tarRoad.get(i).equals(intercept)) {
                recordFlag = true;
            }
            if (recordFlag) {
                Pos tem = tarRoad.get(i);
                tem.left = false;
                resRoad.add(tem);
            }
        }

        double cost = intercept.gcos;

        for (Pos pos : tarOpenMap.keySet()) {
            if (pos.equals(intercept)) {
                cost += pos.gcos;
            }
        }
        System.out.println("Cost = " + cost);
        int num = srcCloseList.size() + tarCloseList.size();
        System.out.println("Close表中数目 = " + num);
        srcCloseList.addAll(tarCloseList);
        //for (Pos pos : srcCloseList) {
        //    Pos tem = pos;
        //    tem.close = true;
        //    resRoad.add(tem);
        //}
        return resRoad;
    }

    private void updateRoadsByAvails(Map<Pos, Double> srcOpenMap, List<Pos> srcAvails, Map<Pos, List<Pos>> srcRoads) {
    }

    private Pos intercept(Map<Pos, Double> srcOpenMap, Map<Pos, Double> tarOpenMap) {
        for(Pos pos : srcOpenMap.keySet()) {
            if (tarOpenMap.containsKey(pos)) {
                return pos;
            }
        }
        return null;
    }

    private Pos getEqualsKey(Pos target, Map<Pos, Double> poses) {
        for (Map.Entry<Pos, Double> entry : poses.entrySet()) {
            if (entry.getKey().equals(target)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        AStarDual aSTarDual = new AStarDual();
        TheMap map = new TheMap();
        // map1和map2采用不同的格式，因此读入方式不同，分别为TheMap#loadMap1, TheMap#loadMap2
        //String filepath = "map/map1.txt";
        //map.loadMap1(filepath);
        String filepath2 = "map/map1.txt";
        map.loadMap1(filepath2);
        List<Pos> resList = aSTarDual.search(map);
        new VisualizeControl().drawMapAndRoad(map, resList);
        System.out.println("The Best Path = " + resList.toString());
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

    private double calFSrc(Pos pos) {
        return calG(pos) + calH(pos, mMap.target);
    }

    private double calFTar(Pos pos) {
        return calG(pos) + calH(pos, mMap.source);
    }

    private double calG(Pos pos) {
        return pos.gcos;
    }

    private double calH(Pos pos1, Pos pos2) {
        return dis(pos1, pos2);
    }

    private double dis(Pos pos1, Pos pos2) {
        return Math.sqrt(
                Math.pow(pos1.x - pos2.x, 2) +
                        Math.pow(pos1.y - pos2.y, 2));
    }


}
