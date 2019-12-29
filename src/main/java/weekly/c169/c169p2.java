package weekly.c169;

import common.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class c169p2 {

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
  }

  public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
    List<Integer> nodes = new ArrayList<>();
    extract(nodes, root1);
    extract(nodes, root2);
    Collections.sort(nodes);
    return nodes;
  }
  private void extract(List<Integer> nodes, TreeNode root) {
    if (root == null) return;
    nodes.add(root.val);
    extract(nodes, root.left);
    extract(nodes, root.right);
  }

  public static void main(String[] args) { Util.runFiles(); }

}

