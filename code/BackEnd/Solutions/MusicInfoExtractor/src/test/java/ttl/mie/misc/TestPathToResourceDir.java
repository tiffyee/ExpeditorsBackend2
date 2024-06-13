package ttl.mie.misc;


import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class TestPathToResourceDir {

   @Test
   public void testWriteToFileInResouceDirectory() {
      Path dest = Paths.get(this.getClass().getResource("/").getPath());

      System.out.println("dest: " + dest);
   }
}
