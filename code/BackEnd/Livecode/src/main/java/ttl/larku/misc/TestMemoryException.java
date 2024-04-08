package ttl.larku.misc;


import java.util.ArrayList;
import java.util.List;

public class TestMemoryException {
   public static void main(String[] args) {
      List<byte[]> list = new ArrayList<>();

      while (true) {
         byte[] b = new byte[1000000];

         list.add(b);
      }
   }
}