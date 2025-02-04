package OrderTypeGraph.Executable;

import OrderTypeGraph.Database;
import OrderTypeGraph.Graph;
import OrderTypeGraph.ResultDatasetIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Statistics {

  public static List<Features> compute(Database database, ResultDatasetIO resultDatasetIO, boolean[] rules) {
    List<Features> ans = new ArrayList<>();
    int n = database.getN();
    for (int id = 0; id < Database.databaseSize(n); id++) {
      Features features = new Features(id, database, resultDatasetIO, rules);
      ans.add(features);
    }
    return ans;
  }

  public static List<Features> compute(int n, boolean[] rules) {
    Database database = Database.read(n);
    String prefixPath = String
        .format("rules123%s%s%s%s", rules[0] ? "4" : "", rules[1] ? "5" : "", rules[2] ? "6" : "",
            File.separator);
    ResultDatasetIO resultDatasetIO =
        new ResultDatasetIO(n, database.size(), Graph.bitFormatSize(n), prefixPath);

    return compute(database, resultDatasetIO, rules);
  }

  public static List<Features> computeParallel(Database database, ResultDatasetIO resultDatasetIO, boolean[] rules, int threadNum)
      throws ExecutionException, InterruptedException {
    ForkJoinPool forkJoinPool = new ForkJoinPool(threadNum); // Configure the number of threads
    List<Features> ans =
        forkJoinPool.submit(() -> IntStream.range(0, database.size())
            .parallel()
            .mapToObj(id -> {
              Features features = new Features(id, database, resultDatasetIO, rules);
              return features;
            })
            .collect(Collectors.toList()))
            .get();
    forkJoinPool.shutdown();
    return ans;

  }

  public static List<Features> computeParallel(int n, boolean[] rules, int threadNum)
      throws ExecutionException, InterruptedException {
    Database database = Database.read(n);
    String prefixPath = String
        .format("rules123%s%s%s%s", rules[0] ? "4" : "", rules[1] ? "5" : "", rules[2] ? "6" : "",
            File.separator);
    ResultDatasetIO resultDatasetIO =
        new ResultDatasetIO(n, database.size(), Graph.bitFormatSize(n), prefixPath);

    return computeParallel(database, resultDatasetIO, rules, threadNum);
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    boolean[] rules = new boolean[] {true, true, true};
    int threadNum = 10;
    for (int n = 3; n <= 8; n++) {
//      List<Features> ans0 = compute(n, rules);
      List<Features> ans1 = computeParallel(n, rules, threadNum);
//      System.out.println(ans0);
//      System.out.println(ans1);
//      assert ans0.equals(ans1);

      Map<Integer, Long> m0 = ans1.stream()
          .collect(Collectors.groupingBy(c -> c.otGraphEdges, Collectors.counting()));
      Map<Integer, Long> m1 = ans1.stream()
          .collect(Collectors.groupingBy(c -> c.exitGraphEdges, Collectors.counting()));
      Map<Integer, Long> m2 = ans1.stream()
          .collect(Collectors.groupingBy(c -> c.otGraphCrossing, Collectors.counting()));
      Map<Integer, Long> m3 = ans1.stream()
          .collect(Collectors.groupingBy(c -> c.exitGraphCrossing, Collectors.counting()));

      System.out.println(m0);
      System.out.println(m1);
      System.out.println(m2);
      System.out.println(m3);

    }
  }

}
