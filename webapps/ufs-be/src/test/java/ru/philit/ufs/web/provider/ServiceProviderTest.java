package ru.philit.ufs.web.provider;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.service.AuditService;
import ru.philit.ufs.service.LogService;

public class ServiceProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User("login"), "2");
  private static final String EVENT = "Event";
  private static final String DATE = "Date";

  @Mock
  private AuditService auditService;
  @Mock
  private LogService logService;

  private ServiceProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new ServiceProvider(auditService, logService);
  }

  @Test
  public void testGetAuditRequests() throws Exception {
    // given
    List<AuditEntity> entitiesFromCache = singletonList(new AuditEntity());

    // when
    when(auditService.getAuditRequests()).thenReturn(entitiesFromCache);
    List<AuditEntity> entities = provider.getAuditRequests();

    // then
    assertNotNull(entities);

    // verify
    verify(auditService, times(1)).getAuditRequests();
    verifyNoMoreInteractions(auditService);
  }

  @Test
  public void testGetAuditRequests_NullFromCache() throws Exception {
    // when
    when(auditService.getAuditRequests()).thenReturn(null);
    List<AuditEntity> entities = provider.getAuditRequests();

    // then
    assertNotNull(entities);
    assertTrue(entities.isEmpty());

    // verify
    verify(auditService, times(1)).getAuditRequests();
    verifyNoMoreInteractions(auditService);
  }

  @Test
  public void testGetLogEvents() throws Exception {
    // given
    List<LogEntity> entitiesFromCache = singletonList(new LogEntity());

    // when
    when(logService.getLogEvents()).thenReturn(entitiesFromCache);
    List<LogEntity> entities = provider.getLogEvents();

    // then
    assertNotNull(entities);

    // verify
    verify(logService, times(1)).getLogEvents();
    verifyNoMoreInteractions(logService);
  }

  @Test
  public void testGetLogEvents_NullFromCache() throws Exception {
    // when
    when(logService.getLogEvents()).thenReturn(null);
    List<LogEntity> entities = provider.getLogEvents();

    // then
    assertNotNull(entities);
    assertTrue(entities.isEmpty());

    // verify
    verify(logService, times(1)).getLogEvents();
    verifyNoMoreInteractions(logService);
  }

  @Test
  public void testAddLogEvent() throws Exception {
    // when
    when(logService.logEvent(any(ClientInfo.class), anyString(), anyString())).thenReturn(true);
    provider.addLogEvent(CLIENT_INFO, EVENT, DATE);

    // verify
    verify(logService, times(1)).logEvent(any(ClientInfo.class), anyString(), anyString());
    verifyNoMoreInteractions(logService);
  }

}