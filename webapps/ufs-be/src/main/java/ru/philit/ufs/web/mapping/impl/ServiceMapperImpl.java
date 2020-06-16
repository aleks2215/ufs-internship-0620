package ru.philit.ufs.web.mapping.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.web.dto.AuditDto;
import ru.philit.ufs.web.dto.LogDto;
import ru.philit.ufs.web.mapping.ServiceMapper;

@Component
public class ServiceMapperImpl extends CommonMapperImpl implements ServiceMapper {

  @Override
  public AuditDto asDto(AuditEntity in) {
    if (in == null) {
      return null;
    }
    AuditDto auditDto = new AuditDto();

    auditDto.setLogin(in.getUserLogin());
    auditDto.setType(in.getActionType());
    auditDto.setDate(asLongDateDto(in.getActionDate()));
    auditDto.setInitialObject(in.getInitialObject());
    auditDto.setRequestObject(in.getRequestObject());
    auditDto.setResponseObject(in.getResponseObject());
    auditDto.setServer(in.getServerHost());
    auditDto.setClient(in.getClientHost());

    return auditDto;
  }

  @Override
  public LogDto asDto(LogEntity in) {
    if (in == null) {
      return null;
    }
    LogDto logDto = new LogDto();

    logDto.setLogin(in.getUserLogin());
    logDto.setEvent(in.getEventMessage());
    logDto.setDate(asLongDateDto(in.getEventDate()));

    return logDto;
  }

  @Override
  public List<AuditDto> asAuditDto(List<AuditEntity> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<AuditDto> list = new ArrayList<>();

    for (AuditEntity auditEntity : in) {
      list.add(asDto(auditEntity));
    }
    return list;
  }

  @Override
  public List<LogDto> asLogDto(List<LogEntity> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<LogDto> list = new ArrayList<>();

    for (LogEntity logEntity : in) {
      list.add(asDto(logEntity));
    }
    return list;
  }

}
