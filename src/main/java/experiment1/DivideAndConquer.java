package experiment1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DivideAndConquer {
    public List<Point> divideAndConquer(List<Point> qPoints) {
        int n = qPoints.size();
        // 如果|Q|<3, 算法停止;
        if (n < 3) {
            return qPoints;
        }
        // 如果|Q|=3, 按照逆时针方向输出CH(Q)的顶点;
        if (n == 3) {
            qPoints.sort(new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.x != o2.x) {
                        return o2.x - o1.x;
                    } else {
                        return o2.y - o1.y;
                    }
                }
            });
            return qPoints;
        }
        // 根据x大小排序
        qPoints.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.x - o2.x;
            }
        });
        int middleIndex = n/2;
        List<Point> Ql = qPoints.subList(0, middleIndex);
        List<Point> Qr = qPoints.subList(middleIndex, n);
        List<Point> leftRes = divideAndConquer(Ql);
        List<Point> rightRes = divideAndConquer(Qr);
        List<Point> res = merger(leftRes, rightRes);
        return res;
    }

    private List<Point> merger(List<Point> leftRes, List<Point> rightRes) {
        List<Point> res = new ArrayList<>();
        if (leftRes != null) {
            res.addAll(leftRes);
        }
        if (rightRes != null) {
            res.addAll(rightRes);
        }
        if (res.size() == 0) {
            return res;
        }
        GrahamScan sca = new GrahamScan();
        res = sca.grahamScan(res);
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
        List<Point> res = new DivideAndConquer().divideAndConquer(qPoints);
        System.out.println("res = " + res.toString());
    }
}

