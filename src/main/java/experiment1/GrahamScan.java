package experiment1;

import java.util.*;

public class GrahamScan {

    public List<Point> grahamScan(List<Point> qPoints) {
        Point P = qPoints.get(0);
        int n = qPoints.size();
        int min = 0;
        for (int i = 1; i < n; i++) {
            int y = qPoints.get(i).y;
            if (y < P.y || y == P.y && qPoints.get(i).x < P.x) {
                P = qPoints.get(i);
                min = i;
            }
        }
        sawp(qPoints, 0, min);
        qPoints.remove(0);
        // 及时更新n
        n = qPoints.size();
        Point finalP = P;
        // Sorting in order of angle
        qPoints.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1 == null && o2 == null) {
                    return 0;
                }
                if(o1 == null) {
                    return -1;
                }
                if(o2 == null) {
                    return 1;
                }
                if (o1.equals(o2)) {
                    return 0;
                }
                int o = orientation(finalP, o1, o2);
                if (o == 0)
                    return (distSq(finalP, o2) >= distSq(finalP, o1)) ? -1 : 1;
                return (o == 2) ? -1 : 1;
            }
        });

        // 如果在相同角度，则仅保留最远的一个
        int m = 0;
        for (int i = 0; i < n; i++) {
            while (i < n - 1 && orientation(finalP, qPoints.get(i), qPoints.get(i+1)) == 0) {
                // when while quits, i will be n-1
                i ++;
            }
            qPoints.set(m, qPoints.get(i));
            m++;
        }
        if (m < 3) {
            return null;
        }
        Stack<Point> S = new Stack<>();
        S.push(finalP);
        S.push(qPoints.get(0));
        S.push(qPoints.get(1));
        // Process remaining n-3 points
        for (int i = 2; i < m; i++)
        {
            // Keep removing top while the angle formed by
            // points next-to-top, top, and points[i] makes
            // a non-left turn
            while (S.size()>1 && orientation(nextToTop(S), S.peek(), qPoints.get(i)) != 2)
                S.pop();
            S.push(qPoints.get(i));
        }

        List<Point> res = new ArrayList<>();
        // Now stack has the output points, print contents of stack
        while (!S.empty())
        {
            Point p = S.peek();
            res.add(p);
            S.pop();
        }
        return res;
    }

    private Point nextToTop(Stack<Point> s) {
        Point top = s.pop();
        Point res = s.peek();
        s.push(top);
        return res;
    }

    public int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) -
                          (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;  // colinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    private void sawp(List<Point> qPoints, int i, int min) {
        Point temp = qPoints.get(i);
        qPoints.set(i, qPoints.get(min));
        qPoints.set(min, temp);
    }

    // A utility function to return square of distance
    // between p1 and p2
    int distSq(Point p1, Point p2)
    {
        return (p1.x - p2.x)*(p1.x - p2.x) +
                       (p1.y - p2.y)*(p1.y - p2.y);
    }


    /***
     * 叉积
     * a×b>0, 则b在a的逆时针方向；
     * a×b<0, 则b在a的顺时针方向；
     * a×b=0, 则a与b共线，但可能同向也可能反向。
     */
    double crossProduct(Point a, Point b){
        return a.x*b.y - a.y*b.x;
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
        qPoints.addAll(new PointsSampler().generatePoints(100));
        List<Point> res = new GrahamScan().grahamScan(qPoints);
        System.out.println("res = " + res.toString());
    }
}
