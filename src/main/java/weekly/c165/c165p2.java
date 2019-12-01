package weekly.c165;

import common.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class c165p2 {

  public List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {
    int totalBurgers = cheeseSlices;
    int minTomatoes = totalBurgers * 2;
    int jumboCount = (tomatoSlices - minTomatoes) / 2;
    int smallCount = totalBurgers - jumboCount;
    boolean ok = jumboCount >= 0 && smallCount >= 0 && (jumboCount * 4 + smallCount * 2 == tomatoSlices);
    return ok ? Arrays.asList(jumboCount, smallCount) : Collections.emptyList();
  }

  public static void main(String[] args) { Util.runFiles(); }

}

