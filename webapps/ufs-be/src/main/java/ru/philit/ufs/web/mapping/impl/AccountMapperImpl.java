package ru.philit.ufs.web.mapping.impl;

import static com.google.common.collect.ComparisonChain.start;
import static com.google.common.collect.Ordering.natural;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.philit.ufs.model.entity.account.Account;
import ru.philit.ufs.model.entity.account.AccountResidues;
import ru.philit.ufs.model.entity.account.Agreement;
import ru.philit.ufs.model.entity.account.LegalEntity;
import ru.philit.ufs.model.entity.account.Seizure;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex1;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2;
import ru.philit.ufs.model.entity.oper.PaymentOrderCardIndex2.PaidPart;
import ru.philit.ufs.web.dto.AccountDto;
import ru.philit.ufs.web.dto.AccountResiduesDto;
import ru.philit.ufs.web.dto.AgreementDto;
import ru.philit.ufs.web.dto.LegalEntityDto;
import ru.philit.ufs.web.dto.PaidDto;
import ru.philit.ufs.web.dto.PaymentOrderCardIndex1Dto;
import ru.philit.ufs.web.dto.PaymentOrderCardIndex2Dto;
import ru.philit.ufs.web.dto.SeizureDto;
import ru.philit.ufs.web.mapping.AccountMapper;

@Component
public class AccountMapperImpl extends CommonMapperImpl implements AccountMapper {

  private final Comparator<PaymentOrderCardIndex1> cardIndex1Comparator =
      new Comparator<PaymentOrderCardIndex1>() {
        @Override
        public int compare(PaymentOrderCardIndex1 o1, PaymentOrderCardIndex1 o2) {
          return start().compare(o1.getNum(), o2.getNum(), natural().nullsLast()).result();
        }
      };

  private final Comparator<PaymentOrderCardIndex2> cardIndex2Comparator =
      new Comparator<PaymentOrderCardIndex2>() {
        @Override
        public int compare(PaymentOrderCardIndex2 o1, PaymentOrderCardIndex2 o2) {
          return start().compare(o1.getNum(), o2.getNum(), natural().nullsLast()).result();
        }
      };

  private final Comparator<Seizure> seizureComparator = new Comparator<Seizure>() {
    @Override
    public int compare(Seizure o1, Seizure o2) {
      return start().compare(o1.getId(), o2.getId(), natural().nullsLast()).result();
    }
  };

  @Override
  public AccountDto asDto(Account in) {
    if (in == null) {
      return null;
    }
    AccountDto out = new AccountDto();

    out.setId(in.getId());
    out.setStatus(in.getStatus());
    out.setChangeStatusDesc(in.getChangeStatusDescription());
    if (in.getType() != null) {
      out.setType(in.getType().value());
    }
    if (in.getAccountancyType() != null) {
      out.setAccountancyType(in.getAccountancyType().value());
    }
    out.setCurrencyType(in.getCurrencyType());
    out.setCurrencyCode(in.getCurrencyCode());
    out.setAgreement(asDto(in.getAgreement()));
    out.setSubbranch(asDto(in.getSubbranch()));
    out.setLastTransactionDate(asShortDateDto(in.getLastTransactionDate()));

    return out;
  }

  @Override
  public LegalEntityDto asDto(LegalEntity in) {
    if (in == null) {
      return null;
    }
    LegalEntityDto out = new LegalEntityDto();

    out.setId(in.getId());
    out.setShortName(in.getShortName());
    out.setFullName(in.getFullName());
    out.setInn(in.getInn());
    out.setOgrn(in.getOgrn());
    out.setKpp(in.getKpp());
    out.setLegalAddress(in.getLegalAddress());
    out.setFactAddress(in.getFactAddress());

    return out;
  }

  @Override
  public AccountResiduesDto asDto(AccountResidues in) {
    if (in == null) {
      return null;
    }
    AccountResiduesDto out = new AccountResiduesDto();

    if (in.getCurrentBalance() != null) {
      out.setCurrentBalance(asDto(in.getCurrentBalance().getCurrentBalance()));
    }
    out.setFixedBalance(asDto(in.getFixedBalance()));
    out.setExpectedBalance(asDto(in.getExpectedBalance()));

    return out;
  }

