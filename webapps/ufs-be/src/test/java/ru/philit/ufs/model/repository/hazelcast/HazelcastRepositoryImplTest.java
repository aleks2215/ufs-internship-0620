package ru.philit.ufs.model.repository.hazelcast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.hazelcast.core.IList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.cache.hazelcast.HazelcastBeClient;
import ru.philit.ufs.model.cache.hazelcast.MockIList;
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.service.LogEntity;

public class HazelcastRepositoryImplTest {

  private final IList<AuditEntity> auditedRequests = new MockIList<>();
  private final IList<LogEntity> loggedEvents = new MockIList<>();

  @Mock
  private HazelcastBeClient client;

  private HazelcastRepositoryImpl repository;

  /**
   * Подготовка объектов.
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    repository = new HazelcastRepositoryImpl(client);
    when(client.getAuditedRequests()).thenReturn(auditedRequests);
    when(client.getLoggedEvents()).thenReturn(loggedEvents);
  }

  @Test
  public void testAuditing() throws Exception {
    List<AuditEntity> auditEntities = repository.getAuditEntities();
    assertNotNull(auditEntities);
    assertTrue(auditEntities.isEmpty());

    boolean saved = repository.saveAuditEntity(new AuditEntity());
    assertTrue(saved);

    List<AuditEntity> changedAuditEntities = repository.getAuditEntities();
    assertNotNull(changedAuditEntities);
    assertEquals(changedAuditEntities.size(), 1);
  }

  @Test
  public void testLogging() throws Exception {
    List<LogEntity> logEntities = repository.getLogEntities();
    assertNotNull(logEntities);
    assertTrue(logEntities.isEmpty());

    boolean saved = repository.saveLogEntity(new LogEntity());
    assertTrue(saved);

    List<LogEntity> changedLogEntities = repository.getLogEntities();
    assertNotNull(changedLogEntities);
    assertEquals(changedLogEntities.size(), 1);
  }
}
