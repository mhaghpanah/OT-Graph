package OrderTypeGraph;

public class GraphGeneratorFunctions extends GraphGenerator {

  GraphGeneratorFunctions(PreCCSystem goal, boolean[] rules) {
    super(goal, rules);
  }

  public static Tuple<Graph, Proofs> generateGraphWithProofs(PreCCSystem goal, int algoType,
      boolean[] rules) {
    GraphGenerator gg = new GraphGenerator(goal, rules);
    Graph graph;
    switch (algoType) {
      case 0:
        graph = gg.runBottomUpRandomAlgorithm();
        break;
      case 1:
        graph = gg.runZeroLevelGreedyAlgorithm();
        break;
      default:
        throw new IllegalArgumentException();
    }

    return new Tuple<>(graph, gg.computeCCClosure(graph).getProofs());
  }

  public static Graph generateGraph(PreCCSystem goal, int algorithmType, boolean[] rules) {
    GraphGenerator gg = new GraphGenerator(goal, rules);
    switch (algorithmType) {
      case 0:
        return gg.runBottomUpRandomAlgorithm();
      case 1:
        return gg.runZeroLevelGreedyAlgorithm();
      default:
        throw new IllegalArgumentException();
    }
  }

  public static Graph generateGraph(PreCCSystem goal, int algorithmType, boolean[] rules,
      int repeatNum) {
    int n = goal.getN();
    Graph ans = Graph.completeGraph(n);
    for (int i = 0; i < repeatNum; i++) {
      Graph graph = generateGraph(goal, algorithmType, rules);
      if (graph.getM() < ans.getM()) {
        ans = graph;
      }
      System.out.printf("Iteration: %d Best so far: %d\n", i, ans.getM());
    }
    return ans;
  }

  public static PlanarGraph generateGraph(Points points, int algorithmType, boolean[] rules,
      int repeatNum) {
    PreCCSystem goal = points.computeCCSystem();
    PlanarGraph ans = PlanarGraph.completeGraph(points);
    for (int i = 0; i < repeatNum; i++) {
      Graph graph = generateGraph(goal, algorithmType, rules);
      PlanarGraph planarEdges = new PlanarGraph(graph, points);
      if (planarEdges.compareTo(ans) < 0) {
        ans = planarEdges;
      }
      System.out.printf("Iteration: %d Best so far: %d\n", i, ans.getM());
    }
    return ans;
  }

  public static int generateGraphIterationCalc(PreCCSystem goal, int algorithmType, boolean[] rules,
      int desireEdgeNum, int maxRepeatNum) {
    for (int i = 0; i < maxRepeatNum; i++) {
      Graph graph = generateGraph(goal, algorithmType, rules);
      if (graph.getM() <= desireEdgeNum) {
        return i + 1;
      }
    }
    return -1;
  }

}
