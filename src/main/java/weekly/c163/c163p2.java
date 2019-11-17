package weekly.c163;

import common.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class c163p2 {

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
  }

  static class FindElements {
    private final TreeNode root;
    public FindElements(TreeNode root) {
      this.root = root;
      fix(root, 0);
    }
    private void fix(TreeNode node, int v) {
      node.val = v;
      if (node.left != null)
        fix(node.left, v * 2 + 1);
      if (node.right != null)
        fix(node.right, v * 2 + 2);
    }
    public boolean find(int target) {
      List<Integer> path = new ArrayList<>();
      int cur = target;
      while (cur > 0) {
        path.add(cur);
        cur = (cur - 1) / 2;
      }
      TreeNode node = this.root;
      Collections.reverse(path);
      for (int idx : path) {
        boolean left = idx % 2 == 1;
        node = left ? node.left : node.right;
        if (node == null)
          return false;
      }
      return true;
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

