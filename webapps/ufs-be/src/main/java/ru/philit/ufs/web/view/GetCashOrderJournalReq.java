package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetCashOrderJournalReq extends BaseRequest {

  /**
   * Журнал кассовых ордеров с даты.
   */
  private String fromDate;
  /**
   * Журнал кассовых ордеров по дату.
   */
  private String toDate;

}
