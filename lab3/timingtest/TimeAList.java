package timingtest;

import edu.princeton.cs.algs4.Stopwatch;
import java.time.LocalDateTime;

/**
 * Created by hug.
 */
public class TimeAList {

  private static void printTimingTable(AList<Integer> Ns, AList<Double> times,
      AList<Integer> opCounts) {
    System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
    System.out.printf("------------------------------------------------------------\n");
    for (int i = 0; i < Ns.size(); i += 1) {
      int N = Ns.get(i);
      double time = times.get(i);
      int opCount = opCounts.get(i);
      double timePerOp = time / opCount * 1e6;
      System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
    }
  }

  public static void main(String[] args) {
    timeAListConstruction();
  }

  public static void timeAListConstruction() {
    // TODO: YOUR CODE HERE
    AList<Integer> Ns = new AList<>();
    AList<Double> times = new AList<>();
    AList<Integer> opCounts = new AList<>();

    for (int i = 1000; i <= 128000; i *= 2) {
      Ns.addLast(i);
//      opCounts.addLast((int) Math.ceil(Math.log((double) i / 100) / Math.log(2)));
      opCounts.addLast(i);
    }

    for (int i = 0; i < Ns.size(); i++) {
      AList<Integer> temp = new AList<>();
      double start = System.currentTimeMillis();
      for (int j = 0; j < Ns.get(i); j++) {
        temp.addLast(j);
      }
      double end = System.currentTimeMillis();
      times.addLast(end - start);
    }
      printTimingTable(Ns, times, opCounts);
  }
}
