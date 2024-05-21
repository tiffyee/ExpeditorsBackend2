package ttl.mie.misc;

import java.io.File;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import ttl.mie.app.JsonFileToDB;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestGetInputStream {

   private JsonFileToDB jsonFileToDB;

   @Test
   public void testGetInputStream() {
      File file = new File("tracks.json");
      //InputStream is = jsonFileToDB.getInputStream(new File("tracks.json"));
      InputStream is = this.getClass().getClassLoader().getResourceAsStream(file.getName());
      assertNotNull(is);
   }


}
