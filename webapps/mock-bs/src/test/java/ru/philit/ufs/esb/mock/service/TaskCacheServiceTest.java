package ru.philit.ufs.esb.mock.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import com.hazelcast.core.Hazelcast;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.philit.ufs.config.property.HazelcastServerProperties;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.cache.hazelcast.HazelcastMockCacheImpl;
import ru.philit.ufs.model.cache.hazelcast.HazelcastMockServer;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRs;

public class TaskCacheServiceTest {

  @Mock
  private EsbClient esbClient;
  private String sentMessage;

  private EksMockService service;

  private final JaxbConverter jaxbConverter =
      new JaxbConverter("ru.philit.ufs.model.entity.esb.eks");

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        sentMessage = (String) invocationOnMock.getArguments()[0];
        return null;
      }
    }).when(esbClient).sendMessage(anyString());

    HazelcastMockServer hazelcastMockServer = new HazelcastMockServer(getServerProperties());
    MockCache mockCache = new HazelcastMockCacheImpl(hazelcastMockServer);
    service = new EksMockService(esbClient, mockCache);
    hazelcastMockServer.init();
    service.init();
  }

  private HazelcastServerProperties getServerProperties() {
    HazelcastServerProperties properties = new HazelcastServerProperties();
    properties.getGroup().setName("taskGroup");
    properties.getGroup().setPassword("task");
    properties.getInstance().setHost("localhost");
    return properties;
  }

  @After
  public void tearDown() throws Exception {
    Hazelcast.shutdownAll();
  }

  @Test
  public void testGetTasks() throws JAXBException {
    // Create Packages

    final String ctrlRequestId = "111";
    final String ctrlSystem1Id = "eks";
    final String ctrlSystem2Id = "ufs";

    String requestMessage01 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvCreateClOperPkgRq><HeaderInfo><rqUID>" + ctrlRequestId + "</rqUID><spName>"
        + ctrlSystem1Id + "</spName><systemId>" + ctrlSystem2Id + "</systemId></HeaderInfo>"
        + "<SrvCreateClOperPkgRqMessage><workPlace_UId>222</workPlace_UId>"
        + "<INN>777000444999222</INN><userLogin>Sidorov_SS</userLogin>"
        + "</SrvCreateClOperPkgRqMessage></SrvCreateClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage01));
    Assert.assertNotNull(sentMessage);

    Object response01 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response01.getClass(), SrvCreateClOperPkgRs.class);
    Assert.assertEquals(((SrvCreateClOperPkgRs) response01).getHeaderInfo().getRqUID(),
        ctrlRequestId);
    Assert.assertEquals(((SrvCreateClOperPkgRs) response01).getHeaderInfo().getSpName(),
        ctrlSystem2Id);
    Assert.assertEquals(((SrvCreateClOperPkgRs) response01).getHeaderInfo().getSystemId(),
        ctrlSystem1Id);

    Assert.assertEquals(((SrvCreateClOperPkgRs) response01).getSrvCreateClOperPkgRsMessage()
        .getResponseCode(), BigInteger.valueOf(0));
    Long package1Id = ((SrvCreateClOperPkgRs) response01).getSrvCreateClOperPkgRsMessage()
        .getPkgId();
    Assert.assertNotNull(package1Id);

    String requestMessage02 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvCreateClOperPkgRq><HeaderInfo/><SrvCreateClOperPkgRqMessage>"
        + "<workPlace_UId>222</workPlace_UId><INN>777000444999333</INN><userLogin>Svetlova_SS"
        + "</userLogin></SrvCreateClOperPkgRqMessage></SrvCreateClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage02));
    Assert.assertNotNull(sentMessage);

    Object response02 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response02.getClass(), SrvCreateClOperPkgRs.class);
    Assert.assertEquals(((SrvCreateClOperPkgRs) response02).getSrvCreateClOperPkgRsMessage()
        .getResponseCode(), BigInteger.valueOf(0));
    Long package2Id = ((SrvCreateClOperPkgRs) response02).getSrvCreateClOperPkgRsMessage()
        .getPkgId();
    Assert.assertNotNull(package2Id);

    // Add Tasks

    String requestMessage11 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvAddTaskClOperPkgRq><HeaderInfo/><SrvAddTaskClOperPkgRqMessage>"
        + "<pkgId>" + package1Id + "</pkgId>"
        + "<ToCardDeposit><TaskItem><accountId>111111</accountId><amount>1500</amount>"
        + "<cardInfo><cardNumber>4276842958742581</cardNumber></cardInfo>"
        + "<pkgTaskStatus>Active</pkgTaskStatus></TaskItem></ToCardDeposit>"
        + "<FromCardWithdraw/><ToAccountDepositRub/><FromAccountWithdrawRub/><CheckbookIssuing/>"
        + "</SrvAddTaskClOperPkgRqMessage></SrvAddTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage11));
    Assert.assertNotNull(sentMessage);

    Object response11 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response11.getClass(), SrvAddTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvAddTaskClOperPkgRs) response11).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().size(), 1);
    Assert.assertEquals(((SrvAddTaskClOperPkgRs) response11).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getResponseCode(), BigInteger.valueOf(0));
    Long task1Id = ((SrvAddTaskClOperPkgRs) response11).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getPkgTaskId();
    Assert.assertNotNull(task1Id);

    String requestMessage12 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvAddTaskClOperPkgRq><HeaderInfo/><SrvAddTaskClOperPkgRqMessage>"
        + "<pkgId>" + package1Id + "</pkgId>"
        + "<ToCardDeposit><TaskItem><accountId>111112</accountId><amount>2000</amount>"
        + "<cardInfo><cardNumber>4276842958742582</cardNumber></cardInfo>"
        + "<pkgTaskStatus>OnApproval</pkgTaskStatus></TaskItem></ToCardDeposit>"
        + "<FromCardWithdraw/><ToAccountDepositRub/><FromAccountWithdrawRub/><CheckbookIssuing/>"
        + "</SrvAddTaskClOperPkgRqMessage></SrvAddTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage12));
    Assert.assertNotNull(sentMessage);

    Object response12 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response12.getClass(), SrvAddTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvAddTaskClOperPkgRs) response12).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().size(), 1);
    Assert.assertEquals(((SrvAddTaskClOperPkgRs) response12).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getResponseCode(), BigInteger.valueOf(0));
    Long task2Id = ((SrvAddTaskClOperPkgRs) response12).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getPkgTaskId();
    Assert.assertNotNull(task2Id);

    String requestMessage13 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvAddTaskClOperPkgRq><HeaderInfo/><SrvAddTaskClOperPkgRqMessage>"
        + "<pkgId>" + package2Id + "</pkgId>"
        + "<ToCardDeposit><TaskItem><accountId>111121</accountId><amount>3200</amount>"
        + "<cardInfo><cardNumber>4276842958742583</cardNumber></cardInfo>"
        + "<pkgTaskStatus>Active</pkgTaskStatus></TaskItem></ToCardDeposit>"
        + "<FromCardWithdraw/><ToAccountDepositRub/><FromAccountWithdrawRub/><CheckbookIssuing/>"
        + "</SrvAddTaskClOperPkgRqMessage></SrvAddTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage13));
    Assert.assertNotNull(sentMessage);

    Object response13 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response13.getClass(), SrvAddTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvAddTaskClOperPkgRs) response13).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().size(), 1);
    Assert.assertEquals(((SrvAddTaskClOperPkgRs) response13).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getResponseCode(), BigInteger.valueOf(0));
    Long task3Id = ((SrvAddTaskClOperPkgRs) response13).getSrvAddTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getPkgTaskId();
    Assert.assertNotNull(task3Id);

    // Upd Tasks

    String requestMessage21 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvUpdTaskClOperPkgRq><HeaderInfo/><SrvUpdTaskClOperPkgRqMessage>"
        + "<pkgId>" + package1Id + "</pkgId>"
        + "<ToCardDeposit><TaskItem><pkgTaskId>" + task1Id + "</pkgTaskId>"
        + "<accountId>111111</accountId><amount>1600</amount><INN>77665511</INN><KPP>4488226</KPP>"
        + "<cardInfo><cardNumber>4276842958742581</cardNumber></cardInfo>"
        + "<pkgTaskStatus>Active</pkgTaskStatus></TaskItem></ToCardDeposit>"
        + "<FromCardWithdraw/><ToAccountDepositRub/><FromAccountWithdrawRub/><CheckbookIssuing/>"
        + "</SrvUpdTaskClOperPkgRqMessage></SrvUpdTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage21));
    Assert.assertNotNull(sentMessage);

    Object response21 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response21.getClass(), SrvUpdTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvUpdTaskClOperPkgRs) response21).getSrvUpdTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().size(), 1);
    Assert.assertEquals(((SrvUpdTaskClOperPkgRs) response21).getSrvUpdTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getResponseCode(), BigInteger.valueOf(0));
    Long task21Id = ((SrvUpdTaskClOperPkgRs) response21).getSrvUpdTaskClOperPkgRsMessage()
        .getToCardDeposit().getTaskItem().get(0).getPkgTaskId();
    Assert.assertEquals(task1Id, task21Id);

    // Get Tasks

    String requestMessage31 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvGetTaskClOperPkgRq><HeaderInfo/><SrvGetTaskClOperPkgRqMessage>"
        + "<pkgId>" + package1Id + "</pkgId>"
        + "</SrvGetTaskClOperPkgRqMessage></SrvGetTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage31));
    Assert.assertNotNull(sentMessage);

    Object response31 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response31.getClass(), SrvGetTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().size(), 1);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getPkgId(), package1Id.longValue());
    Assert.assertNotNull(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getUserLogin());
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getToCardDeposit().getTaskItem().size(), 2);
    Assert.assertNotNull(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getToCardDeposit().getTaskItem().get(0).getPkgTaskId());
    Assert.assertNotNull(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getToCardDeposit().getTaskItem().get(0).getPkgTaskStatus());
    Assert.assertNotNull(((SrvGetTaskClOperPkgRs) response31).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getToCardDeposit().getTaskItem().get(0).getCreatedDttm());

    String requestMessage32 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvGetTaskClOperPkgRq><HeaderInfo/><SrvGetTaskClOperPkgRqMessage>"
        + "<taskStatusGlobal>Active</taskStatusGlobal>"
        + "</SrvGetTaskClOperPkgRqMessage></SrvGetTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage32));
    Assert.assertNotNull(sentMessage);

    Object response32 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response32.getClass(), SrvGetTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response32).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().size(), 2);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response32).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getToCardDeposit().getTaskItem().size(), 1);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response32).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(1).getToCardDeposit().getTaskItem().size(), 1);

    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String requestMessage33 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        + "<SrvGetTaskClOperPkgRq><HeaderInfo/><SrvGetTaskClOperPkgRqMessage>"
        + "<taskStatusGlobal>Active</taskStatusGlobal>"
        + "<dateFrom>" + currentDate + "</dateFrom><dateTo>" + currentDate + "</dateTo>"
        + "</SrvGetTaskClOperPkgRqMessage></SrvGetTaskClOperPkgRq>";
    Assert.assertTrue(service.processMessage(requestMessage33));
    Assert.assertNotNull(sentMessage);

    Object response33 = jaxbConverter.getObject(sentMessage);
    Assert.assertEquals(response33.getClass(), SrvGetTaskClOperPkgRs.class);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response33).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().size(), 2);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response33).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(0).getToCardDeposit().getTaskItem().size(), 1);
    Assert.assertEquals(((SrvGetTaskClOperPkgRs) response33).getSrvGetTaskClOperPkgRsMessage()
        .getPkgItem().get(1).getToCardDeposit().getTaskItem().size(), 1);
  }
}
