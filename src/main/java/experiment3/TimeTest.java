package experiment3;

import time.Runner;
import time.TimeEvaluater;

import java.util.Arrays;

public class TimeTest {
    public static void main(String[] args){
        SetGenerator generator_100 = new SetGenerator();
        generator_100.generateXandF(100);
        SetGenerator generator_1000 = new SetGenerator();
        generator_1000.generateXandF(1000);
        SetGenerator generator_5000 = new SetGenerator();
        generator_5000.generateXandF(5000);
        TimeEvaluater evaluater = new TimeEvaluater();
        SetCoverGreedy greedy = new SetCoverGreedy();
        SetCoverLP lp = new SetCoverLP();
        evaluater.testAndPrint(() -> {
            greedy.coverSet(generator_100.X, generator_1000.F);
        }, "greedy_100");
        evaluater.testAndPrint(() -> {
            lp.coverSet(generator_100.X, generator_1000.F);
        }, "lp_100");

        evaluater.testAndPrint(() -> {
            greedy.coverSet(generator_1000.X, generator_1000.F);
        }, "greedy_1000");
        evaluater.testAndPrint(() -> {
            lp.coverSet(generator_1000.X, generator_1000.F);
        }, "lp_1000");

        evaluater.testAndPrint(() -> {
            greedy.coverSet(generator_5000.X, generator_5000.F);
        }, "greedy_5000");
        evaluater.testAndPrint(() -> {
            lp.coverSet(generator_5000.X, generator_5000.F);
        }, "lp_5000");
    }
}
