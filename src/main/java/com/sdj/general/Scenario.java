package com.sdj.general;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class Scenario {

    private static Random rnd = new Random();

    private final int spins;
    private final Set<Integer> bets = new HashSet<>();
    private final Set<Integer> secondary = new HashSet<>();
    private final int waitLimit;

    Map<Integer, Integer> winDist = new HashMap<>();
    Map<Integer, Integer> preWinDist = new HashMap<>();
    Map<Integer, Integer> depthWinDist = new HashMap<>();
    int attempts = 1;
    int depth = 0;
    int maxDepth = 0;
    int recoveries = 0;
    int dips = 0;

    public Scenario(int spins, int waitLimit, Collection<Integer> bets) {

        this.spins = spins;
        this.waitLimit = waitLimit;
        this.bets.addAll(bets);
    }

    public void run() {

        for (int i = 0; i < spins; i ++) {
            int winner = rnd.nextInt(37);
            process(winner);
        }
    }

    private static void accumulate(Map<Integer, Integer> target, Integer key) {

        Integer value = target.get(key);
        if (value == null) {
            value = 1;
        }
        else {
            value++;
        }
        target.put(key, value);
    }

    private void process(int winner) {

        if (bets.contains(winner)) {
            processWin();
        }
        else {
            processLoss();
        }
        attempts++;
    }

    private void processLoss() {

        accumulate(preWinDist, attempts);
        if (attempts == waitLimit) {
            depth++;
            dips++;
            maxDepth = Math.max(depth, maxDepth);
        }
    }

    private void processWin() {

        accumulate(winDist, attempts);
        if (depth > 0) {
            accumulate(depthWinDist, attempts);
            depth--;
            recoveries++;
        }
        attempts = 0;
    }
}