package experiment3;


import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

import java.util.*;

public class SetCoverLP {
    public List<Set<Integer>> coverSet(Set<Integer> X, List<Set<Integer>> F) {
        double[] xi = new double[F.size()];
        Arrays.fill(xi, 1D);
        // x1 + x2 + x3 +.. xn
        LinearProgram lp = new LinearProgram(xi);
        // xi 在 0 - 1之间
        for (int i = 0; i < xi.length; i++) {
            double[] temp = new double[F.size()];
            temp[i] = 1;
            lp.addConstraint(new LinearBiggerThanEqualsConstraint(temp, 0, "c2" + i));
            lp.addConstraint(new LinearSmallerThanEqualsConstraint(temp, 1, "c3" + i));
        }
        // x1 + x2 + x3 +.. xn <= 1
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(xi.clone(), 1, "c1"));
        lp.setMinProblem(true);
        LinearProgramSolver solver  = SolverFactory.newDefault();
        double[] sol = solver.solve(lp);
        System.out.println("sol = " + Arrays.toString(sol));
        List<Set<Integer>> res = new ArrayList<>();
        int i;
        for (i = 0; i < F.size(); i++) {
            if (sol[i] >= 1/2) {
                res.add(F.get(i));
            }
        }
        return res;
    }
    public static void main(String[] args){
        SetCoverLP setCoverLP = new SetCoverLP();
        SetGenerator generator = new SetGenerator();
        generator.generateXandF(10);
        List<Set<Integer>> list = new ArrayList<>(generator.F);
        List<Set<Integer>> res = setCoverLP.coverSet(generator.X, list);
        System.out.println("X = " + generator.X);
        System.out.println("res = " + res.toString());
    }
}
