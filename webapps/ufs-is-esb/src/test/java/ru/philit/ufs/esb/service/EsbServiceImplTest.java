package ru.philit.ufs.esb.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import ru.philit.ufs.esb.ReceiveMessageListener;
import ru.philit.ufs.esb.client.EsbClient;
import ru.philit.ufs.model.TestData;
import ru.philit.ufs.model.cache.IsEsbCache;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationPackageRequest;
import ru.philit.ufs.model.entity.oper.OperationTasksRequest;
import ru.philit.ufs.model.entity.request.RequestType;

public class EsbServiceImplTest {

  private static final String WRONG_REQUEST_TYPE = "wrong";
  private static final String BAD_MESSAGE = "Bad Message";

  @Mock
  private EsbClient esbClient;
  @Mock
  private IsEsbCache isEsbCache;

  private EsbServiceImpl esbService;

  private TestData testData;
  private ReceiveMessageListener caughtReceiveMessageListener;
  private String sentMessage = null;
  private Account receivedAccount = null;
  private Map<String, ExternalEntityRequest> putRequests;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    testData = new TestData();
    putRequests = new HashMap<>();
    MockitoAnnotations.initMocks(this);
    esbService = new EsbServiceImpl(esbClient, isEsbCache);

    doCallRealMethod().when(esbClient).init();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        caughtReceiveMessageListener = (ReceiveMessageListener) invocationOnMock.getArguments()[0];
        invocationOnMock.callRealMethod();
        return null;
      }
    }).when(esbClient).addReceiveMessageListener(any(ReceiveMessageListener.class));
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        sentMessage = (String) invocationOnMock.getArguments()[0];
        return null;
      }
    }).when(esbClient).sendMessage(anyString());
    doCallRealMethod().when(esbClient).receiveMessage(any(Message.class));

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        putRequests.put((String) invocationOnMock.getArguments()[0],
            (ExternalEntityRequest) invocationOnMock.getArguments()[1]);
        return null;
      }
    }).when(isEsbCache).putRequest(anyString(), any(ExternalEntityRequest.class));
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        if (invocationOnMock.getArguments()[0] instanceof Account) {
          receivedAccount = (Account) invocationOnMock.getArguments()[0];
        }
        return null;
      }
    }).when(isEsbCache).pushResponse(any(ExternalEntity.class));

    esbClient.init();
  }

  @Test
  public void testInit() {
    // when
    esbService.init();
    // then
    Assert.assertEquals(caughtReceiveMessageListener, esbService);
  }

  @Test
  public void testSendRequest() {
    // when
    esbService.sendRequest(testData.getAccountByCardNumberRequest());
    sentMessage = sentMessage
        .replaceFirst("<rqTm>.+?</rqTm>", "<rqTm>2017-05-12T17:57:00.000+03:00</rqTm>")
        .replaceFirst("<rqUID>.+?</rqUID>", "<rqUID>4f04ce04-ac37-4ec9-9923-6a9a5a882a97</rqUID>");
    // then
    Assert.assertEquals(sentMessage, TestData.ACCOUNT_REQUEST_XML);
  }

  @Test
  public void testSendRequests() throws IllegalAccessException {
    // given
    ExternalEntityRequest request = new ExternalEntityRequest();
    request.setSessionId(TestData.SESSION_ID);
    request.setRequestData("000");
    int requestCount = 0;

    // when
    request.setEntityType(RequestType.ACCOUNT_BY_ID);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.ACCOUNT_RESIDUES_BY_ID);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.LEGAL_ENTITY_BY_ACCOUNT);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.ACCOUNTS_BY_LEGAL_ENTITY);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.SEIZURES_BY_ACCOUNT);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.GET_OVN);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.ACCOUNT_20202);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.GET_REPRESENTATIVE_BY_CARD);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.OPERATOR_BY_USER);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new AccountOperationRequest());

    // when
    request.setEntityType(RequestType.COUNT_COMMISSION);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.CHECK_WITH_FRAUD);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new OperationPackageRequest());

    // when
    request.setEntityType(RequestType.CHECK_OPER_PACKAGE);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.CREATE_OPER_PACKAGE);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new OperationTasksRequest());

    // when
    request.setEntityType(RequestType.GET_OPER_TASKS);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new OperationPackage(1L));

    // when
    request.setEntityType(RequestType.ADD_OPER_TASK);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.UPDATE_OPER_TASK);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new CashDepositAnnouncementsRequest());

    // when
    request.setEntityType(RequestType.GET_OVN_LIST);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new CashDepositAnnouncement());

    // when
    request.setEntityType(RequestType.CREATE_OVN);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(RequestType.UPDATE_OVN);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new RepresentativeRequest());

    // when
    request.setEntityType(RequestType.SEARCH_REPRESENTATIVE);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(ImmutableList.of("role1", "role2"));

    // when
    request.setEntityType(RequestType.OPER_TYPES_BY_ROLE);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new CashSymbolRequest());

    // when
    request.setEntityType(RequestType.CASH_SYMBOL);
    esbService.sendRequest(request);
    requestCount++;
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(WRONG_REQUEST_TYPE);
    esbService.sendRequest(request);
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    request.setEntityType(null);
    esbService.sendRequest(request);
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // when
    esbService.sendRequest(null);
    // then
    Assert.assertEquals(putRequests.size(), requestCount);

    // given
    request.setRequestData(new Serializable() {});

    String[] requestTypes = new String[] {RequestType.ACCOUNT_20202,
        RequestType.ACCOUNT_BY_CARD_NUMBER, RequestType.ACCOUNT_BY_ID,
        RequestType.ACCOUNT_RESIDUES_BY_ID, RequestType.ACCOUNTS_BY_LEGAL_ENTITY,
        RequestType.ADD_OPER_TASK, RequestType.CARD_INDEX_ELEMENTS_BY_ACCOUNT,
        RequestType.CASH_SYMBOL, RequestType.CHECK_OPER_PACKAGE, RequestType.CHECK_WITH_FRAUD,
        RequestType.COUNT_COMMISSION, RequestType.CREATE_OPER_PACKAGE, RequestType.CREATE_OVN,
        RequestType.GET_OPER_TASKS, RequestType.GET_OVN, RequestType.GET_OVN_LIST,
        RequestType.GET_REPRESENTATIVE_BY_CARD, RequestType.LEGAL_ENTITY_BY_ACCOUNT,
        RequestType.OPER_TYPES_BY_ROLE, RequestType.OPERATOR_BY_USER,
        RequestType.SEARCH_REPRESENTATIVE, RequestType.SEIZURES_BY_ACCOUNT,
        RequestType.UPDATE_OPER_TASK, RequestType.UPDATE_OVN};
    for (String requestType : requestTypes) {
      // when
      request.setEntityType(requestType);
      esbService.sendRequest(request);
      // then
      Assert.assertEquals(putRequests.size(), requestCount);
    }
  }

  @Test
  public void testReceiveMessage() {
    // given
    esbService.init();
    // when
    esbClient.receiveMessage(new GenericMessage<>(TestData.ACCOUNT_RESPONSE_XML));
    // then
    Assert.assertEquals(receivedAccount, testData.getAccount());
  }

  @Test
  public void testReceiveMessage_Bad() {
    // given
    esbService.init();
    // when
    esbClient.receiveMessage(new GenericMessage<>(BAD_MESSAGE));
    // then
    Assert.assertNull(receivedAccount);
  }
}
