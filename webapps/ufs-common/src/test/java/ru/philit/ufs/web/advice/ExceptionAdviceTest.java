package ru.philit.ufs.web.advice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import ru.philit.ufs.web.advice.ExceptionAdvice.StatusResponse;
import ru.philit.ufs.web.controller.ProjectController;
import ru.philit.ufs.web.controller.RestControllerTest;
import ru.philit.ufs.web.exception.InvalidDataException;
import ru.philit.ufs.web.exception.UserNotFoundException;

public class ExceptionAdviceTest extends RestControllerTest {

  @Mock
  private ProjectController controller;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetupWithAdvice(controller, new ExceptionAdvice());
  }

  @Test
  public void testHandleNotAuthorizedException() throws Exception {
    when(controller.getProjectInfo())
        .thenThrow(new UserNotFoundException("User is not found"));

    String json = performAndExpectError(get("/info"), HttpStatus.UNAUTHORIZED.value());
    StatusResponse response = toResponse(json, StatusResponse.class);

    assertFalse(response.isSuccess());
    assertNotNull(response.getData());
    assertNotNull(response.getMessage());
  }

  @Test
  public void handleInvalidDataException() throws Exception {
    when(controller.getProjectInfo())
        .thenThrow(new InvalidDataException("Something is wrong with data"));

    String json = performAndExpectError(get("/info"), HttpStatus.OK.value());
    StatusResponse response = toResponse(json, StatusResponse.class);

    assertFalse(response.isSuccess());
    assertNotNull(response.getData());
    assertNotNull(response.getMessage());
  }

  @Test
  public void testHandleThrowable() throws Exception {
    when(controller.getProjectInfo())
        .thenThrow(new RuntimeException("Some exception"));

    String json = performAndExpectError(get("/info"), HttpStatus.INTERNAL_SERVER_ERROR.value());
    StatusResponse response = toResponse(json, StatusResponse.class);

    assertFalse(response.isSuccess());
    assertNotNull(response.getData());
    assertNotNull(response.getMessage());
  }
}
