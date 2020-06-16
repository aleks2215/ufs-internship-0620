package ru.philit.ufs.web.mapping.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.philit.ufs.model.entity.oper.OperationTypeLimit;
import ru.philit.ufs.model.entity.user.Operator;
import ru.philit.ufs.model.entity.user.SessionUser;
import ru.philit.ufs.model.entity.user.Workplace;
import ru.philit.ufs.web.dto.OperationTypeLimitDto;
import ru.philit.ufs.web.dto.OperatorDto;
import ru.philit.ufs.web.dto.UserDto;
import ru.philit.ufs.web.dto.WorkplaceDto;
import ru.philit.ufs.web.mapping.UserMapper;

@Component
public class UserMapperImpl extends CommonMapperImpl implements UserMapper {

  @Override
  public UserDto asDto(SessionUser in) {
    if (in == null) {
      return null;
    }
    UserDto out = new UserDto();

    out.setLogin(in.getLogin());
    out.setSessionId(in.getSessionId());
    out.setRoleId(in.getRoleId());
    out.setEmployeeId(in.getEmployeeId());
    out.setLastName(in.getLastName());
    out.setFirstName(in.getFirstName());
    out.setPatronymic(in.getPatronymic());
    out.setEmail(in.getEmail());
    out.setPosition(in.getPosition());

    return out;
  }

  @Override
  public OperatorDto asDto(Operator in) {
    if (in == null) {
      return null;
    }
    OperatorDto out = new OperatorDto();

    out.setWorkplaceId(in.getWorkplaceId());
    out.setSubbranch(asDto(in.getSubbranch()));
    out.setFullName(in.getOperatorFullName());
    out.setLastName(in.getLastName());
    out.setFirstName(in.getFirstName());
    out.setPatronymic(in.getPatronymic());
    out.setEmail(in.getEmail());
    out.setPhoneMobile(in.getPhoneNumMobile());
    out.setPhoneWork(in.getPhoneNumWork());

    return out;
  }

  @Override
  public WorkplaceDto asDto(Workplace in) {
    if (in == null) {
      return null;
    }
    WorkplaceDto out = new WorkplaceDto();

    out.setSubbranchCode(in.getSubbranchCode());
    out.setCashboxDeviceId(in.getCashboxDeviceId());
    out.setCashboxDeviceType(in.getCashboxDeviceType());
    out.setCurrencyType(in.getCurrencyType());
    out.setAmount(asDto(in.getAmount()));
    out.setLimit(asDto(in.getLimit()));
    out.setCategoryLimits(asLimitDto(in.getCategoryLimits()));

    return out;
  }

  private OperationTypeLimitDto asDto(OperationTypeLimit in) {
    OperationTypeLimitDto out = new OperationTypeLimitDto();

    out.setCategoryId(in.getCategoryId());
    out.setLimit(asDto(in.getLimit()));

    return out;
  }

  private List<OperationTypeLimitDto> asLimitDto(List<OperationTypeLimit> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    List<OperationTypeLimitDto> out = new ArrayList<>();

    for (OperationTypeLimit typeLimit : in) {
      out.add(asDto(typeLimit));
    }
    return out;
  }
}
