package ru.philit.ufs.model.converter.esb.pprb;

import org.junit.Assert;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.pprb.HeaderInfoType;

public class PprbAdapterTest extends PprbAdapterBaseTest {

  @Test
  public void testHeaderInfo() {
    HeaderInfoType headerInfo = PprbAdapter.headerInfo();
    assertHeaderInfo(headerInfo);
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(headerInfo());
    Assert.assertNull(externalEntity);
  }
}
