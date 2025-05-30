package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryGame {

  /**
   * The width of the window of this game.
   */
  private int width;
  /**
   * The height of the window of this game.
   */
  private int height;
  /**
   * The current round the user is on.
   */
  private int round;
  /**
   * The Random object used to randomly generate Strings.
   */
  private Random rand;
  /**
   * Whether or not the game is over.
   */
  private boolean gameOver;
  /**
   * Whether or not it is the player's turn. Used in the last section of the spec, 'Helpful UI'.
   */
  private boolean playerTurn;
  /**
   * The characters we generate random Strings from.
   */
  private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  /**
   * Encouraging phrases. Used in the last section of the spec, 'Helpful UI'.
   */
  private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
      "You got this!", "You're a star!", "Go Bears!",
      "Too easy for you!", "Wow, so impressive!"};

  public static void main(String[] args) throws InterruptedException {
    if (args.length < 1) {
      System.out.println("Please enter a seed");
      return;
    }

    long seed = Long.parseLong(args[0]);
    MemoryGame game = new MemoryGame(40, 40, seed);
    game.startGame();
  }

  public MemoryGame(int width, int height, long seed) {
    /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
     * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
     */
    this.width = width;
    this.height = height;
    StdDraw.setCanvasSize(this.width * 16, this.height * 16);
    Font font = new Font("Monaco", Font.BOLD, 30);
    StdDraw.setFont(font);
    StdDraw.setXscale(0, this.width);
    StdDraw.setYscale(0, this.height);
    StdDraw.clear(Color.BLACK);
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenColor(Color.WHITE);
    rand = new Random(seed);
    round = 1;
    gameOver = false;
    playerTurn = false;
  }

  public String generateRandomString(int n) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < n; i++) {
      stringBuilder.append(CHARACTERS[rand.nextInt(26)]);
    }
    return stringBuilder.toString();
  }

  public void drawFrame(String s, String upper) {
    StdDraw.clear(Color.black);
    Font font = new Font("Monaco", Font.BOLD, 30);
    StdDraw.setFont(font);
    if (gameOver) {
      StdDraw.text(20, 20, "GameOver! You made it to " + round + ".");
    } else {
      StdDraw.text(20, 20, s);
      Font topFont = new Font("Monaco", Font.PLAIN, 15);
      StdDraw.setFont(topFont);
      StdDraw.textLeft(0, 39, "Round: " + round);
      StdDraw.text(20, 39, upper);
      StdDraw.textRight(40, 39, ENCOURAGEMENT[rand.nextInt(7)]);
      StdDraw.line(0, 38.5, 40, 38.5);
    }
    StdDraw.show();
  }

  public void flashSequence(String letters) throws InterruptedException {
    for (int i = 0; i < letters.length(); i++) {
      drawFrame(Character.toString(letters.charAt(i)), "Watch!");
      Thread.sleep(500);
      drawFrame("", "Watch!");
      Thread.sleep(500);
    }

  }

  public String solicitNCharsInput(int n) {
    StringBuilder s = new StringBuilder();
    while (n != 0) {
      if (StdDraw.hasNextKeyTyped()) {
        s.append(StdDraw.nextKeyTyped());
        drawFrame(s.toString(), "Type!");
        n--;
      }
    }
    return s.toString();
  }

  public boolean check(String s1, String s2) {
    return s1.equals(s2);
  }

  public void startGame() throws InterruptedException {
    while (!gameOver) {
      drawFrame("Round: " + round, "Watch!");
      Thread.sleep(1000);
      String s = generateRandomString(round);
      flashSequence(s);
      drawFrame("", "Type!");
      String input = solicitNCharsInput(round);
      if (!check(s, input)) {
        gameOver = true;
        drawFrame("", "GameOver");
      } else {
        round++;
        drawFrame("Great", "Wait!");
        Thread.sleep(500);
      }
    }
  }

}
