package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.web.dto.AuditDto;
import ru.philit.ufs.web.dto.LogDto;
import ru.philit.ufs.web.mapping.ServiceMapper;

public class ServiceMapperImplTest {

  private static final String ID = "id";
  private static final String USER_LOGIN = "UserLogin";
  private static final String ACTION_TYPE = "ActionType";
  private static final String DATE = "01.05.2017 12:00:00";
  private static final LogEntity OBJECT = new LogEntity();
  private static final String CLIENT_HOST = "ClientHost";
  private static final String SERVER_HOST = "ServerHost";
  private static final String EVENT_MESSAGE = "EventMessage";

  private final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  private final ServiceMapper mapper = new ServiceMapperImpl();

  @Test
  public void testAsDto_Audit() throws Exception {
    // given
    AuditEntity entity = getAuditEntity();

    // when
    AuditDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getLogin(), USER_LOGIN);
    assertEquals(dto.getType(), ACTION_TYPE);
    assertEquals(dto.getDate(), DATE);
    assertEquals(dto.getInitialObject(), OBJECT);
    assertEquals(dto.getRequestObject(), OBJECT);
    assertEquals(dto.getResponseObject(), OBJECT);
    assertEquals(dto.getServer(), SERVER_HOST);
    assertEquals(dto.getClient(), CLIENT_HOST);
  }

  @Test
  public void testAsDto_Audit_NullEntity() throws Exception {
    // when
    AuditDto dto = mapper.asDto((AuditEntity) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_Log() throws Exception {
    // given
    LogEntity entity = getLogEntity();

    // when
    LogDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getLogin(), USER_LOGIN);
    assertEquals(dto.getEvent(), EVENT_MESSAGE);
    assertEquals(dto.getDate(), DATE);
  }

  @Test
  public void testAsDto_Log_NullEntity() throws Exception {
    // when
    LogDto dto = mapper.asDto((LogEntity) null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsAuditDto() throws Exception {
    // given
    List<AuditEntity> entities = getAuditEntities();

    // when
    List<AuditDto> dtos = mapper.asAuditDto(entities);

    // then
    assertNotNull(dtos);
    assertEquals(dtos.size(), entities.size());
  }

  @Test
  public void testAsAuditDto_NullEntity() throws Exception {
    // when
    List<AuditDto> dtos = mapper.asAuditDto(null);

    // then
    assertNotNull(dtos);
    assertTrue(dtos.isEmpty());
  }

  @Test
  public void testAsLogDto() throws Exception {
    // given
    List<LogEntity> entities = getLogEntities();

    // when
    List<LogDto> dtos = mapper.asLogDto(entities);

    // then
    assertNotNull(dtos);
    assertEquals(dtos.size(), entities.size());
  }

  @Test
  public void testAsLogDto_NullEntity() throws Exception {
    // when
    List<LogDto> dtos = mapper.asLogDto(null);

    // then
    assertNotNull(dtos);
    assertTrue(dtos.isEmpty());
  }

  private AuditEntity getAuditEntity() throws Exception {
    AuditEntity entity = new AuditEntity();

    entity.setId(ID);
    entity.setUserLogin(USER_LOGIN);
    entity.setActionType(ACTION_TYPE);
    entity.setActionDate(longDateFormat.parse(DATE));
    entity.setInitialObject(OBJECT);
    entity.setRequestObject(OBJECT);
    entity.setResponseObject(OBJECT);
    entity.setServerHost(SERVER_HOST);
    entity.setClientHost(CLIENT_HOST);

    return entity;
  }

  private LogEntity getLogEntity() throws Exception {
    LogEntity entity = new LogEntity();

    entity.setId(ID);
    entity.setUserLogin(USER_LOGIN);
    entity.setEventMessage(EVENT_MESSAGE);
    entity.setEventDate(longDateFormat.parse(DATE));

    return entity;
  }

  private List<AuditEntity> getAuditEntities() {
    List<AuditEntity> entities = new ArrayList<>();

    for (long index = 0; index < 3; index++) {
      entities.add(new AuditEntity());
    }
    return entities;
  }

  private List<LogEntity> getLogEntities() {
    List<LogEntity> entities = new ArrayList<>();

    for (long index = 0; index < 3; index++) {
      entities.add(new LogEntity());
    }
    return entities;
  }
}
