package ru.philit.ufs.model.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;
import ru.philit.ufs.model.entity.esb.as_fs.LimitStatusType;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvCreateCashOrderRs.SrvCreateCashOrderRsMessage.KO1;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRq;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRs;
import ru.philit.ufs.model.entity.esb.as_fs.SrvUpdStCashOrderRs.SrvUpdCashOrderRsMessage;
import ru.philit.ufs.model.entity.esb.eks.PkgTaskStatusType;
import ru.philit.ufs.model.entity.esb.eks.SrvGetTaskClOperPkgRs.SrvGetTaskClOperPkgRsMessage;
import ru.philit.ufs.model.entity.oper.OperationPackageInfo;

/**
 * Кеш данных Mock приложения.
 */
public interface MockCache {

  Long saveTaskCardDeposit(Long packageId, Long taskId, Object taskBody);

  Long saveTaskCardWithdraw(Long packageId, Long taskId, Object taskBody);

  Long saveTaskAccountDeposit(Long packageId, Long taskId, Object taskBody);

  Long saveTaskAccountWithdraw(Long packageId, Long taskId, Object taskBody);

  Long saveTaskCheckbookIssuing(Long packageId, Long taskId, Object taskBody);

  void saveTaskStatus(Long taskId, PkgTaskStatusType status);

  Long checkPackage(String inn);

  OperationPackageInfo createPackage(String inn, String workplaceId, String userLogin);

  OperationPackageInfo getPackageInfo(Long packageId);

  Map<Long, List<SrvGetTaskClOperPkgRsMessage.PkgItem.ToCardDeposit.TaskItem>>
      searchTasksCardDeposit(Long packageId, PkgTaskStatusType taskStatus, Date fromDate,
      Date toDate, List<Long> taskIds);

  void createCashOrder(SrvCreateCashOrderRs cashOrderRs, String userLogin);

  void updateStatusCashOrder(SrvUpdStCashOrderRs updStCashOrderRs);

  LimitStatusType checkCashOrdersLimitByUser(String userLogin);

}
