package ru.philit.ufs.model.converter.esb.pprb;

import org.junit.Assert;
import ru.philit.ufs.model.converter.ConverterBaseTest;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.pprb.HeaderInfoType;

public abstract class PprbAdapterBaseTest extends ConverterBaseTest {

  protected static HeaderInfoType headerInfo() {
    return PprbAdapter.headerInfo();
  }

  protected static HeaderInfoType headerInfo(String rqUid) {
    HeaderInfoType headerInfo = PprbAdapter.headerInfo();
    headerInfo.setRqUID(rqUid);
    return headerInfo;
  }

  protected static void assertHeaderInfo(HeaderInfoType headerInfo) {
    Assert.assertNotNull(headerInfo);
    Assert.assertNotNull(headerInfo.getRqUID());
    Assert.assertNotNull(headerInfo.getRqTm());
    Assert.assertEquals(headerInfo.getSpName(), PprbAdapter.REQUEST_SYSTEM);
    Assert.assertEquals(headerInfo.getSystemId(), PprbAdapter.RESPONSE_SYSTEM);
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
