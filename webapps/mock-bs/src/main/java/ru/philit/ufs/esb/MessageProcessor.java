package ru.philit.ufs.esb;

/**
 * Интерфейс обработчика сообщений из КСШ.
 */
public interface MessageProcessor {

  boolean processMessage(String message);

}
