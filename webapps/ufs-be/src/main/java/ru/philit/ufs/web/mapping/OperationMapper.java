package ru.philit.ufs.web.mapping;

import java.util.Collection;
import java.util.List;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationPackage;
import ru.philit.ufs.model.entity.oper.OperationTask;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.web.dto.CardDepositDto;
import ru.philit.ufs.web.dto.OperationDto;
import ru.philit.ufs.web.dto.OperationPackageDto;
import ru.philit.ufs.web.dto.OperationTaskDto;

/**
 * Конвертер для операций.
 */
public interface OperationMapper {

  OperationPackageDto asDto(OperationPackage in);

  OperationTaskDto asDto(OperationTask in);

  OperationDto asDto(Operation in);

  CardDepositDto asDto(OperationTaskCardDeposit in);

  List<CardDepositDto> asDto(Collection<OperationTaskCardDeposit> in);

  OperationTaskCardDeposit asEntity(CardDepositDto in);

  Long asEntity(String in);

}
