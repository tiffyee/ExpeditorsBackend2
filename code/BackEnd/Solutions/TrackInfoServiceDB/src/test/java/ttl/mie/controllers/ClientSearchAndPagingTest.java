package ttl.mie.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.search.ResultWithPageData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientSearchAndPagingTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private RestClient restClient;

    private String baseUrl;
    private String rootUrl;
    private String searchUrl;
    private String oneTrackUrl;

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port;
        rootUrl = "/track";
        searchUrl = "/trackrest/search";
        oneTrackUrl = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void testGetAllShouldGiveAllTracks() throws IOException {

        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<List<TrackDTO>>
                ptr = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<TrackDTO>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<TrackDTO> l2 = response.getBody();
        System.out.println("l2  " + l2);
        System.out.println("l2 size: " + l2.size());
        assertEquals(50, l2.size());
    }

    @Test
    public void testSearchPageWithoutParamsShouldGetAllTracks() throws IOException {

        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<ResultWithPageData<TrackDTO>>
              ptr = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<ResultWithPageData<TrackDTO>> response = restClient.get()
              .uri(searchUrl)
              .retrieve()
              .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResultWithPageData<TrackDTO> l2 = response.getBody();
        System.out.println("l2  " + l2);
        System.out.println("l2 size: " + l2.result().size());


        assertEquals(50, l2.result().size());
    }

    @Test
    public void testSearchWithPageParamsShouldReturnAPageOfResults() throws IOException {

        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<ResultWithPageData<TrackDTO>>
              ptr = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<ResultWithPageData<TrackDTO>> response = restClient.get()
              .uri(searchUrl + "?page={page}", 2)
              .retrieve()
              .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResultWithPageData<TrackDTO> l2 = response.getBody();
        System.out.println("l2  " + l2);
        System.out.println("l2 size: " + l2.result().size());
        assertEquals(50, l2.result().size());

        int page = (int)l2.getProp("page");
        int pageSize = (int)l2.getProp("pageSize");
        int totalElements = (int)l2.getProp("totalElements");
        int totalPages = (int)l2.getProp("totalPages");

        assertEquals(2, page);
        assertEquals(10, pageSize);
        assertEquals(50, totalElements);
        assertEquals(5, totalPages);

    }
}
