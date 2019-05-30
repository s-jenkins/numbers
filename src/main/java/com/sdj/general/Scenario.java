package com.sdj.general;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class Scenario {

    private enum RecoveryMode {OFF, WAITING, RECOVER_WAITING, RECOVERING}
    private static Random rnd = new Random();

    private final int spins;
    private final Set<Integer> bets = new HashSet<>();
    private final Set<Integer> secondary = new HashSet<>();
    private final int waitLimit;

    final Map<Integer, Integer> winDist = new HashMap<>();
    final Map<Integer, Integer> offDist = new HashMap<>();
    final Map<Integer, Integer> preWinDist = new HashMap<>();
    final Map<Integer, Integer> depthWinDist = new HashMap<>();
    final Map<Integer, Integer> waitWinDist = new HashMap<>();

    int attempts = 1;
    int depth = 0;
    int maxDepth = 0;
    int recoveries = 0;
    int dips = 0;
    int waits = 0;
    int wins = 0;
    RecoveryMode rMode = RecoveryMode.OFF;

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
            waits++;
            switch (rMode) {
                case OFF :
                    rMode = RecoveryMode.WAITING;
                    break;
                case RECOVERING :
                    rMode = RecoveryMode.RECOVER_WAITING;
                    break;
            }
        }
    }

    private void processWin() {

        wins++;
        accumulate(winDist, attempts);
        switch (rMode) {
            case OFF :
                accumulate(offDist, attempts);
                break;
            case WAITING :
                rMode = RecoveryMode.RECOVERING;
                depth = 1;
                maxDepth = Math.max(depth, maxDepth);
                dips++;
                break;
            case RECOVERING :
                accumulate(depthWinDist, attempts);
                depth--;
                recoveries++;
                if (depth == 0) {
                    rMode = RecoveryMode.OFF;
                }
                break;
            case RECOVER_WAITING :
                rMode = RecoveryMode.RECOVERING;
                depth++;
                dips++;
                maxDepth = Math.max(depth, maxDepth);
            default :
        }
        attempts = 0;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");

        int max = winDist.keySet().stream().reduce(Integer::max).get();
        float roll = 0;
        for (int i = 1; i <= max; i++) {
            Integer n = winDist.get(i);
            n = n == null ? 0 : n;
            float f = n;
            roll += f;
            float w = wins;
            sb.append(String.format("%.0f\n", f));
            //sb.append(String.format("%s, %.0f, %.1f, %.1f\n",
            //    i, f, f*100/w, roll*100/w));
        }
        sb.append("wins: ");
        sb.append(wins);
        sb.append(" ");
        sb.append(wins*100f/spins);
        sb.append("%");

        sb.append("\n");

        sb.append(winDist);
        sb.append("\n");

        return sb.toString();
    }

    public String toString1() {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("\n-\nwins: %s", wins));
        sb.append(String.format("\n-\nwin dist: %s", winDist));
        sb.append(String.format("\noff dist: %s", offDist));
        sb.append(String.format("\ndepth win dist: %s", depthWinDist));
        sb.append(String.format("\npre dist: %s", preWinDist));
        sb.append(String.format("\ndips: %s", dips));
        sb.append(String.format("\nmax depth: %s", maxDepth));
        sb.append(String.format("\nrecoveries: %s", recoveries));
        sb.append(String.format("\nwaits: %s", waits));
        sb.append(String.format("\n-\nstatus: %s", rMode));
        sb.append(String.format("\nattemps: %s\n-\n", attempts));

        return sb.toString();
    }

}