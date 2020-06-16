package ru.philit.ufs.model.cache.hazelcast;

import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REQUEST_MAP;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.REQUEST_QUEUE;
import static ru.philit.ufs.model.cache.hazelcast.CollectionNames.RESPONSE_QUEUE;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.model.cache.IsEsbCache;
import ru.philit.ufs.model.entity.common.ExternalEntity;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;

/**
 * Клиент доступа к кешу Hazelcast.
 */
@Service("EsbIsCache")
public class HazelcastIsEsbClient
    implements IsEsbCache {

  private static final Logger logger = LoggerFactory.getLogger(HazelcastIsEsbClient.class);

  private final HazelcastInstance instance;

  private IMap<String, ExternalEntityRequest> requestMap;
  @Getter
  private IQueue<ExternalEntityRequest> requestQueue;
  private IQueue<ExternalEntity> responseQueue;

  @Autowired
  public HazelcastIsEsbClient(HazelcastInstance hazelcastClient) {
    this.instance = hazelcastClient;
  }

  /**
   * Инициализатор бина.
   */
  @PostConstruct
  public void init() {
    requestQueue = instance.getQueue(REQUEST_QUEUE);
    responseQueue = instance.getQueue(RESPONSE_QUEUE);
    requestMap = instance.getMap(REQUEST_MAP);

    logger.info("{} started", this.getClass().getSimpleName());
  }

  /**
   * Деструктор бина.
   */
  @PreDestroy
  public void destroy() {
    if (instance != null) {
      HazelcastClient.shutdown(instance);
      logger.info("{} is shut down", this.getClass().getSimpleName());
    }
  }

  /**
   * Кладёт запрос данных <code>request</code> в коллекцию по сформированному
   * <code>requestId</code>.
   */
  @Override
  public void putRequest(String requestId, ExternalEntityRequest request) {
    requestMap.put(requestId, request);
  }

  /**
   * Кладёт ответ на запрос данных в очередь для дальнейшей обработки.
   */
  @Override
  public void pushResponse(ExternalEntity entity) {
    responseQueue.add(entity);
  }
}
