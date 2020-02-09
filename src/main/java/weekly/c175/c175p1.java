package weekly.c175;

import common.Util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c175p1 {

  public boolean checkIfExist(int[] arr) {
    if (IntStream.of(arr).filter(i -> i == 0).count() > 1)
      return true;

    Set<Integer> nums = IntStream.of(arr)
            .filter(i -> i != 0)
            .boxed()
            .collect(Collectors.toSet());
    return IntStream.of(arr)
            .anyMatch(i -> nums.contains(i * 2));
  }

  public static void main(String[] args) { Util.runFiles(); }

}

