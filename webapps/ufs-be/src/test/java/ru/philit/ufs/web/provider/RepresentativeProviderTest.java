package ru.philit.ufs.web.provider;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.cache.RepresentativeCache;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

public class RepresentativeProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User("login"), "2");
  private static final String CARD_NUMBER = "4279987965419873";
  private static final String REPRESENTATIVE_ID = "7865764";

  @Mock
  private RepresentativeCache cache;

  private RepresentativeProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new RepresentativeProvider(cache);
  }

  @Test
  public void testGetRepresentativeByCardNumber() throws Exception {
    // given
    Representative representative = new Representative();

    // when
    when(cache.getRepresentativeByCardNumber(anyString(), any(ClientInfo.class)))
        .thenReturn(representative);
    provider.getRepresentativeByCardNumber(CARD_NUMBER, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getRepresentativeByCardNumber(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetRepresentativeByCardNumber_NullFromCache() throws Exception {
    // when
    when(cache.getRepresentativeByCardNumber(anyString(), any(ClientInfo.class)))
        .thenReturn(null);
    provider.getRepresentativeByCardNumber(CARD_NUMBER, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getRepresentativeByCardNumber(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetRepresentativeById() throws Exception {
    // when
    when(cache.getRepresentativeByCriteria(any(RepresentativeRequest.class), any(ClientInfo.class)))
        .thenReturn(new Representative());
    provider.getRepresentativeById(REPRESENTATIVE_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1))
        .getRepresentativeByCriteria(any(RepresentativeRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetRepresentativeById_NullRepresentativeId() throws Exception {
    // when
    Representative representative = provider.getRepresentativeById(null, CLIENT_INFO);

    // then
    assertNull(representative);

    // verify
    verifyZeroInteractions(cache);
  }

}