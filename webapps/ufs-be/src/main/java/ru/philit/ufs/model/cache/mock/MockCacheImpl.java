package ru.philit.ufs.model.cache.mock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.model.entity.account.CardNetworkCode;
import ru.philit.ufs.model.entity.account.CardType;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationStatus;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.util.UuidUtils;

@Service
public class MockCacheImpl implements MockCache {

  private static final String LOGIN_IVANOV = "Ivanov_II";
  private static final String LOGIN_SIDOROV = "Sidorov_SS";
  private static final String LOGIN_SVETLOVA = "Svetlova_SS";

  private static final BigDecimal MAX_LIMIT = new BigDecimal("5000000.0");

  @Override
  public User getUser(String userLogin, String password) {
    if (StringUtils.isEmpty(userLogin)) {
      return null;
    }
    User user;
    switch (userLogin) {
      case LOGIN_IVANOV:
        user = getIvanovUser();
        break;
      case LOGIN_SIDOROV:
        user = getSidorovUser();
        break;
      case LOGIN_SVETLOVA:
        user = getSvetlovaUser();
        break;
      default:
        user = null;
        break;
    }
    return user;
  }

  private User getIvanovUser() {
    return new User(
        LOGIN_IVANOV, "10003", "98736548", "Иванов", "Иван", "Иванович",
        "ivanov@test.com", "Бизнес-администратор"
    );
  }

  private User getSidorovUser() {
    return new User(
        LOGIN_SIDOROV, "10001", "98745312", "Сидоров", "Сидор", "Сидорович",
        "sidorov@test.com", "Сотрудник по обслуживанию корпоративных клиентов"
    );
  }

  private User getSvetlovaUser() {
    return new User(
        LOGIN_SVETLOVA, "10002", "98561324", "Светлова", "Светлана", "Святославовна",
        "svetlova@test.com", "Кассир"
    );
  }

  @Override
  public Card getCreditCard() {
    Card card = new Card();

    card.setNumber("4279987965419873");
    card.setExpiryDate(new GregorianCalendar(2020, 3 - 1, 3).getTime());
    card.setIssuingNetworkCode(CardNetworkCode.VISA);
    card.setType(CardType.DEBIT);
    card.setOwnerFirstName("PETR");
    card.setOwnerLastName("PETROV");

    return card;
  }

  @Override
  public Operation createOperation(String workplaceId, String operationTypeCode) {
    Operation operation = new Operation();

    operation.setId(UuidUtils.getRandomUuid());
    operation.setWorkplaceId(workplaceId);
    operation.setStatus(OperationStatus.NEW);
    operation.setTypeCode(OperationTypeCode.getByCode(operationTypeCode));
    operation.setCreatedDate(new Date());

    return operation;
  }

  @Override
  public Operation commitOperation(Operation operation) {
    operation.setStatus(OperationStatus.COMMITTED);
    operation.setCommittedDate(new Date());
    return operation;
  }

  @Override
  public Operation cancelOperation(Operation operation) {
    operation.setStatus(OperationStatus.CANCELLED);
    operation.setRollbackReason("Отменено пользователем");
    return operation;
  }
}
