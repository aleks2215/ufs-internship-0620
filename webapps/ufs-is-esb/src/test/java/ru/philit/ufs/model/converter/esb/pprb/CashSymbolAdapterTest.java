package ru.philit.ufs.model.converter.esb.pprb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.philit.ufs.model.converter.esb.multi.MultiAdapter;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityList;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRq;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs.SrvCashSymbolsListRsMessage;
import ru.philit.ufs.model.entity.esb.pprb.SrvCashSymbolsListRs.SrvCashSymbolsListRsMessage.CashSymbolItem;
import ru.philit.ufs.model.entity.oper.CashSymbol;
import ru.philit.ufs.model.entity.oper.CashSymbolRequest;

public class CashSymbolAdapterTest extends PprbAdapterBaseTest {

  private SrvCashSymbolsListRs response;
  private final String cashSymbolCode = "02";
  private final String cashSymbolDesc = "Символ 02";
  private final Boolean cashSymbolGetList = true;

  /**
   * Set up test data.
   */
  @Before
  public void setUp() {
    response = new SrvCashSymbolsListRs();
    response.setHeaderInfo(headerInfo());
    response.setSrvCashSymbolsListRsMessage(new SrvCashSymbolsListRsMessage());
    CashSymbolItem cashSymbolItem = new CashSymbolItem();
    cashSymbolItem.setCashSymbol(cashSymbolCode);
    cashSymbolItem.setCashSymbolDesc(cashSymbolDesc);
    response.getSrvCashSymbolsListRsMessage().getCashSymbolItem().add(cashSymbolItem);
  }

  @Test
  public void testRequestCashSymbol() {
    CashSymbolRequest cashSymbolRequest = new CashSymbolRequest();
    cashSymbolRequest.setCode(cashSymbolCode);
    cashSymbolRequest.setGetList(cashSymbolGetList);
    SrvCashSymbolsListRq request = CashSymbolAdapter.requestCashSymbol(cashSymbolRequest);
    assertHeaderInfo(request.getHeaderInfo());
    Assert.assertNotNull(request.getSrvCashSymbolsListRqMessage());
    Assert.assertEquals(request.getSrvCashSymbolsListRqMessage().getCashSymbol(), cashSymbolCode);
    Assert.assertEquals(request.getSrvCashSymbolsListRqMessage().isGetList(), cashSymbolGetList);
  }

  @Test
  public void testConvertSrvCashSymbolsListRs() {
    ExternalEntityList<CashSymbol> cashSymbolList = CashSymbolAdapter.convert(response);
    assertHeaderInfo(cashSymbolList);
    Assert.assertEquals(cashSymbolList.getItems().size(), 1);
    Assert.assertEquals(cashSymbolList.getItems().get(0).getCode(), cashSymbolCode);
    Assert.assertEquals(cashSymbolList.getItems().get(0).getDescription(), cashSymbolDesc);
  }

  @Test
  public void testMultiAdapter() {
    ExternalEntity externalEntity = MultiAdapter.convert(response);
    Assert.assertNotNull(externalEntity);
    Assert.assertEquals(externalEntity.getClass(), ExternalEntityList.class);
    Assert.assertEquals(((ExternalEntityList) externalEntity).getItems().size(), 1);
    Assert.assertEquals(((ExternalEntityList) externalEntity).getItems().get(0).getClass(),
        CashSymbol.class);
  }
}
