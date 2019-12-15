package weekly.c167;

import common.Util;

public class c167p1 {

  public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
  }

  public int getDecimalValue(ListNode head) {
    int result = 0;
    while (head != null) {
      result *= 2;
      result += head.val;
      head = head.next;
    }
    return result;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

