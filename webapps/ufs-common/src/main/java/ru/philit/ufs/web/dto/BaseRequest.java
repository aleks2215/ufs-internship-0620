package ru.philit.ufs.web.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

/**
 * Базовый класс для запроса REST-сервиса.
 */
@ToString
@Getter
public abstract class BaseRequest implements Serializable {
}
