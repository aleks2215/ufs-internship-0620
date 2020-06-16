package ru.philit.ufs.esb;

/**
 * Интерфейс листенера сообщений из КСШ.
 */
public interface ReceiveMessageListener {

  void receiveMessage(String message);

}
