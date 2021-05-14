package experiment3;

import sun.java2d.pipe.AAShapePipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetCoverGreedy {

    /**
     *
     * @param X X is a finite set
     * @param F F is the set of subset of X
     * @return C is the smallest set to cover X
     */
    public List<Set<Integer>> coverSet(Set<Integer> X, List<Set<Integer>> F) {
        Set<Integer> U = new HashSet<>(X);
        List<Set<Integer>> C = new ArrayList<>();
        while (U.size() > 0) {
            Set<Integer> mostRepeatSet = null;
            Integer mostRepeat = 0;
            for (Set<Integer> set : F) {
                int repeat = 0;
                for(Integer member : set) {
                    if (U.contains(member)) {
                        repeat ++;
                    }
                }
                if (repeat > mostRepeat) {
                    mostRepeat = repeat;
                    mostRepeatSet = set;
                }
            }
            if(mostRepeatSet == null) {
                return null;
            }
            C.add(mostRepeatSet);
            U.removeAll(mostRepeatSet);
        }
        return C;
    }

    public static void main(String[] args){
        SetCoverGreedy setCoverGreedy = new SetCoverGreedy();
        SetGenerator generator = new SetGenerator();
        generator.generateXandF(10);
        List<Set<Integer>> list = new ArrayList<>(generator.F);
        List<Set<Integer>> res = setCoverGreedy.coverSet(generator.X, list);
        System.out.println("X = " + generator.X);
        System.out.println("res = " + res.toString());
    }
}
