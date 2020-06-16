package ru.philit.ufs.web.mapping;

import java.math.BigDecimal;
import java.util.Date;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.dto.CardDepositDto;
import ru.philit.ufs.web.dto.OperationDto;
import ru.philit.ufs.web.dto.OperatorDto;
import ru.philit.ufs.web.dto.RepresentativeDto;
import ru.philit.ufs.web.dto.UserDto;

/**
 * Конвертер для записей журнала операций.
 */
public interface OperationJournalMapper {

  OperatorDto asDto(Operator in);

  UserDto asDto(User in);

  RepresentativeDto asDto(Representative in);

  OperationDto asDto(Operation in);

  CardDepositDto asDto(OperationTaskCardDeposit in);

  String asDto(BigDecimal in);

  Date asEntity(String in);

}
