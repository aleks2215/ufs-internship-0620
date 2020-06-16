package ru.philit.ufs.model.entity.oper;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CashSymbolRequest implements Serializable {

  private boolean getList;
  private String code;

}
