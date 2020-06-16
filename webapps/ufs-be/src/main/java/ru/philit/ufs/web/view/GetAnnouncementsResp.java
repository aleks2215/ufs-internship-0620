package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.AnnouncementDto;
import ru.philit.ufs.web.dto.BaseResponse;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.AnnouncementController#getAnnouncements}
 */
@SuppressWarnings("serial")
public class GetAnnouncementsResp extends BaseResponse<List<AnnouncementDto>> {
}
