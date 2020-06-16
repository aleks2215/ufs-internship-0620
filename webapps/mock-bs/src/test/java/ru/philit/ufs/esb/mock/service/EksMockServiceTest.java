package ru.philit.ufs.esb.mock.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.entity.esb.eks.PkgStatusType;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

public class EksMockServiceTest {

  @Mock
  private EsbClient esbClient;
  @Mock
  private MockCache mockCache;

  private EksMockService service;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        OperationPackageInfo packageInfo = new OperationPackageInfo();
        packageInfo.setStatus(PkgStatusType.NEW);
        return packageInfo;
      }
    }).when(mockCache).createPackage(anyString(), anyString(), anyString());

    service = new EksMockService(esbClient, mockCache);
  }

  @Test
  public void testInit() throws Exception {
    service.init();
  }

  @Test
  public void testProcessMessage_SrvAccountByIdRq() throws Exception {
    String requestMessage = "<SrvAccountByIdRq><HeaderInfo/></SrvAccountByIdRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvAccountByCardNumRq() throws Exception {
    String requestMessage = "<SrvAccountByCardNumRq><HeaderInfo/></SrvAccountByCardNumRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvAccountResiduesByIdRq() throws Exception {
    String requestMessage = "<SrvAccountResiduesByIdRq><HeaderInfo/></SrvAccountResiduesByIdRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvLeAccountListRq() throws Exception {
    String requestMessage = "<SrvLEAccountListRq><HeaderInfo/></SrvLEAccountListRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvLegalEntityByAccountRq() throws Exception {
    String requestMessage = "<SrvLegalEntityByAccountRq><HeaderInfo/></SrvLegalEntityByAccountRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvAddTaskClOperPkgRq() throws Exception {
    String requestMessage = "<SrvAddTaskClOperPkgRq><HeaderInfo/><SrvAddTaskClOperPkgRqMessage>"
        + "<ToCardDeposit><TaskItem><pkgTaskStatus>Active</pkgTaskStatus></TaskItem>"
        + "</ToCardDeposit>"
        + "<FromCardWithdraw><TaskItem><pkgTaskStatus>Active</pkgTaskStatus></TaskItem>"
        + "</FromCardWithdraw>"
        + "<ToAccountDepositRub><TaskItem><pkgTaskStatus>Active</pkgTaskStatus></TaskItem>"
        + "</ToAccountDepositRub>"
        + "<FromAccountWithdrawRub><TaskItem><pkgTaskStatus>Active</pkgTaskStatus></TaskItem>"
        + "</FromAccountWithdrawRub>"
        + "<CheckbookIssuing><TaskItem><pkgTaskStatus>Active</pkgTaskStatus></TaskItem>"
        + "</CheckbookIssuing>"
        + "</SrvAddTaskClOperPkgRqMessage></SrvAddTaskClOperPkgRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCheckClOperPkgRq() throws Exception {
    String requestMessage = "<SrvCheckClOperPkgRq><HeaderInfo/><SrvCheckClOperPkgRqMessage/>"
        + "</SrvCheckClOperPkgRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCreateClOperPkgRq() throws Exception {
    String requestMessage = "<SrvCreateClOperPkgRq><HeaderInfo/><SrvCreateClOperPkgRqMessage/>"
        + "</SrvCreateClOperPkgRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetTaskClOperPkgRq() throws Exception {
    String requestMessage = "<SrvGetTaskClOperPkgRq><HeaderInfo/><SrvGetTaskClOperPkgRqMessage/>"
        + "</SrvGetTaskClOperPkgRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvUpdTaskClOperPkgRq() throws Exception {
    String requestMessage = "<SrvUpdTaskClOperPkgRq><HeaderInfo/><SrvUpdTaskClOperPkgRqMessage>"
        + "<ToCardDeposit><TaskItem><pkgTaskId>1</pkgTaskId><pkgTaskStatus>Active"
        + "</pkgTaskStatus></TaskItem></ToCardDeposit>"
        + "<FromCardWithdraw><TaskItem><pkgTaskId>2</pkgTaskId><pkgTaskStatus>Active"
        + "</pkgTaskStatus></TaskItem></FromCardWithdraw>"
        + "<ToAccountDepositRub><TaskItem><pkgTaskId>3</pkgTaskId><pkgTaskStatus>Active"
        + "</pkgTaskStatus></TaskItem></ToAccountDepositRub>"
        + "<FromAccountWithdrawRub><TaskItem><pkgTaskId>4</pkgTaskId><pkgTaskStatus>Active"
        + "</pkgTaskStatus></TaskItem></FromAccountWithdrawRub>"
        + "<CheckbookIssuing><TaskItem><pkgTaskId>5</pkgTaskId><pkgTaskStatus>Active"
        + "</pkgTaskStatus></TaskItem></CheckbookIssuing>"
        + "</SrvUpdTaskClOperPkgRqMessage></SrvUpdTaskClOperPkgRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCardIndexElementsByAccountRq() throws Exception {
    String requestMessage = "<SrvCardIndexElementsByAccountRq><HeaderInfo/>"
        + "</SrvCardIndexElementsByAccountRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvSeizureByAccountRq() throws Exception {
    String requestMessage = "<SrvSeizureByAccountRq><HeaderInfo/></SrvSeizureByAccountRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCountCommissionRq() throws Exception {
    String requestMessage = "<SrvCountCommissionRq><HeaderInfo/><SrvCountCommissionRqMessage/>"
        + "</SrvCountCommissionRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCheckWithFraudRq() throws Exception {
    String requestMessage = "<SrvCheckWithFraudRq><HeaderInfo/></SrvCheckWithFraudRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_Other() throws Exception {
    String requestMessage = "Not valid xml";
    assertFalse(service.processMessage(requestMessage));
  }
}
