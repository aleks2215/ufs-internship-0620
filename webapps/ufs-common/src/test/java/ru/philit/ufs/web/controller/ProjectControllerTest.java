package ru.philit.ufs.web.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.config.property.ProjectProperties;
import ru.philit.ufs.web.view.GetProjectInfoResp;

public class ProjectControllerTest extends RestControllerTest {

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new ProjectController(new ProjectProperties("test", "1.0")));
  }

  @Test
  public void testGetProjectInfo() throws Exception {
    String json = performAndGetContent(get("/info"));
    GetProjectInfoResp response = toResponse(json, GetProjectInfoResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
    assertNotNull(response.getData().getName());
    assertNotNull(response.getData().getVersion());
  }
}
