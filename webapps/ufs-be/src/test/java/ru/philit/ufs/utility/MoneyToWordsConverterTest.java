package ru.philit.ufs.utility;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class MoneyToWordsConverterTest {

  private static final BigDecimal NUMBER1 = new BigDecimal("100000");
  private static final BigDecimal NUMBER2 = new BigDecimal("1456.67");

  @Test
  public void testConvertToWords() {
    MoneyToWordsConverter moneyToWordsConverter = new MoneyToWordsConverter(NUMBER1);
    String amountInWords = moneyToWordsConverter.num2str();
    Assert.assertEquals(amountInWords, "сто тысяч рублей ноль копеек");

    moneyToWordsConverter = new MoneyToWordsConverter(NUMBER2);
    amountInWords = moneyToWordsConverter.num2str();
    Assert.assertEquals(amountInWords,
        "одна тысяча четыреста пятьдесят шесть рублей шестьдесят семь копеек");
  }

}