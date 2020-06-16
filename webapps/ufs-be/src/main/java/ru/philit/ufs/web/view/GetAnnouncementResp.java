package ru.philit.ufs.web.view;

import ru.philit.ufs.web.dto.AnnouncementDto;
import ru.philit.ufs.web.dto.BaseResponse;

/**
 * Ответ для операции
 * {@link ru.philit.ufs.web.controller.AnnouncementController#getAnnouncementById}
 */
@SuppressWarnings("serial")
public class GetAnnouncementResp extends BaseResponse<AnnouncementDto> {
}
