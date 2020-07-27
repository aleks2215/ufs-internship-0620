package ru.philit.ufs.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoneyToWordsConverter {

  private final BigDecimal amount;

  String[][] sex = {
      {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
      {"", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"},
  };
  String[] str100 = {"", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот",
      "восемьсот", "девятьсот"};
  String[] str11 = {"", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
      "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать", "двадцать"};
  String[] str10 = {"", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
      "семьдесят", "восемьдесят", "девяносто"};
  String[][] forms = {
      {"копейка", "копейки", "копеек", "1"},
      {"рубль", "рубля", "рублей", "0"},
      {"тысяча", "тысячи", "тысяч", "1"},
      {"миллион", "миллиона", "миллионов", "0"},
      {"миллиард", "миллиарда", "миллиардов", "0"},
      {"триллион", "триллиона", "триллионов", "0"},
  };

  /**
   * Конструктор.
   */
  public MoneyToWordsConverter(BigDecimal amount) {
    String s = amount.toString();

    if (s.lastIndexOf(".") != -1) {
      if (s.substring(s.lastIndexOf(".")).length() > 3) {
        throw new IllegalArgumentException("There is more than two digits after the decimal point");
      }
    }

    if (!amount.toString().contains(".")) {
      s += ".0";
    }
    this.amount = new BigDecimal(s);
  }

  /**
   * Вернуть сумму прописью, с точностью до копеек.
   */
  public String num2str() {
    return num2str(false);
  }

  /**
   * Выводим сумму прописью
   *
   * @param stripkop boolean флаг - показывать копейки или нет
   * @return String Сумма прописью.
   */
  public String num2str(boolean stripkop) {
    // получаем отдельно рубли и копейки
    long rub = amount.longValue();

    String[] moi = amount.toString().split("\\.");
    long kop = Long.parseLong(moi[1]);
    if (!moi[1].startsWith("0")) { // начинается не с нуля
      if (kop < 10) {
        kop *= 10;
      }
    }

    String amountInWords = convertIntegerPartOfNumber(rub);
    if (!stripkop) {
      amountInWords = convertDecimalPartOfNumber(amountInWords, kop);
    }

    amountInWords = amountInWords.replaceAll(" {2,}", " ");
    return amountInWords;
  }

  /**
   * Конвертируем сумму без копеек в текст
   *
   * @param rub сумма без копеек
   * @return сумма текстом.
   */
  private String convertIntegerPartOfNumber(long rub) {
    long rubTmp = rub;
    // Разбиватель суммы на сегменты по 3 цифры с конца
    List<Long> segments = new ArrayList<>();
    while (rubTmp > 999) {
      long seg = rubTmp / 1000;
      segments.add(rubTmp - (seg * 1000));
      rubTmp = seg;
    }
    segments.add(rubTmp);
    Collections.reverse(segments);
    // Анализируем сегменты
    if (rub == 0) { // если Ноль
      return "ноль " + morph(0, forms[1][0], forms[1][1], forms[1][2]) + " ";
    }
    String o = "";
    // Больше нуля
    int lev = segments.size();
    for (int i = 0; i < segments.size(); i++) { // перебираем сегменты
      int sexi = Integer.parseInt(forms[lev][3]);// определяем род
      int ri = Integer.parseInt(segments.get(i).toString());// текущий сегмент
      if (ri == 0 && lev > 1) { // если сегмент == 0 И не последний уровень(там Units)
        lev--;
        continue;
      }
      String rs = String.valueOf(ri); // число в строку
      // нормализация
      if (rs.length() == 1) {
        rs = "00" + rs;// два нулика в префикс?
      }
      if (rs.length() == 2) {
        rs = "0" + rs; // или лучше один?
      }
      // получаем циферки для анализа
      int r1 = Integer.parseInt(rs.substring(0, 1)); //первая цифра
      int r2 = Integer.parseInt(rs.substring(1, 2)); //вторая
      int r3 = Integer.parseInt(rs.substring(2, 3)); //третья
      int r22 = Integer.parseInt(rs.substring(1, 3)); //вторая и третья
      // анализатор цифр
      if (ri > 99) {
        o += str100[r1] + " "; // Сотни
      }
      if (r22 > 20) { // >20
        o += str10[r2] + " ";
        o += sex[sexi][r3] + " ";
      } else { // <=20
        if (r22 > 9) {
          o += str11[r22 - 9] + " "; // 10-20
        } else {
          o += sex[sexi][r3] + " "; // 0-9
        }
      }
      // Единицы измерения (рубли...)
      o += morph(ri, forms[lev][0], forms[lev][1], forms[lev][2]) + " ";
      lev--;
    }
    return o;
  }

  /**
   * Конвертируем сумму с копейками в текст
   *
   * @param firstPartOfWord сумма без копеек в текстовом виде
   * @param kop             копейки
   * @return сумма текстом.
   */
  private String convertDecimalPartOfNumber(String firstPartOfWord, long kop) {
    String kops = String.valueOf(kop);
    if (kops.length() == 1) {
      kops = "0" + kops;
    }

    String o = firstPartOfWord;
    int r1 = Integer.parseInt(kops.substring(0, 1)); //первая цифра
    int r2 = Integer.parseInt(kops.substring(1, 2)); //вторая
    int r22 = Integer.parseInt(kops.substring(0, 2)); //вторая и третья
    // анализатор цифр
    if (r22 == 0) {
      o += "ноль";
    }
    if (r22 > 20) { // >20
      o += str10[r1] + " ";
      o += sex[1][r2] + " ";
    } else { // <=20
      if (r22 > 9) {
        o += str11[r22 - 9] + " "; // 10-20
      } else {
        o += sex[1][r2] + " "; // 0-9
      }
    }
    o = o + morph(kop, forms[0][0], forms[0][1], forms[0][2]);
    return o;
  }

  /**
   * Склоняем словоформу
   *
   * @param n  Long количество объектов
   * @param f1 String вариант словоформы для одного объекта
   * @param f2 String вариант словоформы для двух объектов
   * @param f5 String вариант словоформы для пяти объектов
   * @return String правильный вариант словоформы для указанного количества объектов.
   */
  private String morph(long n, String f1, String f2, String f5) {
    n = Math.abs(n) % 100;
    long n1 = n % 10;
    if (n > 10 && n < 20) {
      return f5;
    }
    if (n1 > 1 && n1 < 5) {
      return f2;
    }
    if (n1 == 1) {
      return f1;
    }
    return f5;
  }

}
