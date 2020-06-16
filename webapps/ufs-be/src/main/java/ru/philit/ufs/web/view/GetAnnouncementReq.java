package ru.philit.ufs.web.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции
 * {@link ru.philit.ufs.web.controller.AnnouncementController#getAnnouncementById}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class GetAnnouncementReq extends BaseRequest {

  /**
   * Уникальный номер ОВН.
   */
  private String announcementId;

}
