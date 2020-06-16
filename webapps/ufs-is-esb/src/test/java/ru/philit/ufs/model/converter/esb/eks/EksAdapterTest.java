package ru.philit.ufs.model.converter.esb.eks;

import org.junit.Assert;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.esb.eks.HeaderInfoType;

public class EksAdapterTest extends EksAdapterBaseTest {

  @Test
  public void testHeaderInfo() {
    HeaderInfoType headerInfo = EksAdapter.headerInfo();
    assertHeaderInfo(headerInfo);
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(headerInfo());
    Assert.assertNull(externalEntity);
  }
}
