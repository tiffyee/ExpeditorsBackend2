package org.ttl.ratingservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CourseRatingControllerMvcTest {

   @Autowired
   private MockMvc mockMvc;

   @Test
   public void testGetRatingForCourseWithoutAuthInfoGives401() throws Exception {
      int id = 1;

      MockHttpServletRequestBuilder builder = get("/rating/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

      ResultActions actions = mockMvc.perform(builder)
            .andExpect(status().isUnauthorized());

//      MvcResult result = actions.andReturn();
//
//      double rating = Double.parseDouble(result.getResponse().getContentAsString());
//
//      System.out.println("Rating: " + rating);
//      assertTrue(rating > 0);
   }

   @Test
   @WithMockUser(roles = {"USER"})
   public void testGetLimitsWithValidUserGivesOK() throws Exception {
      var actions = mockMvc.perform(get("/admin/lowerLimit"))
            .andExpect(status().isOk()).andReturn();

      var ll = actions.getResponse().getContentAsString();

      actions = mockMvc.perform(get("/admin/upperLimit"))
            .andExpect(status().isOk()).andReturn();

      var ul = actions.getResponse().getContentAsString();

      System.out.println("ll: " + ll + ", ul: " + ul);
   }

   @Test
   @WithMockUser(roles = {"USER"})
   public void testSetLimitsWithoutAdminUserGives403() throws Exception {
      var actions = mockMvc.perform(put("/admin/lowerLimit/{ll}", 15.0))
            .andExpect(status().isForbidden()).andReturn();

      actions = mockMvc.perform(put("/admin/upperLimit/{ul}", 60.0))
            .andExpect(status().isForbidden()).andReturn();

//      actions =  mockMvc.perform(get("/admin/bothLimits"))
//                  .andExpect(status().isOk()).andReturn();
//
//      var bothLimits = actions.getResponse().getContentAsString();
//
//      System.out.println("bothLimits: " + bothLimits);
//
//      assertEquals("15.0:60.0", bothLimits);
   }

   @Test
   @WithMockUser(roles = "ADMIN")
   public void testSetLimitsWithGoodInputGivesNoContent() throws Exception {
      var actions = mockMvc.perform(put("/admin/bothLimits/{ll}/{ul}", 15.0, 60.0))
            .andExpect(status().isNoContent()).andReturn();

      actions =  mockMvc.perform(get("/admin/bothLimits"))
            .andExpect(status().isOk()).andReturn();

      var bothLimits = actions.getResponse().getContentAsString();

      System.out.println("bothLimits: " + bothLimits);

      assertEquals("15.0:60.0", bothLimits);
   }

   @Test
   @WithMockUser(roles = "ADMIN")
   public void testSetLimitsWithBadInputGivesBadRequest() throws Exception {
      var actions = mockMvc.perform(put("/admin/bothLimits/{ll}/{ul}", 65.0, 60.0))
            .andExpect(status().isBadRequest()).andReturn();

      var resultString = actions.getResponse().getContentAsString();
      System.out.println("resultString: " + resultString);
   }

}
