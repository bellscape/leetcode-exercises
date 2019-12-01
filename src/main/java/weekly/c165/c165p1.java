package weekly.c165;

import common.Util;

import java.util.stream.IntStream;

public class c165p1 {

  public String tictactoe(int[][] moves) {
    byte[] board = play(moves);
    if (isWinner(board, A)) return "A";
    if (isWinner(board, B)) return "B";
    return moves.length >= board.length ? "Draw" : "Pending";
  }
  private static final int n = 3;
  private static final byte A = 1;
  private static final byte B = 2;
  private static byte[] play(int[][] moves) {
    byte[] board = new byte[n * n];
    byte current = A;
    for (int[] move : moves) {
      board[move[0] * n + move[1]] = current;
      current = current == A ? B : A;
    }
    return board;
  }
  private static boolean isWinner(byte[] board, byte player) {
    for (int i = 0; i < n; i++) {
      int row = i;
      if (IntStream.range(0, n)
              .allMatch(col -> board[row * n + col] == player))
        return true;
    }
    for (int i = 0; i < n; i++) {
      int col = i;
      if (IntStream.range(0, n)
              .allMatch(row -> board[row * n + col] == player))
        return true;
    }
    if (IntStream.range(0, n)
            .allMatch(row -> board[row * n + row] == player))
      return true;
    if (IntStream.range(0, n)
            .allMatch(row -> board[row * n + 2 - row] == player))
      return true;
    return false;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

