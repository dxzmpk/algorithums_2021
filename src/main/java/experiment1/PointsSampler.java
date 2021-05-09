package experiment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PointsSampler {


    Point getRandom(Random random) {
        return new Point(random.nextInt(101), random.nextInt(101));
    }

    List<Point> generatePoints(Integer num) {
        List<Point> pointList = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < num; i++) {
            pointList.add(getRandom(random));
        }
        return pointList;
    }

    public static void main(String[] args){
        PointsSampler pointsSampler = new PointsSampler();
        List<Point> pointList = pointsSampler.generatePoints(100);
        System.out.println(pointList.size());
    }
}
