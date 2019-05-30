package com.sdj.general;

import java.util.Arrays;
import java.util.List;

class App {

    public static void main(String[] args) {

        List<Integer> c1 = Arrays.asList(new Integer[] {25, 26, 28, 29});

        Scenario s1 = new Scenario(5000, 99, c1);
        s1.run();
        System.out.println(s1);
    }
}
