package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import ru.philit.ufs.model.entity.common.OperationTypeCode;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.OperationType;
import ru.philit.ufs.model.entity.oper.OperationTypeFavourite;
import ru.philit.ufs.web.dto.CashSymbolDto;
import ru.philit.ufs.web.dto.OperationTypeDto;
import ru.philit.ufs.web.mapping.OperationTypeMapper;

public class OperationTypeMapperImplTest {

  private static final String ID = "46474545L";
  private static final String NAME = "Name";
  private static final OperationTypeCode CODE = OperationTypeCode.FROM_CARD_WITHDRAW;
  private static final long CATEGORY_ID = 37355L;
  private static final String CATEGORY_NAME = "CategoryName";
  private static final boolean VISIBLE = true;
  private static final boolean ENABLED = false;
  private static final String SYMBOL_CODE = "Code";
  private static final String DESCRIPTION = "Description";

  private final OperationTypeMapper mapper = new OperationTypeMapperImpl();

  @Test
  public void testAsDto_OperationType() throws Exception {
    // given
    OperationType entity = getOperationType();

    // when
    OperationTypeDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), String.valueOf(ID));
    assertEquals(dto.getName(), NAME);
    assertEquals(dto.getCode(), CODE.code());
    assertEquals(dto.getCategoryId(), String.valueOf(CATEGORY_ID));
    assertEquals(dto.getCategoryName(), CATEGORY_NAME);
    assertEquals(dto.isEnabled(), ENABLED);
  }

  @Test
  public void testAsDto_OperationType_NullEntity() throws Exception {
    // when
    OperationTypeDto dto = mapper.asDto(null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_OperationTypeFavourites() throws Exception {
    // given
    List<OperationTypeFavourite> entities = getOperationTypeFavourites();
    String[] typeIds = {"102", "103", "101", "104"};

    // when
    List<String> list = mapper.asFavouriteDto(entities);

    // then
    assertNotNull(list);
    assertThat(list, IsIterableContainingInOrder.contains(typeIds));
  }

  @Test
  public void testAsDto_OperationTypeFavourites_NullEntity() throws Exception {
    // when
    List<String> list = mapper.asFavouriteDto(null);

    // then
    assertNotNull(list);
    assertTrue(list.isEmpty());
  }

  @Test
  public void testAsEntity_CashSymbols() throws Exception {
    // given
    List<CashSymbol> entities = Collections.singletonList(getCashSymbol());

    // when
    List<CashSymbolDto> list = mapper.asSymbolDto(entities);

    // then
    assertNotNull(list);
    assertFalse(list.isEmpty());

    CashSymbolDto dto = list.get(0);
    assertEquals(dto.getCode(), SYMBOL_CODE);
    assertEquals(dto.getDesc(), DESCRIPTION);
  }

  @Test
  public void testAsEntity_CashSymbols_NullEntity() throws Exception {
    // when
    List<CashSymbolDto> list = mapper.asSymbolDto(null);

    // then
    assertNotNull(list);
    assertTrue(list.isEmpty());
  }

  @Test
  public void testAsEntity_FavouriteIds() throws Exception {
    // given
    List<String> typeIds = Arrays.asList("101", "102", "103");

    // when
    List<OperationTypeFavourite> favourites = mapper.asEntity(typeIds);

    // then
    assertNotNull(favourites);
    assertEquals(favourites.size(), 3);
    OperationTypeFavourite favourite1 = favourites.get(0);
    OperationTypeFavourite favourite2 = favourites.get(1);
    OperationTypeFavourite favourite3 = favourites.get(2);
    assertTrue(favourite1.getIndex() < favourite2.getIndex());
    assertTrue(favourite2.getIndex() < favourite3.getIndex());
  }

  @Test
  public void testAsEntity_FavouriteIds_NullDto() throws Exception {
    // when
    List<OperationTypeFavourite> list = mapper.asEntity(null);

    // then
    assertNotNull(list);
    assertTrue(list.isEmpty());
  }

  private OperationType getOperationType() {
    OperationType entity = new OperationType();

    entity.setId(ID);
    entity.setName(NAME);
    entity.setCode(CODE);
    entity.setCategoryId(CATEGORY_ID);
    entity.setCategoryName(CATEGORY_NAME);
    entity.setVisible(VISIBLE);
    entity.setEnabled(ENABLED);
    return entity;
  }

  private List<OperationTypeFavourite> getOperationTypeFavourites() {
    List<OperationTypeFavourite> favourites = new ArrayList<>();

    favourites.add(new OperationTypeFavourite(101L, 2));
    favourites.add(new OperationTypeFavourite(102L, 0));
    favourites.add(new OperationTypeFavourite(103L, 1));
    favourites.add(new OperationTypeFavourite(104L, null));
    return favourites;
  }

  private CashSymbol getCashSymbol() {
    CashSymbol entity = new CashSymbol();

    entity.setCode(SYMBOL_CODE);
    entity.setDescription(DESCRIPTION);

    return entity;
  }
}
