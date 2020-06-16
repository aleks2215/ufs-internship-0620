package ru.philit.ufs.model.cache.hazelcast;

import com.hazelcast.aggregation.Aggregator;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.map.MapInterceptor;
import com.hazelcast.map.QueryCache;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.map.listener.MapPartitionLostListener;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.aggregation.Aggregation;
import com.hazelcast.mapreduce.aggregation.Supplier;
import com.hazelcast.monitor.LocalMapStats;
import com.hazelcast.projection.Projection;
import com.hazelcast.query.Predicate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MockIMap<K, V> extends HashMap<K, V>
    implements IMap<K, V> {

  @Override
  public void removeAll(Predicate<K, V> predicate) {

  }

  @Override
  public void delete(Object o) {

  }

  @Override
  public void flush() {

  }

  @Override
  public Map<K, V> getAll(Set<K> set) {
    return null;
  }

  @Override
  public void loadAll(boolean b) {

  }

  @Override
  public void loadAll(Set<K> set, boolean b) {

  }

  @Override
  public ICompletableFuture<V> getAsync(K k) {
    return null;
  }

  @Override
  public ICompletableFuture<V> putAsync(K k, V v) {
    return null;
  }

  @Override
  public ICompletableFuture<V> putAsync(K k, V v, long l, TimeUnit timeUnit) {
    return null;
  }

  @Override
  public ICompletableFuture<Void> setAsync(K k, V v) {
    return null;
  }

  @Override
  public ICompletableFuture<Void> setAsync(K k, V v, long l, TimeUnit timeUnit) {
    return null;
  }

  @Override
  public ICompletableFuture<V> removeAsync(K k) {
    return null;
  }

  @Override
  public boolean tryRemove(K k, long l, TimeUnit timeUnit) {
    return false;
  }

  @Override
  public boolean tryPut(K k, V v, long l, TimeUnit timeUnit) {
    return false;
  }

  @Override
  public V put(K k, V v, long l, TimeUnit timeUnit) {
    return null;
  }

  @Override
  public void putTransient(K k, V v, long l, TimeUnit timeUnit) {

  }

  @Override
  public V putIfAbsent(K k, V v, long l, TimeUnit timeUnit) {
    return null;
  }

  @Override
  public void set(K k, V v) {

  }

  @Override
  public void set(K k, V v, long l, TimeUnit timeUnit) {

  }

  @Override
  public void lock(K k) {

  }

  @Override
  public void lock(K k, long l, TimeUnit timeUnit) {

  }

  @Override
  public boolean isLocked(K k) {
    return false;
  }

  @Override
  public boolean tryLock(K k) {
    return false;
  }

  @Override
  public boolean tryLock(K k, long l, TimeUnit timeUnit) throws InterruptedException {
    return false;
  }

  @Override
  public boolean tryLock(K k, long l, TimeUnit timeUnit, long l1, TimeUnit timeUnit1)
      throws InterruptedException {
    return false;
  }

  @Override
  public void unlock(K k) {

  }

  @Override
  public void forceUnlock(K k) {

  }

  @Override
  public String addLocalEntryListener(MapListener mapListener) {
    return null;
  }

  @Override
  public String addLocalEntryListener(EntryListener entryListener) {
    return null;
  }

  @Override
  public String addLocalEntryListener(MapListener mapListener, Predicate<K, V> predicate,
      boolean b) {
    return null;
  }

  @Override
  public String addLocalEntryListener(EntryListener entryListener, Predicate<K, V> predicate,
      boolean b) {
    return null;
  }

  @Override
  public String addLocalEntryListener(MapListener mapListener, Predicate<K, V> predicate, K k,
      boolean b) {
    return null;
  }

  @Override
  public String addLocalEntryListener(EntryListener entryListener, Predicate<K, V> predicate, K k,
      boolean b) {
    return null;
  }

  @Override
  public String addInterceptor(MapInterceptor mapInterceptor) {
    return null;
  }

  @Override
  public void removeInterceptor(String s) {

  }

  @Override
  public String addEntryListener(MapListener mapListener, boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(EntryListener entryListener, boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(MapListener mapListener, K k, boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(EntryListener entryListener, K k, boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(MapListener mapListener, Predicate<K, V> predicate, boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(EntryListener entryListener, Predicate<K, V> predicate,
      boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(MapListener mapListener, Predicate<K, V> predicate, K k,
      boolean b) {
    return null;
  }

  @Override
  public String addEntryListener(EntryListener entryListener, Predicate<K, V> predicate, K k,
      boolean b) {
    return null;
  }

  @Override
  public boolean removeEntryListener(String s) {
    return false;
  }

  @Override
  public String addPartitionLostListener(MapPartitionLostListener mapPartitionLostListener) {
    return null;
  }

  @Override
  public boolean removePartitionLostListener(String s) {
    return false;
  }

  @Override
  public EntryView<K, V> getEntryView(K k) {
    return null;
  }

  @Override
  public boolean evict(K k) {
    return false;
  }

  @Override
  public void evictAll() {

  }

  @Override
  public Set<K> keySet(Predicate predicate) {
    return null;
  }

  @Override
  public Set<Entry<K, V>> entrySet(Predicate predicate) {
    return null;
  }

  @Override
  public Collection<V> values(Predicate predicate) {
    return null;
  }

  @Override
  public Set<K> localKeySet() {
    return null;
  }

  @Override
  public Set<K> localKeySet(Predicate predicate) {
    return null;
  }

  @Override
  public void addIndex(String s, boolean b) {

  }

  @Override
  public LocalMapStats getLocalMapStats() {
    return null;
  }

  @Override
  public Object executeOnKey(K k, EntryProcessor entryProcessor) {
    return null;
  }

  @Override
  public Map<K, Object> executeOnKeys(Set<K> set, EntryProcessor entryProcessor) {
    return null;
  }

  @Override
  public void submitToKey(K k, EntryProcessor entryProcessor, ExecutionCallback executionCallback) {

  }

  @Override
  public ICompletableFuture submitToKey(K k, EntryProcessor entryProcessor) {
    return null;
  }

  @Override
  public Map<K, Object> executeOnEntries(EntryProcessor entryProcessor) {
    return null;
  }

  @Override
  public Map<K, Object> executeOnEntries(EntryProcessor entryProcessor, Predicate predicate) {
    return null;
  }

  @Override
  public <R> R aggregate(Aggregator<Entry<K, V>, R> aggregator) {
    return null;
  }

  @Override
  public <R> R aggregate(Aggregator<Entry<K, V>, R> aggregator, Predicate<K, V> predicate) {
    return null;
  }

  @Override
  @Deprecated
  @SuppressWarnings("deprecation")
  public <S, R> R aggregate(Supplier<K, V, S> supplier,
      Aggregation<K, S, R> aggregation) {
    return null;
  }

  @Override
  @Deprecated
  @SuppressWarnings("deprecation")
  public <S, R> R aggregate(Supplier<K, V, S> supplier,
      Aggregation<K, S, R> aggregation, JobTracker jobTracker) {
    return null;
  }

  @Override
  public <R> Collection<R> project(Projection<Entry<K, V>, R> projection) {
    return null;
  }

  @Override
  public <R> Collection<R> project(Projection<Entry<K, V>, R> projection,
      Predicate<K, V> predicate) {
    return null;
  }

  @Override
  public QueryCache<K, V> getQueryCache(String s) {
    return null;
  }

  @Override
  public QueryCache<K, V> getQueryCache(String s, Predicate<K, V> predicate, boolean b) {
    return null;
  }

  @Override
  public QueryCache<K, V> getQueryCache(String s, MapListener mapListener,
      Predicate<K, V> predicate, boolean b) {
    return null;
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
