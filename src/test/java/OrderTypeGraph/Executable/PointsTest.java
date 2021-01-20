package OrderTypeGraph.Executable;

import static org.junit.Assert.assertEquals;

import OrderTypeGraph.MyFile;
import OrderTypeGraph.MyFile.Address;
import OrderTypeGraph.Point;
import OrderTypeGraph.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.junit.Test;

public class PointsTest {

  public void testFileInitializer() throws FileNotFoundException {
    String filename = "points.txt";
    File file = MyFile.getInstance(filename, Address.RESOURCE);
    Scanner in = new Scanner(file);

    int t = in.nextInt();
    System.err.printf("Number of tests: %d\n", t);
    while (t-- > 0) {
      System.err.println("----------------------------");
      Points points = new Points(in);
      System.err.println(points);
      System.err.println(points.computeCCSystem());
    }

  }

  @Test
  public void addPoint() {
    Point p = new Point(1, -1);

    List<Point> list = new ArrayList<>();
    list.add(p);

    Points points1 = new Points();
    points1.add(p);

    Points points2 = new Points(list);

    p.setX(0);
    p.setY(0);

    System.out.println(points1);
    System.out.println(points2);
  }

  @Test
  public void randomPointGenerator() {
    Random random = new Random();
    int n = 12;
    Points points = Points.randomPointGenerator(n, random);
    assertEquals(n, points.size());
    System.out.println(points);
  }

  @Test
  public void testRandomPointGenerator() {
    int n = 10;
    Points points = Points.randomPointGenerator(n);
    assertEquals(n, points.size());
    System.out.println(points);
  }
}
