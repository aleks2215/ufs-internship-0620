package ru.philit.ufs.esb.mock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderStatusType;
import ru.philit.ufs.model.entity.esb.asfs.CashOrderType;
import ru.philit.ufs.model.entity.esb.asfs.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCheckOverLimitRs.SrvCheckOverLimitRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1;
import ru.philit.ufs.model.entity.esb.asfs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1.CashSymbols;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit;
import ru.philit.ufs.model.entity.esb.asfs.SrvGetWorkPlaceInfoRs.SrvGetWorkPlaceInfoRsMessage.WorkPlaceOperationTypeLimit.OperationTypeLimitItem;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.asfs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;


/**
 * Сервис на обработку запросов к АС_ФС.
 */
@Service
public class AsfsMockService extends CommonMockService implements MessageProcessor {

  private static final String CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.asfs";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final EsbClient esbClient;
  private final MockCache mockCache;

  private final JaxbConverter jaxbConverter = new JaxbConverter(CONTEXT_PATH);

  @Autowired
  public AsfsMockService(EsbClient esbClient, MockCache mockCache) {
    this.esbClient = esbClient;
    this.mockCache = mockCache;
  }

  @PostConstruct
  public void init() {
    esbClient.addMessageProcessor(this);
    logger.info("{} started", this.getClass().getSimpleName());
  }

  @Override
  public boolean processMessage(String requestMessage) {
    try {
      Object request = jaxbConverter.getObject(requestMessage);
      logger.debug("Received message: {}", request);
      if (request != null) {
        if (request instanceof SrvCreateCashOrderRq) {
          sendResponse(getResponse((SrvCreateCashOrderRq) request));

        } else if (request instanceof SrvUpdStCashOrderRq) {
          sendResponse(getResponse((SrvUpdStCashOrderRq) request));

        } else if (request instanceof SrvCheckOverLimitRq) {
          sendResponse(getResponse((SrvCheckOverLimitRq) request));

        } else if (request instanceof SrvGetWorkPlaceInfoRq) {
          sendResponse(getResponse((SrvGetWorkPlaceInfoRq) request));

        }
        return true;
      }
    } catch (JAXBException e) {
      // this message can not be processed this processor
      logger.trace("this message can not be processed this processor", e);
    }
    return false;
  }

  private void sendResponse(Object responseObject) throws JAXBException {
    String responseMessage = jaxbConverter.getXml(responseObject);
    esbClient.sendMessage(responseMessage);
  }

  private SrvCreateCashOrderRs getResponse(SrvCreateCashOrderRq request) {
    SrvCreateCashOrderRs response = new SrvCreateCashOrderRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCreateCashOrderRsMessage(new SrvCreateCashOrderRsMessage());
    response.getSrvCreateCashOrderRsMessage().setKO1(new KO1());
    response.getSrvCreateCashOrderRsMessage().getKO1().setResponseCode("0");
    response.getSrvCreateCashOrderRsMessage().getKO1().setResponseMsg("Created");
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderId("101");
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderINum("50");
    response.getSrvCreateCashOrderRsMessage().getKO1()
        .setCashOrderStatus(CashOrderStatusType.CREATED);
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashOrderType(CashOrderType.KO_1);
    response.getSrvCreateCashOrderRsMessage().getKO1()
        .setCreatedDttm(xmlCalendar(2020, 6, 24, 14, 52));
    response.getSrvCreateCashOrderRsMessage().getKO1().setOperationId("1");
    response.getSrvCreateCashOrderRsMessage().getKO1().setRepFIO("Петров Петр Петрович");
    response.getSrvCreateCashOrderRsMessage().getKO1().setLegalEntityShortName("CO");
    response.getSrvCreateCashOrderRsMessage().getKO1().setINN("4802");
    response.getSrvCreateCashOrderRsMessage().getKO1().setAmount(BigDecimal.valueOf(100000));
    response.getSrvCreateCashOrderRsMessage().getKO1().setAccountId("1001");
    CashSymbols cashSymbols = new CashSymbols();
    CashSymbols.CashSymbolItem cashSymbolItem = new CashSymbols.CashSymbolItem();
    cashSymbolItem.setCashSymbol("02");
    cashSymbolItem.setCashSymbolAmount(BigDecimal.valueOf(100000));
    cashSymbols.getCashSymbolItem().add(cashSymbolItem);
    response.getSrvCreateCashOrderRsMessage().getKO1().setCashSymbols(cashSymbols);
    response.getSrvCreateCashOrderRsMessage().getKO1().setSenderBank("ПАО \"Сбербанк\"");
    response.getSrvCreateCashOrderRsMessage().getKO1().setSenderBankBIC("044525225");
    response.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBank("ПАО \"Сбербанк\"");
    response.getSrvCreateCashOrderRsMessage().getKO1().setRecipientBankBIC("044525225");
    response.getSrvCreateCashOrderRsMessage().getKO1().setClientTypeFK(false);
    response.getSrvCreateCashOrderRsMessage().getKO1().setFDestLEName("ООО \"Два Альбатроса\"");
    response.getSrvCreateCashOrderRsMessage().getKO1().setOperatorPosition("Оператор зала");
    response.getSrvCreateCashOrderRsMessage().getKO1().setUserFullName("Иванов Иван Иванович");
    response.getSrvCreateCashOrderRsMessage().getKO1().setUserPosition("Программист");

    mockCache.createCashOrder(response, request.getSrvCreateCashOrderRqMessage()
        .getAdditionalInfo().getUserLogin());

    return response;
  }

