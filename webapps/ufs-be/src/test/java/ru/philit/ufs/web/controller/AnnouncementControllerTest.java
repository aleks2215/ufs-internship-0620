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

import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.AnnouncementMapper;
import ru.philit.ufs.web.mapping.impl.AnnouncementMapperImpl;
import ru.philit.ufs.web.provider.AnnouncementProvider;
import ru.philit.ufs.web.view.GetAccount20202Req;
import ru.philit.ufs.web.view.GetAccount20202Resp;
import ru.philit.ufs.web.view.GetAnnouncementReq;
import ru.philit.ufs.web.view.GetAnnouncementResp;
import ru.philit.ufs.web.view.GetAnnouncementsReq;
import ru.philit.ufs.web.view.GetAnnouncementsResp;
import ru.philit.ufs.web.view.GetCommissionReq;
import ru.philit.ufs.web.view.GetCommissionResp;

public class AnnouncementControllerTest extends RestControllerTest {

  @Mock
  private AnnouncementProvider provider;
  @Spy
  private AnnouncementMapper mapper = new AnnouncementMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new AnnouncementController(provider, mapper));
  }

  @Test
  public void testGetAnnouncements() throws Exception {
    GetAnnouncementsReq request = new GetAnnouncementsReq();
    request.setAccountId("5959686825253535");
    request.setStatus("Pending");
    String requestJson = toRequest(request);

    when(provider.getAnnouncements(anyString(), anyString(), any(ClientInfo.class)))
        .thenReturn(new ArrayList<CashDepositAnnouncement>());

    String responseJson = performAndGetContent(post("/announcement/").content(requestJson));
    GetAnnouncementsResp response = toResponse(responseJson, GetAnnouncementsResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1))
        .getAnnouncements(anyString(), anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetAnnouncementById() throws Exception {
    GetAnnouncementReq request = new GetAnnouncementReq();
    request.setAnnouncementId("FD1D833A431F498DB556E91998C8C8A5");
    String requestJson = toRequest(request);

    when(provider.getAnnouncement(anyString(), any(ClientInfo.class)))
        .thenReturn(new CashDepositAnnouncement());

    String responseJson = performAndGetContent(post("/announcement/byId").content(requestJson));
    GetAnnouncementResp response = toResponse(responseJson, GetAnnouncementResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getAnnouncement(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetCommission() throws Exception {
    GetCommissionReq request = new GetCommissionReq();
    request.setAccountId("5959686825253535");
    request.setAmount("25362.23");
    request.setTypeCode("ToCardDeposit");
    String requestJson = toRequest(request);

    when(provider.getCommission(anyString(), anyString(), anyString(), any(ClientInfo.class)))
        .thenReturn(BigDecimal.ZERO);

    String urlTemplate = "/announcement/commission";
    String responseJson = performAndGetContent(post(urlTemplate).content(requestJson));
    GetCommissionResp response = toResponse(responseJson, GetCommissionResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1))
        .getCommission(anyString(), anyString(), anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetAccount20202() throws Exception {
    GetAccount20202Req request = new GetAccount20202Req();
    request.setWorkplaceId("AC11921E8E1247009ED17924B8CD9E72");
    String requestJson = toRequest(request);

    when(provider.getAccount20202(anyString(), any(ClientInfo.class))).thenReturn("1234567890");

    String urlTemplate = "/announcement/account20202";
    String responseJson = performAndGetContent(post(urlTemplate).content(requestJson));
    GetAccount20202Resp response = toResponse(responseJson, GetAccount20202Resp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getAccount20202(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }
}
