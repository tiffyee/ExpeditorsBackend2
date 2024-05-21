package expeditors.backend.adoptapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.System.out;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdopterRepoControllerMVCTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   private String rootUrl = "/petreposervice";
   private String oneAdopterUrl = rootUrl + "/{adopterId}";
   private String deletePetUrl = rootUrl + "/{adopterId}/{petId}";

   @Test
   public void testGetAll() throws Exception {
      MockHttpServletRequestBuilder builder = get(rootUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isOk());

   }

   @Test
   public void testGetOneWithGoodId() throws Exception {
      MockHttpServletRequestBuilder builder = get(oneAdopterUrl, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isOk());
   }

   @Test
   public void testGetOneWithBadId() throws Exception {
      MockHttpServletRequestBuilder builder = get(oneAdopterUrl, 1000)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isNotFound());
   }

   @Test
   @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
   public void testPostAdopter() throws Exception {
      Adopter c = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
            Pet.builder(PetType.DOG).name("woofie").breed("mixed").build());
      String jsonString = mapper.writeValueAsString(c);

      ResultActions actions = mockMvc.perform(post(rootUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString));

      actions = actions.andExpect(status().isCreated());

      MvcResult result = actions.andReturn();
      String locHeader = result.getResponse().getHeader("Location");
      assertNotNull(locHeader);
      out.println("locHeader: " + locHeader);
   }

   @Test
   public void testDeleteAdopterWithGoodId() throws Exception {
      Adopter c = new Adopter("Adopter For Delete", "383 9999 9393", LocalDate.of(1960, 6, 9),
            Pet.builder(PetType.DOG).name("woofie").breed("mixed").build());
      String jsonString = mapper.writeValueAsString(c);

      ResultActions actions = mockMvc.perform(post(rootUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString));

      actions = actions.andExpect(status().isCreated());

      MvcResult result = actions.andReturn();
      String locHeader = result.getResponse().getHeader("Location");
      assertNotNull(locHeader);

      actions = mockMvc.perform(delete(locHeader)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));

      actions.andExpect(status().isNoContent());
   }

   @Test
   public void testDeleteAdopterWithBadId() throws Exception {
      ResultActions actions = mockMvc.perform(delete(oneAdopterUrl, 1000)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON));

      actions.andExpect(status().isNotFound());
   }

   @Test
   @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
   public void testUpdateAdopterWithGoodId() throws Exception {
      MockHttpServletRequestBuilder builder = get(oneAdopterUrl, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isOk());
      MvcResult result = actions.andReturn();

      String jsonResult = result.getResponse().getContentAsString();
      JsonNode node = mapper.readTree(jsonResult);
      Adopter adopter = mapper.treeToValue(node, Adopter.class);
      adopter.setName("Blech");

      String updatedJson = mapper.writeValueAsString(adopter);

      actions = mockMvc.perform(put(rootUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedJson));

      actions = actions.andExpect(status().isNoContent());

      builder = get(oneAdopterUrl, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      actions = mockMvc.perform(builder)
            .andExpect(status().isOk());
      result = actions.andReturn();

      jsonResult = result.getResponse().getContentAsString();
      node = mapper.readTree(jsonResult);
      adopter = mapper.treeToValue(node, Adopter.class);

      assertEquals("Blech", adopter.getName());
   }

   @Test
   public void testUpdateAdopterWithBadId() throws Exception {
      MockHttpServletRequestBuilder builder = get(oneAdopterUrl, 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isOk());
      MvcResult result = actions.andReturn();

      String jsonResult = result.getResponse().getContentAsString();
      JsonNode node = mapper.readTree(jsonResult);
      Adopter adopter = mapper.treeToValue(node, Adopter.class);
      adopter.setName("Blech");
      adopter.setId(1000);

      String updatedJson = mapper.writeValueAsString(adopter);

      actions = mockMvc.perform(put(rootUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedJson));

      actions = actions.andExpect(status().isNotFound());

   }

   @Test
   public void testAddPetToExistingAdopter() throws Exception {
      int testId = 1;
      MockHttpServletRequestBuilder getBuilder = get(oneAdopterUrl, testId)
            .accept(MediaType.APPLICATION_JSON);

      var actions = mockMvc.perform(getBuilder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pets", hasSize(2)));

      String jsonResult = actions.andReturn().getResponse().getContentAsString();

      var adopter = mapper.readValue(jsonResult, Adopter.class);
      assertEquals(2, adopter.getPets().size());

      var newPet = Pet.builder(PetType.TURTLE).name("dopey").build();

      String updatedJson = mapper.writeValueAsString(newPet);
      var postBuilder = post(oneAdopterUrl, testId)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedJson);

      actions = mockMvc.perform(postBuilder)
                  .andExpect(status().isNoContent());


      //Now wo do a get of the adopter and check that they have 3 pets.
      mockMvc.perform(getBuilder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pets", hasSize(3)));
   }

   @Test
   public void testAddPetToNonExistingAdopter() throws Exception {
      int testId = 1000;

      var newPet = Pet.builder(PetType.TURTLE).name("dopey").build();

      String updatedJson = mapper.writeValueAsString(newPet);
      var postBuilder = post(oneAdopterUrl, testId)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedJson);

      var actions = mockMvc.perform(postBuilder)
            .andExpect(status().isNotFound());

   }

   @Test
   public void testRemovePetFromExistingAdopter() throws Exception {
      int testId = 1;
      MockHttpServletRequestBuilder getBuilder = get(oneAdopterUrl, testId)
            .accept(MediaType.APPLICATION_JSON);

      var actions = mockMvc.perform(getBuilder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pets", hasSize(2)));

      String jsonResult = actions.andReturn().getResponse().getContentAsString();

      var adopter = mapper.readValue(jsonResult, Adopter.class);
      assertEquals(2, adopter.getPets().size());

      var pet = adopter.getPets().stream().findAny().orElse(null);
      assertNotNull(pet);

      int petId = pet.getPetId();

      var postBuilder = delete(deletePetUrl, testId, petId)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      actions = mockMvc.perform(postBuilder)
            .andExpect(status().isNoContent());


      //Now wo do a get of the adopter and check that they have 1 pet.
      mockMvc.perform(getBuilder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pets", hasSize(1)));
   }

   @Test
   public void testRemovePetFromNonExistingAdopter() throws Exception {
      int testId = 1000;

      var postBuilder = delete(deletePetUrl, 1000, 1000)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      mockMvc.perform(postBuilder)
            .andExpect(status().isNotFound());

   }

   @Test
   public void testRemoveNonExistentPetFromExistingAdopter() throws Exception {
      int testId = 1;
      MockHttpServletRequestBuilder getBuilder = get(oneAdopterUrl, testId)
            .accept(MediaType.APPLICATION_JSON);

      var actions = mockMvc.perform(getBuilder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pets", hasSize(2)));

      var postBuilder = delete(deletePetUrl, testId, 1000)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      actions = mockMvc.perform(postBuilder)
            .andExpect(status().isNotFound());

   }
}
