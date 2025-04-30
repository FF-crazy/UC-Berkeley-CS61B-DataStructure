package byow.Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

  static final File CWD = new File(System.getProperty("user.dir"));
  static final File SAVE = join(CWD, "savefile.txt");


  static File join(File first, String... others) {
    return Paths.get(first.getPath(), others).toFile();
  }

  public static void toFile(long seed, int x, int y) {
    try (PrintWriter writer = new PrintWriter(SAVE)) {
      writer.println(seed);
      writer.println(x + " " + y);
      // Writer is automatically closed when the block exits
    } catch (IOException e) {
      throw new RuntimeException("Failed to save game state", e);
    }
  }

  static String readFile() {
    if (!SAVE.exists()) {
      return null;
    }
    try {
      // Read all content from the file as a string with explicit UTF-8 encoding
      return new String(Files.readAllBytes(SAVE.toPath()), StandardCharsets.UTF_8);
    } catch (IOException e) {
      System.err.println("Error reading save file: " + e.getMessage());
      return null;
    }
  }

}

