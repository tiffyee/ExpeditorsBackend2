package ttl.mie.jconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestMapConfig {

   @Bean
   @Qualifier("blah")
   public Map<String, Object> extraStuffMap() {
      var map = new ConcurrentHashMap<String, Object>();
      return map;
   }
}
