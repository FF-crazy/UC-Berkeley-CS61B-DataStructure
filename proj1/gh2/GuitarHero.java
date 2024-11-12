package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {


  private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

  public static void main(String[] args) {
    while (true) {
      if (StdDraw.hasNextKeyTyped()) {
        char c = StdDraw.nextKeyTyped();
        double frequency = charToFrequency(c);
        if (frequency != -1.0) {
          GuitarString guitarString = new GuitarString(frequency);
          guitarString.pluck();
          StdAudio.play(guitarString.sample());
          guitarString.tic();
        }
      }
    }
  }

  private static double charToFrequency(char c) {
//    for (double i = 0; i < keyboard.length(); i++) {
//      if (c == keyboard.charAt((int) i)) {
//        return 440 * Math.pow(2, (i - 24) / 12);
//      }
//    }
//    return -1.0;
    double i = keyboard.indexOf(c);
    if (i != -1.0) {
      return 440 * Math.pow(2, (i - 24) / 12);
    }
    return -1.0;
  }


}
