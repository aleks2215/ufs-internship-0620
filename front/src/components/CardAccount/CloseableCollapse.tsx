import React from 'react'
import {
  Button,
  Collapse,
  Row,
  Col
} from 'antd'
import renderPair from './RenderPair'

const styles = require('./CardAccount.less')

const Panel = Collapse.Panel

export interface PairsItem {
  pairs: Array<string>
}

interface CloseableCollapseProps {
  sidePanel: Array<PairsItem>,
  removePanel: Function,
}

export default function CloseableCollapse(props: CloseableCollapseProps) {
  const removePanel = (item) => props.removePanel(item)

  const renderHeader = (item) => (
    <Row>
      <Col span={21}>{item.header}</Col>
      <Col span={3}><Button shape="circle" onClick={() => removePanel(item)}>X</Button></Col>
    </Row>
  )

  const items = props.sidePanel.map((item, i) =>
    <Panel header={renderHeader(item)} key={`${i}`}>
      {item.pairs.filter((pair) => pair[1]).map((pair, j) => renderPair(pair[0], pair[1], j))}
    </Panel>
  )

  if (items.length === 0) {
    return null
  }

  return (
    <Collapse className={styles.accountCard} defaultActiveKey={['0']}>
      {items}
    </Collapse>
  )
}
