package ru.philit.ufs.model.converter.esb.pprb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.account.Sex;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.pprb.IDDtype;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs.SrvSearchRepCardRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs.SrvSearchRepRsMessage;

public class RepresentativeAdapterTest extends PprbAdapterBaseTest {

  private static final String FIX_UUID = "a55ed415-3976-41f8-916c-4c17ca79e969";

  private RepresentativeRequest repRequest;
  private SrvSearchRepRs response1;
  private SrvGetRepByCardRs response2;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    repRequest = new RepresentativeRequest();
    repRequest.setId("BB374D51ABE946A98A6963F59767DC4F");
    repRequest.setFirstName("Петр");
    repRequest.setLastName("Петров");
    repRequest.setPatronymic("Петрович");
    repRequest.setPhoneNumWork("+7-495-900-00-00");
    repRequest.setPhoneNumMobile("+7-900-900-00-00");
    repRequest.setEmail("petrov@nodomen.nono");
    repRequest.setBirthDate(date(1967, 8, 13, 0, 0));
    repRequest.setPlaceOfBirth("г. Москва");
    repRequest.setSex(Sex.MALE);
    repRequest.setIdentityDocument(new IdentityDocument());
    repRequest.getIdentityDocument().setType(IdentityDocumentType.INTERNAL_PASSPORT);
    repRequest.getIdentityDocument().setSeries("4233");
    repRequest.getIdentityDocument().setNumber("983017");
    repRequest.getIdentityDocument().setIssuedBy(
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    repRequest.getIdentityDocument().setIssuedDate(date(2010, 1, 1, 0, 0));
    repRequest.setLegalEntity(new LegalEntity());
    repRequest.getLegalEntity().setId("8FF9ACBDF006486CAE88109BBD7A8680");
    repRequest.getLegalEntity().setInn("0278000222");

    response1 = new SrvSearchRepRs();
    response1.setHeaderInfo(headerInfo(FIX_UUID));
    response1.setSrvSearchRepRsMessage(new SrvSearchRepRsMessage());
    SrvSearchRepRsMessage.Representative representative =
        new SrvSearchRepRsMessage.Representative();
    representative.setRepID("BB374D51ABE946A98A6963F59767DC4F");
    representative.setFirstName("Петр");
    representative.setLastName("Петров");
    representative.setPatronymic("Петрович");
    representative.setPhoneNumWork("+7-495-900-00-00");
    representative.setPhoneNumMobile("+7-900-900-00-00");
    representative.setEmail("petrov@nodomen.nono");
    representative.setDateOfBirth(xmlCalendar(1967, 8, 13, 0, 0));
    representative.setPlaceOfBirth("г. Москва");
    representative.setIsResident(true);
    representative.setSex(true);
    SrvSearchRepRsMessage.Representative.IdentityDocumentType identityDocumentType =
        new SrvSearchRepRsMessage.Representative.IdentityDocumentType();
    identityDocumentType.setValue(IDDtype.INTERNPASSPORT);
    identityDocumentType.setSeries("4233");
    identityDocumentType.setNumber("983017");
    identityDocumentType.setIssuedBy(
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    identityDocumentType.setIssuedDate(xmlCalendar(2010, 1, 1, 0, 0));
    representative.getIdentityDocumentType().add(identityDocumentType);
    representative.setLegalEntity(new SrvSearchRepRsMessage.Representative.LegalEntity());
    SrvSearchRepRsMessage.Representative.LegalEntity.LegalEntityItem legalEntity =
        new SrvSearchRepRsMessage.Representative.LegalEntity.LegalEntityItem();
    legalEntity.setLegalEntityId("8FF9ACBDF006486CAE88109BBD7A8680");
    legalEntity.setLegalEntityShortName("ООО \"ДНК\"");
    legalEntity.setLegalEntityFullName(
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    legalEntity.setINN("0278000222");
    legalEntity.setOGRN("1030224552966");
    legalEntity.setKPP("027811001");
    legalEntity.setLegalAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    legalEntity.setFactAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 2");
    representative.getLegalEntity().getLegalEntityItem().add(legalEntity);
    response1.getSrvSearchRepRsMessage().getRepresentative().add(representative);

    response2 = new SrvGetRepByCardRs();
    response2.setHeaderInfo(headerInfo(FIX_UUID));
    response2.setSrvSearchRepCardRsMessage(new SrvSearchRepCardRsMessage());
    SrvSearchRepCardRsMessage.Representative representative2 =
        new SrvSearchRepCardRsMessage.Representative();
    representative2.setRepID("BB374D51ABE946A98A6963F59767DC4F");
    representative2.setFirstName("Петр");
    representative2.setLastName("Петров");
    representative2.setPatronymic("Петрович");
    representative2.setPhoneNumWork("+7-495-900-00-00");
    representative2.setPhoneNumMobile("+7-900-900-00-00");
    representative2.setEmail("petrov@nodomen.nono");
    representative2.setDateOfBirth(xmlCalendar(1967, 8, 13, 0, 0));
    representative2.setPlaceOfBirth("г. Москва");
    representative2.setIsResident(true);
    representative2.setSex(true);
    SrvSearchRepCardRsMessage.Representative.IdentityDocumentType identityDocumentType2 =
        new SrvSearchRepCardRsMessage.Representative.IdentityDocumentType();
    identityDocumentType2.setValue(IDDtype.INTERNPASSPORT);
    identityDocumentType2.setSeries("4233");
    identityDocumentType2.setNumber("983017");
    identityDocumentType2.setIssuedBy(
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    identityDocumentType2.setIssuedDate(xmlCalendar(2010, 1, 1, 0, 0));
    representative2.getIdentityDocumentType().add(identityDocumentType2);
    representative2.setLegalEntity(new SrvSearchRepCardRsMessage.Representative.LegalEntity());
    SrvSearchRepCardRsMessage.Representative.LegalEntity.LegalEntityItem legalEntity2 =
        new SrvSearchRepCardRsMessage.Representative.LegalEntity.LegalEntityItem();
    legalEntity2.setLegalEntityId("8FF9ACBDF006486CAE88109BBD7A8680");
    legalEntity2.setLegalEntityShortName("ООО \"ДНК\"");
    legalEntity2.setLegalEntityFullName(
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    legalEntity2.setINN("0278000222");
    legalEntity2.setOGRN("1030224552966");
    legalEntity2.setKPP("027811001");
    legalEntity2.setLegalAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    legalEntity2.setFactAddress(
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 2");
    representative2.getLegalEntity().getLegalEntityItem().add(legalEntity2);
    response2.getSrvSearchRepCardRsMessage().setRepresentative(representative2);
  }

  @Test
  public void testRequestSearchRepCard() {
    SrvSearchRepRq request = RepresentativeAdapter.requestSearchRepCard(repRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvSearchRepRqMessage());
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getRepID(),
        "BB374D51ABE946A98A6963F59767DC4F");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getFirstName(), "Петр");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getLastName(), "Петров");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getPatronymic(), "Петрович");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getPhoneNumWork(),
        "+7-495-900-00-00");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getPhoneNumMobile(),
        "+7-900-900-00-00");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getEmail(), "petrov@nodomen.nono");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getDateOfBirth(),
        xmlCalendar(1967, 8, 13, 0, 0));
    Assert.assertEquals(request.getSrvSearchRepRqMessage().isSex(), true);
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getIdentityDocumentType(),
        IDDtype.INTERNPASSPORT);
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getSeries(), "4233");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getNumber(), "983017");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getIssuedBy(),
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    Assert.assertEquals(request.getSrvSearchRepRqMessage().getIssuedDate(),
        xmlCalendar(2010, 1, 1, 0, 0));
  }

  @Test
  public void testConvertSrvSearchRepCardRs() {
    ExternalEntityList<Representative> representativeList = RepresentativeAdapter
        .convert(response1);
    assertHeaderInfo(representativeList, FIX_UUID);
    Assert.assertEquals(representativeList.getItems().size(), 1);
    Assert.assertEquals(representativeList.getItems().get(0).getId(),
        "BB374D51ABE946A98A6963F59767DC4F");
    Assert.assertEquals(representativeList.getItems().get(0).getFirstName(), "Петр");
    Assert.assertEquals(representativeList.getItems().get(0).getLastName(), "Петров");
    Assert.assertEquals(representativeList.getItems().get(0).getPatronymic(), "Петрович");
    Assert.assertEquals(representativeList.getItems().get(0).getPhoneNumWork(),
        "+7-495-900-00-00");
    Assert.assertEquals(representativeList.getItems().get(0).getPhoneNumMobile(),
        "+7-900-900-00-00");
    Assert.assertEquals(representativeList.getItems().get(0).getEmail(), "petrov@nodomen.nono");
    Assert.assertEquals(representativeList.getItems().get(0).getBirthDate(),
        date(1967, 8, 13, 0, 0));
    Assert.assertEquals(representativeList.getItems().get(0).getPlaceOfBirth(), "г. Москва");
    Assert.assertTrue(representativeList.getItems().get(0).isResident());
    Assert.assertEquals(representativeList.getItems().get(0).getSex(), Sex.MALE);
    Assert.assertNotNull(representativeList.getItems().get(0).getIdentityDocuments());
    Assert.assertEquals(representativeList.getItems().get(0).getIdentityDocuments().size(), 1);
    Assert
        .assertEquals(representativeList.getItems().get(0).getIdentityDocuments().get(0).getType(),
            IdentityDocumentType.INTERNAL_PASSPORT);
    Assert.assertEquals(representativeList.getItems().get(0).getIdentityDocuments().get(0)
        .getSeries(), "4233");
    Assert.assertEquals(representativeList.getItems().get(0).getIdentityDocuments().get(0)
        .getNumber(), "983017");
    Assert.assertEquals(representativeList.getItems().get(0).getIdentityDocuments().get(0)
            .getIssuedBy(),
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    Assert.assertEquals(representativeList.getItems().get(0).getIdentityDocuments().get(0)
        .getIssuedDate(), date(2010, 1, 1, 0, 0));
    Assert.assertNotNull(representativeList.getItems().get(0).getLegalEntities());
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().size(), 1);
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0).getId(),
        "8FF9ACBDF006486CAE88109BBD7A8680");
    Assert
        .assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0).getShortName(),
            "ООО \"ДНК\"");
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0)
            .getFullName(),
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0).getInn(),
        "0278000222");
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0).getOgrn(),
        "1030224552966");
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0).getKpp(),
        "027811001");
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0)
            .getLegalAddress(),
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    Assert.assertEquals(representativeList.getItems().get(0).getLegalEntities().get(0)
            .getFactAddress(),
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 2");
  }


  @Test
  public void testRequestGetRepByCard() {
    SrvGetRepByCardRq request = RepresentativeAdapter.requestGetRepByCard("123456789");
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvSearchRepCardRqMessage());
    Assert.assertEquals(request.getSrvSearchRepCardRqMessage().getCardNumber(), "123456789");
  }

  @Test
  public void testConvertSrvGetRepByCardRs() {
    Representative representative = RepresentativeAdapter.convert(response2);
    assertHeaderInfo(representative, FIX_UUID);
    Assert.assertEquals(representative.getId(),
        "BB374D51ABE946A98A6963F59767DC4F");
    Assert.assertEquals(representative.getFirstName(), "Петр");
    Assert.assertEquals(representative.getLastName(), "Петров");
    Assert.assertEquals(representative.getPatronymic(), "Петрович");
    Assert.assertEquals(representative.getPhoneNumWork(), "+7-495-900-00-00");
    Assert.assertEquals(representative.getPhoneNumMobile(), "+7-900-900-00-00");
    Assert.assertEquals(representative.getEmail(), "petrov@nodomen.nono");
    Assert.assertEquals(representative.getBirthDate(), date(1967, 8, 13, 0, 0));
    Assert.assertEquals(representative.getPlaceOfBirth(), "г. Москва");
    Assert.assertTrue(representative.isResident());
    Assert.assertEquals(representative.getSex(), Sex.MALE);
    Assert.assertNotNull(representative.getIdentityDocuments());
    Assert.assertEquals(representative.getIdentityDocuments().size(), 1);
    Assert.assertEquals(representative.getIdentityDocuments().get(0).getType(),
        IdentityDocumentType.INTERNAL_PASSPORT);
    Assert.assertEquals(representative.getIdentityDocuments().get(0).getSeries(), "4233");
    Assert.assertEquals(representative.getIdentityDocuments().get(0).getNumber(), "983017");
    Assert.assertEquals(representative.getIdentityDocuments().get(0).getIssuedBy(),
        "ТП № 29 отдела УФМС России по Санкт-Петербургу в Кировском р-не гор. Санкт-Петербург");
    Assert.assertEquals(representative.getIdentityDocuments().get(0).getIssuedDate(),
        date(2010, 1, 1, 0, 0));
    Assert.assertNotNull(representative.getLegalEntities());
    Assert.assertEquals(representative.getLegalEntities().size(), 1);
    Assert.assertEquals(representative.getLegalEntities().get(0).getId(),
        "8FF9ACBDF006486CAE88109BBD7A8680");
    Assert
        .assertEquals(representative.getLegalEntities().get(0).getShortName(), "ООО \"ДНК\"");
    Assert.assertEquals(representative.getLegalEntities().get(0).getFullName(),
        "Общество с ограниченной ответственностью \"Дороголюбовская Нефтедобывающая Компания\"");
    Assert.assertEquals(representative.getLegalEntities().get(0).getInn(), "0278000222");
    Assert.assertEquals(representative.getLegalEntities().get(0).getOgrn(), "1030224552966");
    Assert.assertEquals(representative.getLegalEntities().get(0).getKpp(), "027811001");
    Assert.assertEquals(representative.getLegalEntities().get(0).getLegalAddress(),
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 1");
    Assert.assertEquals(representative.getLegalEntities().get(0).getFactAddress(),
        "124533, Россия, Тамбовская область, п. Дороголюбовский , ул. Ленина, 10, корп. 1, оф. 2");
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity1 = MultiAdapter.convert(response1);
    Assert.assertNotNull(externalEntity1);
    Assert.assertEquals(externalEntity1.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity1).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntity1).getItems().get(0).getClass(),
        Representative.class);

    ExternalEntity externalEntity2 = MultiAdapter.convert(response2);
    Assert.assertNotNull(externalEntity2);
    Assert.assertEquals(externalEntity2.getClass(), Representative.class);
  }
}
