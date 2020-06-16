package ru.philit.ufs.util;

import java.util.UUID;

public final class UuidUtils {

  private UuidUtils() {
  }

  public static String getRandomUuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

}
