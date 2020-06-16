package ru.philit.ufs.web.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.web.mapping.PosMapper;
import ru.philit.ufs.web.mapping.impl.PosMapperImpl;
import ru.philit.ufs.web.provider.PosProvider;
import ru.philit.ufs.web.view.PosOperationConfirmReq;
import ru.philit.ufs.web.view.PosOperationConfirmResp;
import ru.philit.ufs.web.view.PosVerifyReq;
import ru.philit.ufs.web.view.PosVerifyResp;

public class PosControllerTest extends RestControllerTest {

  @Mock
  private PosProvider provider;
  @Spy
  private PosMapper mapper = new PosMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new PosController(provider, mapper));
  }

  @Test
  public void testVerify() throws Exception {
    PosVerifyReq request = new PosVerifyReq();
    request.setNumber("1234");
    request.setPin("4321");
    String requestJson = toRequest(request);

    when(provider.verifyCreditCard(anyString(), anyString())).thenReturn(new Card());

    String responseJson = performAndGetContent(post("/pos/verify").content(requestJson));
    PosVerifyResp response = toResponse(responseJson, PosVerifyResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).verifyCreditCard(anyString(), anyString());
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testOperationConfirm() throws Exception {
    PosOperationConfirmReq request = new PosOperationConfirmReq();
    request.setTaskId("15935746");
    request.setConfirm(true);
    String requestJson = toRequest(request);

    String responseJson = performAndGetContent(post("/pos/operationConfirm").content(requestJson));
    PosOperationConfirmResp response = toResponse(responseJson, PosOperationConfirmResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
  }
}