  @Override
  public PaymentOrderCardIndex1Dto asDto(PaymentOrderCardIndex1 in) {
    if (in == null) {
      return null;
    }
    PaymentOrderCardIndex1Dto out = new PaymentOrderCardIndex1Dto();

    out.setDocId(in.getDocId());
    out.setDocType(in.getDocType());
    out.setRecipientShortName(in.getRecipientShortName());
    out.setAmount(asDto(in.getAmount()));
    out.setComeInDate(asShortDateDto(in.getComeInDate()));

    return out;
  }

  @Override
  public PaymentOrderCardIndex2Dto asDto(PaymentOrderCardIndex2 in) {
    if (in == null) {
      return null;
    }
    PaymentOrderCardIndex2Dto out = new PaymentOrderCardIndex2Dto();

    out.setDocId(in.getDocId());
    out.setDocType(in.getDocType());
    out.setRecipientShortName(in.getRecipientShortName());
    out.setTotalAmount(asDto(in.getTotalAmount()));
    out.setPaidPartly(in.isPaidPartly());
    out.setPartAmount(asDto(in.getPartAmount()));
    out.setComeInDate(asShortDateDto(in.getComeInDate()));
    if (!CollectionUtils.isEmpty(in.getPaidParts())) {
      out.setPaids(new ArrayList<PaidDto>());

      for (PaidPart paidPart : in.getPaidParts()) {
        out.getPaids().add(asDto(paidPart));
      }
    }
    if (in.getPaymentPriority() != null) {
      out.setPriority(in.getPaymentPriority().code());
    }

    return out;
  }

  @Override
  public SeizureDto asDto(Seizure in) {
    if (in == null) {
      return null;
    }
    SeizureDto out = new SeizureDto();

    out.setType(in.getType());
    out.setReason(in.getReason());
    out.setFromDate(asShortDateDto(in.getFromDate()));
    out.setToDate(asShortDateDto(in.getToDate()));
    out.setAmount(asDto(in.getAmount()));
    out.setInitiatorShortName(in.getInitiatorShortName());

    return out;
  }

  private AgreementDto asDto(Agreement in) {
    if (in == null) {
      return null;
    }
    AgreementDto out = new AgreementDto();

    out.setId(in.getId());
    out.setOpenDate(asShortDateDto(in.getOpenDate()));
    out.setCloseDate(asShortDateDto(in.getCloseDate()));

    return out;
  }

  private PaidDto asDto(PaidPart in) {
    PaidDto out = new PaidDto();

    out.setDate(asShortDateDto(in.getPaidDate()));
    out.setAmount(asDto(in.getPaidAmount()));

    return out;
  }

  @Override
  public List<PaymentOrderCardIndex1Dto> asIndex1Dto(List<PaymentOrderCardIndex1> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    if (in.size() > 1) {
      Collections.sort(in, cardIndex1Comparator);
    }
    List<PaymentOrderCardIndex1Dto> out = new ArrayList<>();

    for (PaymentOrderCardIndex1 cardIndex1 : in) {
      out.add(asDto(cardIndex1));
    }
    return out;
  }

  @Override
  public List<PaymentOrderCardIndex2Dto> asIndex2Dto(List<PaymentOrderCardIndex2> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    if (in.size() > 1) {
      Collections.sort(in, cardIndex2Comparator);
    }
    List<PaymentOrderCardIndex2Dto> out = new ArrayList<>();

    for (PaymentOrderCardIndex2 cardIndex2 : in) {
      out.add(asDto(cardIndex2));
    }
    return out;
  }

  @Override
  public List<SeizureDto> asSeizureDto(List<Seizure> in) {
    if (in == null) {
      return Collections.emptyList();
    }
    if (in.size() > 1) {
      Collections.sort(in, seizureComparator);
    }
    List<SeizureDto> out = new ArrayList<>();

    for (Seizure seizure : in) {
      out.add(asDto(seizure));
    }
    return out;
  }
}
