package byow.lab12;

import java.util.Scanner;
import org.junit.Test;

import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

  private static final int WIDTH = 100;
  private static final int HEIGHT = 100;
  private static final int SEED = 666;
  private static final Random RANDOM = new Random(SEED);


  public static void main(String[] args) {
    TERenderer ter = new TERenderer();
    ter.initialize(WIDTH, HEIGHT);
    TETile[][] board = new TETile[WIDTH][HEIGHT];
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        board[i][j] = Tileset.NOTHING;
      }
    }
    Scanner scanner = new Scanner(System.in);
    int size = scanner.nextInt();
    fillBoard(board, size);
    ter.renderFrame(board);
  }

  private static TETile randomColor() {
    int type = RANDOM.nextInt(8);
    switch (type) {
      case 0: return Tileset.AVATAR;
      case 1: return Tileset.WALL;
      case 2: return Tileset.FLOOR;
      case 3: return Tileset.GRASS;
      case 4: return Tileset.WATER;
      case 5: return Tileset.SAND;
      case 6: return Tileset.MOUNTAIN;
      case 7: return Tileset.TREE;
    }
    return null;
  }

  private static TETile[][] createHex(TETile[][] board, int i, int j, int size) {
    TETile type = randomColor();
    createHex(board, i, j, (int) Math.ceil( (double) size / 4.0), (int) Math.ceil( (double) size / 4.0) + 1, type);
    return board;

  }
  private static void createHex(TETile[][] board, int i, int j, int b, int f, TETile type) {
    for (int k = 0; k < f; k++) {
      board[i + b + k][j] = type;
    }
    if (b > 0) {
      createHex(board, i, j + 1, b - 1, f + 2, type);
    }
    for (int k = 0; k < f; k++) {
      board[i + b + k][j + b * 2 + 1] = type;
    }
  }
  private static void fillBoard(TETile[][] board, int size) {
    int min = (int) Math.ceil( (double) size / 4.0);
    int height = min * 2 + 2;
    int witdh = min + size + 1;
    for (int i = 0; i <= WIDTH - size; i += witdh) {
      for (int j = 0; j <= HEIGHT - size; j += height) {
        board = createHex(board, i, j, size);
      }
    }
    for (int i = min * 2 + 1; i <= WIDTH - size; i += witdh) {
      for (int j = min + 1; j <= HEIGHT - size; j += height) {
        board = createHex(board, i, j, size);
      }
    }
  }


}
