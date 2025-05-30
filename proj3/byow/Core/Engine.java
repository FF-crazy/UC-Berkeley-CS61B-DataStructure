package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Engine {

  private TERenderer ter;
  /* Feel free to change the width and height. */
  public static final int WIDTH = 50;
  public static final int HEIGHT = 50;
  private Random random;
  private TETile[][] tetile;
  private int width;
  private int height;
  private Position position;
  private boolean readyToQuit;
  private long seed;


  public Engine() {
    ter = new TERenderer();
    ter.initialize(WIDTH, HEIGHT);
    readyToQuit = false;
  }

  /**
   * Method used for autograding and testing your code. The input string will be a series of
   * characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should behave
   * exactly as if the user typed these characters into the engine using interactWithKeyboard.
   * <p>
   * Recall that strings ending in ":q" should cause the game to quite save. For example, if we do
   * interactWithInputString("n123sss:q"), we expect the game to run the first 7 commands (n123sss)
   * and then quit and save. If we then do interactWithInputString("l"), we should be back in the
   * exact same state.
   * <p>
   * In other words, both of these calls: - interactWithInputString("n123sss:q") -
   * interactWithInputString("lww")
   * <p>
   * should yield the exact same world state as: - interactWithInputString("n123sssww")
   *
   * @param input the input string to feed to your program
   */
  public TETile[][] interactWithInputString(String input) {
    // passed in as an argument, and return a 2D tile representation of the
    // world that would have been drawn if the same inputs had been given
    // to interactWithKeyboard().
    //
    // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
    // that works for many different input types.
    boolean flag = initialize(input);
    if (!flag) {
      System.out.println("Undefined Behavior!\n");
      return null;
    }
    ter.renderFrame(tetile);
    gameGoing();
    return tetile;
  }

  /**
   * Method used for exploring a fresh world. This method should handle all inputs, including inputs
   * from the main menu.
   */
  public TETile[][] interactWithKeyboard() {
    Menu menu = new Menu(WIDTH, HEIGHT);
    String input = menu.freshAndGetString();
    menu.close();
    boolean flag = initialize(input);
    if (!flag) {
      System.out.println("Undefined Behavior!\n");
      return null;
    }
    ter.renderFrame(tetile);
    gameGoing();
    return tetile;
  }

  private boolean initialize(String input) {
    initializeTETile();
    long number = handleInput(input);
    if (number == -1) {
      return false;
    } else {
      seed = number;
      random = new Random(seed);
    }
    width = RandomUtils.uniform(random, WIDTH / 3 * 2, WIDTH-1);
    height = RandomUtils.uniform(random, HEIGHT / 3 * 2, HEIGHT-1);
    createBound(width + 1, height + 1);
    divide(0, 0, width, height);
    bfs(1, 1);
    setAvatar();
    if (input.charAt(0) == 'l' || input.charAt(0) == 'L') {
      for (int i = 1; i < input.length(); i++) {
        handleInteract(input.charAt(i));
      }
    }
    return true;
  }

  private void initializeTETile() {
    tetile = new TETile[WIDTH][HEIGHT];
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        tetile[i][j] = Tileset.NOTHING;
      }
    }
  }

  private long handleInput(String input) {
    String s;
    long res;
    switch (input.charAt(0)) {
      case 'N', 'n':
        s = input.substring(1, finFirstS(input));
        if (!checkSeedValid(s)) {
          return -1;
        }
        res = Long.parseLong(s);
        position = new Position(1, 1);
        return res;
      case 'L', 'l' :
        s = FileUtils.readFile();
        int half = s.indexOf('\n');
        int another = s.indexOf(' ');
        position = new Position(Integer.parseInt(s, half + 1, another, 10), Integer.parseInt(s, another + 1, s.length() - 1, 10));
        return Long.parseLong(s, 0, half, 10);
    }
    return -1;
  }

  private int finFirstS(String s) {
    return s.indexOf('s') == -1 ? s.indexOf('S') : s.indexOf('s');
  }

  private void setAvatar() {
    tetile[position.x][position.y] = Tileset.AVATAR;
  }

  private boolean handleInteract(char c) {
    int x = position.x;
    int y = position.y;
    switch (c) {
      case 'Q', 'q':
      if (readyToQuit) {
        FileUtils.toFile(seed, position.x, position.y);
        return true;
      }
      break;
      case 'A', 'a':
        if (tetile[x-1][y] != Tileset.WALL) {
          tetile[x][y] = Tileset.FLOOR;
          position.reset(x-1, y);
          setAvatar();
        }
        break;
      case 'D', 'd' :
        if (tetile[x+1][y] != Tileset.WALL) {
          tetile[x][y] = Tileset.FLOOR;
          position.reset(x+1, y);
          setAvatar();
        }
        break;
      case 'W', 'w':
        if (tetile[x][y+1] != Tileset.WALL) {
          tetile[x][y] = Tileset.FLOOR;
          position.reset(x, y+1);
          setAvatar();
        }
        break;
      case 'S', 's':
        if (tetile[x][y-1] != Tileset.WALL) {
          tetile[x][y] = Tileset.FLOOR;
          position.reset(x, y-1);
          setAvatar();
        }
        break;
    }
    readyToQuit = c == ':';
    return false;
  }

  private boolean checkSeedValid(String s) {
    if (s == null || s.isEmpty()) {
      return false;
    }
    try {
      Long.parseLong(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
   for (int j = HEIGHT - 1; j >= 0; j--) {
     for (int i = 0; i < WIDTH; i++) {
       s.append(tetile[i][j].character());
     }
     s.append('\n');
    }
    return s.toString();
  }

  private void createBound(int w, int h) {
    for (int i = 0; i <= w; i++) {
      tetile[i][0] = Tileset.WALL;
      tetile[i][h] = Tileset.WALL;
    }
    for (int i = 0; i <= h; i++) {
      tetile[0][i] = Tileset.WALL;
      tetile[w][i] = Tileset.WALL;
    }
  }

  private boolean selectDirection(int w, int h) {
    if (w == h) {
      return random.nextBoolean();
    }
    return w < h;
  }

  private void divide(int x1, int y1, int x2, int y2) {
    if (y2 - y1 <= 3 || x2 - x1 <= 3) {
      return;
    }
    if (selectDirection(x2 - x1, y2 - y1)) {
      int y = RandomUtils.uniform(random, y1 + 2, y2-1);
      int x = horizontal(x1, x2, y);
      divide(x1, y1, x, y);
      divide(x, y1, x2, y);
      divide(x1, y, x, y2);
      divide(x, y, x2, y2);
    } else {
      int x = RandomUtils.uniform(random, x1 + 2, x2-1);
      int y = vertical(y1, y2, x);
      divide(x1, y1, x, y);
      divide(x, y1, x2, y);
      divide(x1, y, x, y2);
      divide(x, y, x2, y2);
    }
  }

  private int horizontal(int x1, int x2, int y) {
    int startX = Math.min(x1, x2);
    int endX = Math.max(x1, x2);
    if (startX == endX) {
      return -1;
    }

    for (int i = startX + 1; i < endX; i++) {
      tetile[i][y] = Tileset.WALL;
    }
    int gap = RandomUtils.uniform(random, startX + 1, endX);
    tetile[gap][y] = Tileset.NOTHING;
    return gap;
  }

  private int vertical(int y1, int y2, int x) {
    int startY = Math.min(y1, y2);
    int endY = Math.max(y1, y2);
    if (startY == endY) {
      return -1;
    }

    for (int i = startY + 1; i < endY; i++) {
      tetile[x][i] = Tileset.WALL;
    }
    int gap = RandomUtils.uniform(random, startY + 1, endY);
    tetile[x][gap] = Tileset.NOTHING;
    return gap;
  }

  private void bfs(int startX, int startY) {
    int width = tetile.length;
    int height = tetile[0].length;
    Queue<Position> queue = new LinkedList<>();
    if (startX >= 0 && startX < width && startY >= 0 && startY < height &&
        tetile[startX][startY] != Tileset.WALL) {
      tetile[startX][startY] = Tileset.FLOOR;
      queue.add(new Position(startX, startY));
    } else {
      System.err.println("BFS starting point (" + startX + ", " + startY + ") is invalid or a wall.");
      return;
    }
    int[] dx = {0, 1, -1, 0};
    int[] dy = {1, 0, 0, -1};
    while (!queue.isEmpty()) {
      Position current = queue.remove();
      int x = current.x;
      int y = current.y;
      for (int i = 0; i < 4; i++) {
        int nextX = x + dx[i];
        int nextY = y + dy[i];
        if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
          if (tetile[nextX][nextY] != Tileset.WALL && tetile[nextX][nextY] != Tileset.FLOOR) {
            tetile[nextX][nextY] = Tileset.FLOOR;
            queue.add(new Position(nextX, nextY));
          }
        }
      }
    }
  }

  private void gameGoing() {
    while (true) {
      if (StdDraw.hasNextKeyTyped()) {
        if (handleInteract(StdDraw.nextKeyTyped())) {
          return;
        }
        ter.renderFrame(tetile);
      }
    }
  }



  private class Menu {

    private int width;
    private int height;

    public Menu(int w, int h) {
      width = w;
      height = h;
      StdDraw.setCanvasSize(width * 16, height * 16);
      StdDraw.setXscale(0, this.width);
      StdDraw.setYscale(0, this.height);
      StdDraw.clear(Color.BLACK);
      StdDraw.enableDoubleBuffering();
      StdDraw.setPenColor(Color.WHITE);
    }

    private void drawFrame(String s) {
      StdDraw.clear(Color.black);
      Font font = new Font("Monaco", Font.BOLD, height * width / 40);
      StdDraw.setFont(font);
      StdDraw.text((double) width / 2, (double) height / 5 * 4, "CS61B: THE GAME");
      font = new Font("Monaca", Font.PLAIN, height * width / 80);
      StdDraw.setFont(font);
      StdDraw.text((double) width / 2, (double) height / 5 * 3, "New Game (N)");
      StdDraw.text((double) width / 2, (double) height / 5 * 2.7, "Load Game (L)");
      StdDraw.text((double) width / 2, (double) height / 5 * 2.4, "Quit (Q)");
      StdDraw.text((double) width / 2, (double) height / 5 * 2, s);
      StdDraw.show();
    }

    public String freshAndGetString() {
      StringBuilder sb = new StringBuilder();
      drawFrame(sb.toString());
      while (true) {
        if (StdDraw.hasNextKeyTyped()) {
          sb.append(StdDraw.nextKeyTyped());
          drawFrame(sb.toString());
        }
        String s = sb.toString();
        if (!s.isEmpty() && (s.charAt(s.length() - 1) == 'S' || s.charAt(s.length() - 1) == 's' || s.charAt(s.length() - 1) == 'l' || s.charAt(s.length() - 1) == 'L')) {
          break;
        }
      }
      return sb.toString();
    }

    public void close() {
    }
  }
  private class Position {
    private int x;
    private int y;
    public Position(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public void reset(int x, int y) {
      this.x = x;
      this.y = y;
    }

  }
}

