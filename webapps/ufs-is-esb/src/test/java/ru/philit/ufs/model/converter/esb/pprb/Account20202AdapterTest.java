package ru.philit.ufs.model.converter.esb.pprb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityContainer;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvGet20202NumRs.SrvGet20202NumRsMessage;

public class Account20202AdapterTest extends PprbAdapterBaseTest {

  private SrvGet20202NumRs response;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvGet20202NumRs();
    response.setHeaderInfo(headerInfo());
    response.setSrvGet20202NumRsMessage(new SrvGet20202NumRsMessage());
    response.getSrvGet20202NumRsMessage().setAccount20202Num("20202897357400146292");
  }

  @Test
  public void testRequestByWorkPlace() {
    SrvGet20202NumRq request = Account20202Adapter.requestByWorkPlace("NHJ786JHKJ979");
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvGet20202NumRqMessage());
    Assert.assertEquals(request.getSrvGet20202NumRqMessage().getWorkPlaceUId(), "NHJ786JHKJ979");
  }

  @Test
  public void testConvertSrvGet20202NumRs() {
    ExternalEntityContainer<String> container = Account20202Adapter.convert(response);
    assertHeaderInfo(container);
    Assert.assertEquals(container.getData(), "20202897357400146292");
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityContainer.class);
    Assert.assertEquals(((ExternalEntityContainer) externalEntity).getData().getClass(),
        String.class);
  }
}
