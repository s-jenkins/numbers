package com.sdj.general;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class App {

    private static final Random rnd = new java.util.Random();


    public static void main(String[] args) {

        int n = 500;
        int balance = 3000;
        Strategy four = new Strategy(1, 8, new int[]{25, 26, 28, 29});

        int won = 0;
        int min = balance;
        int i = 0;
        while (i++ < n || won == 0) {
            int preBal = balance;
            int stake = four.begin();
            balance += stake;

            int winner = rnd.nextInt(37);
            won = four.end(winner);
            balance += won;
            min = Math.min(min, balance);

            if (preBal - stake < 0) {
                System.out.printf("#%s %s £%s (%s) £%s %s\n", i, winner, preBal, stake, balance, won > 0 ? won : "-");
            }
        }
        System.out.printf("\n£%s / £%s\n", balance, min);
        //System.out.printf("\nguide - %s\n", four.getGuide());
        System.out.printf("\nwaits - %s\n", four.getWinWaits());
    }

    private void run(Strategy... strats) {


    }

    public static void main2(String[] args) {

        List<Integer> c1 = Arrays.asList(new Integer[] {25, 26, 28, 29});

        Scenario s1 = new Scenario(5000, 99, c1);
        s1.run();
        System.out.println(s1);
    }


}
