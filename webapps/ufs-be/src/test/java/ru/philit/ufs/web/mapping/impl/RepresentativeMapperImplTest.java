package ru.philit.ufs.web.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.web.dto.RepresentativeDto;
import ru.philit.ufs.web.mapping.RepresentativeMapper;

public class RepresentativeMapperImplTest {

  private static final String REPRESENTATIVE_ID = "BB374D51ABE946A98A6963F59767DC4F";
  private static final String INN = "628726651431";
  private static final String LAST_NAME = "Петров";
  private static final String FIRST_NAME = "Петр";
  private static final String PATRONYMIC = "Петрович";
  private static final String PHONE_NUM_WORK = "+7-495-900-00-00";
  private static final String PHONE_NUM_MOBILE = "+7-495-900-00-00";
  private static final String EMAIL = "petrov@nodomen.nono";
  private static final String ADDRESS = "улица Кремль, г. Москва";
  private static final String POST_INDEX = "101000";
  private static final String PLACE_OF_BIRTH = "г. Москва Родильный дом № 14";
  private static final boolean RESIDENT = true;

  private static final String SERIES = "2005";
  private static final String NUMBER = "895656";
  private static final String ISSUEDBY =
      "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург";
  private static final String DATE = "01.07.2017";

  private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  private final RepresentativeMapper mapper = new RepresentativeMapperImpl();

  @Test
  public void testAsDto_Representative() throws Exception {
    // given
    Representative entity = getRepresentative();

    // when
    RepresentativeDto dto = mapper.asDto(entity);

    // then
    assertNotNull(dto);
    assertEquals(dto.getId(), REPRESENTATIVE_ID);
    assertEquals(dto.getInn(), INN);
    assertEquals(dto.getLastName(), LAST_NAME);
    assertEquals(dto.getFirstName(), FIRST_NAME);
    assertEquals(dto.getPatronymic(), PATRONYMIC);
    assertEquals(dto.getPhoneWork(), PHONE_NUM_WORK);
    assertEquals(dto.getPhoneMobile(), PHONE_NUM_MOBILE);
    assertEquals(dto.getEmail(), EMAIL);
    assertEquals(dto.getAddress(), ADDRESS);
    assertEquals(dto.getPostcode(), POST_INDEX);
    assertEquals(dto.getBirthDate(), DATE);
    assertEquals(dto.getBirthPlace(), PLACE_OF_BIRTH);
    assertEquals(dto.isResident(), RESIDENT);
  }

  @Test
  public void testAsDto_Representative_NullEntity() throws Exception {
    // when
    RepresentativeDto dto = mapper.asDto(null);

    // then
    assertNull(dto);
  }

  @Test
  public void testAsDto_IdentityDocument_Sort() throws Exception {
    // given
    List<IdentityDocument> identityDocuments = new ArrayList<>(4);
    identityDocuments.add(0, getIdentityDocument(IdentityDocumentType.SEAMEN_ID));
    identityDocuments.add(1, getIdentityDocument(IdentityDocumentType.MILITARY_ID));
    identityDocuments.add(2, getIdentityDocument(IdentityDocumentType.INTERNAL_PASSPORT));
    identityDocuments.add(3, getIdentityDocument(IdentityDocumentType.PASSPORT));

    // when
    Collections.sort(identityDocuments);

    // then
    assertEquals(identityDocuments.get(0).getType(), IdentityDocumentType.PASSPORT);
    assertEquals(identityDocuments.get(1).getType(), IdentityDocumentType.INTERNAL_PASSPORT);
    assertEquals(identityDocuments.get(2).getType(), IdentityDocumentType.MILITARY_ID);
    assertEquals(identityDocuments.get(3).getType(), IdentityDocumentType.SEAMEN_ID);
  }

  private Representative getRepresentative() throws Exception {
    Representative entity = new Representative();

    entity.setId(REPRESENTATIVE_ID);
    entity.setInn(INN);
    entity.setLastName(LAST_NAME);
    entity.setFirstName(FIRST_NAME);
    entity.setPatronymic(PATRONYMIC);
    entity.setPhoneNumWork(PHONE_NUM_WORK);
    entity.setPhoneNumMobile(PHONE_NUM_MOBILE);
    entity.setEmail(EMAIL);
    entity.setAddress(ADDRESS);
    entity.setPostindex(POST_INDEX);
    entity.setBirthDate(shortDateFormat.parse(DATE));
    entity.setPlaceOfBirth(PLACE_OF_BIRTH);
    entity.setResident(RESIDENT);

    return entity;
  }

  private IdentityDocument getIdentityDocument(IdentityDocumentType type) throws Exception {
    IdentityDocument entity = new IdentityDocument();

    entity.setType(type);
    entity.setSeries(SERIES);
    entity.setNumber(NUMBER);
    entity.setIssuedBy(ISSUEDBY);
    entity.setIssuedDate(shortDateFormat.parse(DATE));

    return entity;
  }

}
