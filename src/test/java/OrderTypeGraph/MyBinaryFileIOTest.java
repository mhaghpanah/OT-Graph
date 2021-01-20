package OrderTypeGraph;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MyBinaryFileIOTest {

  @Test
  public void test0() {
    String testFileName = "test.bin";
    String str = "Hello World!";
    byte[] bytes = str.getBytes();

    MyBinaryFileIO.write(testFileName, bytes);

    byte[] res = MyBinaryFileIO.read(testFileName);

    assertArrayEquals(bytes, res);
    assertEquals(str, new String(res));
  }

  @Test
  public void test1() {
    String testFileName = "test.bin";
    String str = "Hello World!";

    MyBinaryFileIO.write(testFileName, str.getBytes());
    assertArrayEquals(str.getBytes(), MyBinaryFileIO.read(testFileName));
    assertEquals(str, new String(MyBinaryFileIO.read(testFileName)));

    String strChange = "Earth~";
    MyBinaryFileIO.write(testFileName, 6, strChange.getBytes());

    assertArrayEquals("Hello Earth~".getBytes(), MyBinaryFileIO.read(testFileName));
    assertEquals("Hello Earth~", new String(MyBinaryFileIO.read(testFileName)));

    assertArrayEquals("Hello".getBytes(), MyBinaryFileIO.read(testFileName, 0, 5));
    assertEquals("Hello", new String(MyBinaryFileIO.read(testFileName, 0, 5)));

    assertArrayEquals("ello".getBytes(), MyBinaryFileIO.read(testFileName, 1, 4));
    assertEquals("ello", new String(MyBinaryFileIO.read(testFileName, 1, 4)));
  }

}
