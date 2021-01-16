package OrderTypeGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter {

  File file;

  public MyFileWriter(String suffixPath, boolean rootPath) {
    file = MyFile.getInstance(suffixPath, rootPath);
  }

  public static boolean write(String pathname, String str) {
    MyFileWriter w = new MyFileWriter(pathname, false);
    return w.writeFile(str);
  }

  public static boolean write(String pathname, String str, boolean rootPath) {
    MyFileWriter w = new MyFileWriter(pathname, rootPath);
    return w.writeFile(str);
  }

  public boolean writeFile(String str) {
    try {
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(str);
      fileWriter.close();
      System.err.printf("Successfully wrote to %s\n", file.getAbsoluteFile());
      return true;
    } catch (IOException e) {
      System.err.println("An error occurred.");
      e.printStackTrace();
      return false;
    }
  }

}
