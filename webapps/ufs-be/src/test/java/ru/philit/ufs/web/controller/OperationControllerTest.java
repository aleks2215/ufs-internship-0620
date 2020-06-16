package ru.philit.ufs.web.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.dto.CardDepositDto;
import ru.philit.ufs.web.mapping.OperationMapper;
import ru.philit.ufs.web.mapping.impl.OperationMapperImpl;
import ru.philit.ufs.web.provider.OperationProvider;
import ru.philit.ufs.web.view.AddTaskToPackageReq;
import ru.philit.ufs.web.view.AddTaskToPackageResp;
import ru.philit.ufs.web.view.FinishOperationReq;
import ru.philit.ufs.web.view.FinishOperationResp;

public class OperationControllerTest extends RestControllerTest {

  @Mock
  private OperationProvider provider;
  @Spy
  private OperationMapper mapper = new OperationMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new OperationController(provider, mapper));
  }

  @Test
  public void testContinueWithUrm() throws Exception {
    AddTaskToPackageReq request = new AddTaskToPackageReq();
    request.setWorkplaceId("123");
    request.setDeposit(new CardDepositDto());
    String requestJson = toRequest(request);

    when(provider.addActiveDepositTask(
        anyString(), any(OperationTaskCardDeposit.class), any(ClientInfo.class)
    )).thenReturn(new OperationPackage());

    String urlTemplate = "/operation/continueUrm";
    String responseJson = performAndGetContent(post(urlTemplate).content(requestJson));
    AddTaskToPackageResp response = toResponse(responseJson, AddTaskToPackageResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).addActiveDepositTask(
        anyString(), any(OperationTaskCardDeposit.class), any(ClientInfo.class)
    );
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testContinueWithCash() throws Exception {
    AddTaskToPackageReq request = new AddTaskToPackageReq();
    request.setWorkplaceId("123");
    request.setDeposit(new CardDepositDto());
    String requestJson = toRequest(request);

    when(provider.addForwardedDepositTask(
        anyString(), any(OperationTaskCardDeposit.class), any(ClientInfo.class)
    )).thenReturn(new OperationPackage());

    String urlTemplate = "/operation/continueCash";
    String responseJson = performAndGetContent(post(urlTemplate).content(requestJson));
    AddTaskToPackageResp response = toResponse(responseJson, AddTaskToPackageResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).addForwardedDepositTask(
        anyString(), any(OperationTaskCardDeposit.class), any(ClientInfo.class)
    );
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testConfirmOperation() throws Exception {
    FinishOperationReq request = new FinishOperationReq();
    request.setPackageId("1234");
    request.setTaskId("123");
    request.setWorkplaceId("12");
    request.setOperationTypeCode("ToCardDeposit");
    final String requestJson = toRequest(request);

    OperationPackage opPackage = new OperationPackage();
    OperationTask task1 = new OperationTask();
    task1.setId(122L);
    OperationTask task2 = new OperationTask();
    task2.setId(123L);
    opPackage.setToCardDeposits(Arrays.asList(task1, task2));

    when(provider.confirmOperation(
        anyLong(), anyLong(), anyString(), anyString(), any(ClientInfo.class)
    )).thenReturn(new Operation());

    String responseJson = performAndGetContent(post("/operation/confirm").content(requestJson));
    FinishOperationResp response = toResponse(responseJson, FinishOperationResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());

    verify(provider, times(1)).confirmOperation(
        anyLong(), anyLong(), anyString(), anyString(), any(ClientInfo.class)
    );
    verifyNoMoreInteractions(provider);
  }
}
