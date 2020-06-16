import React from 'react'
import {
  sortableContainer,
  sortableElement
} from 'react-sortable-hoc'

import {
  Alert,
  Row
} from 'antd'

const emptyList = (
  <Alert
    message="Выбранный список пуст"
    description="В данном разделе нет пунктов, удовлетворяющих критериям выбора"
    type="info"
    showIcon
  />
)

interface ListProps {
  items: any[],
  draggable: boolean,
  onSortEnd?: any,
  Item: any,
}

export default (props: ListProps) => {
  const { items, draggable, onSortEnd } = props
  const { Item } = props

  const SortableItem = sortableElement(({ item }) => <Item item={item} />)

  const SortableList = sortableContainer(({ items }) => (
    <Row align="top">
      {items.length > 0 ? items.map((item, i) => (
        <SortableItem key={i} index={i} item={item} />
      )) : emptyList}
    </Row>
  ))

  const ListWithSort = ({ items }) => (
    <SortableList
      items={items}
      axis="xy"
      pressDelay={0}
      pressThreshold={0}
      distance={10}
      helperClass="onMove"
      transitionDuration={110}
      onSortEnd={onSortEnd}
    />
  )

  const List = ({ items }) => (
    <Row align="top">
      {items.length > 0 ? items.map((item, i) => (<Item key={i} item={item} />)
      ) : emptyList}
    </Row>
  )

  return draggable ? (<ListWithSort items={items} />) : (<List items={items} />)
}
