package time;

import java.util.Random;

public class TimeEvaluater {


    public static void main(String[] args){
        //measuring elapsed experiment1.time using System.nanoTime
        TimeEvaluater evaluater = new TimeEvaluater();
        //evaluater.testAndPrint(()->new MatrixInit().run1());
        //evaluater.testMultiAndPrint(()->new MatrixInit().run1(), 10000);
    }

    public static int[] getRandomIntArray(int N) {
        int[] result = new int[N];
        Random random = new Random(1);
        while (N-- > 0) {
            result[N] = Math.abs(random.nextInt(1000000000));
        }
        return result;
    }

    public void testAndPrint(Runner runner){
        testAndPrint(runner, "Anonymous");
    }


    public void testAndPrint(Runner runner, String masterName){



        long startTime = System.nanoTime();

        runner.run();

        long elapsedTime = System.nanoTime() - startTime;

        System.out.println(masterName + " time = " + elapsedTime/1000000);
    }


    public void testMultiAndPrint(Runner runner,int N){

        long startTime = System.nanoTime();

        for(int i = 0; i < N; i++) {
            runner.run();
        }

        long elapsedTime = System.nanoTime() - startTime;

        System.out.println(N + "次test平均用时（毫秒）= "
                                   + (double)elapsedTime/1000000/N);
    }


}
