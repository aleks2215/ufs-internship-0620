package ru.philit.ufs.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.philit.ufs.web.dto.BaseResponse;
import ru.philit.ufs.web.exception.InvalidDataException;
import ru.philit.ufs.web.exception.UserNotFoundException;

/**
 * Класс перехвата исключения при обработке REST-запросов.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

  /**
   * Обработка исключения отсутствия авторизации пользователя.
   */
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public StatusResponse handleNotAuthorizedException(final UserNotFoundException e) {
    log.error("User session was not found: {}", e.getMessage());
    return new StatusResponse().withData("UNAUTHORIZED")
        .withError("Пользователь не авторизован в системе");
  }

  /**
   * Обработка исключения при обработке данных REST-запроса.
   */
  @ExceptionHandler(InvalidDataException.class)
  @ResponseStatus(HttpStatus.OK)
  public StatusResponse handleInvalidDataException(final InvalidDataException e) {
    log.error("Invalid data error has occurred: {}", e.getMessage());
    return new StatusResponse().withData("INVALID_DATA_ERROR").withError(e.getMessage());
  }

  /**
   * Обработка прочих исключений REST-запроса.
   */
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public StatusResponse handleThrowable(final Throwable t) {
    log.error("Unexpected server error has occurred", t);
    return new StatusResponse().withData("INTERNAL_SERVER_ERROR")
        .withError("При обработке запроса произошла непредвиденная ошибка");
  }

  /**
   * Ответ на REST-запрос при возникновении исключения.
   */
  @SuppressWarnings("serial")
  public static class StatusResponse extends BaseResponse<String> {
  }

}
