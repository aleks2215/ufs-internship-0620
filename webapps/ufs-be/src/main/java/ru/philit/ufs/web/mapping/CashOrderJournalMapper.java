package ru.philit.ufs.web.mapping;

import ru.philit.ufs.model.entity.oper.CashOrder;
import ru.philit.ufs.web.dto.CashOrderJournalDto;

public interface CashOrderJournalMapper {

  CashOrderJournalDto asDto(CashOrder in);

}
