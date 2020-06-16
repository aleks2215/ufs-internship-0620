package ru.philit.ufs.web.mapping.impl;

import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.web.dto.RepresentativeDto;
import ru.philit.ufs.web.mapping.RepresentativeMapper;

@Component
public class RepresentativeMapperImpl extends CommonMapperImpl implements RepresentativeMapper {

  @Override
  public RepresentativeDto asDto(Representative in) {
    if (in == null) {
      return null;
    }
    RepresentativeDto out = new RepresentativeDto();

    out.setId(in.getId());
    out.setInn(in.getInn());
    out.setLastName(in.getLastName());
    out.setFirstName(in.getFirstName());
    out.setPatronymic(in.getPatronymic());
    out.setPhoneWork(in.getPhoneNumWork());
    out.setPhoneMobile(in.getPhoneNumMobile());
    out.setEmail(in.getEmail());
    out.setAddress(in.getAddress());
    out.setPostcode(in.getPostindex());
    out.setBirthDate(asShortDateDto(in.getBirthDate()));
    out.setBirthPlace(in.getPlaceOfBirth());
    out.setResident(in.isResident());
    out.setDocument(asDocumentDto(in.getIdentityDocuments()));

    return out;
  }

}
