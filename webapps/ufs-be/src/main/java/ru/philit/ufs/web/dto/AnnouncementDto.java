package ru.philit.ufs.web.dto;

import java.io.Serializable;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Объект объявления на взнос наличных (ОВН).
 */
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
@SuppressWarnings("serial")
public class AnnouncementDto implements Serializable {

  /**
   * Идентификатор ОВН.
   */
  private String id;
  /**
   * Номер ОВН.
   */
  private String num;
  /**
   * Статус.
   */
  private String status;
  /**
   * ФИО представителя клиента.
   */
  private String repFio;
  /**
   * ЮЛ-получатель.
   */
  private String legalEntityShortName;
  /**
   * Сумма.
   */
  private String amount;
  /**
   * Дата и время создания.
   */
  private String createdDate;
  /**
   * ИНН.
   */
  private String inn;
  /**
   * КПП.
   */
  private String kpp;
  /**
   * Номер счета.
   */
  private String accountId;
  /**
   * Номер счета отправителя.
   */
  private String senderAccountId;
  /**
   * Банк-отправитель.
   */
  private String senderBank;
  /**
   * БИК банка-отправителя.
   */
  private String senderBankBic;
  /**
   * Номер счета получателя.
   */
  private String recipientAccountId;
  /**
   * Банк-получатель.
   */
  private String recipientBank;
  /**
   * БИК банка-получателя.
   */
  private String recipientBankBic;
  /**
   * Источник поступления.
   */
  private String source;
  /**
   * Признак типа клиента ФК/ОФК/УОВФ.
   */
  private boolean clientTypeFk;
  /**
   * Наименование организации в ФК/ОФК/УОВФ.
   */
  private String organisationNameFk;
  /**
   * Лицевой счет.
   */
  private String personalAccountId;
  /**
   * Код валюты.
   */
  private String currencyType;
  /**
   * Кассовые символы.
   */
  private List<CashSymbolDto> symbols;

}
