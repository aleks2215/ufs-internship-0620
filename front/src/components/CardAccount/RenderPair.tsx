import React from 'react'
import {
  Row,
  Col
} from 'antd'

const styles = require('./CardAccount.less')

const LEFT_SPAN = 8
const RIGHT_SPAN = 16
const rightBold = `${styles.rightAligned} ${styles.bold}`

export default function RenderPair(field, value, i) {
  return (
    <Row key={i} className={styles.pairRow}>
      <Col span={LEFT_SPAN} className={rightBold}>
        {field}
      </Col>
      <Col span={RIGHT_SPAN}>
        {value}
      </Col>
    </Row>
  )
}
