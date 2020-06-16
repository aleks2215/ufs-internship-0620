package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.AnnouncementController#getAnnouncements}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetAnnouncementsReq extends BaseRequest {

  /**
   * Уникальный номер УРМ/Кассы.
   */
  private String accountId;
  /**
   * Статус ОВН.
   */
  private String status;

}
