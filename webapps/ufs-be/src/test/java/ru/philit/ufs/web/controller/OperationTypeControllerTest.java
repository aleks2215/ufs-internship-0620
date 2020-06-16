package ru.philit.ufs.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.model.entity.user.ClientInfo;
import ru.philit.ufs.web.mapping.OperationTypeMapper;
import ru.philit.ufs.web.mapping.impl.OperationTypeMapperImpl;
import ru.philit.ufs.web.provider.OperationTypeProvider;
import ru.philit.ufs.web.view.GetFavouritesResp;
import ru.philit.ufs.web.view.GetOperationTypesResp;
import ru.philit.ufs.web.view.SaveFavouritesReq;
import ru.philit.ufs.web.view.SaveFavouritesResp;

public class OperationTypeControllerTest extends RestControllerTest {

  @Mock
  private OperationTypeProvider provider;
  @Spy
  private OperationTypeMapper mapper = new OperationTypeMapperImpl();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    standaloneSetup(new OperationTypeController(provider, mapper));
  }

  @Test
  public void testGetOperationTypes() throws Exception {
    List<OperationType> operationTypes = Arrays.asList(
        new OperationType("1", null, "First type", null, null, true, true),
        new OperationType("2", null, "Second type", null, null, true, true),
        new OperationType("2", null, "Second type", null, null, false, true)
    );

    when(provider.getOperationTypes(any(ClientInfo.class))).thenReturn(operationTypes);

    String responseJson = performAndGetContent(post("/operationTypes/"));
    GetOperationTypesResp response = toResponse(responseJson, GetOperationTypesResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
    assertThat(response.getData(), hasSize(2));

    verify(provider, times(1)).getOperationTypes(any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testGetFavourites() throws Exception {
    List<OperationTypeFavourite> favourites = new ArrayList<>();

    when(provider.getFavourites(any(ClientInfo.class))).thenReturn(favourites);

    String responseJson = performAndGetContent(post("/operationTypes/getFavourites"));
    GetFavouritesResp response = toResponse(responseJson, GetFavouritesResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
    assertThat(response.getData(), hasSize(favourites.size()));

    verify(provider, times(1)).getFavourites(any(ClientInfo.class));
    verifyNoMoreInteractions(provider);
  }

  @Test
  public void testSaveFavourites() throws Exception {
    SaveFavouritesReq request = new SaveFavouritesReq();
    request.setTypeIds(Arrays.asList("1", "2", "3"));
    String requestJson = toRequest(request);

    when(provider.saveFavourites(
        anyListOf(OperationTypeFavourite.class), any(ClientInfo.class)
    )).thenReturn(true);

    String urlTemplate = "/operationTypes/saveFavourites";
    String responseJson = performAndGetContent(post(urlTemplate).content(requestJson));
    SaveFavouritesResp response = toResponse(responseJson, SaveFavouritesResp.class);

    assertTrue(response.isSuccess());
    assertNotNull(response.getData());
    assertTrue(response.getData());

    verify(provider, times(1)).saveFavourites(
        anyListOf(OperationTypeFavourite.class), any(ClientInfo.class)
    );
    verifyNoMoreInteractions(provider);
  }
}
