package experiment2;

public class Pos implements Cloneable{
    int x;
    int y;
    double gcos;
    boolean left = false;
    boolean close = false;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(int x, int y, double gcos) {
        this.x = x;
        this.y = y;
        this.gcos = gcos;
    }

    Pos move(int detX, int detY) {
        return new Pos(x + detX, y + detY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        // 当两个点位置相同时，认为是相同的，主要用来判断目标点。
        return x == pos.x && y == pos.y;
    }

    @Override
    public int hashCode() {
        return 17*x + y;
    }

    @Override
    public String toString() {
        return "Pos{" +
                       "x=" + x +
                       ", y=" + y +
                       ", cos=" + gcos +
                       '}';
    }

}
