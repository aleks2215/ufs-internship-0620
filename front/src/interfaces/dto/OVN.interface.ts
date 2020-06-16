import {CachSymbol} from './CachSymbol.interface'
import { CurrencyType } from './CurrencyType.interface'
/**
 * Объект объявления на взнос наличных (ОВН).
 */
export interface OVN {
  /**
   * Идентификатор ОВН.
   */
  id: string,
  /**
   * Номер ОВН.
   */
  num: string,
  /**
   * Статус.
   */
  status: string,
  /**
   * ФИО представителя клиента.
   */
  repFio: string,
  /**
   * ЮЛ-получатель.
   */
  legalEntityShortName: string,
  /**
   * Сумма.
   */
  amount: string,
  /**
   * Дата и время создания.
   */
  createdDate: string,
  /**
   * ИНН.
   */
  inn: string,
  /**
   * КПП.
   */
  kpp: string,
  /**
   * Номер счета.
   */
  accountId: string,
  /**
   * Номер счета отправителя.
   */
  senderAccountId: string,
  /**
   * Банк-отправитель.
   */
  senderBank: string,
  /**
   * БИК банка-отправителя.
   */
  senderBankBic: string,
  /**
   * Номер счета получателя.
   */
  recipientAccountId: string,
  /**
   * Банк-получатель.
   */
  recipientBank: string,
  /**
   * БИК банка-получателя.
   */
  recipientBankBic: string,
  /**
   * Источник поступления.
   */
  source: string,
  /**
   * Признак типа клиента ФК/ОФК/УОВФ.
   */
  clientTypeFk: boolean,
  /**
   * Наименование организации в ФК/ОФК/УОВФ.
   */
  organisationNameFk: string,
  /**
   * Лицевой счет.
   */
  personalAccountId: string,
  /**
   * Код валюты.
   */
  currencyType: CurrencyType,
  /**
   * Кассовые символы.
   */
  symbols: Array<CachSymbol> ,

  packageId?: string,
}
