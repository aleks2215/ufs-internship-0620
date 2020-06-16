package ru.philit.ufs.model.converter.esb.pprb;

import java.util.ArrayList;
import ru.philit.ufs.model.entity.account.IdentityDocument;
import ru.philit.ufs.model.entity.account.IdentityDocumentType;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.account.RepresentativeRequest;
import ru.philit.ufs.model.entity.account.Sex;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.pprb.IDDtype;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRq.SrvSearchRepCardRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs.SrvSearchRepCardRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs.SrvSearchRepCardRsMessage.Representative.LegalEntity.LegalEntityItem;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRq.SrvSearchRepRqMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs.SrvSearchRepRsMessage;

/**
 * Преобразователь между сущностью Representative и соответствующим транспортным объектом.
 */
public class RepresentativeAdapter extends PprbAdapter {

  //******** Converters *******

  private static Sex sex(boolean sexBoolean) {
    return sexBoolean ? Sex.MALE : Sex.FEMALE;
  }

  private static boolean sexBoolean(Sex sex) {
    return sex != null && sex == Sex.MALE;
  }

  private static IdentityDocumentType identityDocumentType(IDDtype statusType) {
    return (statusType != null) ? IdentityDocumentType.getByCode(statusType.value()) : null;
  }

  private static IDDtype iddType(IdentityDocumentType status) {
    return (status != null) ? IDDtype.fromValue(status.code()) : null;
  }

  //******** Mappers *******

  private static void map(RepresentativeRequest repRequest, SrvSearchRepRqMessage message) {
    message.setRepID(repRequest.getId());
    message.setINN(repRequest.getInn());
    message.setFirstName(repRequest.getFirstName());
    message.setLastName(repRequest.getLastName());
    message.setPatronymic(repRequest.getPatronymic());
    message.setPhoneNumWork(repRequest.getPhoneNumWork());
    message.setPhoneNumMobile(repRequest.getPhoneNumMobile());
    message.setEmail(repRequest.getEmail());
    message.setDateOfBirth(xmlCalendar(repRequest.getBirthDate()));
    message.setSex(sexBoolean(repRequest.getSex()));
    message.setPlaceOfBirth(repRequest.getPlaceOfBirth());
    if (repRequest.getIdentityDocument() != null) {
      map(repRequest.getIdentityDocument(), message);
    }
    if (repRequest.getLegalEntity() != null) {
      map(repRequest.getLegalEntity(), message);
    }
  }

  private static void map(IdentityDocument identityDocument, SrvSearchRepRqMessage message) {
    message.setIdentityDocumentType(iddType(identityDocument.getType()));
    message.setSeries(identityDocument.getSeries());
    message.setNumber(identityDocument.getNumber());
    message.setIssuedBy(identityDocument.getIssuedBy());
    message.setIssuedDate(xmlCalendar(identityDocument.getIssuedDate()));
  }

  private static void map(LegalEntity legalEntity, SrvSearchRepRqMessage message) {
    message.setLegalEntityId(legalEntity.getId());
    message.setLegalEntityINN(legalEntity.getInn());
  }

  private static void map(SrvSearchRepRsMessage.Representative representativeItem,
      Representative representative) {
    representative.setId(representativeItem.getRepID());
    representative.setInn(representativeItem.getINN());
    representative.setFirstName(representativeItem.getFirstName());
    representative.setLastName(representativeItem.getLastName());
    representative.setPatronymic(representativeItem.getPatronymic());
    representative.setPhoneNumWork(representativeItem.getPhoneNumWork());
    representative.setPhoneNumMobile(representativeItem.getPhoneNumMobile());
    representative.setEmail(representativeItem.getEmail());
    representative.setAddress(representativeItem.getAddress());
    representative.setPostindex(representativeItem.getPostindex());
    representative.setBirthDate(date(representativeItem.getDateOfBirth()));
    representative.setSex(sex(representativeItem.isSex()));
    representative.setPlaceOfBirth(representativeItem.getPlaceOfBirth());
    representative.setResident(representativeItem.isIsResident());
    representative.setIdentityDocuments(new ArrayList<IdentityDocument>());
    for (SrvSearchRepRsMessage.Representative.IdentityDocumentType identityDocumentRep :
        representativeItem.getIdentityDocumentType()) {
      IdentityDocument identityDocument = new IdentityDocument();
      map(identityDocumentRep, identityDocument);
      representative.getIdentityDocuments().add(identityDocument);
    }
    representative.setLegalEntities(new ArrayList<LegalEntity>());
    for (SrvSearchRepRsMessage.Representative.LegalEntity.LegalEntityItem legalEntityItem :
        representativeItem.getLegalEntity().getLegalEntityItem()) {
      LegalEntity legalEntity = new LegalEntity();
      map(legalEntityItem, legalEntity);
      representative.getLegalEntities().add(legalEntity);
    }
  }

  private static void map(SrvSearchRepRsMessage.Representative.IdentityDocumentType
      identityDocumentRep, IdentityDocument identityDocument) {
    identityDocument.setType(identityDocumentType(identityDocumentRep.getValue()));
    identityDocument.setSeries(identityDocumentRep.getSeries());
    identityDocument.setNumber(identityDocumentRep.getNumber());
    identityDocument.setIssuedBy(identityDocumentRep.getIssuedBy());
    identityDocument.setIssuedDate(date(identityDocumentRep.getIssuedDate()));
  }

