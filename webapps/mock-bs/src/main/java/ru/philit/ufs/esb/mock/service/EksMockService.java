package ru.philit.ufs.esb.mock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.eks.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.eks.OVNStatusType;
import ru.philit.ufs.model.entity.esb.eks.PaymentQueueType;
import ru.philit.ufs.model.entity.esb.eks.PkgStatusType;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs.SrvAccountByCardNumRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs.SrvAccountByIdMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs.SrvAccountResiduesByIdMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRq.SrvAddTaskClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs.SrvAddTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes1;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes1.CardIndex1Item;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs.SrvCardIndexElementsByAccountRsMessage.CardIndexes2.CardIndex2Item.PaymentOrderAmountPaidByDt;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs.SrvCheckClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs.SrvCheckWithFraudRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs.SrvCountCommissionRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs.SrvCreateClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRq.SrvGetTaskClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs.SrvLEAccountListRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs.SrvLegalEntityByAccountRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRq;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs.SrvSeizureByAccountRsMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs.SrvSeizureByAccountRsMessage.SrvSeizureByAccountItem;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRq;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRq.SrvUpdTaskClOperPkgRqMessage;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRs.SrvUpdTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

/**
 * Сервис на обработку запросов к ЕКС.
 */
@Service
public class EksMockService extends CommonMockService implements MessageProcessor {

  private static final String CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.eks";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final EsbClient esbClient;
  private final MockCache mockCache;

  private final JaxbConverter jaxbConverter = new JaxbConverter(CONTEXT_PATH);

