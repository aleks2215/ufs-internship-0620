package ru.philit.ufs.esb.mock.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.esb.mock.client.EsbClient;

public class PprbMockServiceTest {

  @Mock
  private EsbClient esbClient;

  private PprbMockService service;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    service = new PprbMockService(esbClient);
  }


  @Test
  public void testInit() throws Exception {
    service.init();
  }

  @Test
  public void testProcessMessage_SrvCashDepAnmntListByAccountIdRq() throws Exception {
    String requestMessage = "<SrvCashDepAnmntListByAccountIdRq><HeaderInfo/>"
        + "</SrvCashDepAnmntListByAccountIdRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvCreateCashDepAnmntItemRq() throws Exception {
    String requestMessage = "<SrvCreateCashDepAnmntItemRq><HeaderInfo/>"
        + "</SrvCreateCashDepAnmntItemRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetCashDepAnmntItemRq() throws Exception {
    String requestMessage = "<SrvGetCashDepAnmntItemRq><HeaderInfo/>"
        + "<SrvGetCashDepAnmntItemRqMessage/></SrvGetCashDepAnmntItemRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvUpdCashDepAnmntItemRq() throws Exception {
    String requestMessage = "<SrvUpdCashDepAnmntItemRq><HeaderInfo/></SrvUpdCashDepAnmntItemRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGet20202NumRq() throws Exception {
    String requestMessage = "<SrvGet20202NumRq><HeaderInfo/></SrvGet20202NumRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetUserOperationsByRoleRq() throws Exception {
    String requestMessage = "<SrvGetUserOperationsByRoleRq><HeaderInfo/>"
        + "</SrvGetUserOperationsByRoleRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvSearchRepRq() throws Exception {
    String requestMessage = "<SrvSearchRepRq><HeaderInfo/></SrvSearchRepRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetRepByCardRq() throws Exception {
    String requestMessage = "<SrvGetRepByCardRq><HeaderInfo/></SrvGetRepByCardRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_SrvGetOperatorInfoByUserRq() throws Exception {
    String requestMessage = "<SrvGetOperatorInfoByUserRq><HeaderInfo/>"
        + "<SrvGetOperatorInfoByUserRqMessage>"
        + "<userLogin>%s</userLogin>"
        + "</SrvGetOperatorInfoByUserRqMessage>"
        + "</SrvGetOperatorInfoByUserRq>";
    assertTrue(service.processMessage(String.format(requestMessage, "Ivanov_II")));
    assertTrue(service.processMessage(String.format(requestMessage, "Sidorov_SS")));
    assertTrue(service.processMessage(String.format(requestMessage, "Svetlova_SS")));
    assertTrue(service.processMessage(String.format(requestMessage, "Petrov_PP")));
  }

  @Test
  public void testProcessMessage_SrvCashSymbolsListRq() throws Exception {
    String requestMessage = "<SrvCashSymbolsListRq><HeaderInfo/></SrvCashSymbolsListRq>";
    assertTrue(service.processMessage(requestMessage));
  }

  @Test
  public void testProcessMessage_Other() throws Exception {
    String requestMessage = "Not valid xml";
    assertFalse(service.processMessage(requestMessage));
  }
}
