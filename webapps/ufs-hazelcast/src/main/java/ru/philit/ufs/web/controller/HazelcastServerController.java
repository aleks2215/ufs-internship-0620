package ru.philit.ufs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.philit.ufs.model.server.HazelcastServer;

/**
 * Контроллер операций с сервером Hazelcast.
 */
@RestController
@RequestMapping("/")
public class HazelcastServerController {

  private final HazelcastServer hazelcastServer;

  @Autowired
  public HazelcastServerController(HazelcastServer hazelcastServer) {
    this.hazelcastServer = hazelcastServer;
  }

  @RequestMapping(value = "/shutdown", method = RequestMethod.GET)
  public String shutdownServer() {
    hazelcastServer.shutdown();
    return "Hazelcast server called to shut down";
  }
}
