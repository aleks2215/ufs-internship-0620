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
import ru.philit.ufs.model.entity.service.AuditEntity;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.model.repository.AuditRepository;

public class AuditServiceTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("123", new User("login"), "1234");
  private static final String REQUEST_TYPE = "Type";
  private static final AuditEntity OBJECT = new AuditEntity();

  @Mock
  private AuditRepository repository;

  private AuditService service;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    service = new AuditService(repository);
  }

  @Test
  public void testAuditRequest() throws Exception {
    when(repository.saveAuditEntity(any(AuditEntity.class))).thenReturn(true);

    service.auditRequest(CLIENT_INFO, REQUEST_TYPE, OBJECT, OBJECT, OBJECT);

    verify(repository, times(1)).saveAuditEntity(any(AuditEntity.class));
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testAuditRequest_WithoutInitialObject() throws Exception {
    when(repository.saveAuditEntity(any(AuditEntity.class))).thenReturn(true);

    service.auditRequest(CLIENT_INFO, REQUEST_TYPE, OBJECT, OBJECT);

    verify(repository, times(1)).saveAuditEntity(any(AuditEntity.class));
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testGetAuditRequests() throws Exception {
    when(repository.getAuditEntities()).thenReturn(new ArrayList<AuditEntity>());

    service.getAuditRequests();

    verify(repository, times(1)).getAuditEntities();
    verifyNoMoreInteractions(repository);
  }
}
