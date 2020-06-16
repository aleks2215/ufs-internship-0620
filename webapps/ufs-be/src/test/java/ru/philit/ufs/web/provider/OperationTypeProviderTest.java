package ru.philit.ufs.web.provider;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.philit.ufs.model.cache.OperationTypeCache;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.user.ClientInfo;

public class OperationTypeProviderTest {

  private static final ClientInfo CLIENT_INFO = new ClientInfo();

  private final Javers javers = JaversBuilder.javers().build();

  @Mock
  private OperationTypeCache cache;

  private OperationTypeProvider provider;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    provider = new OperationTypeProvider(cache, javers);
  }

  @Test
  public void testGetOperationTypes() throws Exception {
    // given
    List<OperationType> operationTypesFromCache = singletonList(new OperationType());

    // when
    when(cache.getOperationTypes(any(ClientInfo.class))).thenReturn(operationTypesFromCache);
    List<OperationType> operationTypes = provider.getOperationTypes(CLIENT_INFO);

    // then
    assertNotNull(operationTypes);

    // verify
    verify(cache, times(1)).getOperationTypes(any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetOperationTypes_NullFromCache() throws Exception {
    // when
    when(cache.getOperationTypes(any(ClientInfo.class))).thenReturn(null);
    List<OperationType> operationTypes = provider.getOperationTypes(CLIENT_INFO);

    // then
    assertNotNull(operationTypes);
    assertTrue(operationTypes.isEmpty());

    // verify
    verify(cache, times(1)).getOperationTypes(any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetFavourites() throws Exception {
    // given
    List<OperationTypeFavourite> favouritesFromCache = singletonList(new OperationTypeFavourite());

    // when
    when(cache.getOperationTypeFavourites(any(ClientInfo.class))).thenReturn(favouritesFromCache);
    List<OperationTypeFavourite> favourites = provider.getFavourites(CLIENT_INFO);

    // then
    assertNotNull(favourites);

    // verify
    verify(cache, times(1)).getOperationTypeFavourites(any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetFavourites_NullFromCache() throws Exception {
    // when
    when(cache.getOperationTypeFavourites(any(ClientInfo.class))).thenReturn(null);
    List<OperationTypeFavourite> favourites = provider.getFavourites(CLIENT_INFO);

    // then
    assertNotNull(favourites);
    assertTrue(favourites.isEmpty());

    // verify
    verify(cache, times(1)).getOperationTypeFavourites(any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testSaveFavourites() throws Exception {
    // given
    List<OperationTypeFavourite> favourites = singletonList(new OperationTypeFavourite());

    // when
    doNothing().when(cache).saveOperationTypeFavourites(
        anyListOf(OperationTypeFavourite.class), anyListOf(OperationTypeFavourite.class),
        any(ClientInfo.class)
    );
    provider.saveFavourites(favourites, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getOperationTypeFavourites(any(ClientInfo.class));
    verify(cache, times(1)).saveOperationTypeFavourites(
        anyListOf(OperationTypeFavourite.class), anyListOf(OperationTypeFavourite.class),
        any(ClientInfo.class)
    );
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testSaveFavourites_NoChanges() throws Exception {
    // given
    List<OperationTypeFavourite> current = singletonList(new OperationTypeFavourite(1L, 0));
    List<OperationTypeFavourite> previous = singletonList(new OperationTypeFavourite(1L, 0));

    // when
    when(cache.getOperationTypeFavourites(any(ClientInfo.class))).thenReturn(previous);
    doNothing().when(cache).saveOperationTypeFavourites(
        anyListOf(OperationTypeFavourite.class), anyListOf(OperationTypeFavourite.class),
        any(ClientInfo.class)
    );
    provider.saveFavourites(current, CLIENT_INFO);

    // verify
    verify(cache, times(1)).getOperationTypeFavourites(any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetCashSymbols() throws Exception {
    // given
    List<CashSymbol> symbolsFromCache = singletonList(new CashSymbol());

    // when
    when(cache.getCashSymbols(any(CashSymbolRequest.class), any(ClientInfo.class)))
        .thenReturn(symbolsFromCache);
    List<CashSymbol> symbols = provider.getCashSymbols("Code", CLIENT_INFO);

    // then
    assertNotNull(symbols);

    // verify
    verify(cache, times(1)).getCashSymbols(any(CashSymbolRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

  @Test
  public void testGetCashSymbols_NullFromCache() throws Exception {
    // when
    when(cache.getCashSymbols(any(CashSymbolRequest.class), any(ClientInfo.class)))
        .thenReturn(null);
    List<CashSymbol> symbols = provider.getCashSymbols("Code", CLIENT_INFO);

    // then
    assertNotNull(symbols);
    assertTrue(symbols.isEmpty());

    // verify
    verify(cache, times(1)).getCashSymbols(any(CashSymbolRequest.class), any(ClientInfo.class));
    verifyNoMoreInteractions(cache);
  }

}