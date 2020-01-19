package weekly.c172;

import common.Util;

public class c172p3 {

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
  }

  public TreeNode removeLeafNodes(TreeNode node, int target) {
    if (node == null) return null;

    node.left = removeLeafNodes(node.left, target);
    node.right = removeLeafNodes(node.right, target);

    if (node.left == null && node.right == null) {
      return node.val == target ? null : node;
    } else {
      return node;
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

