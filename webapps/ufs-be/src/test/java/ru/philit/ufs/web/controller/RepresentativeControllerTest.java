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
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.RepresentativeMapper;
import ru.philit.ufs.web.mapping.impl.RepresentativeMapperImpl;
import ru.philit.ufs.web.provider.RepresentativeProvider;
import ru.philit.ufs.web.view.GetRepresentativeReq;
import ru.philit.ufs.web.view.GetRepresentativeResp;

public class RepresentativeControllerTest extends RestControllerTest {

  @Mock
  private RepresentativeProvider provider;
  @Spy
  private RepresentativeMapper mapper = new RepresentativeMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new RepresentativeController(provider, mapper));
  }

  @Test
  public void testGetAccountData() throws Exception {
    GetRepresentativeReq request = new GetRepresentativeReq();
    request.setCardNumber("5959686825253535");
    String requestJson = toRequest(request);

    when(provider.getRepresentativeByCardNumber(anyString(), any(ClientInfo.class)))
        .thenReturn(new Representative());

    String urlTemplate = "/representative/byCardNumber";
    String responseJson = performAndGetContent(post(urlTemplate).content(requestJson));
    GetRepresentativeResp response = toResponse(responseJson, GetRepresentativeResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getRepresentativeByCardNumber(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }
}
