package ru.philit.ufs.model.entity.account;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.philit.ufs.model.entity.common.ExternalEntity;

/**
 * Сущность кредитной карты.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"number"}, callSuper = false)
@ToString
@Getter
@Setter
public class Card extends ExternalEntity {

  private String number;
  private Date expiryDate;
  private CardNetworkCode issuingNetworkCode;
  private CardType type;
  private String ownerFirstName;
  private String ownerLastName;

}
