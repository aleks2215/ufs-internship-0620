import React from 'react'
import {
  Row,
  Col,
  Button
} from 'antd'
import renderPair from './RenderPair'

const styles = require('./CardAccount.less')

export interface CardIndex1Props {
  items: Array<any>,
  addSidePanel: Function,
}

export default function CardIndex1({ items, addSidePanel }: CardIndex1Props) {
  const listItems = items.map((item, i) => {
    const pairs = [['Номер документа', item.docId],
      ['Вид', item.docType],
      ['Получатель', item.recipientShortName],
      ['Сумма', item.amount],
      ['Дата', item.comeInDate]];
    return (
      <Row className={styles.separatedRow} key={i}>
        <Col span={23}>
          {pairs.map((pair, j) => renderPair(pair[0], pair[1], j))}
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
