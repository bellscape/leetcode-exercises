package weekly.c164;

import common.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class c164p3 {

  public List<List<String>> suggestedProducts(String[] products, String searchWord) {
    Arrays.sort(products);

    List<String> selected = Arrays.asList(products);
    List<List<String>> out = new ArrayList<>();
    for (int i = 0; i < searchWord.length(); i++) {
      int idx = i;
      char c = searchWord.charAt(i);
      selected = selected.stream()
              .filter(product -> product.length() > idx && product.charAt(idx) == c)
              .collect(Collectors.toList());
      out.add(selected.subList(0, Math.min(3, selected.size())));
    }
    return out;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

