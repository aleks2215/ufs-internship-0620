package ru.philit.ufs.model.cache.mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.entity.account.Card;
import ru.philit.ufs.model.entity.oper.Operation;
import ru.philit.ufs.model.entity.user.User;

@RunWith(DataProviderRunner.class)
public class MockCacheImplTest {

  private final MockCache cache = new MockCacheImpl();

  /**
   * Список проверяемых логинов пользователей.
   */
  @DataProvider
  public static Object[] userLogins() {
    return new Object[]{
        "Ivanov_II",
        "Sidorov_SS",
        "Svetlova_SS"
    };
  }

  @Test
  @UseDataProvider("userLogins")
  public void testGetUser(String userLogin) throws Exception {
    // when
    User user = cache.getUser(userLogin, "");

    // then
    assertNotNull(user);
  }

  @Test
  public void testGetUser_NullLogin() throws Exception {
    // when
    User user = cache.getUser(null, "");

    // then
    assertNull(user);
  }

  @Test
  public void testGetUser_NotFoundLogin() throws Exception {
    // given
    String userLogin = "Random";

    // when
    User user = cache.getUser(userLogin, "");

    // then
    assertNull(user);
  }

  @Test
  public void testGetCreditCard() throws Exception {
    // when
    Card card = cache.getCreditCard();

    // then
    assertNotNull(card);
  }

  @Test
  public void testCreateOperation() throws Exception {
    // given
    String workplaceId = "AC11921E8E1247009ED17924B8CD9E72";
    String operationTypeCode = "ToCardDeposit";

    // when
    Operation operation = cache.createOperation(workplaceId, operationTypeCode);

    // then
    assertNotNull(operation);
  }

  @Test
  public void testCommitOperation() throws Exception {
    // given
    Operation operation = new Operation();

    // when
    operation = cache.commitOperation(operation);

    // then
    assertNotNull(operation);
  }
}
