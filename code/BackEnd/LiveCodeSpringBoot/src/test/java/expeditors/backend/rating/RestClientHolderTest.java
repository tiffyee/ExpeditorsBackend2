package expeditors.backend.rating;

import expeditors.backend.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestClientHolder.class)
public class RestClientHolderTest {
   @Autowired
   private MockRestServiceServer server;

   @Autowired
   private RestClientHolder clientHolder;

   @Test
   public void testRestClientHolderGivesARatingGreaterThanZero() {
      String url = "http://localhost:10001/courseRating/1";
      this.server.expect(requestTo(url)).andRespond(withSuccess("1", MediaType.APPLICATION_JSON));
      Course course = new Course("Math-101", "Intro to Math");
      course.setId(1);

      course.setRating(clientHolder.getRating(course.getId()));

      assertTrue(course.getRating() > 0);
   }
}
