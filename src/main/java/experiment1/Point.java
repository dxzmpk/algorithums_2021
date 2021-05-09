package experiment1;

public class Point implements Comparable<Point> {
    int x;
    int y;

    @Override
    public int compareTo(Point o) {
        return x - o.x;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point sub(Point b) {
        return new Point(x - b.x, y - b.y);
    }

    public int mul(Point ap) {
        return x*ap.y - y*ap.x;
    }

    @Override
    public String toString() {
        return '[' + "x = " + x + ", y = " + y + ']';
    }

    public boolean leftInclusiveTo(Point anchor) {
        return anchor.x - x >= 0;
    }

    public int getX() {
        return x;
    }
}
