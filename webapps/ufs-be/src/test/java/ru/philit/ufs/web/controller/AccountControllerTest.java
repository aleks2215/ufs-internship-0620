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
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.AccountMapper;
import ru.philit.ufs.web.mapping.impl.AccountMapperImpl;
import ru.philit.ufs.web.provider.AccountProvider;
import ru.philit.ufs.web.view.GetAccountDataReq;
import ru.philit.ufs.web.view.GetAccountDataResp;

public class AccountControllerTest extends RestControllerTest {

  @Mock
  private AccountProvider provider;
  @Spy
  private AccountMapper mapper = new AccountMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new AccountController(provider, mapper));
  }

  @Test
  public void testGetAccountData() throws Exception {
    GetAccountDataReq request = new GetAccountDataReq();
    request.setCardNumber("5959686825253535");
    final String requestJson = toRequest(request);

    AccountResidues accountResidues = new AccountResidues();
    accountResidues.setCardIndex1Flag(true);
    accountResidues.setCardIndex2Flag(true);
    accountResidues.setSeizureFlag(true);

    when(provider.getAccount(anyString(), any(ClientInfo.class)))
        .thenReturn(new Account());
    when(provider.getLegalEntity(anyString(), any(ClientInfo.class)))
        .thenReturn(new LegalEntity());
    when(provider.getAccountResidues(anyString(), any(ClientInfo.class)))
        .thenReturn(accountResidues);
    when(provider.getCardIndexes1(anyString(), any(ClientInfo.class)))
        .thenReturn(new ArrayList<PaymentOrderCardIndex1>());
    when(provider.getCardIndexes2(anyString(), any(ClientInfo.class)))
        .thenReturn(new ArrayList<PaymentOrderCardIndex2>());
    when(provider.getSeizures(anyString(), any(ClientInfo.class)))
        .thenReturn(new ArrayList<Seizure>());

    String responseJson = performAndGetContent(post("/account/byCardNumber").content(requestJson));
    GetAccountDataResp response = toResponse(responseJson, GetAccountDataResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).getAccount(anyString(), any(ClientInfo.class));
    verify(provider, times(1)).getLegalEntity(anyString(), any(ClientInfo.class));
    verify(provider, times(1)).getAccountResidues(anyString(), any(ClientInfo.class));
    verify(provider, times(1)).getCardIndexes1(anyString(), any(ClientInfo.class));
    verify(provider, times(1)).getCardIndexes2(anyString(), any(ClientInfo.class));
    verify(provider, times(1)).getSeizures(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }
}
