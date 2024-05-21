package ttl.larku.rating;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("networkrating")
public class RestClientRatingProvider implements RatingProvider {

   private RestClient restClient;

   public RestClientRatingProvider() {
      var baseUrl = "http://localhost:10001/rating";
      this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   @Override
   public String getRating(int id) {
      var result = getRatingFromService(id);
      return result;
   }

   private String getRatingFromService(int id) {
      var response = restClient.get()
            .uri("/{id}", id)
            .retrieve()
            .toEntity(String.class);

      if(response.getStatusCode() == HttpStatus.OK) {
         var rating = response.getBody();
         return rating;
      }
      return null;
   }
}
