package ru.philit.ufs.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class RestControllerTest {

  private MockMvc mockMvc;

  protected void standaloneSetup(Object... controllers) {
    mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
  }

  protected String performAndGetContent(MockHttpServletRequestBuilder builder) throws Exception {
    return mockMvc
        .perform(builder.contentType(MediaType.APPLICATION_JSON_UTF8).header("etoken", "0"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn().getResponse().getContentAsString();
  }

  protected <R> String toRequest(R request) throws Exception {
    return new ObjectMapper().writeValueAsString(request);
  }

  protected <R> R toResponse(String json, Class<R> objectClass) throws Exception {
    return new ObjectMapper().readValue(json, objectClass);
  }

}
