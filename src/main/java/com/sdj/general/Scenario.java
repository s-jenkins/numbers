package com.sdj.general;

import java.util.Random;

class Scenario {

  private static Random rnd = new Random();

  public static void run() {


    System.out.println("Hello Scenario " + rnd.nextInt());
  }
}