  private static void map(SrvSearchRepRsMessage.Representative.LegalEntity.LegalEntityItem
      legalEntityItem, LegalEntity legalEntity) {
    legalEntity.setId(legalEntityItem.getLegalEntityId());
    legalEntity.setShortName(legalEntityItem.getLegalEntityShortName());
    legalEntity.setFullName(legalEntityItem.getLegalEntityFullName());
    legalEntity.setInn(legalEntityItem.getINN());
    legalEntity.setOgrn(legalEntityItem.getOGRN());
    legalEntity.setKpp(legalEntityItem.getKPP());
    legalEntity.setLegalAddress(legalEntityItem.getLegalAddress());
    legalEntity.setFactAddress(legalEntityItem.getFactAddress());
  }

  private static void map(SrvSearchRepCardRsMessage.Representative response,
      Representative representative) {
    representative.setId(response.getRepID());
    representative.setInn(response.getINN());
    representative.setFirstName(response.getFirstName());
    representative.setLastName(response.getLastName());
    representative.setPatronymic(response.getPatronymic());
    representative.setPhoneNumWork(response.getPhoneNumWork());
    representative.setPhoneNumMobile(response.getPhoneNumMobile());
    representative.setEmail(response.getEmail());
    representative.setAddress(response.getAddress());
    representative.setPostindex(response.getPostindex());
    representative.setBirthDate(date(response.getDateOfBirth()));
    representative.setSex(sex(response.isSex()));
    representative.setPlaceOfBirth(response.getPlaceOfBirth());
    representative.setResident(response.isIsResident());
    representative.setIdentityDocuments(new ArrayList<IdentityDocument>());
    for (SrvSearchRepCardRsMessage.Representative.IdentityDocumentType identityDocumentRep :
        response.getIdentityDocumentType()) {
      IdentityDocument identityDocument = new IdentityDocument();
      map(identityDocumentRep, identityDocument);
      representative.getIdentityDocuments().add(identityDocument);
    }
    representative.setLegalEntities(new ArrayList<LegalEntity>());
    for (SrvSearchRepCardRsMessage.Representative.LegalEntity.LegalEntityItem legalEntityItem :
        response.getLegalEntity().getLegalEntityItem()) {
      LegalEntity legalEntity = new LegalEntity();
      map(legalEntityItem, legalEntity);
      representative.getLegalEntities().add(legalEntity);
    }
  }

  private static void map(
      SrvSearchRepCardRsMessage.Representative.IdentityDocumentType identityDocumentRep,
      IdentityDocument identityDocument) {
    identityDocument.setType(identityDocumentType(identityDocumentRep.getValue()));
    identityDocument.setSeries(identityDocumentRep.getSeries());
    identityDocument.setNumber(identityDocumentRep.getNumber());
    identityDocument.setIssuedBy(identityDocumentRep.getIssuedBy());
    identityDocument.setIssuedDate(date(identityDocumentRep.getIssuedDate()));
  }

  private static void map(LegalEntityItem legalEntityItem, LegalEntity legalEntity) {
    legalEntity.setId(legalEntityItem.getLegalEntityId());
    legalEntity.setShortName(legalEntityItem.getLegalEntityShortName());
    legalEntity.setFullName(legalEntityItem.getLegalEntityFullName());
    legalEntity.setInn(legalEntityItem.getINN());
    legalEntity.setOgrn(legalEntityItem.getOGRN());
    legalEntity.setKpp(legalEntityItem.getKPP());
    legalEntity.setLegalAddress(legalEntityItem.getLegalAddress());
    legalEntity.setFactAddress(legalEntityItem.getFactAddress());
  }

  //******** Methods *******

  /**
   * Возвращает объект запроса поиска ответственных лиц.
   */
  public static SrvSearchRepRq requestSearchRepCard(RepresentativeRequest repRequest) {
    SrvSearchRepRq request = new SrvSearchRepRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvSearchRepRqMessage(new SrvSearchRepRqMessage());
    map(repRequest, request.getSrvSearchRepRqMessage());
    return request;
  }

  /**
   * Возвращает объект запроса получения ответственных лиц по карте.
   */
  public static SrvGetRepByCardRq requestGetRepByCard(String cardNumber) {
    SrvGetRepByCardRq request = new SrvGetRepByCardRq();
    request.setHeaderInfo(headerInfo());
    request.setSrvSearchRepCardRqMessage(new SrvSearchRepCardRqMessage());
    request.getSrvSearchRepCardRqMessage().setCardNumber(cardNumber);
    return request;
  }

  /**
   * Преобразует транспортный объект списка Ответственных лиц во внутреннюю сущность.
   */
  public static ExternalEntityList<Representative> convert(SrvSearchRepRs response) {
    ExternalEntityList<Representative> representativeList = new ExternalEntityList<>();
    map(response.getHeaderInfo(), representativeList);

    for (SrvSearchRepRsMessage.Representative representativeItem :
        response.getSrvSearchRepRsMessage().getRepresentative()) {
      Representative representative = new Representative();
      map(response.getHeaderInfo(), representative);
      map(representativeItem, representative);
      representativeList.getItems().add(representative);
    }
    return representativeList;
  }

  /**
   * Преобразует транспортный объект Ответственное лицо во внутреннюю сущность.
   */
  public static Representative convert(SrvGetRepByCardRs response) {
    Representative representative = new Representative();
    map(response.getHeaderInfo(), representative);
    map(response.getSrvSearchRepCardRsMessage().getRepresentative(), representative);
    return representative;
  }
}
