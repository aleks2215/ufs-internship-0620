package ru.philit.ufs.esb.mock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.pprb.HeaderInfoType;
import ru.philit.ufs.model.entity.esb.pprb.IDDtype;
import ru.philit.ufs.model.entity.esb.pprb.OVNStatusType;
import ru.philit.ufs.model.entity.esb.pprb.OperTypeLabel;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs.SrvCashDepAnmntListByAccountIdRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs.SrvCashDepAnmntListByAccountIdRsMessage.CashDepAnmntList;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs.SrvCashSymbolsListRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs.SrvCashSymbolsListRsMessage.CashSymbolItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs.SrvCreateCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs.SrvGet20202NumRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs.SrvGetCashDepAnmntItemRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs.SrvGetCashDepAnmntItemRsMessage.CashSymbols;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage.OperatorInfoItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs.SrvGetOperatorInfoByUserRsMessage.OperatorInfoItem.SubbranchInfoItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs.SrvSearchRepCardRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs.SrvGetUserOperationsByRoleRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs.SrvGetUserOperationsByRoleRsMessage.OperationTypeItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs.SrvSearchRepRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs.SrvUpdCashDepAnmntItemRsMessage;

/**
 * Сервис на обработку запросов к ППРБ.
 */
@Service
public class PprbMockService extends CommonMockService implements MessageProcessor {

  private static final String CONTEXT_PATH = "ru.philit.ufs.model.entity.esb.pprb";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final String LOGIN_IVANOV = "Ivanov_II";
  private static final String LOGIN_SIDOROV = "Sidorov_SS";
  private static final String LOGIN_SVETLOVA = "Svetlova_SS";

  private final EsbClient esbClient;

  private final JaxbConverter jaxbConverter = new JaxbConverter(CONTEXT_PATH);

