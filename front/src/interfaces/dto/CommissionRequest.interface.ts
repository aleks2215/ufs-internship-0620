/**
 * Запрос для операции получения комиссии
 */
export interface CommissionRequest {
  /**
   * Номер счета.
   */
  accountId: string,
  /**
   * Сумма операции.
   */
  amount: string,
  /**
   * Код типа операции.
   */
  typeCode: string,
}
