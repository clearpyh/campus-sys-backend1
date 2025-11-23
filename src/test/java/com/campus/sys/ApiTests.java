package com.campus.sys;

import com.campus.sys.activity.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTests {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;

  String loginToken() throws Exception {
    String body = "{\"username\":\"admin\",\"password\":\"admin123\"}";
    String res = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    return mapper.readTree(res).get("token").asText();
  }

  @Test
  void createAndPublishActivity() throws Exception {
    String token = loginToken();
    Activity a = new Activity();
    a.setName("冬令营");
    a.setTitle("数学特训");
    String payload = mapper.writeValueAsString(a);
    String created = mvc.perform(post("/api/activities").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(payload))
      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    long id = mapper.readTree(created).get("id").asLong();
    mvc.perform(post("/api/activities/" + id + "/publish").header("Authorization", "Bearer " + token))
      .andExpect(status().isOk());
  }
}