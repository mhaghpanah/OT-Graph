package OrderTypeGraph.Executable;

import static org.junit.Assert.assertEquals;

import OrderTypeGraph.Database;
import OrderTypeGraph.MyFile.Address;
import OrderTypeGraph.MyFileWriter;
import OrderTypeGraph.Points;
import java.io.File;
import org.junit.Test;

public class DatabaseTest {

  public void printPoints() {
    String filenameDataset = "otypes09.b16";
    String pathnameDataset = String.join(File.separator,
        System.getProperty("user.dir"), "data", filenameDataset);
    System.out.println(pathnameDataset);

    Database database = new Database(pathnameDataset, 9, 16);
    Points points = database.get(1874);
    System.out.println(points);
  }

  public void writePoints() {
    String filenameDataset = "otypes09.b16";
    String pathnameDataset = String.join(File.separator,
        System.getProperty("user.dir"), "data", filenameDataset);
    System.out.println(pathnameDataset);

    Database database = new Database(pathnameDataset, 9, 16);
    Points points = database.get(1874);

    System.out.println(points);
    System.out.println(points.toFileFormatString());

    String filenameOutput = "1874-9_2.txt";
    MyFileWriter.write(filenameOutput, points.toFileFormatString(), Address.RESOURCE);

  }

  @Test
  public void datasetSize() {
    for (int n = 3; n <= 9; n++) {
      Database database = Database.read(n);
      assertEquals(database.size(), Database.databaseSize(n));
    }
  }
}
