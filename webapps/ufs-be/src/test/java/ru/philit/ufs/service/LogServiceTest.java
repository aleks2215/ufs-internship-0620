package ru.philit.ufs.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.entity.service.LogEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.model.repository.LogRepository;

public class LogServiceTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("123", new User("login"), "1234");
  private static final String EVENT_MESSAGE = "eventMessage";
  private static final String EVENT_DATE = "01.05.2017 12:34:56";
  private static final String WRONG_DATE = "Some date";

  @Mock
  private LogRepository repository;

  private LogService service;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    service = new LogService(repository);
  }

  @Test
  public void testLogEvent() throws Exception {
    when(repository.saveLogEntity(any(LogEntity.class))).thenReturn(true);

    service.logEvent(CLIENT_INFO, EVENT_MESSAGE, EVENT_DATE);

    verify(repository, times(1)).saveLogEntity(any(LogEntity.class));
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testLogEvent_WrongDate() throws Exception {
    when(repository.saveLogEntity(any(LogEntity.class))).thenReturn(true);

    service.logEvent(CLIENT_INFO, EVENT_MESSAGE, WRONG_DATE);

    verify(repository, times(1)).saveLogEntity(any(LogEntity.class));
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testGetLogEvents() throws Exception {
    when(repository.getLogEntities()).thenReturn(new ArrayList<LogEntity>());

    service.getLogEvents();

    verify(repository, times(1)).getLogEntities();
    verifyNoMoreInteractions(repository);
  }
}
