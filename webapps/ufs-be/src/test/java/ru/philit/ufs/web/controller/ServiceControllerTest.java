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

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.ServiceMapper;
import ru.philit.ufs.web.mapping.impl.ServiceMapperImpl;
import ru.philit.ufs.web.provider.ServiceProvider;
import ru.philit.ufs.web.view.GetAuditHistoryResp;
import ru.philit.ufs.web.view.GetLogHistoryResp;
import ru.philit.ufs.web.view.LogEventReq;
import ru.philit.ufs.web.view.LogEventResp;

public class ServiceControllerTest extends RestControllerTest {

  @Mock
  private ServiceProvider provider;
  @Spy
  private ServiceMapper mapper = new ServiceMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new ServiceController(provider, mapper));
  }

  @Test
  public void testLogEvent() throws Exception {
    LogEventReq request = new LogEventReq();
    request.setEvent("Testing service controller");
    request.setDate("today");
    String requestJson = toRequest(request);

    when(provider.addLogEvent(any(ClientInfo.class), anyString(), anyString())).thenReturn(true);

    String responseJson = performAndGetContent(post("/logger").content(requestJson));
    LogEventResp response = toResponse(responseJson, LogEventResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
    assertTrue(response.getData());

    verify(provider, times(1)).addLogEvent(any(ClientInfo.class), anyString(), anyString());
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetAuditHistory() throws Exception {
    when(provider.getAuditRequests()).thenReturn(new ArrayList<AuditEntity>());

    String responseJson = performAndGetContent(post("/auditHistory"));
    GetAuditHistoryResp response = toResponse(responseJson, GetAuditHistoryResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getAuditRequests();
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetLogHistory() throws Exception {
    when(provider.getLogEvents()).thenReturn(new ArrayList<LogEntity>());

    String responseJson = performAndGetContent(post("/logHistory"));
    GetLogHistoryResp response = toResponse(responseJson, GetLogHistoryResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getLogEvents();
    verifyNoMoreInteractions(provider);
  }
}