package OrderTypeGraph.Executable;

import OrderTypeGraph.Database;
import OrderTypeGraph.Graph;
import OrderTypeGraph.GraphGeneratorWrapper;
import OrderTypeGraph.GraphGeneratorWrapper.AlgorithmParameters;
import OrderTypeGraph.Points;
import OrderTypeGraph.Proofs;
import OrderTypeGraph.Tuple;
import java.io.File;
import java.io.FileNotFoundException;

public class GraphGeneratorTest {

  public void algo0() {
    Database database = Database.read(6);
    boolean[] rules = new boolean[]{true, true, true};
    AlgorithmParameters algorithmParameters = new AlgorithmParameters(0, rules);
    for (int i = 0; i < database.size(); i++) {
      Points points = database.get(i);
      Tuple<Graph, Proofs> tuple = GraphGeneratorWrapper
          .generateGraphWithProofs(points, algorithmParameters);

      System.out.printf("%06d %s\n", i, points);
      System.out.printf("edges size: %d %s\n", tuple.x.getM(), tuple.x);
      System.out.printf("proofs size: %d %s\n", tuple.y.size(), tuple.y);
      System.out.println("------------------------------");
    }
  }

  public void algo1() {
    Database database = Database.read(8);
    boolean[] rules = new boolean[]{true, true, true};
    AlgorithmParameters algorithmParameters = new AlgorithmParameters(0, rules);
    for (int i = 0; i < database.size(); i++) {
      Points points = database.get(i);
      Tuple<Graph, Proofs> tuple = GraphGeneratorWrapper
          .generateGraphWithProofs(points, algorithmParameters);

      System.out.printf("%06d %s\n", i, points);
      System.out.printf("edges size: %d %s\n", tuple.x.getM(), tuple.x);
      System.out.printf("proofs size: %d %s\n", tuple.y.size(), tuple.y);
      System.out.println("------------------------------");
    }
  }

  public void compare() {
    Database database = Database.read(6);
    int algoTypes = 2;
    int[][] results = new int[database.size()][algoTypes];
    int[] repNum = new int[]{10_000, 10_000};
    boolean[] rules = new boolean[]{true, true};

    for (int i = 0; i < database.size(); i++) {
      Points points = database.get(i);
      for (int j = 0; j < algoTypes; j++) {
        results[i][j] = Integer.MAX_VALUE;
        AlgorithmParameters algorithmParameters = new AlgorithmParameters(j, rules);
        for (int k = 0; k < repNum[j]; k++) {
          Graph graph = GraphGeneratorWrapper.generateGraph(points, algorithmParameters);
          results[i][j] = Math.min(results[i][j], graph.getM());
        }
      }
      System.out.println(results[i][0]);
      System.out.println(results[i][1]);
    }
  }

  public void convex() {

    int repeatNum = 10_000;
    int algorithmType = 0;
    boolean[] rules = new boolean[]{true, true, true};
    AlgorithmParameters algorithmParameters = new AlgorithmParameters(algorithmType, rules);
    for (int n = 3; n < 10; n++) {
      Database database = Database.read(n);
      Points points = database.get(0);
      OrderTypeGraph.Exp.DrawingFunctions.draw(points,
          String.format("%s%s%d%sid%06d", "convex_2", File.separator, n, File.separator, 0),
          algorithmParameters,
          repeatNum);
    }

  }

  public void points() throws FileNotFoundException {
//    String filenamePoints = "1874-9.txt";
//    String filenamePoints = "14points-1.txt";
    String filenamePoints = "points_n11t0d2.txt";
//    String filenamePoints = "points_n14t1d2.txt";

    String filenameOutput = "points_n11t0d2_2";
//    String filenameOutput = "points_n14t1d2";
    String suffixPathnameOutput = String.join(File.separator, "tmp", filenameOutput);
    boolean[] rules = new boolean[]{true, true, true};
    OrderTypeGraph.Exp.DrawingFunctions.draw(filenamePoints, suffixPathnameOutput, rules);
  }

}
