package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        for (int i = 1000; i <= 128000; i *= 2) {
            Ns.addLast(i);
//      opCounts.addLast((int) Math.ceil(Math.log((double) i / 100) / Math.log(2)));
            opCounts.addLast(10000);
        }

        for (int i = 0; i < Ns.size(); i++) {
          Ns.get(i);
          SLList<Integer> temp = new SLList<>();
            for (int j = 0; j < Ns.get(i); j++) {
                temp.addLast(j);
            }
            double start = System.currentTimeMillis();
            for (int j = 0; j < opCounts.get(i); j++) {
                temp.getLast();
            }
            double end = System.currentTimeMillis();
            times.addLast(end - start);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
