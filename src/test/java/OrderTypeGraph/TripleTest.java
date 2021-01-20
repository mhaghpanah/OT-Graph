package OrderTypeGraph;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public class TripleTest {

  @Test
  public void cyclicSymmetry() {
    Triple t1 = new Triple(1, 2, 3);
    Triple t2 = t1.cyclicSymmetry();
    System.out.println(t1);
    System.out.println(t2);
    assertEquals(t1.a, t2.c);
    assertEquals(t1.b, t2.a);
    assertEquals(t1.c, t2.b);
  }

  @Test
  public void antiSymmetry() {
    Triple t1 = new Triple(1, 2, 3);
    Triple t2 = t1.antiSymmetry();
    System.out.println(t1);
    System.out.println(t2);
    assertEquals(t1.a, t2.a);
    assertEquals(t1.b, t2.c);
    assertEquals(t1.c, t2.b);
  }


  @Test
  public void testHashCode() {
    Triple t = new Triple(1, 2, 3);
    Set<Triple> set = new HashSet<>();
    for (int i = 0; i < 6; i++) {
      set.add(t);
      t = t.cyclicSymmetry();
    }
    System.out.println(set);
    System.out.println(set.size());
    assertEquals(3, set.size());
  }

  @Test
  public void compareTo() {
    Triple t = new Triple(1, 2, 3);
    Set<Triple> set = new TreeSet<>();
    for (int i = 0; i < 6; i++) {
      set.add(t);
      t = t.cyclicSymmetry();
    }
    System.out.println(set);
    System.out.println(set.size());
    assertEquals(3, set.size());
  }

}
