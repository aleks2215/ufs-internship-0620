package ru.philit.ufs.model.cache.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.model.entity.oper.CashOrderStatus;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.user.Subbranch;

/**
 * Mock данные по Кассовым Ордерам.
 */
public class MockCacheOrder {

  /**
   * Возвращает предварительно созданный Кассовый Ордер.
   */
  public static CashOrder getCashOrder() {
    CashOrder cashOrder = new CashOrder();
    cashOrder.setCashOrderId("101");
    cashOrder.setOperationType(OperationTypeCode.TO_CARD_DEPOSIT);
    cashOrder.setCashOrderINum("50");
    cashOrder.setAccountId("1001");
    cashOrder.setAmount(BigDecimal.valueOf(100000));
    cashOrder.setAmountInWords("Сто тысяч");
    cashOrder.setCurrencyType("RUB");
    cashOrder.setCashOrderStatus(CashOrderStatus.CREATED);
    cashOrder.setWorkPlaceUId("12");
    cashOrder.setRepId("500");
    cashOrder.setRepFio("Петров Петр Петрович");
    cashOrder.setAddress("Ул. Титова");
    cashOrder.setDateOfBirth(new GregorianCalendar(2020, Calendar.JUNE, 30)
        .getTime());

    cashOrder.setIdentityDocuments(new ArrayList<IdentityDocument>());
    IdentityDocument identityDocument = new IdentityDocument();
    identityDocument.setType(IdentityDocumentType.PASSPORT);
    identityDocument.setSeries("44 33");
    identityDocument.setNumber("444666");
    identityDocument.setIssuedDate(
        new GregorianCalendar(1980, Calendar.JANUARY, 30).getTime());
    identityDocument.setIssuedBy("МВД");
    cashOrder.getIdentityDocuments().add(identityDocument);

    cashOrder.setPlaceOfBirth("Москва");
    cashOrder.setResident(true);
    cashOrder.setInn("7703040404");

    cashOrder.setCashSymbols(new ArrayList<CashSymbol>());
    CashSymbol cashSymbol = new CashSymbol();
    cashSymbol.setCode("02");
    cashSymbol.setAmount(BigDecimal.valueOf(70500));
    cashOrder.getCashSymbols().add(cashSymbol);

    cashOrder.setSubbranch(new Subbranch());
    cashOrder.getSubbranch().setSubbranchCode("1385930102");
    cashOrder.getSubbranch().setVspCode("0102");
    cashOrder.getSubbranch().setOsbCode(null);
    cashOrder.getSubbranch().setGosbCode("8593");
    cashOrder.getSubbranch().setTbCode("13");

    cashOrder.setComment("Коммент");
    cashOrder.setAccount20202Num("20202897357400146292");
    cashOrder.setUserLogin("aleks_login");

    return cashOrder;
  }
}
