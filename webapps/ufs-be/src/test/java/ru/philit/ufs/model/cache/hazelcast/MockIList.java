package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.core.IList;
import com.hazelcast.core.ItemListener;
import java.util.ArrayList;

public class MockIList<E> extends ArrayList<E> implements IList<E> {

  @Override
  public String getPartitionKey() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public String getServiceName() {
    return null;
  }

  @Override
  public void destroy() {

  }

  @Override
  public String addItemListener(ItemListener<E> listener, boolean includeValue) {
    return null;
  }

  @Override
  public boolean removeItemListener(String registrationId) {
    return false;
  }
}
