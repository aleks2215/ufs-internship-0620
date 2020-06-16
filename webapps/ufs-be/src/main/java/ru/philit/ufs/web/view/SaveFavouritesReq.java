package ru.philit.ufs.web.view;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.web.dto.BaseRequest;

/**
 * Запрос для операции {@link ru.philit.ufs.web.controller.OperationTypeController#saveFavourites}
 */
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class SaveFavouritesReq extends BaseRequest {

  /**
   * Упорядоченный список идентификаторов типов операций.
   */
  private List<String> typeIds;

}
