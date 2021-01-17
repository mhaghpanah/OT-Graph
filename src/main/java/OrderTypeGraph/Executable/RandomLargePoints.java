package OrderTypeGraph.Executable;

import OrderTypeGraph.Graph;
import OrderTypeGraph.GraphGeneratorWrapper;
import OrderTypeGraph.GraphGeneratorWrapper.AlgorithmParameters;
import OrderTypeGraph.Points;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class RandomLargePoints {

  public static void main(String[] args) {

    if (args.length == 3) {
      int n = 0;
      int repeatNum = 0;
      int algorithmType = 0;
      boolean[] rules = new boolean[]{true, true, true};
      
      try {
        n = Integer.parseInt(args[0]);
        repeatNum = Integer.parseInt(args[1]);
        algorithmType = Integer.parseInt(args[2]);
      } catch (Exception e) {
        System.err.println("An error occurred.");
        System.exit(1);
      }

      Instant start = Instant.now();

      Random random = new Random();
      Points points = Points.randomPointGenerator(n, random);
      AlgorithmParameters algorithmParameters = new AlgorithmParameters(algorithmType, rules);


      Graph ans = GraphGeneratorWrapper
          .generateGraphCrossOrder(points, algorithmParameters, repeatNum);
      System.out.printf("size edges: %d %s\n", ans.getM(), ans);

      Instant end = Instant.now();
      System.err.printf("Program completed in %d seconds\n",
          Duration.between(start, end).getSeconds());

    } else {
      System.out.println("Not enough arguments!");
      System.out.println("args: pointsNum repeatNum algorithmType(0 random 1 greedy)");
      System.out.println("example: 20 1000 1");
      System.exit(1);
    }

  }

}
