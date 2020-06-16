package ru.philit.ufs.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class RestControllerTest {

  private MockMvc mockMvc;

  protected void standaloneSetup(Object controller) {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  protected void standaloneSetupWithAdvice(Object controller, Object advice) {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(advice).build();
  }

  protected ResultActions perform(MockHttpServletRequestBuilder restBuilder) throws Exception {
    return mockMvc
        .perform(restBuilder.contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
  }

  protected String performAndGetContent(MockHttpServletRequestBuilder restBuilder)
      throws Exception {
    return getContent(perform(restBuilder));
  }

  protected String performAndExpectError(MockHttpServletRequestBuilder restBuilder, int status)
      throws Exception {
    return getContent(mockMvc
            .perform(restBuilder.contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().is(status))
    );
  }

  private String getContent(ResultActions actions) throws Exception {
    return actions
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
