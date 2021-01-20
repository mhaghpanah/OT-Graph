package OrderTypeGraph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PointTest {

  @Test
  public void getX() {
    Point p0 = new Point(1, 2);
    assertEquals(1, p0.getX());
    Point p1 = new Point(0, 0);
    assertEquals(0, p1.getX());
  }

  @Test
  public void setX() {
    Point p0 = new Point(1, 2);
    p0.setX(-1);
    assertEquals(-1, p0.getX());
    Point p1 = new Point(0, 0);
    p1.setX(1);
    assertEquals(1, p1.getX());
    Point p2 = new Point(4, 4);
    p2.setX(4);
    assertEquals(4, p2.getX());
  }

  @Test
  public void getY() {
    Point p0 = new Point(1, 2);
    assertEquals(2, p0.getY());
    Point p1 = new Point(0, 0);
    assertEquals(0, p1.getY());
  }

  @Test
  public void setY() {
    Point p0 = new Point(1, 2);
    p0.setY(-2);
    assertEquals(-2, p0.getY());
    Point p1 = new Point(0, 0);
    p1.setY(1);
    assertEquals(1, p1.getY());
    Point p2 = new Point(4, 4);
    p2.setY(4);
    assertEquals(4, p2.getY());
  }

  @Test
  public void plus() {
    Point p0 = new Point(0, 0);
    Point p1 = new Point(1, 1);

    assertEquals(p0, p0.plus(p0));
    assertEquals(p1, p1.plus(p0));
    assertEquals(p1, p0.plus(p1));
    assertEquals(new Point(2, 2), p1.plus(p1));
  }

  @Test
  public void minus() {
    Point p0 = new Point(0, 0);
    Point p1 = new Point(1, 1);

    assertEquals(p0, p0.minus(p0));
    assertEquals(p1, p1.minus(p0));
    assertEquals(new Point(-1, -1), p0.minus(p1));
    assertEquals(p0, p1.minus(p1));
  }

  @Test
  public void cross() {
    Point i = new Point(1, 0);
    Point j = new Point(0, 1);
    Point z = new Point(0, 0);
    assertEquals(1, i.cross(j));
    assertEquals(-1, j.cross(i));
    assertEquals(0, z.cross(i));
    assertEquals(0, i.cross(z));
  }

  @Test
  public void testCross() {
    Point a = new Point(2, 1);
    Point b = new Point(1, 2);
    Point c = new Point(1, 1);
    assertEquals(1, c.cross(a, b));
    assertEquals(-1, c.cross(b, a));
  }

  @Test
  public void testToString() {
    Point p = new Point(-1, 1);
    assertEquals("(-1, 1)", p.toString());
  }
}
