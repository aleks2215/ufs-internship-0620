package ru.philit.ufs.web.view;

import java.util.List;
import ru.philit.ufs.web.dto.BaseResponse;

/**
 * Ответ для операции {@link ru.philit.ufs.web.controller.OperationTypeController#getFavourites}
 */
@SuppressWarnings("serial")
public class GetFavouritesResp extends BaseResponse<List<String>> {
}
