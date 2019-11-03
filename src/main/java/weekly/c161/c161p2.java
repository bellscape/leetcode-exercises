package weekly.c161;

import common.Util;

public class c161p2 {

  public int numberOfSubarrays(int[] nums, int k) {
    int[] evenChoices = new int[nums.length + 1];
    int totalOdds = 0;
    for (int num : nums) {
      if (num % 2 == 0) {
        evenChoices[totalOdds]++;
      } else {
        totalOdds++;
      }
    }

    int ret = 0;
    for (int startOdd = 0; startOdd <= totalOdds - k; startOdd++) {
      ret += (evenChoices[startOdd] + 1) * (evenChoices[startOdd + k] + 1);
    }
    return ret;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

