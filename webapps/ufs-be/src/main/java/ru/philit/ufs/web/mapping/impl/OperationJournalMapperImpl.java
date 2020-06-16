package ru.philit.ufs.web.mapping.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.account.Representative;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.oper.OperationTaskCardDeposit;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.Subbranch;
import ru.philit.ufs.model.entity.user.User;
import ru.philit.ufs.web.dto.CardDepositDto;
import ru.philit.ufs.web.dto.OperationDto;
import ru.philit.ufs.web.dto.OperatorDto;
import ru.philit.ufs.web.dto.RepresentativeDto;
import ru.philit.ufs.web.dto.SubbranchDto;
import ru.philit.ufs.web.dto.UserDto;
import ru.philit.ufs.web.mapping.OperationJournalMapper;

@Component
public class OperationJournalMapperImpl
    extends CommonMapperImpl implements OperationJournalMapper {

  @Override
  public OperatorDto asDto(Operator in) {
    if (in == null) {
      return null;
    }
    OperatorDto out = new OperatorDto();

    out.setFullName(in.getOperatorFullName());
    out.setLastName(in.getLastName());
    out.setFirstName(in.getFirstName());
    out.setPatronymic(in.getPatronymic());
    out.setEmail(in.getEmail());
    out.setPhoneWork(in.getPhoneNumWork());
    out.setPhoneMobile(in.getPhoneNumMobile());
    out.setSubbranch(asDto(in.getSubbranch()));

    return out;
  }

  @Override
  protected SubbranchDto asDto(Subbranch in) {
    if (in == null) {
      return null;
    }
    SubbranchDto out = new SubbranchDto();

    out.setTbCode(in.getTbCode());
    out.setOsbCode(in.getOsbCode() != null ? in.getOsbCode() : in.getGosbCode());
    out.setVspCode(in.getVspCode());

    return out;
  }

  @Override
  public UserDto asDto(User in) {
    if (in == null) {
      return null;
    }
    UserDto out = new UserDto();

    out.setPosition(in.getPosition());

    return out;
  }

  @Override
  public String asDto(BigDecimal in) {
    return super.asDto(in);
  }

  @Override
  public RepresentativeDto asDto(Representative in) {
    if (in == null) {
      return null;
    }
    RepresentativeDto out = new RepresentativeDto();

    out.setLastName(in.getLastName());
    out.setFirstName(in.getFirstName());
    out.setPatronymic(in.getPatronymic());
    out.setBirthDate(asShortDateDto(in.getBirthDate()));
    out.setBirthPlace(in.getPlaceOfBirth());
    out.setInn(in.getInn());
    out.setAddress(in.getAddress());
    out.setPostcode(in.getPostindex());
    out.setResident(in.isResident());
    out.setDocument(asDocumentDto(in.getIdentityDocuments()));

    return out;
  }

  @Override
  public OperationDto asDto(Operation in) {
    if (in == null) {
      return null;
    }
    OperationDto out = new OperationDto();

    out.setId(in.getId());
    if (in.getTypeCode() != null) {
      out.setType(in.getTypeCode().value());
    }
    if (in.getStatus() != null) {
      out.setStatus(in.getStatus().value());
    }
    out.setCreatedDate(asLongDateDto(in.getCreatedDate()));
    out.setRollbackReason(in.getRollbackReason());

    return out;
  }

  @Override
  public CardDepositDto asDto(OperationTaskCardDeposit in) {
    if (in == null) {
      return null;
    }
    CardDepositDto out = new CardDepositDto();

    out.setTaskId(asDto(in.getId()));
    out.setId(in.getOvnUid());
    out.setInn(in.getInn());
    out.setKpp(in.getKpp());
    out.setAccountId(in.getAccountId());
    out.setLegalEntityShortName(in.getLegalEntityShortName());
    out.setRepFio(in.getRepFio());
    out.setAmount(asDto(in.getAmount()));

    return out;
  }

  @Override
  public Date asEntity(String in) {
    try {
      return asShortDateEntity(in);
    } catch (ParseException e) {
      return null;
    }
  }
}
