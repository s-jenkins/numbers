package com.sdj.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class Scenario {

    private static Random rnd = new Random();

    private final int n;
    private final Set<Integer> bets = new HashSet<>();
    private final Set<Integer> secondary = new HashSet<>();
    private int wins;
    private Map<Integer, Integer> winDist = new HashMap<>();;


    public Scenario() {

    }

    public static void run() {


        System.out.println("Hello Scenario " + rnd.nextInt());
  }
}