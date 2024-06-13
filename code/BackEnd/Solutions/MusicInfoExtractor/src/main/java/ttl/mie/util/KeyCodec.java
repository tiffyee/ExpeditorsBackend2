package ttl.mie.util;

import java.util.Map;
import java.util.stream.Collectors;

public class KeyCodec {

   private static Map<String, String> codeMap;

   private static Map<String, String> decodeMap;
   static {
      codeMap = Map.of("/", "%2F");

      decodeMap = codeMap.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
   }


   public static String encode(String toEncode) {
      for(var entry : codeMap.entrySet()) {
        toEncode = toEncode.replaceAll(entry.getKey(), entry.getValue());
      }
      return toEncode;
   }

   public static String decode(String toDecode) {
      for(var entry : decodeMap.entrySet()) {
         toDecode = toDecode.replaceAll(entry.getKey(), entry.getValue());
      }
      return toDecode;
   }
}
