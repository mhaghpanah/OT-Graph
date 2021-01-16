package OrderTypeGraph;

public class Line {

  Point v;
  long c;

  public Line(Point v, long c) {
    this.v = new Point(v.getX(), v.getY());
    this.c = c;
  }

  public Line(long a, long b, long c) {
    this.v = new Point(b, -a);
    this.c = c;
  }

  public Line(Point p, Point q) {
    this.v = q.minus(p);
    this.c = v.cross(p);
  }

  public long side(Point p) {
    return v.cross(p) - c;
  }
}
