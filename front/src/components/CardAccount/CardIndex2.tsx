import React from 'react'
import {
  Row,
  Col,
  Button
} from 'antd'
import renderPair from './RenderPair'

const styles = require('./CardAccount.less')

export interface CardIndex2Props {
  items: any[],
  addSidePanel: Function,
}

export default function CardIndex2({ items, addSidePanel }: CardIndex2Props) {
  const listItems = items.map((item, i) => {
    const pairs = [['Номер документа', item.docId],
      ['Очередь оплаты', item.priority],
      ['Сумма по документу', item.totalAmount],
      ['Оплачено', item.partAmount],
      ['Получатель', item.recipientShortName]]
    return (
      <Row className={styles.separatedRow} key={i}>
        <Col span={23}>
          {pairs.filter((pair) => pair[1]).map((pair, j) => renderPair(pair[0], pair[1], j))}
        </Col>
        <Col span={1}>
          <Button
            className={styles.largeIcon}
            onClick={() => addSidePanel(pairs, `ПП ${item.docId}`)}
            icon="menu-unfold"
          />
        </Col>
      </Row>
    )
  })

  return (
    <div>
      {listItems}
    </div>
  )
}
