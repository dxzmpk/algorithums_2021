package experiment1;

import time.Runner;
import time.TimeEvaluater;

import java.util.List;

public class ComparingAlgos {
    public static void main(String[] args){
        PointsSampler sampler = new PointsSampler();
        TimeEvaluater timeEvaluater = new TimeEvaluater();
        BruteForce bruteForce = new BruteForce();
        GrahamScan grahamScan = new GrahamScan();
        DivideAndConquer divideAndConquer = new DivideAndConquer();
        int num = 100;
        for (int i = 1; i <= 10; i++) {
            num *= 10;
            List<Point> pointList = sampler.generatePoints(num);
            //timeEvaluater.testAndPrint(new Runner() {
            //    @Override
            //    public void run() {
            //        bruteForce.bruteForce(pointList);
            //    }
            //}, BruteForce.class.getName());

            timeEvaluater.testAndPrint(new Runner() {
                @Override
                public void run() {
                    grahamScan.grahamScan(pointList);
                }
            }, "\t");

            timeEvaluater.testAndPrint(new Runner() {
                @Override
                public void run() {
                    divideAndConquer.divideAndConquer(pointList);
                }
            }, "\n");
        }
        System.out.println("num = " + num);
    }
}
