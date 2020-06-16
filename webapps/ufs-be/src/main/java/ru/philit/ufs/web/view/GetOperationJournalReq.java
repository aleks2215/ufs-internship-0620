package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для метода {@link ru.philit.ufs.web.controller.ReportController#getOperationJournal}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetOperationJournalReq extends BaseRequest {

  /**
   * Журнал операций с даты.
   */
  private String fromDate;
  /**
   * Журнал операций по дату.
   */
  private String toDate;

}
