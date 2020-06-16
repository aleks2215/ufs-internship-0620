package ru.philit.ufs.model.converter.esb.eks;

import org.junit.Assert;
import ru.philit.ufs.model.converter.ConverterBaseTest;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.eks.HeaderInfoType;

public abstract class EksAdapterBaseTest extends ConverterBaseTest {

  /**
   * Возврфщфет заполненный HeaderInfoType.
   */
  public static HeaderInfoType headerInfo() {
    return EksAdapter.headerInfo();
  }

  /**
   * Возврфщфет заполненный HeaderInfoType.
   * @param rqUid заданный requestId
   */
  public static HeaderInfoType headerInfo(String rqUid) {
    HeaderInfoType headerInfo = EksAdapter.headerInfo();
    headerInfo.setRqUID(rqUid);
    return headerInfo;
  }

  protected static void assertHeaderInfo(HeaderInfoType headerInfo) {
    Assert.assertNotNull(headerInfo);
    Assert.assertNotNull(headerInfo.getRqUID());
    Assert.assertNotNull(headerInfo.getRqTm());
    Assert.assertEquals(headerInfo.getSpName(), EksAdapter.REQUEST_SYSTEM);
    Assert.assertEquals(headerInfo.getSystemId(), EksAdapter.RESPONSE_SYSTEM);
  }

  protected static void assertHeaderInfo(ExternalEntity externalEntity) {
    Assert.assertNotNull(externalEntity.getReceiveDate());
    Assert.assertNotNull(externalEntity.getRequestUid());
  }

  protected static void assertHeaderInfo(ExternalEntity externalEntity, String rqUid) {
    Assert.assertNotNull(externalEntity.getReceiveDate());
    Assert.assertEquals(externalEntity.getRequestUid(), rqUid);
  }
}
