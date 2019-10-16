package some.test.problem;

import java.util.HashSet;
import java.util.Set;

public class p3 {

  public int lengthOfLongestSubstring(String s) {
    int maxLen = 0;
    int start = 0;
    Set<Character> met = new HashSet<>();
    for (int end = 0; end < s.length(); end++) {
      char c = s.charAt(end);
      if (met.contains(c)) {
        while (s.charAt(start) != c) {
          met.remove(s.charAt(start));
          start++;
        }
        start++;
      } else {
        met.add(c);
        maxLen = Math.max(maxLen, end - start + 1);
      }
    }
    return maxLen;
  }

}
