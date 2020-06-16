package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.AnnouncementController#getAccount20202}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetAccount20202Req extends BaseRequest {

  /**
   * Уникальный номер УРМ/Кассы.
   */
  private String workplaceId;

}
