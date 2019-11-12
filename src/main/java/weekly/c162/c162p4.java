package weekly.c162;

import common.Util;

import java.util.Arrays;

public class c162p4 {

  public int maxScoreWords(String[] words, char[] letters, int[] score) {
    // count letters
    this.letters = new int[VOCAB_SIZE];
    for (char c : letters) {
      this.letters[c - 'a']++;
    }

    // analyse words
    wordScores = new int[words.length];
    wordVocabs = new int[words.length][];
    for (int i = 0; i < words.length; i++) {
      wordVocabs[i] = new int[VOCAB_SIZE];
      for (char c : words[i].toCharArray()) {
        wordScores[i] += score[c - 'a'];
        wordVocabs[i][c - 'a']++;
      }
    }

    maxScore = 0;
    int maxPossibleScore = Arrays.stream(wordScores).sum();
    explore(maxPossibleScore, 0);
    return maxScore;
  }
  private static final int VOCAB_SIZE = 26;
  private int[] wordScores;
  private int[][] wordVocabs;

  private int[] letters;
  private int currScore = 0;
  private int maxScore = 0;
  private void explore(int maxPossibleScore, int i) {
    if (currScore + maxPossibleScore <= maxScore) return;
    if (i >= wordScores.length) return;

    int score = wordScores[i];
    int[] word = wordVocabs[i];
    if (isEnough(letters, word)) {
      // use this word
      use(letters, word);
      currScore += score;
      if (currScore > maxScore)
        maxScore = currScore;
      explore(maxPossibleScore - score, i + 1);

      // restore using word
      restore(letters, word);
      currScore -= score;
    }

    // try not use word
    explore(maxPossibleScore - score, i + 1);
  }

  private static boolean isEnough(int[] letters, int[] word) {
    for (int c = 0; c < VOCAB_SIZE; c++) {
      if (letters[c] < word[c]) return false;
    }
    return true;
  }
  private static void use(int[] letters, int[] word) {
    for (int c = 0; c < VOCAB_SIZE; c++) {
      letters[c] -= word[c];
    }
  }
  private static void restore(int[] letters, int[] word) {
    for (int c = 0; c < VOCAB_SIZE; c++) {
      letters[c] += word[c];
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

