package OrderTypeGraph.Executable;

import OrderTypeGraph.CrossingNumber;
import OrderTypeGraph.Database;
import OrderTypeGraph.ExitGraph;
import OrderTypeGraph.Graph;
import OrderTypeGraph.GraphGeneratorWrapper;
import OrderTypeGraph.Points;
import OrderTypeGraph.ResultDatasetIO;

public class Features {
  int n;
  int id;

  Integer otGraphEdges;
  Integer exitGraphEdges;

  Integer otGraphCrossing;
  Integer exitGraphCrossing;

  Integer otGraphComplexity;

  public Features(int n, int id) {
    this.n = n;
    this.id = id;
  }

  public Features(int n, int id, Points points, Graph otGraph, boolean[] rules) {
    this(n, id);
    assert GraphGeneratorWrapper.isOTGraph(otGraph, points, rules);
    ExitGraph exitGraph = ExitGraph.generateGraph(points);

    otGraphEdges = otGraph.getM();
    exitGraphEdges = exitGraph.getM();

    otGraphCrossing = CrossingNumber.CrossingNumberCalc(points, otGraph);
    exitGraphCrossing =  CrossingNumber.CrossingNumberCalc(points, exitGraph);

    otGraphComplexity = GraphGeneratorWrapper.computeCCClosure(otGraph, points, rules).getProofsComplexity();
  }

  public Features(int n, int id, Database database, ResultDatasetIO resultDatasetIO, boolean[] rules) {
    this(n, id, database.get(id), resultDatasetIO.getGraph(id), rules);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Features)) {
      return false;
    }

    Features features = (Features) o;

    if (n != features.n) {
      return false;
    }
    if (id != features.id) {
      return false;
    }
    if (!otGraphEdges.equals(features.otGraphEdges)) {
      return false;
    }
    if (!exitGraphEdges.equals(features.exitGraphEdges)) {
      return false;
    }
    if (!otGraphCrossing.equals(features.otGraphCrossing)) {
      return false;
    }
    if (!exitGraphCrossing.equals(features.exitGraphCrossing)) {
      return false;
    }
    return otGraphComplexity.equals(features.otGraphComplexity);
  }

  @Override
  public int hashCode() {
    int result = n;
    result = 31 * result + id;
    result = 31 * result + otGraphEdges.hashCode();
    result = 31 * result + exitGraphEdges.hashCode();
    result = 31 * result + otGraphCrossing.hashCode();
    result = 31 * result + exitGraphCrossing.hashCode();
    result = 31 * result + otGraphComplexity.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Features{" +
        "n=" + n +
        ", id=" + id +
        ", otGraphEdges=" + otGraphEdges +
        ", exitGraphEdges=" + exitGraphEdges +
        ", otGraphCrossing=" + otGraphCrossing +
        ", exitGraphCrossing=" + exitGraphCrossing +
        ", otGraphComplexity=" + otGraphComplexity +
        '}';
  }
}
