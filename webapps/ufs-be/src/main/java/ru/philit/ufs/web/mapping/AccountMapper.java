package ru.philit.ufs.web.mapping;

import java.util.List;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.web.dto.AccountDto;
import ru.philit.ufs.web.dto.AccountResiduesDto;
import ru.philit.ufs.web.dto.LegalEntityDto;
import ru.philit.ufs.web.dto.PaymentOrderCardIndex1Dto;
import ru.philit.ufs.web.dto.PaymentOrderCardIndex2Dto;
import ru.philit.ufs.web.dto.SeizureDto;

/**
 * Конвертер для счетов.
 */
public interface AccountMapper {

  AccountDto asDto(Account in);

  LegalEntityDto asDto(LegalEntity in);

  AccountResiduesDto asDto(AccountResidues in);

  PaymentOrderCardIndex1Dto asDto(PaymentOrderCardIndex1 in);

  PaymentOrderCardIndex2Dto asDto(PaymentOrderCardIndex2 in);

  SeizureDto asDto(Seizure in);

  List<PaymentOrderCardIndex1Dto> asIndex1Dto(List<PaymentOrderCardIndex1> in);

  List<PaymentOrderCardIndex2Dto> asIndex2Dto(List<PaymentOrderCardIndex2> in);

  List<SeizureDto> asSeizureDto(List<Seizure> in);

}
