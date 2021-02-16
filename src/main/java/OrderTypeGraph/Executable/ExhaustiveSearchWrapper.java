package OrderTypeGraph.Executable;

import static java.lang.System.exit;

import OrderTypeGraph.Database;
import OrderTypeGraph.Graph;
import OrderTypeGraph.MyFile;
import OrderTypeGraph.MyFile.Address;
import OrderTypeGraph.MyFileWriter;
import OrderTypeGraph.ResultDatasetIO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExhaustiveSearchWrapper {

  public static List<Features> compute(Database database, ResultDatasetIO resultDatasetIO, boolean[] rules) {
    List<Features> ans = new ArrayList<>();
    int n = database.getN();
    for (int id = 0; id < Database.databaseSize(n); id++) {
      ExhaustiveSearch.ExhaustiveSearch(id, database, resultDatasetIO, rules);
      Features features = new Features(id, database, resultDatasetIO, rules);
      ans.add(features);
    }
    return ans;
  }

  public static List<Features> compute(int n, boolean[] rules) {
    Database database = Database.read(n);
    String prefixPath = String
        .format("z-rules123%s%s%s%s", rules[0] ? "4" : "", rules[1] ? "5" : "", rules[2] ? "6" : "",
            File.separator);
    ResultDatasetIO resultDatasetIO =
        new ResultDatasetIO(n, database.size(), Graph.bitFormatSize(n), prefixPath);

    return compute(database, resultDatasetIO, rules);
  }

  public static List<Features> computeParallel(Database database, ResultDatasetIO resultDatasetIO, boolean[] rules, int threadNum, int lo, int hi)
      throws ExecutionException, InterruptedException {
    ForkJoinPool forkJoinPool = new ForkJoinPool(threadNum); // Configure the number of threads
    List<Features> ans =
        forkJoinPool.submit(() -> IntStream.range(lo, hi)
            .parallel()
            .mapToObj(id -> {
              ExhaustiveSearch.ExhaustiveSearch(id, database, resultDatasetIO, rules);
              Features features = new Features(id, database, resultDatasetIO, rules);
              return features;
            })
            .collect(Collectors.toList()))
            .get();
    forkJoinPool.shutdown();
    return ans;

  }

  public static List<Features> computeParallel(int n, boolean[] rules, int threadNum, int lo, int hi)
      throws ExecutionException, InterruptedException {
    Database database = Database.read(n);
    String prefixPath = String
        .format("ExhaustiveSearchP20-rules123%s%s%s%s", rules[0] ? "4" : "", rules[1] ? "5" : "", rules[2] ? "6" : "",
            File.separator);
    ResultDatasetIO resultDatasetIO =
        new ResultDatasetIO(n, database.size(), Graph.bitFormatSize(n), prefixPath);

    return computeParallel(database, resultDatasetIO, rules, threadNum, lo, hi);
  }

  public static List<Features> computeParallel(int n, boolean[] rules, int threadNum)
      throws ExecutionException, InterruptedException {
    return computeParallel(n, rules, threadNum, 0, Database.databaseSize(n));
  }

  public static void breakTasks(int n) {
    MyFileWriter myFileWriter = new MyFileWriter(String.format("tasks_%d.txt", n), Address.RESULTS);
    StringBuilder sb = new StringBuilder();
    int delta = 1_000;
    for (int i = 0; i < Database.databaseSize(n); i += delta) {
      sb.append(String.format("%d %d\n", i, Math.min(Database.databaseSize(n), i + delta)));
    }
    System.out.println(sb.toString());
    myFileWriter.writeFile(sb.toString());
  }

  public static int[] readTask(int n) throws IOException {
    String pathname = String.join(File.separator,
        System.getProperty("user.dir"), "results", String.format("tasks_%d.txt", n));
    File inputFile = new File(pathname);
    if (!inputFile.exists()) {
      breakTasks(n);
      inputFile = new File(pathname);
    }
    File tempFile = MyFile.getInstance(String.format("tmp_tasks_%d.txt", n), Address.RESULTS);
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String firstLine = reader.readLine();
    System.out.println(firstLine);
    String currentLine;
    while ((currentLine = reader.readLine()) != null) {
      System.out.println(currentLine);
      writer.write(currentLine + "\n");
    }
    writer.close();
    reader.close();
    boolean successful = tempFile.renameTo(inputFile);
    if (firstLine == null) return null;
    String[] strs = firstLine.split("\\s+");
    int[] ans = new int[] {Integer.valueOf(strs[0]), Integer.valueOf(strs[1])};
    return ans;
  }

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, IOException {
    int lo = 0;
    int hi = 0;
    if (args.length == 0) {
      int[] range = readTask(9);
      lo = range[0];
      hi = range[1];
    } else if (args.length == 2) {
      lo = Integer.valueOf(args[0]);
      hi = Integer.valueOf(args[1]);
    } else {
      System.err.printf("Invalid Argument\n");
      exit(0);
    }
    boolean[] rules = new boolean[] {true, true, true};
    int threadNum = 15;
    for (int n = 9; n <= 9; n++) {
//      List<Features> ans0 = compute(n, rules);
      List<Features> ans1 = computeParallel(n, rules, threadNum, lo, hi);
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

      System.err.printf("n = %d\n", n);
      System.err.println(m0);
      System.err.println(m1);
      System.err.println(m2);
      System.err.println(m3);

    }
  }

}
