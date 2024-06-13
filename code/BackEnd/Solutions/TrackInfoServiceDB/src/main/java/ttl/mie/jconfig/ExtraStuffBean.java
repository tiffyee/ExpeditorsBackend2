package ttl.mie.jconfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public record ExtraStuffBean(Map<String, Object> stuff) {

   public ExtraStuffBean() {
      this(new ConcurrentHashMap<>());
   }
}
