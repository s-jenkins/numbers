package com.sdj.general;

import java.util.Arrays;
import java.util.List;

class App {

    public static void main(String[] args) {
        
        List<Integer> c1 = Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12});

        Scenario s1 = new Scenario(1000, 6, c1);
        s1.run();
        System.out.printf("\nwin dist: %s", s1.winDist);
        System.out.printf("\ndepth win dist: %s", s1.depthWinDist);
        System.out.printf("\npre dist: %s", s1.preWinDist);
        System.out.printf("\ndepth: %s", s1.depth);
        System.out.printf("\ndips: %s", s1.dips);
        System.out.printf("\nmax depth: %s", s1.maxDepth);
        System.out.printf("\nrecoveries: %s", s1.recoveries);
        System.out.printf("\n\n");
    }
}
