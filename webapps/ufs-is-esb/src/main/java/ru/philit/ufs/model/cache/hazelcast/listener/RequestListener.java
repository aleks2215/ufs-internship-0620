package ru.philit.ufs.model.cache.hazelcast.listener;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.philit.ufs.esb.service.EsbService;
import ru.philit.ufs.model.cache.hazelcast.HazelcastIsEsbClient;
import ru.philit.ufs.model.entity.common.ExternalEntityRequest;

/**
 * Прослушиватель события поступления запроса данных в Мастер-систем.
 */
@Service
public class RequestListener
    implements ItemListener<ExternalEntityRequest> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final HazelcastIsEsbClient hazelcastIsEsbClient;
  private final EsbService esbService;

  @Autowired
  public RequestListener(HazelcastIsEsbClient hazelcastIsEsbClient, EsbService esbService) {
    this.hazelcastIsEsbClient = hazelcastIsEsbClient;
    this.esbService = esbService;
  }

  @PostConstruct
  public void init() {
    hazelcastIsEsbClient.getRequestQueue().addItemListener(this, false);
    logger.info("{} started", this.getClass().getSimpleName());
  }

  /**
   * Обработчик события поступления запроса данных в очередь requestQueue.
   */
  @Override
  public void itemAdded(ItemEvent<ExternalEntityRequest> itemEvent) {
    logger.debug("Call itemAdded of requestQueue");
    ExternalEntityRequest entityRequest = hazelcastIsEsbClient.getRequestQueue().poll();
    if (entityRequest != null) {
      logger.debug("  polled {}", entityRequest);
      esbService.sendRequest(entityRequest);
    }
  }

  @Override
  public void itemRemoved(ItemEvent<ExternalEntityRequest> itemEvent) {
    // This event is not required to handling
  }
}
