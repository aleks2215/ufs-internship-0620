package ru.philit.ufs.web.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.SessionUser;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.web.mapping.UserMapper;
import ru.philit.ufs.web.mapping.impl.UserMapperImpl;
import ru.philit.ufs.web.provider.UserProvider;
import ru.philit.ufs.web.view.GetOperatorResp;
import ru.philit.ufs.web.view.GetWorkplaceResp;
import ru.philit.ufs.web.view.LoginUserReq;
import ru.philit.ufs.web.view.LoginUserResp;
import ru.philit.ufs.web.view.LogoutUserResp;

public class UserControllerTest extends RestControllerTest {

  @Mock
  private UserProvider provider;
  @Spy
  private UserMapper mapper = new UserMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new UserController(provider, mapper));
  }

  @Test
  public void testLoginUser() throws Exception {
    LoginUserReq request = new LoginUserReq();
    request.setLogin("Sidorov_SS");
    request.setPassword("Si");
    String requestJson = toRequest(request);

    when(provider.loginUser(anyString(), anyString())).thenReturn(new SessionUser());

    String responseJson = performAndGetContent(post("/login").content(requestJson));
    LoginUserResp response = toResponse(responseJson, LoginUserResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).loginUser(anyString(), anyString());
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testLogoutUser() throws Exception {
    when(provider.logoutUser(anyString())).thenReturn(true);

    String responseJson = performAndGetContent(post("/logout"));
    LogoutUserResp response = toResponse(responseJson, LogoutUserResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
    assertTrue(response.getData());

    verify(provider, times(1)).logoutUser(anyString());
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetOperator() throws Exception {
    when(provider.getOperator(any(ClientInfo.class))).thenReturn(new Operator());

    String responseJson = performAndGetContent(post("/operator"));
    GetOperatorResp response = toResponse(responseJson, GetOperatorResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getOperator(any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetWorkplace() throws Exception {
    when(provider.getWorkplace(any(ClientInfo.class))).thenReturn(new Workplace());

    String responseJson = performAndGetContent(post("/workplace"));
    GetWorkplaceResp response = toResponse(responseJson, GetWorkplaceResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getWorkplace(any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }
}
