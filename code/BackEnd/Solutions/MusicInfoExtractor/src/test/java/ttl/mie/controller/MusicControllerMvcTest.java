package ttl.mie.controller;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ttl.mie.util.KeyCodec;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MusicControllerMvcTest {

   @Autowired
   private MockMvc mockMvc;

   @Test
   public void testGetArtistsWithQueryString() throws Exception {
      var str = "Henry Threadgill's Zooid#Tomorrow Sunny / The Revelry, Spp";
      var encodedStr = KeyCodec.encode(str);

      MockHttpServletRequestBuilder builder = get("/musicinfo/{key}", encodedStr)
            .accept(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(7)));

      MvcResult result = actions.andReturn();

      String jsonResult = result.getResponse().getContentAsString();

      System.out.println("jsonResult: " + jsonResult);


   }
}
