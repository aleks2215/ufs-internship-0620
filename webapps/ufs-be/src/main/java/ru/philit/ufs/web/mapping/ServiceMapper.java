package ru.philit.ufs.web.mapping;

import java.util.List;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.web.dto.AuditDto;
import ru.philit.ufs.web.dto.LogDto;

/**
 * Конвертер для аудита и журналирования.
 */
public interface ServiceMapper {

  AuditDto asDto(AuditEntity in);

  LogDto asDto(LogEntity in);

  List<AuditDto> asAuditDto(List<AuditEntity> in);

  List<LogDto> asLogDto(List<LogEntity> in);

}