  @Autowired
  public PprbMockService(EsbClient esbClient) {
    this.esbClient = esbClient;
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
        if (request instanceof SrvCashDepAnmntListByAccountIdRq) {
          sendResponse(getResponse((SrvCashDepAnmntListByAccountIdRq) request));

        } else if (request instanceof SrvCreateCashDepAnmntItemRq) {
          sendResponse(getResponse((SrvCreateCashDepAnmntItemRq) request));

        } else if (request instanceof SrvGetCashDepAnmntItemRq) {
          sendResponse(getResponse((SrvGetCashDepAnmntItemRq) request));

        } else if (request instanceof SrvUpdCashDepAnmntItemRq) {
          sendResponse(getResponse((SrvUpdCashDepAnmntItemRq) request));

        } else if (request instanceof SrvGet20202NumRq) {
          sendResponse(getResponse((SrvGet20202NumRq) request));

        } else if (request instanceof SrvGetUserOperationsByRoleRq) {
          sendResponse(getResponse((SrvGetUserOperationsByRoleRq) request));

        } else if (request instanceof SrvSearchRepRq) {
          sendResponse(getResponse((SrvSearchRepRq) request));

        } else if (request instanceof SrvGetRepByCardRq) {
          sendResponse(getResponse((SrvGetRepByCardRq) request));

        } else if (request instanceof SrvGetOperatorInfoByUserRq) {
          sendResponse(getResponse((SrvGetOperatorInfoByUserRq) request));

        } else if (request instanceof SrvCashSymbolsListRq) {
          sendResponse(getResponse((SrvCashSymbolsListRq) request));

        }
        return true;
      }
    } catch (JAXBException e) {
      // this message can not be processed this processor
      logger.debug("this message can not be processed this processor", e);
    }
    return false;
  }

  private void sendResponse(Object responseObject) throws JAXBException {
    String responseMessage = jaxbConverter.getXml(responseObject);
    esbClient.sendMessage(responseMessage);
  }

  private SrvCashDepAnmntListByAccountIdRs getResponse(SrvCashDepAnmntListByAccountIdRq request) {
    SrvCashDepAnmntListByAccountIdRs response = new SrvCashDepAnmntListByAccountIdRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCashDepAnmntListByAccountIdRsMessage(
        new SrvCashDepAnmntListByAccountIdRsMessage());
    CashDepAnmntList cashDepAnmnt = new CashDepAnmntList();
    cashDepAnmnt.setOVNUId("FD1D833A431F498DB556E91998C8C8A5");
    cashDepAnmnt.setOVNNum(BigInteger.valueOf(11040617));
    cashDepAnmnt.setOVNStatus(OVNStatusType.PENDING);
    cashDepAnmnt.setAmount(BigDecimal.valueOf(10000));
    cashDepAnmnt.setCreatedDttm(xmlCalendar(2017, 6, 4, 9, 30));
    cashDepAnmnt.setINN("0278000222");
    cashDepAnmnt.setAccountId("40702810638050013199");
    cashDepAnmnt.setLegalEntityShortName("ООО \"ДНК\"");
    cashDepAnmnt.setRepFIO("Петров Петр Петрович");
    response.getSrvCashDepAnmntListByAccountIdRsMessage().getCashDepAnmntList().add(cashDepAnmnt);
    CashDepAnmntList cashDepAnmnt2 = new CashDepAnmntList();
    cashDepAnmnt2.setOVNUId("FD1D833A431F498DB556E91778C8C8A6");
    cashDepAnmnt2.setOVNNum(BigInteger.valueOf(12050617));
    cashDepAnmnt2.setOVNStatus(OVNStatusType.PENDING);
    cashDepAnmnt2.setAmount(BigDecimal.valueOf(70500));
    cashDepAnmnt2.setCreatedDttm(xmlCalendar(2017, 6, 5, 9, 30));
    cashDepAnmnt2.setINN("0278000222");
    cashDepAnmnt2.setAccountId("40702810638050013199");
    cashDepAnmnt2.setLegalEntityShortName("ООО \"ДНК\"");
    cashDepAnmnt2.setRepFIO("Петров Петр Петрович");
    response.getSrvCashDepAnmntListByAccountIdRsMessage().getCashDepAnmntList().add(cashDepAnmnt2);
    return response;
  }

  private SrvCreateCashDepAnmntItemRs getResponse(SrvCreateCashDepAnmntItemRq request) {
    SrvCreateCashDepAnmntItemRs response = new SrvCreateCashDepAnmntItemRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCreateCashDepAnmntItemRsMessage(new SrvCreateCashDepAnmntItemRsMessage());
    response.getSrvCreateCashDepAnmntItemRsMessage().setOVNUId("FD1D833A431F498DB556E91778C8C8A6");
    response.getSrvCreateCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(1));
    response.getSrvCreateCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.PENDING);
    response.getSrvCreateCashDepAnmntItemRsMessage().setResponseCode("0");
    return response;
  }

  private SrvGetCashDepAnmntItemRs getResponse(SrvGetCashDepAnmntItemRq request) {
    SrvGetCashDepAnmntItemRs response = new SrvGetCashDepAnmntItemRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetCashDepAnmntItemRsMessage(new SrvGetCashDepAnmntItemRsMessage());
    if (!StringUtils.isEmpty(request.getSrvGetCashDepAnmntItemRqMessage().getOVNUId())
        && request.getSrvGetCashDepAnmntItemRqMessage().getOVNUId()
        .equals("FD1D833A431F498DB556E91998C8C8A5")) {
      response.getSrvGetCashDepAnmntItemRsMessage().setOVNUId("FD1D833A431F498DB556E91998C8C8A5");
      response.getSrvGetCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(11040617));
      response.getSrvGetCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.PENDING);
      response.getSrvGetCashDepAnmntItemRsMessage().setRepFIO("Петров Петр Петрович");
      response.getSrvGetCashDepAnmntItemRsMessage().setLegalEntityShortName("ООО \"ДНК\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setAmount(BigDecimal.valueOf(10000));
      response.getSrvGetCashDepAnmntItemRsMessage().setCreatedDttm(xmlCalendar(2017, 6, 4, 9, 30));
      response.getSrvGetCashDepAnmntItemRsMessage().setINN("0278000222");
      response.getSrvGetCashDepAnmntItemRsMessage().setKPP("027811001");
      response.getSrvGetCashDepAnmntItemRsMessage().setAccountId("40702810638050013199");
      response.getSrvGetCashDepAnmntItemRsMessage().setSenderAccountId("40702810638050013188");
      response.getSrvGetCashDepAnmntItemRsMessage().setSenderBank("ПАО \"Сбербанк\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setSenderBankBIC("044525225");
      response.getSrvGetCashDepAnmntItemRsMessage().setRecipientBank("ПАО \"Сбербанк\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setRecipientBankBIC("044525225");
      response.getSrvGetCashDepAnmntItemRsMessage().setSource("Торговая выручка");
      response.getSrvGetCashDepAnmntItemRsMessage().setIsClientTypeFK(false);
      response.getSrvGetCashDepAnmntItemRsMessage().setFDestLEName("ООО \"Синстройопт\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setPersonalAccountId("40502987865430001897");
      response.getSrvGetCashDepAnmntItemRsMessage().setCurrencyType("RUB");
      CashSymbols cashSymbols = new CashSymbols();
      CashSymbols.CashSymbolItem cashSymbolItem = new CashSymbols.CashSymbolItem();
      cashSymbolItem.setCashSymbol("02");
      cashSymbolItem.setCashSymbolAmount(BigDecimal.valueOf(10000));
      cashSymbols.getCashSymbolItem().add(cashSymbolItem);
      response.getSrvGetCashDepAnmntItemRsMessage().setCashSymbols(cashSymbols);
    } else {
      response.getSrvGetCashDepAnmntItemRsMessage().setOVNUId(
          StringUtils.isEmpty(request.getSrvGetCashDepAnmntItemRqMessage().getOVNUId())
              ? "FD1D833A431F498DB556E91778C8C8A6"
              : request.getSrvGetCashDepAnmntItemRqMessage().getOVNUId());
      response.getSrvGetCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(12050617));
      response.getSrvGetCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.PENDING);
      response.getSrvGetCashDepAnmntItemRsMessage().setRepFIO("Петров Петр Петрович");
      response.getSrvGetCashDepAnmntItemRsMessage().setLegalEntityShortName("ООО \"ДНК\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setAmount(BigDecimal.valueOf(70500));
      response.getSrvGetCashDepAnmntItemRsMessage().setCreatedDttm(xmlCalendar(2017, 6, 4, 9, 30));
      response.getSrvGetCashDepAnmntItemRsMessage().setINN("0278000222");
      response.getSrvGetCashDepAnmntItemRsMessage().setKPP("027811001");
      response.getSrvGetCashDepAnmntItemRsMessage().setAccountId("40702810638050013199");
      response.getSrvGetCashDepAnmntItemRsMessage().setSenderAccountId("40702810638050013188");
      response.getSrvGetCashDepAnmntItemRsMessage().setSenderBank("ПАО \"Сбербанк\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setSenderBankBIC("044525225");
      response.getSrvGetCashDepAnmntItemRsMessage().setRecipientBank("ПАО \"Сбербанк\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setRecipientBankBIC("044525225");
      response.getSrvGetCashDepAnmntItemRsMessage().setSource("Торговая выручка");
      response.getSrvGetCashDepAnmntItemRsMessage().setIsClientTypeFK(false);
      response.getSrvGetCashDepAnmntItemRsMessage().setFDestLEName("ООО \"Синстройопт\"");
      response.getSrvGetCashDepAnmntItemRsMessage().setPersonalAccountId("40502987865430001897");
      response.getSrvGetCashDepAnmntItemRsMessage().setCurrencyType("RUB");
      CashSymbols cashSymbols = new CashSymbols();
      CashSymbols.CashSymbolItem cashSymbolItem = new CashSymbols.CashSymbolItem();
      cashSymbolItem.setCashSymbol("02");
      cashSymbolItem.setCashSymbolAmount(BigDecimal.valueOf(70500));
      cashSymbols.getCashSymbolItem().add(cashSymbolItem);
      response.getSrvGetCashDepAnmntItemRsMessage().setCashSymbols(cashSymbols);
    }
    return response;
  }

  private SrvUpdCashDepAnmntItemRs getResponse(SrvUpdCashDepAnmntItemRq request) {
    SrvUpdCashDepAnmntItemRs response = new SrvUpdCashDepAnmntItemRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvUpdCashDepAnmntItemRsMessage(new SrvUpdCashDepAnmntItemRsMessage());
    response.getSrvUpdCashDepAnmntItemRsMessage().setOVNUId("FD1D833A431F498DB556E91998C8C8A5");
    response.getSrvUpdCashDepAnmntItemRsMessage().setOVNNum(BigInteger.valueOf(1));
    response.getSrvUpdCashDepAnmntItemRsMessage().setOVNStatus(OVNStatusType.COMPLETED);
    response.getSrvUpdCashDepAnmntItemRsMessage().setResponseCode("0");
    return response;
  }

  private SrvGet20202NumRs getResponse(SrvGet20202NumRq request) {
    SrvGet20202NumRs response = new SrvGet20202NumRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGet20202NumRsMessage(new SrvGet20202NumRsMessage());
    response.getSrvGet20202NumRsMessage().setAccount20202Num("20202897357400146292");
    return response;
  }

  private SrvGetUserOperationsByRoleRs getResponse(SrvGetUserOperationsByRoleRq request) {
    SrvGetUserOperationsByRoleRs response = new SrvGetUserOperationsByRoleRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetUserOperationsByRoleRsMessage(new SrvGetUserOperationsByRoleRsMessage());
    OperationTypeItem operationTypeItem1 = new OperationTypeItem();
    operationTypeItem1.setOperationTypeId("0");
    operationTypeItem1.setOperationType(OperTypeLabel.TO_CARD_DEPOSIT);
    operationTypeItem1.setOperationTypeName("Взнос наличных на счет корпоративной/бюджетной карты");
    operationTypeItem1.setOperationCatId(BigInteger.valueOf(0));
    operationTypeItem1.setOperationCatName("Операции корпоративных клиентов");
    operationTypeItem1.setVisibleFlg(true);
    operationTypeItem1.setEnabledFlg(true);
    response.getSrvGetUserOperationsByRoleRsMessage().getOperationTypeItem()
        .add(operationTypeItem1);
    OperationTypeItem operationTypeItem2 = new OperationTypeItem();
    operationTypeItem2.setOperationTypeId("1");
    operationTypeItem2.setOperationType(OperTypeLabel.FROM_CARD_WITHDRAW);
    operationTypeItem2.setOperationTypeName(
        "Выдача наличных со счета корпоративной/бюджетной карты");
    operationTypeItem2.setOperationCatId(BigInteger.valueOf(0));
    operationTypeItem2.setOperationCatName("Операции корпоративных клиентов");
    operationTypeItem2.setVisibleFlg(true);
    operationTypeItem2.setEnabledFlg(true);
    response.getSrvGetUserOperationsByRoleRsMessage().getOperationTypeItem()
        .add(operationTypeItem2);
    return response;
  }

  private SrvSearchRepRs getResponse(SrvSearchRepRq request) {
    SrvSearchRepRs response = new SrvSearchRepRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvSearchRepRsMessage(new SrvSearchRepRsMessage());
    SrvSearchRepRsMessage.Representative representative =
        new SrvSearchRepRsMessage.Representative();
    representative.setRepID("BB374D51ABE946A98A6963F59767DC4F");
    representative.setINN("0278000222");
    representative.setFirstName("Петр");
    representative.setLastName("Петров");
    representative.setPatronymic("Петрович");
    representative.setPhoneNumWork("+7-495-900-00-00");
    representative.setPhoneNumMobile("+7-900-900-00-00");
    representative.setEmail("petrov@nodomen.nono");
    representative.setAddress("г. Москва, ул. Пырьева, д. 10, кв. 13");
    representative.setPostindex("119285");
    representative.setDateOfBirth(xmlCalendar(1967, 8, 13, 0, 0));
    representative.setSex(true);
    representative.setPlaceOfBirth("г. Москва");
    representative.setIsResident(true);
    SrvSearchRepRsMessage.Representative.IdentityDocumentType identityDocumentType =
        new SrvSearchRepRsMessage.Representative.IdentityDocumentType();
    identityDocumentType.setValue(IDDtype.INTERNPASSPORT);
    identityDocumentType.setSeries("4233");
    identityDocumentType.setNumber("983017");
    identityDocumentType.setIssuedBy(
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    identityDocumentType.setIssuedDate(xmlCalendar(2010, 1, 1, 0, 0));
    representative.getIdentityDocumentType().add(identityDocumentType);
    representative.setLegalEntity(new SrvSearchRepRsMessage.Representative.LegalEntity());
    SrvSearchRepRsMessage.Representative.LegalEntity.LegalEntityItem legalEntity =
        new SrvSearchRepRsMessage.Representative.LegalEntity.LegalEntityItem();
    legalEntity.setLegalEntityId("8FF9ACBDF006486CAE88109BBD7A8680");
    legalEntity.setLegalEntityShortName("ООО \"ДНК\"");
    legalEntity.setLegalEntityFullName(
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    legalEntity.setINN("0278000222");
    legalEntity.setOGRN("1030224552966");
    legalEntity.setKPP("027811001");
    legalEntity.setLegalAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    legalEntity.setFactAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 2");
    representative.getLegalEntity().getLegalEntityItem().add(legalEntity);
    response.getSrvSearchRepRsMessage().getRepresentative().add(representative);
    return response;
  }

  private SrvGetRepByCardRs getResponse(SrvGetRepByCardRq request) {
    SrvGetRepByCardRs response = new SrvGetRepByCardRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvSearchRepCardRsMessage(new SrvSearchRepCardRsMessage());
    SrvSearchRepCardRsMessage.Representative representative = new SrvSearchRepCardRsMessage
        .Representative();
    representative.setRepID("BB374D51ABE946A98A6963F59767DC4F");
    representative.setINN("0278000222");
    representative.setFirstName("Петр");
    representative.setLastName("Петров");
    representative.setPatronymic("Петрович");
    representative.setPhoneNumWork("+7-495-900-00-00");
    representative.setPhoneNumMobile("+7-900-900-00-00");
    representative.setEmail("petrov@nodomen.nono");
    representative.setAddress("г. Москва, ул. Пырьева, д. 10, кв. 13");
    representative.setPostindex("119285");
    representative.setDateOfBirth(xmlCalendar(1967, 8, 13, 0, 0));
    representative.setSex(true);
    representative.setPlaceOfBirth("г. Москва");
    representative.setIsResident(true);
    SrvSearchRepCardRsMessage.Representative.IdentityDocumentType identityDocumentType =
        new SrvSearchRepCardRsMessage.Representative.IdentityDocumentType();
    identityDocumentType.setValue(IDDtype.INTERNPASSPORT);
    identityDocumentType.setSeries("4233");
    identityDocumentType.setNumber("983017");
    identityDocumentType.setIssuedBy(
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    identityDocumentType.setIssuedDate(xmlCalendar(2010, 1, 1, 0, 0));
    representative.getIdentityDocumentType().add(identityDocumentType);
    representative.setLegalEntity(new SrvSearchRepCardRsMessage.Representative.LegalEntity());
    SrvSearchRepCardRsMessage.Representative.LegalEntity.LegalEntityItem legalEntity =
        new SrvSearchRepCardRsMessage.Representative.LegalEntity.LegalEntityItem();
    legalEntity.setLegalEntityId("8FF9ACBDF006486CAE88109BBD7A8680");
    legalEntity.setLegalEntityShortName("ООО \"ДНК\"");
    legalEntity.setLegalEntityFullName(
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    legalEntity.setINN("0278000222");
    legalEntity.setOGRN("1030224552966");
    legalEntity.setKPP("027811001");
    legalEntity.setLegalAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    legalEntity.setFactAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 2");
    representative.getLegalEntity().getLegalEntityItem().add(legalEntity);
    response.getSrvSearchRepCardRsMessage().setRepresentative(representative);
    return response;
  }

  private SrvGetOperatorInfoByUserRs getResponse(SrvGetOperatorInfoByUserRq request) {
    SrvGetOperatorInfoByUserRs response = new SrvGetOperatorInfoByUserRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvGetOperatorInfoByUserRsMessage(new SrvGetOperatorInfoByUserRsMessage());
    OperatorInfoItem operatorInfoItem = null;
    if (LOGIN_IVANOV.equals(request.getSrvGetOperatorInfoByUserRqMessage().getUserLogin())) {
      operatorInfoItem = getOperatorInfoItem("CB4F526A9F53406799ADDA94B1108310",
          "Иванов Иван Иванович", "ivanov@test.com");
    } else if (LOGIN_SIDOROV
        .equals(request.getSrvGetOperatorInfoByUserRqMessage().getUserLogin())) {
      operatorInfoItem = getOperatorInfoItem("79244EE9AC7241A6AC4A9A463EBE3B17",
          "Сидоров Сидор Сидорович", "sidorov@test.com");
    } else if (LOGIN_SVETLOVA
        .equals(request.getSrvGetOperatorInfoByUserRqMessage().getUserLogin())) {
      operatorInfoItem = getOperatorInfoItem("385F61CB14E646CCA1BDC7EADC524CA0",
          "Светлова Светлана Святославовна", "svetlova@test.com");
    }
    response.getSrvGetOperatorInfoByUserRsMessage().setOperatorInfoItem(operatorInfoItem);
    return response;
  }

  private SrvCashSymbolsListRs getResponse(SrvCashSymbolsListRq request) {
    SrvCashSymbolsListRs response = new SrvCashSymbolsListRs();
    response.setHeaderInfo(copyHeaderInfo(request.getHeaderInfo()));
    response.setSrvCashSymbolsListRsMessage(new SrvCashSymbolsListRsMessage());
    CashSymbolItem cashSymbolItem = new CashSymbolItem();
    cashSymbolItem.setCashSymbol("A");
    cashSymbolItem.setCashSymbolDesc("Описание А");
    response.getSrvCashSymbolsListRsMessage().getCashSymbolItem().add(cashSymbolItem);
    return response;
  }

  private HeaderInfoType copyHeaderInfo(HeaderInfoType headerInfo0) {
    HeaderInfoType headerInfo = new HeaderInfoType();
    headerInfo.setRqUID(headerInfo0.getRqUID());
    headerInfo.setRqTm(headerInfo0.getRqTm());
    headerInfo.setSpName(headerInfo0.getSystemId());
    headerInfo.setSystemId(headerInfo.getSpName());
    return headerInfo;
  }

  private OperatorInfoItem getOperatorInfoItem(String workPlaceUId, String fullName, String email) {
    OperatorInfoItem operatorInfoItem = new OperatorInfoItem();
    operatorInfoItem.setWorkPlaceUId(workPlaceUId);
    operatorInfoItem.setOperatorFullName(fullName);
    operatorInfoItem.setSubbranchInfoItem(getSubbranchInfoItem());
    String[] fio = fullName.split("\\s");
    if (fio.length == 3) {
      operatorInfoItem.setFirstName(fio[1]);
      operatorInfoItem.setLastName(fio[0]);
      operatorInfoItem.setPatronymic(fio[2]);
    }
    operatorInfoItem.setEmail(email);
    return operatorInfoItem;
  }

  private SubbranchInfoItem getSubbranchInfoItem() {
    SubbranchInfoItem subbranchInfoItem = new SubbranchInfoItem();
    subbranchInfoItem.setTBCode("13");
    subbranchInfoItem.setGOSBCode("8593");
    subbranchInfoItem.setOSBCode(null);
    subbranchInfoItem.setVSPCode("0102");
    subbranchInfoItem.setSubbranchCode("1385930102");
    subbranchInfoItem.setSubbranchLevel(BigInteger.valueOf(1L));
    subbranchInfoItem.setINN("0278000222");
    subbranchInfoItem.setBIC("044525225");
    subbranchInfoItem.setBankName("ПАО \"Сбербанк\"");
    return subbranchInfoItem;
  }
}
