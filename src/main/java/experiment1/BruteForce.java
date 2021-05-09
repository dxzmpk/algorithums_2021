package experiment1;

import java.util.*;
import java.util.stream.Collectors;

public class BruteForce {

    public List<Point> bruteForce(List<Point> qPoints) {
        List<Point> res = new ArrayList<>();
        Set<Integer> removedIndex = new HashSet<>();
        int n = qPoints.size();
        for (int i1 = 0; i1 < n; i1 ++) {
            for (int i2 = 0; i2 < n; i2 ++) {
                for (int i3 = 0; i3 < n; i3++) {
                    for (int i4 = 0; i4 < n; i4 ++) {
                        if (i1 != i2 && i1 != i3 && i1 != i4
                        && i2 != i3 && i2 != i4
                        && i3 != i4) {
                            Point P = qPoints.get(i1);
                            Point A = qPoints.get(i2);
                            Point B = qPoints.get(i3);
                            Point C = qPoints.get(i4);
                            if (isPinABC(A, B, C, P)) {
                                removedIndex.add(i1);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (!removedIndex.contains(i)) {
                res.add(qPoints.get(i));
            }
        }
        Point A = res.get(0); // 横坐标最大
        int AIndex = 0;
        Point B = res.get(0); // 横坐标最小
        int BIndex = 0;
        for (int i = 1; i < res.size(); i++) {
            Point cur = res.get(i);
            if (cur.x < A.x) {
                A = cur;
                AIndex = i;
            }
            if (cur.x > B.x) {
                B = cur;
                BIndex = i;
            }
        }
        if (AIndex == BIndex) {
            res.remove(AIndex);
        } else {
            res.remove(AIndex);
            // remove A 之后， 坐标会前移一位
            res.remove(BIndex - 1);
        }
        // A 横坐标最小 B横坐标最大，和课件相反
        Point AB = B.sub(A);
        List<Point> SL = new ArrayList<>();
        Point finalA = A;
        SL = res.stream().filter(x -> pBeyondAB(x.sub(finalA), AB)).sorted().collect(Collectors.toList());
        List<Point> SR = new ArrayList<>();
        SR = res.stream().filter(x -> !pBeyondAB(x.sub(finalA), AB)).sorted().collect(Collectors.toList());
        Collections.reverse(SR);
        // 重新组织答案
        res.clear();
        res.add(A);
        res.addAll(SL);
        res.add(B);
        res.addAll(SR);
        return res;
    }
    
    public static void main(String[] args){
        Point A = new Point(0,0);
        Point B = new Point(0,5);
        Point C = new Point(5,0);
        Point P = new Point(1,1);
        Point D = new Point(5,5);
        Point E = new Point(1,-1);
        List<Point> qPoints = new ArrayList<>();
        qPoints.add(A);
        qPoints.add(B);
        qPoints.add(C);
        qPoints.add(P);
        qPoints.add(D);
        qPoints.add(E);
        qPoints.add(new Point(2,2));
        List<Point> res = new BruteForce().bruteForce(qPoints);
        System.out.println("res = " + res.toString());
    }

    boolean isPinABC(Point A, Point B, Point C, Point P){
        Point AB = B.sub(A);
        Point AP = P.sub(A);
        Point AC = C.sub(A);

        Point BC = C.sub(B);
        Point BA = A.sub(B);
        Point BP = P.sub(B);

        return atSameSide(AB, AP, AC)
                && atSameSide(BC, BA, BP)
                && atSameSide(AC, AP, AB);
    }

    boolean pBeyondAB(Point AP, Point AB) {
        return AB.mul(AP) > 0;
    }

    boolean atSameSide(Point vector, Point pointA, Point pointB){
        // 判断A、B是否位于vector同侧
        return vector.mul(pointA) * vector.mul(pointB) > 0;
    }


}
