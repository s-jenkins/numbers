package com.sdj.general;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Map.*;
import static java.util.stream.IntStream.*;

@Data
@RequiredArgsConstructor
public class Strategy {

    private final int unit;
    private final int factor;
    private final int[] bets;
    private final Map<Integer, Integer> guide = new HashMap<>();
    private int stake;
    private int played = 0;
    private int wins = 0;
    private int sinceWin = 0;
    private Map<Integer, Integer> winWaits = new HashMap<>();

    public int begin() {

        stake = suggest() * unit;
        return -stake;
    }

    public int end(int winner) {

        played++;
        if (of(bets).anyMatch(i -> i == winner)) {
            accumulate(winWaits, sinceWin);
            sinceWin = 0;
            wins++;
            return stake + factor * stake;
        }
        sinceWin++;
        return 0;
    }

    private static void accumulate(Map<Integer, Integer> target, int key) {

        Integer value = target.get(key);
        target.put(key, value == null ? 1 : value++);
    }

    private int suggest() {

        Integer suggest = guide.get(sinceWin);
        if (suggest == null) {
            int lost = guide.entrySet().stream().filter(e -> e.getKey() < played).mapToInt(Entry::getValue).sum();
            suggest = lost / factor;
            suggest = factor * suggest - lost < 1 ? suggest + 1 : suggest;
            guide.put(sinceWin, suggest);
        }
        return suggest;
    }
}
