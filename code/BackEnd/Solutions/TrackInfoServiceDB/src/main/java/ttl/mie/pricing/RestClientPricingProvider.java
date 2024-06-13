package ttl.mie.pricing;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("networkpricing")
public class RestClientPricingProvider implements PricingProvider {

   private Logger logger = LoggerFactory.getLogger(getClass());

   private RestClient restClient;

   private String baseUrl = "http://localhost:10002/price";
   public RestClientPricingProvider() {
      this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   @Override
   public BigDecimal getPriceByTrackId(int trackId) {
      var result = getRatingFromService(trackId);
      return result;
   }

   private BigDecimal getRatingFromService(int id) {
      var response = restClient.get()
            .uri("/{id}", id)
            .retrieve()
            .toEntity(BigDecimal.class);

      if(response.getStatusCode() == HttpStatus.OK) {
         var rating = response.getBody();
         return rating;
      } {
         logger.warn("Bad Status Returned from call to pricing service at: {}, code: {}",
               baseUrl, response.getStatusCode());
         return new BigDecimal("0.00");
      }
   }
}
