package weekly.c159;

import common.UnorderedJudge;
import common.Util;

import java.util.*;

public class c159p2 {

  public List<String> removeSubfolders(String[] folders) {
    Arrays.sort(folders);
    parents.clear();
    for (String folder : folders) {
      if (!hasParent(folder))
        parents.add(folder);
    }
    return new ArrayList<>(parents);
  }
  private Set<String> parents = new HashSet<>();
  private boolean hasParent(String folder) {
    while (true) {
      int i = folder.lastIndexOf('/');
      if (i <= 0) return false;
      folder = folder.substring(0, i);
      if (parents.contains(folder))
        return true;
    }
  }

  public static void main(String[] args) { Util.runFiles(UnorderedJudge.instance); }

}