  private SrvUpdStCashOrderRs getResponse(SrvUpdStCashOrderRq request) {
    SrvUpdStCashOrderRs response = new SrvUpdStCashOrderRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvUpdCashOrderRsMessage(new SrvUpdCashOrderRsMessage());
    response.getSrvUpdCashOrderRsMessage().setResponseCode("0");
    response.getSrvUpdCashOrderRsMessage().setResponseMsg("Committed");
    response.getSrvUpdCashOrderRsMessage().setCashOrderId("101");
    response.getSrvUpdCashOrderRsMessage().setCashOrderINum("55");
    response.getSrvUpdCashOrderRsMessage().setCashOrderStatus(CashOrderStatusType.COMMITTED);
    response.getSrvUpdCashOrderRsMessage().setCashOrderType(CashOrderType.KO_1);

    mockCache.updateStatusCashOrder(response);

    return response;
  }

  private SrvCheckOverLimitRs getResponse(SrvCheckOverLimitRq request) {
    SrvCheckOverLimitRs response = new SrvCheckOverLimitRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCheckOverLimitRsMessage(new SrvCheckOverLimitRsMessage());

    response.getSrvCheckOverLimitRsMessage().setResponseCode("1");
    response.getSrvCheckOverLimitRsMessage().setStatus(
        mockCache.checkCashOrdersLimitByUser(
            request.getSrvCheckOverLimitRqMessage().getUserLogin()));

    return response;
  }

  private SrvGetWorkPlaceInfoRs getResponse(SrvGetWorkPlaceInfoRq request) {
    SrvGetWorkPlaceInfoRs response = new SrvGetWorkPlaceInfoRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetWorkPlaceInfoRsMessage(new SrvGetWorkPlaceInfoRsMessage());
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceType(BigInteger.valueOf(1));
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxOnBoard(true);
    response.getSrvGetWorkPlaceInfoRsMessage().setSubbranchCode("1385930100");
    response.getSrvGetWorkPlaceInfoRsMessage()
        .setCashboxOnBoardDevice("530690F50B9E49A6B3EDAE2CF6B7CC4F");
    response.getSrvGetWorkPlaceInfoRsMessage().setCashboxDeviceType("CashierPro2520-sx");
    response.getSrvGetWorkPlaceInfoRsMessage().setCurrentCurrencyType("RUB");
    response.getSrvGetWorkPlaceInfoRsMessage().setAmount(new BigDecimal("100000.0"));
    response.getSrvGetWorkPlaceInfoRsMessage().setWorkPlaceLimit(new BigDecimal("500000.0"));

    WorkPlaceOperationTypeLimit workPlaceOperationTypeLimit = new WorkPlaceOperationTypeLimit();

    WorkPlaceOperationTypeLimit.OperationTypeLimitItem operationTypeLimitItem1
        = new OperationTypeLimitItem();
    operationTypeLimitItem1.setOperationCategory(BigInteger.valueOf(0));
    operationTypeLimitItem1.setOperationLimit(new BigDecimal("50000.0"));
    workPlaceOperationTypeLimit.getOperationTypeLimitItem().add(operationTypeLimitItem1);

    WorkPlaceOperationTypeLimit.OperationTypeLimitItem operationTypeLimitItem2
        = new OperationTypeLimitItem();
    operationTypeLimitItem2.setOperationCategory(BigInteger.valueOf(1));
    operationTypeLimitItem2.setOperationLimit(new BigDecimal("15000.0"));
    workPlaceOperationTypeLimit.getOperationTypeLimitItem().add(operationTypeLimitItem2);

    response.getSrvGetWorkPlaceInfoRsMessage()
        .setWorkPlaceOperationTypeLimit(workPlaceOperationTypeLimit);

    return response;
  }

  private HeaderInfoType copyHeaderInfo(HeaderInfoType headerInfo0) {
    HeaderInfoType headerInfo = new HeaderInfoType();
    headerInfo.setRqUID(headerInfo0.getRqUID());
    headerInfo.setRqTm(headerInfo0.getRqTm());
    headerInfo.setSpName(headerInfo0.getSystemId());
    headerInfo.setSystemId(headerInfo0.getSpName());
    return headerInfo;
  }
}
