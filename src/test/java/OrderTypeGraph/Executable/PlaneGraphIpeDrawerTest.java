package OrderTypeGraph.Executable;

import OrderTypeGraph.Database;
import OrderTypeGraph.Graph;
import OrderTypeGraph.PlaneGraphIpeDrawer;
import OrderTypeGraph.Points;
import OrderTypeGraph.ResultDatasetIO;
import java.io.File;

public class PlaneGraphIpeDrawerTest {

  public void drawAndWrite() {
    int n = 6;
    boolean[] rules = new boolean[]{true, true, true};
    String prefixPath = String
        .format("rules123%s%s%s%s", rules[0] ? "4" : "", rules[1] ? "5" : "", rules[2] ? "6" : "",
            File.separator);

    Database database = Database.read(n);
    ResultDatasetIO resultDatasetIO =
        new ResultDatasetIO(n, database.size(), Graph.bitFormatSize(n), prefixPath);

    for (int i = 0; i < database.size(); i++) {
      Points points = database.get(i);
      Graph graph = resultDatasetIO.getGraph(i);
      boolean addText = false;
      PlaneGraphIpeDrawer
          .drawAndWrite(graph, points, String.format("z4_n%d_id%02d", n, i), addText);
    }

  }
}
