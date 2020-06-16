package ru.philit.ufs.web.provider;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.cache.AnnouncementCache;
import ru.philit.ufs.model.entity.account.AccountOperationRequest;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncement;
import ru.philit.ufs.model.entity.oper.CashDepositAnnouncementsRequest;
import ru.philit.ufs.model.entity.oper.OvnStatus;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

public class AnnouncementProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User("login"), "2");
  private static final String ACCOUNT_ID = "74563";
  private static final String STATUS = OvnStatus.PENDING.code();
  private static final String ANNOUNCEMENT_ID = "352140412";
  private static final String AMOUNT = "27858.32";
  private static final String TYPE_CODE = OperationTypeCode.TO_CARD_DEPOSIT.code();
  private static final String WORKPLACE_ID = "236240192";

  @Mock
  private AnnouncementCache cache;
  @Mock
  private UserProvider userProvider;

  private AnnouncementProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new AnnouncementProvider(cache, userProvider);
  }

  @Test
  public void testGetAnnouncements() throws Exception {
    // given
    List<CashDepositAnnouncement> listFromCache = singletonList(new CashDepositAnnouncement());

    // when
    when(cache.getAnnouncements(any(CashDepositAnnouncementsRequest.class), any(ClientInfo.class)))
        .thenReturn(listFromCache);
    List<CashDepositAnnouncement> announcements = provider
        .getAnnouncements(ACCOUNT_ID, STATUS, CLIENT_INFO);

    // then
    assertNotNull(announcements);

    // verify
    verify(cache, times(1))
        .getAnnouncements(any(CashDepositAnnouncementsRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetAnnouncements_NullFromCache() throws Exception {
    // when
    when(cache.getAnnouncements(any(CashDepositAnnouncementsRequest.class), any(ClientInfo.class)))
        .thenReturn(null);
    List<CashDepositAnnouncement> announcements = provider
        .getAnnouncements(ACCOUNT_ID, STATUS, CLIENT_INFO);

    // then
    assertNotNull(announcements);
    assertTrue(announcements.isEmpty());

    // verify
    verify(cache, times(1))
        .getAnnouncements(any(CashDepositAnnouncementsRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetAnnouncements_NullStatus() throws Exception {
    // when
    when(cache.getAnnouncements(any(CashDepositAnnouncementsRequest.class), any(ClientInfo.class)))
        .thenReturn(new ArrayList<CashDepositAnnouncement>());
    List<CashDepositAnnouncement> announcements = provider
        .getAnnouncements(ACCOUNT_ID, null, CLIENT_INFO);

    // then
    assertNotNull(announcements);

    // verify
    verify(cache, times(1))
        .getAnnouncements(any(CashDepositAnnouncementsRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetAnnouncement() throws Exception {
    // when
    when(cache.getAnnouncementById(anyString(), any(ClientInfo.class)))
        .thenReturn(new CashDepositAnnouncement());
    provider.getAnnouncement(ANNOUNCEMENT_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getAnnouncementById(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetAnnouncement_NullAnnouncementId() throws Exception {
    // when
    provider.getAnnouncement(null, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

  @Test
  public void testGetCommission() throws Exception {
    // when
    doNothing().when(userProvider)
        .checkWorkplaceIncreasedAmount(any(BigDecimal.class), any(ClientInfo.class));
    when(cache.getCommission(any(AccountOperationRequest.class), any(ClientInfo.class)))
        .thenReturn(BigDecimal.ONE);
    provider.getCommission(ACCOUNT_ID, AMOUNT, TYPE_CODE, CLIENT_INFO);

    // verify
    verify(userProvider, times(1))
        .checkWorkplaceIncreasedAmount(any(BigDecimal.class), any(ClientInfo.class));
    verify(cache, times(1))
        .getCommission(any(AccountOperationRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(userProvider);
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetCommission_NullAccountId() throws Exception {
    // when
    provider.getCommission(null, AMOUNT, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(userProvider);
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetCommission_NullAmount() throws Exception {
    // when
    provider.getCommission(ACCOUNT_ID, null, TYPE_CODE, CLIENT_INFO);

    // verify
    verifyZeroInteractions(userProvider);
    verifyZeroInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetCommission_NullTypeCode() throws Exception {
    // when
    provider.getCommission(ACCOUNT_ID, AMOUNT, null, CLIENT_INFO);

    // verify
    verifyZeroInteractions(userProvider);
    verifyZeroInteractions(cache);
  }

  @Test
  public void testGetAccount20202() throws Exception {
    // when
    when(cache.getAccount20202(anyString(), any(ClientInfo.class))).thenReturn(ACCOUNT_ID);
    provider.getAccount20202(WORKPLACE_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getAccount20202(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetAccount20202_NullWorkplaceId() throws Exception {
    // when
    provider.getAccount20202(null, CLIENT_INFO);

    // verify
    verifyZeroInteractions(cache);
  }

}