package ttl.larku.rating;

import java.math.BigDecimal;

public interface RatingProvider {
   default BigDecimal getRating(int id) {
      throw new UnsupportedOperationException("Needs Implementing");
   }

   default BigDecimal getRating(int id, String user, String pw) {
      throw new UnsupportedOperationException("Needs Implementing");
   }
}