  @Autowired
  public EksMockService(EsbClient esbClient, MockCache mockCache) {
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
        if (request instanceof SrvAccountByIdRq) {
          sendResponse(getResponse((SrvAccountByIdRq) request));

        } else if (request instanceof SrvAccountByCardNumRq) {
          sendResponse(getResponse((SrvAccountByCardNumRq) request));

        } else if (request instanceof SrvAccountResiduesByIdRq) {
          sendResponse(getResponse((SrvAccountResiduesByIdRq) request));

        } else if (request instanceof SrvLEAccountListRq) {
          sendResponse(getResponse((SrvLEAccountListRq) request));
          
        } else if (request instanceof SrvLegalEntityByAccountRq) {
          sendResponse(getResponse((SrvLegalEntityByAccountRq) request));

        } else if (request instanceof SrvCheckClOperPkgRq) {
          sendResponse(getResponse((SrvCheckClOperPkgRq) request));

        } else if (request instanceof SrvCreateClOperPkgRq) {
          sendResponse(getResponse((SrvCreateClOperPkgRq) request));

        } else if (request instanceof SrvAddTaskClOperPkgRq) {
          sendResponse(getResponse((SrvAddTaskClOperPkgRq) request));

        } else if (request instanceof SrvGetTaskClOperPkgRq) {
          sendResponse(getResponse((SrvGetTaskClOperPkgRq) request));

        } else if (request instanceof SrvUpdTaskClOperPkgRq) {
          sendResponse(getResponse((SrvUpdTaskClOperPkgRq) request));

        } else if (request instanceof SrvCardIndexElementsByAccountRq) {
          sendResponse(getResponse((SrvCardIndexElementsByAccountRq) request));

        } else if (request instanceof SrvSeizureByAccountRq) {
          sendResponse(getResponse((SrvSeizureByAccountRq) request));

        } else if (request instanceof SrvCountCommissionRq) {
          sendResponse(getResponse((SrvCountCommissionRq) request));

        } else if (request instanceof SrvCheckWithFraudRq) {
          sendResponse(getResponse((SrvCheckWithFraudRq) request));

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

  private SrvAccountByIdRs getResponse(SrvAccountByIdRq request) {
    SrvAccountByIdRs response = new SrvAccountByIdRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvAccountByIdMessage(new SrvAccountByIdMessage());
    response.getSrvAccountByIdMessage().setAccountId("40702810638050013199");
    response.getSrvAccountByIdMessage().setAccountStatus("Active");
    response.getSrvAccountByIdMessage().setAccountStatusDesc("Счет активен");
    response.getSrvAccountByIdMessage().setAccountTypeId("0");
    response.getSrvAccountByIdMessage().setAccountCurrencyType("RUB");
    response.getSrvAccountByIdMessage().setAccountCurrencyCode("643");
    response.getSrvAccountByIdMessage().setBankBIC("044525225");
    response.getSrvAccountByIdMessage().setAccountTitle("Счет клиента");
    response.getSrvAccountByIdMessage().setAccountancyTypeId(BigInteger.valueOf(2));
    response.getSrvAccountByIdMessage().setLastTransactionDt(xmlCalendar(2017, 5, 4, 0, 0));
    SrvAccountByIdMessage.AgreementInfo agreementInfo =
        new SrvAccountByIdMessage.AgreementInfo();
    agreementInfo.setAgreementId("8941651ТРК651-НЭ");
    agreementInfo.setDateFrom(xmlCalendar(2016, 8, 13, 0, 0));
    response.getSrvAccountByIdMessage().setAgreementInfo(agreementInfo);
    SrvAccountByIdMessage.BankInfo bankInfo = new SrvAccountByIdMessage.BankInfo();
    bankInfo.setBankBIC("044525225");
    bankInfo.setCorrespondentAccount("30845214660004854009");
    bankInfo.setBankName("Дивногорское отделение");
    bankInfo.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo.setLocationType("type3");
    bankInfo.setTBCode("13");
    bankInfo.setGOSBCode("8593");
    bankInfo.setVSPCode("1385930100");
    bankInfo.setSubbranchCode("1385930100");
    response.getSrvAccountByIdMessage().setBankInfo(bankInfo);
    SrvAccountByIdMessage.AccountIdentity accountIdentity =
        new SrvAccountByIdMessage.AccountIdentity();
    accountIdentity.setID("112255");
    accountIdentity.setIDtype("Unknown identity");
    response.getSrvAccountByIdMessage().setAccountIdentity(accountIdentity);
    return response;
  }

  private SrvAccountByCardNumRs getResponse(SrvAccountByCardNumRq request) {
    SrvAccountByCardNumRs response = new SrvAccountByCardNumRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvAccountByCardNumRsMessage(new SrvAccountByCardNumRsMessage());
    response.getSrvAccountByCardNumRsMessage().setAccountId("40702810638050013199");
    response.getSrvAccountByCardNumRsMessage().setAccountStatus("Active");
    response.getSrvAccountByCardNumRsMessage().setAccountStatusDesc("Счет активен");
    response.getSrvAccountByCardNumRsMessage().setAccountTypeId("0");
    response.getSrvAccountByCardNumRsMessage().setAccountCurrencyType("RUB");
    response.getSrvAccountByCardNumRsMessage().setAccountCurrencyCode("643");
    response.getSrvAccountByCardNumRsMessage().setBankBIC("044525225");
    response.getSrvAccountByCardNumRsMessage().setAccountTitle("Счет клиента");
    response.getSrvAccountByCardNumRsMessage().setAccountancyTypeId(BigInteger.valueOf(2));
    response.getSrvAccountByCardNumRsMessage().setLastTransactionDt(xmlCalendar(2017, 5, 4, 0, 0));
    SrvAccountByCardNumRsMessage.AgreementInfo agreementInfo =
        new SrvAccountByCardNumRsMessage.AgreementInfo();
    agreementInfo.setAgreementId("8941651ТРК651-НЭ");
    agreementInfo.setDateFrom(xmlCalendar(2016, 8, 13, 0, 0));
    response.getSrvAccountByCardNumRsMessage().setAgreementInfo(agreementInfo);
    SrvAccountByCardNumRsMessage.BankInfo bankInfo = new SrvAccountByCardNumRsMessage.BankInfo();
    bankInfo.setBankBIC("044525225");
    bankInfo.setCorrespondentAccount("30845214660004854009");
    bankInfo.setBankName("Дивногорское отделение");
    bankInfo.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo.setLocationType("type3");
    bankInfo.setTBCode("13");
    bankInfo.setGOSBCode("8593");
    bankInfo.setVSPCode("1385930100");
    bankInfo.setSubbranchCode("1385930100");
    response.getSrvAccountByCardNumRsMessage().setBankInfo(bankInfo);
    SrvAccountByCardNumRsMessage.AccountIdentity accountIdentity =
        new SrvAccountByCardNumRsMessage.AccountIdentity();
    accountIdentity.setID("112255");
    accountIdentity.setIDtype("Unknown identity");
    response.getSrvAccountByCardNumRsMessage().setAccountIdentity(accountIdentity);
    return response;
  }

  private SrvAccountResiduesByIdRs getResponse(SrvAccountResiduesByIdRq request) {
    SrvAccountResiduesByIdRs response = new SrvAccountResiduesByIdRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvAccountResiduesByIdMessage(new SrvAccountResiduesByIdMessage());
    response.getSrvAccountResiduesByIdMessage().setAccountId("40702810638050013199");
    response.getSrvAccountResiduesByIdMessage().setBankBIC("044525225");
    response.getSrvAccountResiduesByIdMessage().setOperationalDate(xmlCalendar(2017, 5, 29, 0, 0));
    response.getSrvAccountResiduesByIdMessage().setCurrentAvailBalance(
        new SrvAccountResiduesByIdMessage.CurrentAvailBalance());
    response.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setOpeningBalanceRub(BigDecimal.valueOf(50000));
    response.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setOpeningBalanceFcurr(BigDecimal.valueOf(50000));
    response.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setClosingBalanceRub(BigDecimal.valueOf(50000));
    response.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setClosingBalanceFcurr(BigDecimal.valueOf(50000));
    response.getSrvAccountResiduesByIdMessage().getCurrentAvailBalance()
        .setCurrentBalance(BigDecimal.valueOf(50000));
    response.getSrvAccountResiduesByIdMessage().setFixedBalance(BigDecimal.valueOf(10000));
    response.getSrvAccountResiduesByIdMessage().setExpectedBalanceInfo(
        new SrvAccountResiduesByIdMessage.ExpectedBalanceInfo());
    response.getSrvAccountResiduesByIdMessage().getExpectedBalanceInfo()
        .setExpectedBalance(BigDecimal.valueOf(45000));
    response.getSrvAccountResiduesByIdMessage().getExpectedBalanceInfo()
        .setBalanceDif(BigDecimal.valueOf(5000));
    response.getSrvAccountResiduesByIdMessage().setAvailOverdraftLimit(BigDecimal.valueOf(5000));
    response.getSrvAccountResiduesByIdMessage().setOverdraftLimit(BigDecimal.valueOf(2000));
    response.getSrvAccountResiduesByIdMessage().setAvailDebetLimit(BigDecimal.valueOf(3000));
    response.getSrvAccountResiduesByIdMessage().setSeizureInfo(
        new SrvAccountResiduesByIdMessage.SeizureInfo());
    response.getSrvAccountResiduesByIdMessage().getSeizureInfo().setSeizureFlg(true);
    response.getSrvAccountResiduesByIdMessage().getSeizureInfo().setSeizNum(BigInteger.valueOf(2));
    response.getSrvAccountResiduesByIdMessage().setCardIndexesInfo(
        new SrvAccountResiduesByIdMessage.CardIndexesInfo());
    response.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex1Flg(true);
    response.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex1DocNum(
        BigInteger.valueOf(1));
    response.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex2Flg(true);
    response.getSrvAccountResiduesByIdMessage().getCardIndexesInfo().setCardindex2DocNum(
        BigInteger.valueOf(2));
    response.getSrvAccountResiduesByIdMessage().setCardindexesAmount(
        new SrvAccountResiduesByIdMessage.CardindexesAmount());
    response.getSrvAccountResiduesByIdMessage().getCardindexesAmount().setCardindex1Total(
        BigInteger.valueOf(84520));
    response.getSrvAccountResiduesByIdMessage().getCardindexesAmount().setCardindex2Total(
        BigInteger.valueOf(1500000));
    response.getSrvAccountResiduesByIdMessage().setOpenStatusInfo(
        new SrvAccountResiduesByIdMessage.OpenStatusInfo());
    response.getSrvAccountResiduesByIdMessage().getOpenStatusInfo().setActiveFlg(true);
    response.getSrvAccountResiduesByIdMessage().getOpenStatusInfo().setDateOpen(
        xmlCalendar(2017, 5, 1, 0, 0));
    response.getSrvAccountResiduesByIdMessage().getOpenStatusInfo().setDateClose(
        xmlCalendar(2017, 7, 1, 0, 0));
    response.getSrvAccountResiduesByIdMessage().setTargetLoanAmount(BigDecimal.valueOf(4000));
    response.getSrvAccountResiduesByIdMessage().setLoanRepaymentAmount(BigDecimal.valueOf(6000));
    response.getSrvAccountResiduesByIdMessage().setAdditionalAgreements(
        new SrvAccountResiduesByIdMessage.AdditionalAgreements());
    response.getSrvAccountResiduesByIdMessage().getAdditionalAgreements()
        .setAgreementId("8941651ТРК652-НЭ");
    response.getSrvAccountResiduesByIdMessage().getAdditionalAgreements().setDateFrom(
        xmlCalendar(2017, 5, 1, 0, 0));
    response.getSrvAccountResiduesByIdMessage().getAdditionalAgreements().setDateTo(
        xmlCalendar(2017, 6, 1, 0, 0));
    response.getSrvAccountResiduesByIdMessage().setServiceTarificationInfo(
        new SrvAccountResiduesByIdMessage.ServiceTarificationInfo());
    SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service service1 =
        new SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service();
    service1.setServiceCode("0853");
    service1.setServiceDesc("Service1");
    service1.setPercentage(4);
    response.getSrvAccountResiduesByIdMessage().getServiceTarificationInfo().getService()
        .add(service1);
    SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service service2 =
        new SrvAccountResiduesByIdMessage.ServiceTarificationInfo.Service();
    service2.setServiceCode("0824");
    service2.setServiceDesc("Service2");
    service2.setFixed(BigDecimal.valueOf(250));
    response.getSrvAccountResiduesByIdMessage().getServiceTarificationInfo().getService()
        .add(service2);
    response.getSrvAccountResiduesByIdMessage().setCompProdMemberFlg(false);
    response.getSrvAccountResiduesByIdMessage().setAccountCurrencyType("RUB");
    response.getSrvAccountResiduesByIdMessage().setAccountCurrencyCode("643");
    response.getSrvAccountResiduesByIdMessage().setResiduesDttm(xmlCalendar(2017, 5, 29, 15, 50));
    return response;
  }

  private SrvLEAccountListRs getResponse(SrvLEAccountListRq request) {
    SrvLEAccountListRs response = new SrvLEAccountListRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvLEAccountListRsMessage(new SrvLEAccountListRsMessage());
    SrvLEAccountListRsMessage.AccountItem accountItem = new SrvLEAccountListRsMessage.AccountItem();
    accountItem.setAccountId("40702810638050013199");
    accountItem.setAccountStatus("Active");
    accountItem.setAccountStatusDesc("Счет активен");
    accountItem.setAccountTypeId("0");
    accountItem.setAccountCurrencyType("RUB");
    accountItem.setAccountCurrencyCode("643");
    accountItem.setBankBIC("044525225");
    accountItem.setAccountTitle("Счет клиента");
    accountItem.setAccountancyTypeId(BigInteger.valueOf(2));
    accountItem.setLastTransactionDt(xmlCalendar(2017, 5, 4, 0, 0));
    SrvLEAccountListRsMessage.AccountItem.AgreementInfo agreementInfo =
        new SrvLEAccountListRsMessage.AccountItem.AgreementInfo();
    agreementInfo.setAgreementId("8941651ТРК651-НЭ");
    agreementInfo.setDateFrom(xmlCalendar(2016, 8, 13, 0, 0));
    accountItem.setAgreementInfo(agreementInfo);
    SrvLEAccountListRsMessage.AccountItem.BankInfo bankInfo =
        new SrvLEAccountListRsMessage.AccountItem.BankInfo();
    bankInfo.setBankBIC("044525225");
    bankInfo.setCorrespondentAccount("30845214660004854009");
    bankInfo.setBankName("Дивногорское отделение");
    bankInfo.setLocationTitle("Москва, ул. Академика Янгеля, д. 13 к. 3");
    bankInfo.setLocationType("type3");
    bankInfo.setTBCode("13");
    bankInfo.setGOSBCode("8593");
    bankInfo.setVSPCode("1385930100");
    bankInfo.setSubbranchCode("1385930100");
    accountItem.setBankInfo(bankInfo);
    SrvLEAccountListRsMessage.AccountItem.AccountIdentity accountIdentity =
        new SrvLEAccountListRsMessage.AccountItem.AccountIdentity();
    accountIdentity.setID("112255");
    accountIdentity.setIDtype("Unknown identity");
    accountItem.setAccountIdentity(accountIdentity);
    response.getSrvLEAccountListRsMessage().getAccountItem().add(accountItem);
    return response;
  }
  
  private SrvLegalEntityByAccountRs getResponse(SrvLegalEntityByAccountRq request) {
    SrvLegalEntityByAccountRs response = new SrvLegalEntityByAccountRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvLegalEntityByAccountRsMessage(new SrvLegalEntityByAccountRsMessage());
    response.getSrvLegalEntityByAccountRsMessage().setLegalEntityId(
        "8FF9ACBDF006486CAE88109BBD7A8680");
    response.getSrvLegalEntityByAccountRsMessage().setLegalEntityShortName("ООО \"ДНК\"");
    response.getSrvLegalEntityByAccountRsMessage().setLegalEntityFullName(
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    response.getSrvLegalEntityByAccountRsMessage().setINN("0278000222");
    response.getSrvLegalEntityByAccountRsMessage().setOGRN("1030224552966");
    response.getSrvLegalEntityByAccountRsMessage().setKPP("027811001");
    response.getSrvLegalEntityByAccountRsMessage().setLegalAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    response.getSrvLegalEntityByAccountRsMessage().setFactAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    return response;
  }

  private SrvCheckClOperPkgRs getResponse(SrvCheckClOperPkgRq request) {
    SrvCheckClOperPkgRs response = new SrvCheckClOperPkgRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCheckClOperPkgRsMessage(new SrvCheckClOperPkgRsMessage());
    Long packageId = mockCache.checkPackage(request.getSrvCheckClOperPkgRqMessage().getINN());
    response.getSrvCheckClOperPkgRsMessage().setPkgExistsFlg(packageId != null);
    response.getSrvCheckClOperPkgRsMessage().setPkgId(packageId);
    response.getSrvCheckClOperPkgRsMessage().setPkgStatus(PkgStatusType.NEW);
    return response;
  }

  private SrvCreateClOperPkgRs getResponse(SrvCreateClOperPkgRq request) {
    SrvCreateClOperPkgRs response = new SrvCreateClOperPkgRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCreateClOperPkgRsMessage(new SrvCreateClOperPkgRsMessage());
    OperationPackageInfo packageInfo = mockCache.createPackage(
        request.getSrvCreateClOperPkgRqMessage().getINN(),
        request.getSrvCreateClOperPkgRqMessage().getWorkPlaceUId(),
        request.getSrvCreateClOperPkgRqMessage().getUserLogin());
    response.getSrvCreateClOperPkgRsMessage().setResponseCode(BigInteger.valueOf(0));
    response.getSrvCreateClOperPkgRsMessage().setPkgId(packageInfo.getId());
    response.getSrvCreateClOperPkgRsMessage().setPkgStatus(packageInfo.getStatus());
    return response;
  }

  private SrvAddTaskClOperPkgRs getResponse(SrvAddTaskClOperPkgRq request) {
    Long packageId = request.getSrvAddTaskClOperPkgRqMessage().getPkgId();
    SrvAddTaskClOperPkgRs response = new SrvAddTaskClOperPkgRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvAddTaskClOperPkgRsMessage(new SrvAddTaskClOperPkgRsMessage());
    response.getSrvAddTaskClOperPkgRsMessage().setPkgId(packageId);
    response.getSrvAddTaskClOperPkgRsMessage()
        .setToCardDeposit(new SrvAddTaskClOperPkgRsMessage.ToCardDeposit());
    response.getSrvAddTaskClOperPkgRsMessage()
        .setFromCardWithdraw(new SrvAddTaskClOperPkgRsMessage.FromCardWithdraw());
    response.getSrvAddTaskClOperPkgRsMessage()
        .setToAccountDepositRub(new SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub());
    response.getSrvAddTaskClOperPkgRsMessage()
        .setFromAccountWithdrawRub(new SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub());
    response.getSrvAddTaskClOperPkgRsMessage()
        .setCheckbookIssuing(new SrvAddTaskClOperPkgRsMessage.CheckbookIssuing());
    
    for (SrvAddTaskClOperPkgRqMessage.ToCardDeposit.TaskItem task :
        request.getSrvAddTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()) {
      if (task.getCreatedDttm() == null) {
        task.setCreatedDttm(xmlCalendar(new Date()));
      }
      if (task.getPkgTaskStatus() == null) {
        task.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
      }
      if (StringUtils.isEmpty(task.getOVNUId())) {
        task.setOVNUId("FD1D833A431F498DB556E91778C8" + (long)(Math.random() * 10000));
      }
      if (task.getOVNNum() == null) {
        task.setOVNNum(BigInteger.valueOf(1));
      }
      if (task.getOVNStatus() == null) {
        task.setOVNStatus(OVNStatusType.PENDING);
      }
      Long taskId = mockCache.saveTaskCardDeposit(packageId, null, task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvAddTaskClOperPkgRsMessage.ToCardDeposit.TaskItem taskItem =
          new SrvAddTaskClOperPkgRsMessage.ToCardDeposit.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvAddTaskClOperPkgRsMessage().getToCardDeposit().getTaskItem().add(taskItem);
    }

    for (SrvAddTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem task :
        request.getSrvAddTaskClOperPkgRqMessage().getFromCardWithdraw().getTaskItem()) {
      if (task.getCreatedDttm() == null) {
        task.setCreatedDttm(xmlCalendar(new Date()));
      }
      if (task.getPkgTaskStatus() == null) {
        task.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
      }
      Long taskId = mockCache.saveTaskCardWithdraw(packageId, null, task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvAddTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem taskItem =
          new SrvAddTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvAddTaskClOperPkgRsMessage().getFromCardWithdraw().getTaskItem().add(taskItem);
    }

    for (SrvAddTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem task :
        request.getSrvAddTaskClOperPkgRqMessage().getToAccountDepositRub().getTaskItem()) {
      if (task.getCreatedDttm() == null) {
        task.setCreatedDttm(xmlCalendar(new Date()));
      }
      if (task.getPkgTaskStatus() == null) {
        task.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
      }
      Long taskId = mockCache.saveTaskAccountDeposit(packageId, null, task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem taskItem =
          new SrvAddTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvAddTaskClOperPkgRsMessage().getToAccountDepositRub().getTaskItem()
          .add(taskItem);
    }

    for (SrvAddTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem task :
        request.getSrvAddTaskClOperPkgRqMessage().getFromAccountWithdrawRub().getTaskItem()) {
      if (task.getCreatedDttm() == null) {
        task.setCreatedDttm(xmlCalendar(new Date()));
      }
      if (task.getPkgTaskStatus() == null) {
        task.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
      }
      Long taskId = mockCache.saveTaskAccountWithdraw(packageId, null, task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem taskItem =
          new SrvAddTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvAddTaskClOperPkgRsMessage().getFromAccountWithdrawRub().getTaskItem()
          .add(taskItem);
    }

    for (SrvAddTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem task :
        request.getSrvAddTaskClOperPkgRqMessage().getCheckbookIssuing().getTaskItem()) {
      if (task.getCreatedDttm() == null) {
        task.setCreatedDttm(xmlCalendar(new Date()));
      }
      if (task.getPkgTaskStatus() == null) {
        task.setPkgTaskStatus(PkgTaskStatusType.ACTIVE);
      }
      Long taskId = mockCache.saveTaskCheckbookIssuing(packageId, null, task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvAddTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem taskItem =
          new SrvAddTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvAddTaskClOperPkgRsMessage().getCheckbookIssuing().getTaskItem().add(taskItem);
    }
    
    return response;
  }

  private SrvGetTaskClOperPkgRs getResponse(SrvGetTaskClOperPkgRq request) {
    List<Long> requestTaskIds = null;
    if (!request.getSrvGetTaskClOperPkgRqMessage().getTaskType().isEmpty()) {
      requestTaskIds = new ArrayList<>();
      for (SrvGetTaskClOperPkgRqMessage.TaskType taskType :
          request.getSrvGetTaskClOperPkgRqMessage().getTaskType()) {
        requestTaskIds.add(taskType.getPkgTaskId());
      }
    }
    Map<Long, List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>> resultCardDeposit =
        mockCache.searchTasksCardDeposit(request.getSrvGetTaskClOperPkgRqMessage().getPkgId(),
            request.getSrvGetTaskClOperPkgRqMessage().getTaskStatusGlobal(),
            date(request.getSrvGetTaskClOperPkgRqMessage().getDateFrom()),
            date(request.getSrvGetTaskClOperPkgRqMessage().getDateTo()), requestTaskIds);

    SrvGetTaskClOperPkgRs response = new SrvGetTaskClOperPkgRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetTaskClOperPkgRsMessage(new SrvGetTaskClOperPkgRsMessage());

    for (Long packageId : resultCardDeposit.keySet()) {
      OperationPackageInfo packageInfo = mockCache.getPackageInfo(packageId);
      SrvGetTaskClOperPkgRsMessage.PkgItem pakage = new SrvGetTaskClOperPkgRsMessage.PkgItem();
      pakage.setPkgId(packageId);
      pakage.setUserLogin(packageInfo.getUserLogin());
      pakage.setToCardDeposit(new SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit());
      for (SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem task :
          resultCardDeposit.get(packageId)) {
        pakage.getToCardDeposit().getTaskItem().add(task);
      }
      response.getSrvGetTaskClOperPkgRsMessage().getPkgItem().add(pakage);
    }

    return response;
  }

  private SrvUpdTaskClOperPkgRs getResponse(SrvUpdTaskClOperPkgRq request) {
    Long packageId = request.getSrvUpdTaskClOperPkgRqMessage().getPkgId();
    SrvUpdTaskClOperPkgRs response = new SrvUpdTaskClOperPkgRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvUpdTaskClOperPkgRsMessage(new SrvUpdTaskClOperPkgRsMessage());
    response.getSrvUpdTaskClOperPkgRsMessage().setPkgId(packageId);
    response.getSrvUpdTaskClOperPkgRsMessage()
        .setToCardDeposit(new SrvUpdTaskClOperPkgRsMessage.ToCardDeposit());
    response.getSrvUpdTaskClOperPkgRsMessage()
        .setFromCardWithdraw(new SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw());
    response.getSrvUpdTaskClOperPkgRsMessage()
        .setToAccountDepositRub(new SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub());
    response.getSrvUpdTaskClOperPkgRsMessage()
        .setFromAccountWithdrawRub(new SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub());
    response.getSrvUpdTaskClOperPkgRsMessage()
        .setCheckbookIssuing(new SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing());

    for (SrvUpdTaskClOperPkgRqMessage.ToCardDeposit.TaskItem task :
        request.getSrvUpdTaskClOperPkgRqMessage().getToCardDeposit().getTaskItem()) {
      Long taskId = mockCache.saveTaskCardDeposit(packageId, task.getPkgTaskId(), task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvUpdTaskClOperPkgRsMessage.ToCardDeposit.TaskItem taskItem =
          new SrvUpdTaskClOperPkgRsMessage.ToCardDeposit.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvUpdTaskClOperPkgRsMessage().getToCardDeposit().getTaskItem().add(taskItem);
    }

    for (SrvUpdTaskClOperPkgRqMessage.FromCardWithdraw.TaskItem task :
        request.getSrvUpdTaskClOperPkgRqMessage().getFromCardWithdraw().getTaskItem()) {
      Long taskId = mockCache.saveTaskCardWithdraw(packageId, task.getPkgTaskId(), task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem taskItem =
          new SrvUpdTaskClOperPkgRsMessage.FromCardWithdraw.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvUpdTaskClOperPkgRsMessage().getFromCardWithdraw().getTaskItem().add(taskItem);
    }

    for (SrvUpdTaskClOperPkgRqMessage.ToAccountDepositRub.TaskItem task :
        request.getSrvUpdTaskClOperPkgRqMessage().getToAccountDepositRub().getTaskItem()) {
      Long taskId = mockCache.saveTaskAccountDeposit(packageId, task.getPkgTaskId(), task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem taskItem =
          new SrvUpdTaskClOperPkgRsMessage.ToAccountDepositRub.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvUpdTaskClOperPkgRsMessage().getToAccountDepositRub().getTaskItem()
          .add(taskItem);
    }

    for (SrvUpdTaskClOperPkgRqMessage.FromAccountWithdrawRub.TaskItem task :
        request.getSrvUpdTaskClOperPkgRqMessage().getFromAccountWithdrawRub().getTaskItem()) {
      Long taskId = mockCache.saveTaskAccountWithdraw(packageId, task.getPkgTaskId(), task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem taskItem =
          new SrvUpdTaskClOperPkgRsMessage.FromAccountWithdrawRub.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvUpdTaskClOperPkgRsMessage().getFromAccountWithdrawRub().getTaskItem()
          .add(taskItem);
    }

    for (SrvUpdTaskClOperPkgRqMessage.CheckbookIssuing.TaskItem task :
        request.getSrvUpdTaskClOperPkgRqMessage().getCheckbookIssuing().getTaskItem()) {
      Long taskId = mockCache.saveTaskCheckbookIssuing(packageId, task.getPkgTaskId(), task);
      mockCache.saveTaskStatus(taskId, task.getPkgTaskStatus());
      SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem taskItem =
          new SrvUpdTaskClOperPkgRsMessage.CheckbookIssuing.TaskItem();
      taskItem.setPkgTaskId(taskId);
      taskItem.setResponseCode(BigInteger.valueOf(0));
      response.getSrvUpdTaskClOperPkgRsMessage().getCheckbookIssuing().getTaskItem().add(taskItem);
    }

    return response;
  }

  private SrvCardIndexElementsByAccountRs getResponse(SrvCardIndexElementsByAccountRq request) {
    SrvCardIndexElementsByAccountRs response = new SrvCardIndexElementsByAccountRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCardIndexElementsByAccountRsMessage(
        new SrvCardIndexElementsByAccountRsMessage());

    response.getSrvCardIndexElementsByAccountRsMessage().setCardIndexes1(new CardIndexes1());

    CardIndex1Item cardIndex1Item1 = new CardIndex1Item();
    cardIndex1Item1.setDocSeqId(BigInteger.valueOf(0));
    cardIndex1Item1.setPaymentOrderDocId("98710245");
    cardIndex1Item1.setPaymentOrderDocType("Платежое поручение");
    cardIndex1Item1.setRecipientLegEntShortName("ООО \"Клиент-сервис100\"");
    cardIndex1Item1.setPaymentOrderAmount(BigDecimal.valueOf(500.0));
    cardIndex1Item1.setComeInDate(xmlCalendar(2017, 5, 1, 0, 0));
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes1().getCardIndex1Item()
        .add(cardIndex1Item1);

    CardIndex1Item cardIndex1Item2 = new CardIndex1Item();
    cardIndex1Item2.setDocSeqId(BigInteger.valueOf(1));
    cardIndex1Item2.setPaymentOrderDocId("68768123");
    cardIndex1Item2.setPaymentOrderDocType("Платежое поручение");
    cardIndex1Item2.setRecipientLegEntShortName("ООО \"Новый свет\"");
    cardIndex1Item2.setPaymentOrderAmount(BigDecimal.valueOf(1450.0));
    cardIndex1Item2.setComeInDate(xmlCalendar(2017, 4, 25, 0, 0));
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes1().getCardIndex1Item()
        .add(cardIndex1Item2);

    CardIndex1Item cardIndex1Item3 = new CardIndex1Item();
    cardIndex1Item3.setDocSeqId(BigInteger.valueOf(2));
    cardIndex1Item3.setPaymentOrderDocId("00654687");
    cardIndex1Item3.setPaymentOrderDocType("Платежое поручение");
    cardIndex1Item3.setRecipientLegEntShortName("ООО \"Авангард-авто\"");
    cardIndex1Item3.setPaymentOrderAmount(BigDecimal.valueOf(950.0));
    cardIndex1Item3.setComeInDate(xmlCalendar(2017, 4, 23, 0, 0));
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes1().getCardIndex1Item()
        .add(cardIndex1Item3);

    response.getSrvCardIndexElementsByAccountRsMessage().setCardIndexes2(new CardIndexes2());

    CardIndex2Item cardIndex2Item1 = new CardIndex2Item();
    cardIndex2Item1.setDocSeqId(BigInteger.valueOf(0));
    cardIndex2Item1.setPaymentOrderDocId("99712379");
    cardIndex2Item1.setPaymentOrderDocType("Платежное поручение");
    cardIndex2Item1.setRecipientLegEntShortName("ПАО \"Спринтсервис\"");
    cardIndex2Item1.setPaymentOrderAmountTotal(BigDecimal.valueOf(500000.0));
    cardIndex2Item1.setPaymentOrderPaidPartly(true);
    cardIndex2Item1.setPaymentOrderAmountPaid(BigDecimal.valueOf(150000.0));
    cardIndex2Item1.setComeInDate(xmlCalendar(2017, 4, 23, 0, 0));
    cardIndex2Item1.setQueuePriority(PaymentQueueType.II);
    cardIndex2Item1.setPaymentOrderAmountPaidByDt(new PaymentOrderAmountPaidByDt());
    PaymentOrderAmountPaidByDt.PaidByDtItem paidByDtItem11 =
        new PaymentOrderAmountPaidByDt.PaidByDtItem();
    paidByDtItem11.setPaidAmount(BigDecimal.valueOf(50000.0));
    paidByDtItem11.setPaidDt(xmlCalendar(2017, 1, 1, 0, 0));
    cardIndex2Item1.getPaymentOrderAmountPaidByDt().getPaidByDtItem().add(paidByDtItem11);
    PaymentOrderAmountPaidByDt.PaidByDtItem paidByDtItem12 =
        new PaymentOrderAmountPaidByDt.PaidByDtItem();
    paidByDtItem12.setPaidAmount(BigDecimal.valueOf(100000.0));
    paidByDtItem12.setPaidDt(xmlCalendar(2017, 1, 15, 0, 0));
    cardIndex2Item1.getPaymentOrderAmountPaidByDt().getPaidByDtItem().add(paidByDtItem12);
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes2().getCardIndex2Item()
        .add(cardIndex2Item1);

    CardIndex2Item cardIndex2Item2 = new CardIndex2Item();
    cardIndex2Item2.setDocSeqId(BigInteger.valueOf(1));
    cardIndex2Item2.setPaymentOrderDocId("68451553");
    cardIndex2Item2.setPaymentOrderDocType("Платежное поручение");
    cardIndex2Item2.setRecipientLegEntShortName("ПАО \"Спринтсервис\"");
    cardIndex2Item2.setPaymentOrderAmountTotal(BigDecimal.valueOf(1000000.0));
    cardIndex2Item2.setPaymentOrderPaidPartly(false);
    cardIndex2Item2.setComeInDate(xmlCalendar(2017, 4, 22, 0, 0));
    cardIndex2Item2.setQueuePriority(PaymentQueueType.III);
    cardIndex2Item2.setPaymentOrderAmountPaidByDt(new PaymentOrderAmountPaidByDt());
    response.getSrvCardIndexElementsByAccountRsMessage().getCardIndexes2().getCardIndex2Item()
        .add(cardIndex2Item2);
    return response;
  }

  private SrvSeizureByAccountRs getResponse(SrvSeizureByAccountRq request) {
    SrvSeizureByAccountRs response = new SrvSeizureByAccountRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvSeizureByAccountRsMessage(new SrvSeizureByAccountRsMessage());
    SrvSeizureByAccountItem seizure1 = new SrvSeizureByAccountItem();
    seizure1.setSeizureSequenceId(BigInteger.valueOf(0));
    seizure1.setSeizureType(BigInteger.valueOf(0));
    seizure1.setSeizureReasonDesc("Судебное постановление №4283 от 1.01.2017");
    seizure1.setDateFrom(xmlCalendar(2017, 1, 1, 0, 0));
    seizure1.setDateTo(xmlCalendar(2018, 1, 1, 0, 0));
    seizure1.setAmount(BigDecimal.valueOf(2000.0));
    seizure1.setInitiatorShortName("ООО \"НТЦРАН\"");
    response.getSrvSeizureByAccountRsMessage().getSrvSeizureByAccountItem().add(seizure1);
    return response;
  }

  private SrvCountCommissionRs getResponse(SrvCountCommissionRq request) {
    SrvCountCommissionRs response = new SrvCountCommissionRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCountCommissionRsMessage(new SrvCountCommissionRsMessage());
    response.getSrvCountCommissionRsMessage().setIsCommission(true);
    BigDecimal amount = request.getSrvCountCommissionRqMessage().getAmount();
    response.getSrvCountCommissionRsMessage().setCommissionAmount(amount == null
        ? BigDecimal.valueOf(100) : BigDecimal.valueOf(Math.round(amount.doubleValue() * 0.01)));
    return response;
  }

  private SrvCheckWithFraudRs getResponse(SrvCheckWithFraudRq request) {
    SrvCheckWithFraudRs response = new SrvCheckWithFraudRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCheckWithFraudRsMessage(new SrvCheckWithFraudRsMessage());
    response.getSrvCheckWithFraudRsMessage().setIsWithFraud(true);
    response.getSrvCheckWithFraudRsMessage().setResponseCode("0");
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
