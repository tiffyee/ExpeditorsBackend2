package ttl.mie.pricing;

import java.math.BigDecimal;

public interface PricingProvider {
   BigDecimal getPriceByTrackId(int trackId);
}
