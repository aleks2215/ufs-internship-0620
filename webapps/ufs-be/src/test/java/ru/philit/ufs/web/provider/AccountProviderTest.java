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
import ru.philit.ufs.model.cache.AccountCache;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.exception.InvalidDataException;

public class AccountProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo("1", new User("login"), "2");
  private static final String CARD_NUMBER = "4279987965419873";
  private static final String ACCOUNT_ID = "3562212";

  @Mock
  private AccountCache cache;

  private AccountProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new AccountProvider(cache);
  }

  @Test
  public void testGetAccount() throws Exception {
    // given
    Account account = new Account();

    // when
    when(cache.getAccount(anyString(), any(ClientInfo.class))).thenReturn(account);
    provider.getAccount(CARD_NUMBER, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getAccount(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetAccount_NullFromCache() throws Exception {
    // when
    when(cache.getAccount(anyString(), any(ClientInfo.class))).thenReturn(null);
    provider.getAccount(CARD_NUMBER, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getAccount(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetLegalEntity() throws Exception {
    // given
    LegalEntity legalEntity = new LegalEntity();

    // when
    when(cache.getLegalEntity(anyString(), any(ClientInfo.class))).thenReturn(legalEntity);
    provider.getLegalEntity(ACCOUNT_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getLegalEntity(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetLegalEntity_NullFromCache() throws Exception {
    // when
    when(cache.getLegalEntity(anyString(), any(ClientInfo.class))).thenReturn(null);
    provider.getLegalEntity(ACCOUNT_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getLegalEntity(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetAccountResidues() throws Exception {
    // given
    AccountResidues accountResidues = new AccountResidues();

    // when
    when(cache.getAccountResidues(anyString(), any(ClientInfo.class))).thenReturn(accountResidues);
    provider.getAccountResidues(ACCOUNT_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getAccountResidues(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test(expected = InvalidDataException.class)
  public void testGetAccountResidues_NullFromCache() throws Exception {
    // when
    when(cache.getAccountResidues(anyString(), any(ClientInfo.class))).thenReturn(null);
    provider.getAccountResidues(ACCOUNT_ID, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getAccountResidues(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetCardIndexes1() throws Exception {
    // given
    List<PaymentOrderCardIndex1> indexesFromCache = singletonList(new PaymentOrderCardIndex1());

    // when
    when(cache.getCardIndexes1(anyString(), any(ClientInfo.class))).thenReturn(indexesFromCache);
    List<PaymentOrderCardIndex1> indexes = provider.getCardIndexes1(ACCOUNT_ID, CLIENT_INFO);

    // then
    assertNotNull(indexes);

    // verify
    verify(cache, times(1)).getCardIndexes1(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetCardIndexes1_NullFromCache() throws Exception {
    // when
    when(cache.getCardIndexes1(anyString(), any(ClientInfo.class))).thenReturn(null);
    List<PaymentOrderCardIndex1> indexes = provider.getCardIndexes1(ACCOUNT_ID, CLIENT_INFO);

    // then
    assertNotNull(indexes);
    assertTrue(indexes.isEmpty());

    // verify
    verify(cache, times(1)).getCardIndexes1(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetCardIndexes2() throws Exception {
    // given
    List<PaymentOrderCardIndex2> indexesFromCache = singletonList(new PaymentOrderCardIndex2());

    // when
    when(cache.getCardIndexes2(anyString(), any(ClientInfo.class))).thenReturn(indexesFromCache);
    List<PaymentOrderCardIndex2> indexes = provider.getCardIndexes2(ACCOUNT_ID, CLIENT_INFO);

    // then
    assertNotNull(indexes);

    // verify
    verify(cache, times(1)).getCardIndexes2(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetCardIndexes2_NullFromCache() throws Exception {
    // when
    when(cache.getCardIndexes2(anyString(), any(ClientInfo.class))).thenReturn(null);
    List<PaymentOrderCardIndex2> indexes = provider.getCardIndexes2(ACCOUNT_ID, CLIENT_INFO);

    // then
    assertNotNull(indexes);
    assertTrue(indexes.isEmpty());

    // verify
    verify(cache, times(1)).getCardIndexes2(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetSeizures() throws Exception {
    // given
    List<Seizure> seizuresFromCache = singletonList(new Seizure());

    // when
    when(cache.getSeizures(anyString(), any(ClientInfo.class))).thenReturn(seizuresFromCache);
    List<Seizure> seizures = provider.getSeizures(ACCOUNT_ID, CLIENT_INFO);

    // then
    assertNotNull(seizures);

    // verify
    verify(cache, times(1)).getSeizures(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetSeizures_NullFromCache() throws Exception {
    // when
    when(cache.getSeizures(anyString(), any(ClientInfo.class))).thenReturn(null);
    List<Seizure> seizures = provider.getSeizures(ACCOUNT_ID, CLIENT_INFO);

    // then
    assertNotNull(seizures);
    assertTrue(seizures.isEmpty());

    // verify
    verify(cache, times(1)).getSeizures(anyString(), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

}