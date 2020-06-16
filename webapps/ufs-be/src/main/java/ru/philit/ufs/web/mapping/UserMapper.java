package ru.philit.ufs.web.mapping;

import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.SessionUser;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.web.dto.OperatorDto;
import ru.philit.ufs.web.dto.UserDto;
import ru.philit.ufs.web.dto.WorkplaceDto;

/**
 * Конвертер для пользователей.
 */
public interface UserMapper {

  UserDto asDto(SessionUser in);

  OperatorDto asDto(Operator in);

  WorkplaceDto asDto(Workplace in);

}
