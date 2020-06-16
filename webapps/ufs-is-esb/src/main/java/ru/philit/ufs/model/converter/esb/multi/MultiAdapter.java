package ru.philit.ufs.model.converter.esb.multi;

import ru.philit.ufs.model.converter.esb.eks.AccountAdapter;
import ru.philit.ufs.model.converter.esb.eks.CheckFraudAdapter;
import ru.philit.ufs.model.converter.esb.eks.CommissionAdapter;
import ru.philit.ufs.model.converter.esb.eks.LegalEntityAdapter;
import ru.philit.ufs.model.converter.esb.eks.OperationPackageAdapter;
import ru.philit.ufs.model.converter.esb.eks.PaymentOrderAdapter;
import ru.philit.ufs.model.converter.esb.eks.SeizureAdapter;
import ru.philit.ufs.model.converter.esb.pprb.Account20202Adapter;
import ru.philit.ufs.model.converter.esb.pprb.CashDepositAnnouncementAdapter;
import ru.philit.ufs.model.converter.esb.pprb.CashSymbolAdapter;
import ru.philit.ufs.model.converter.esb.pprb.OperationTypeAdapter;
import ru.philit.ufs.model.converter.esb.pprb.OperatorAdapter;
import ru.philit.ufs.model.converter.esb.pprb.RepresentativeAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByCardNumRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAccountResiduesByIdRs;
import ru.philit.ufs.model.entity.esb.eks.SrvAddTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCardIndexElementsByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCheckWithFraudRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCountCommissionRs;
import ru.philit.ufs.model.entity.esb.eks.SrvCreateClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLEAccountListRs;
import ru.philit.ufs.model.entity.esb.eks.SrvLegalEntityByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvSeizureByAccountRs;
import ru.philit.ufs.model.entity.esb.eks.SrvUpdTaskClOperPkgRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashDepAnmntListByAccountIdRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCreateCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetCashDepAnmntItemRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetOperatorInfoByUserRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetRepByCardRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGetUserOperationsByRoleRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvSearchRepRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvUpdCashDepAnmntItemRs;

/**
 * Преобразователь ответов от Мастер-систем.
 */
public class MultiAdapter {

  private MultiAdapter() {
  }

  /**
   * Преобразует произвольный транспортный объект во внутреннюю сущность.
   */
  public static ExternalEntity convert(Object source) {
    if (source == null) {
      return null;

    } else if (source instanceof SrvAccountByCardNumRs) {
      return AccountAdapter.convert((SrvAccountByCardNumRs) source);

    } else if (source instanceof SrvAccountByIdRs) {
      return AccountAdapter.convert((SrvAccountByIdRs) source);

    } else if (source instanceof SrvLEAccountListRs) {
      return AccountAdapter.convert((SrvLEAccountListRs) source);

    } else if (source instanceof SrvAccountResiduesByIdRs) {
      return AccountAdapter.convert((SrvAccountResiduesByIdRs) source);

    } else if (source instanceof SrvLegalEntityByAccountRs) {
      return LegalEntityAdapter.convert((SrvLegalEntityByAccountRs) source);

    } else if (source instanceof SrvAddTaskClOperPkgRs) {
      return OperationPackageAdapter.convert((SrvAddTaskClOperPkgRs) source);

    } else if (source instanceof SrvCheckClOperPkgRs) {
      return OperationPackageAdapter.convert((SrvCheckClOperPkgRs) source);

    } else if (source instanceof SrvCreateClOperPkgRs) {
      return OperationPackageAdapter.convert((SrvCreateClOperPkgRs) source);

    } else if (source instanceof SrvGetTaskClOperPkgRs) {
      return OperationPackageAdapter.convert((SrvGetTaskClOperPkgRs) source);

    } else if (source instanceof SrvUpdTaskClOperPkgRs) {
      return OperationPackageAdapter.convert((SrvUpdTaskClOperPkgRs) source);

    } else if (source instanceof SrvCardIndexElementsByAccountRs) {
      return PaymentOrderAdapter.convert((SrvCardIndexElementsByAccountRs) source);

    } else if (source instanceof SrvSeizureByAccountRs) {
      return SeizureAdapter.convert((SrvSeizureByAccountRs) source);

    } else if (source instanceof SrvCountCommissionRs) {
      return CommissionAdapter.convert((SrvCountCommissionRs) source);

    } else if (source instanceof SrvCheckWithFraudRs) {
      return CheckFraudAdapter.convert((SrvCheckWithFraudRs) source);

    } else if (source instanceof SrvCashDepAnmntListByAccountIdRs) {
      return CashDepositAnnouncementAdapter.convert((SrvCashDepAnmntListByAccountIdRs) source);

    } else if (source instanceof SrvCreateCashDepAnmntItemRs) {
      return CashDepositAnnouncementAdapter.convert((SrvCreateCashDepAnmntItemRs) source);

    } else if (source instanceof SrvGetCashDepAnmntItemRs) {
      return CashDepositAnnouncementAdapter.convert((SrvGetCashDepAnmntItemRs) source);

    } else if (source instanceof SrvUpdCashDepAnmntItemRs) {
      return CashDepositAnnouncementAdapter.convert((SrvUpdCashDepAnmntItemRs) source);

    } else if (source instanceof SrvGet20202NumRs) {
      return Account20202Adapter.convert((SrvGet20202NumRs) source);

    } else if (source instanceof SrvSearchRepRs) {
      return RepresentativeAdapter.convert((SrvSearchRepRs) source);

    } else if (source instanceof SrvGetUserOperationsByRoleRs) {
      return OperationTypeAdapter.convert((SrvGetUserOperationsByRoleRs) source);

    } else if (source instanceof SrvGetRepByCardRs) {
      return RepresentativeAdapter.convert((SrvGetRepByCardRs) source);

    } else if (source instanceof SrvGetOperatorInfoByUserRs) {
      return OperatorAdapter.convert((SrvGetOperatorInfoByUserRs) source);

    } else if (source instanceof SrvCashSymbolsListRs) {
      return CashSymbolAdapter.convert((SrvCashSymbolsListRs) source);
    }

    return null;
  }
}
