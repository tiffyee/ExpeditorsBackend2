package expeditors.backend.rating;

import expeditors.backend.domain.Course;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("networkrating")
public class NetworkRatingProvider implements RatingProvider {

   private String ratingUrl;
   private RestClient restClient;

   public NetworkRatingProvider(RestTemplateBuilder restTemplateBuilder) {
      var baseUrl = "http://localhost:10001";

      var rootUrl = baseUrl + "/courseRating";
      ratingUrl = rootUrl + "/{id}";

      this.restClient = RestClient.builder(restTemplateBuilder.build())
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }


   @Override
   public void addRatingToCourse(Course course) {

      ResponseEntity<Integer> response = restClient.get()
            .uri(ratingUrl, course.getId())
            .retrieve()
            .toEntity(Integer.class);

      if(response.getStatusCode() == HttpStatus.OK) {
         Integer rating =  response.getBody();
         if(rating != null) {
            course.setRating(rating);
         }
      }
   }
}
