import React from 'react'
import {
  Row,
  Col,
  Button
} from 'antd'
import renderPair from './RenderPair'
import {
  AccountResidues,
  PersonalAccount,
  Seizure
} from 'interfaces/dto/Account.interface'

const styles = require('./CardAccount.less')

const getDetails = (accountResidues: AccountResidues) => ([['Текущий остаток', accountResidues.currentBalance],
    ['Неснижаемый остаток', accountResidues.fixedBalance],
    ['Планируемый остаток', accountResidues.expectedBalance]]
)

const getSeizures = (seizure) => (
  [['Сумма ареста', seizure.amount],
    ['Основание ареста ', seizure.reason],
    ['Срок ареста', `С ${seizure.fromDate} По ${seizure.toDate}`],
    ['ЮЛ/орган-инициатор', seizure.initiatorShortName],
  ])

const renderStatus = (info, accountResidues, addSidePanel, seizures) => (
  <Row className={(info.status === 'Active') ? styles.green : styles.red}>
    <Col span={8}>
      {(info.status === 'Active') ? 'Активный' : 'Неактивный'}
    </Col>
    <Col span={10} className={styles.rightAligned}>
      <Button
        className={`${styles.green} ${styles.noBorder}`}
        onClick={() => addSidePanel(getDetails(accountResidues), 'Доп. информация по счету')}
      >
        Подробнее
      </Button>
    </Col>
    <Col span={6}>
      {(seizures && seizures.length > 0) ? (
        <Button
          className={`${styles.red} ${styles.noBorder}`}
          onClick={() => addSidePanel(getSeizures(seizures[0]), 'Информация об арестах')}
        >
          Арест суммы
        </Button>) : null}
    </Col>
  </Row>
)

export interface PerosnalEntityProps {
  info: PersonalAccount,
  accountResidues: AccountResidues,
  addSidePanel: Function,
  seizures: Array<Seizure>,
}

export default function PerosnalEntity({ info, accountResidues, addSidePanel, seizures }: PerosnalEntityProps) {
  const pairs = [['Номер счета', info.id],
    ['Вид', 'Расчетный счет'],
    ['Номер договора', info.agreement.id],
    ['Текущий остаток', accountResidues.currentBalance],
    ['Валюта', info.currencyType],
    ['Дата последней операции', info.lastTransactionDate],
    ['Статус', renderStatus(info, accountResidues, addSidePanel, seizures)]]

  return (
    <Row>
      <Col span={23}>
        {pairs.map((item, i) => renderPair(item[0], item[1], i))}
      </Col>
    </Row>
  )
}
