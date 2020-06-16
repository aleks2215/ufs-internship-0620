package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.core.IQueue;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemEventType;
import com.hazelcast.core.ItemListener;
import com.hazelcast.monitor.LocalQueueStats;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MockIQueue<E> extends ArrayBlockingQueue<E> implements IQueue<E> {

  private final List<ItemListener<E>> itemListeners = new ArrayList<>();

  public MockIQueue(int capacity) {
    super(capacity);
  }

  @Override
  public boolean offer(E e) {
    boolean result = super.offer(e);
    if (result) {
      for (ItemListener<E> listener : itemListeners) {
        listener.itemAdded(new ItemEvent<>("added", ItemEventType.ADDED, e, null));
      }
    }
    return result;
  }

  @Override
  public E poll() {
    E result = super.poll();
    for (ItemListener<E> listener : itemListeners) {
      listener.itemRemoved(new ItemEvent<>("removed", ItemEventType.REMOVED, result, null));
    }
    return result;
  }

  @Override
  public LocalQueueStats getLocalQueueStats() {
    return null;
  }

  @Override
  public String addItemListener(ItemListener<E> itemListener, boolean b) {
    itemListeners.add(itemListener);
    return null;
  }

  @Override
  public boolean removeItemListener(String s) {
    return false;
  }

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
}
