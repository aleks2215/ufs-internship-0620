package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

/**
 * Базовый класс для ответа REST-сервиса.
 *
 * @param <T> тип данных, составляющих тело ответа
 */
@ToString
@Getter
@SuppressWarnings("unchecked")
public abstract class BaseResponse<T> implements Serializable {

  /**
   * Флаг успешности выполнения.
   */
  private boolean success;
  /**
   * Данные ответа.
   */
  private T data;
  /**
   * Сообщение об ошибке.
   */
  private String message;

  /**
   * Построитель ответа на запрос данных.
   */
  public <R extends BaseResponse<T>> R withData(T data) {
    this.data = data;
    return (R) this;
  }

  /**
   * Построитель ответа успешного выполнения операции.
   */
  public <R extends BaseResponse<T>> R withSuccess(T data) {
    this.success = true;
    return withData(data);
  }

  /**
   * Построитель ответа неуспешного выполнения операции (ошибка).
   */
  public <R extends BaseResponse<T>> R withError(String message) {
    this.success = false;
    this.message = message;
    return (R) this;
  }

}
