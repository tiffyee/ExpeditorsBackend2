package ttl.mie.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ResultWithPageData<T>(List<T> result,
                                    Map<String, Object> extraProps) {
   public ResultWithPageData(List<T> result) {
      this(result, new HashMap<>());
   }

   public ResultWithPageData<T> addProp(String name, Object value) {
      extraProps.put(name, value);
      return this;
   }

   public Object getProp(String name) {
      return extraProps.get(name);
   }

   public Map<String, Object> getExtraProps() {
      return extraProps;
   }
}
