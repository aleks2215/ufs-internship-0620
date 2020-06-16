package ru.philit.ufs.web.mapping;

import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.web.dto.RepresentativeDto;

/**
 * Конвертер для представителей.
 */
public interface RepresentativeMapper {

  RepresentativeDto asDto(Representative in);

}
