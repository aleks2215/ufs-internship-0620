import React from 'react'

import * as _ from 'lodash'
import { arrayMove } from 'react-sortable-hoc'

/* Components */
import {
  Row,
  Col,
  Modal
} from 'antd'

import Item from 'components/Menu/Item'
import List from 'components/Menu/List'
import Tabs from 'components/Tabs/Group'
import Message from 'components/Indicators/Message'
import Autocomplete from 'components/Inputs/Autocomplete'

/* Styles */
const styles = require('./Menu.less')

export interface MenuProps {
  onComplete(),
  load(),
  fave: Function,
  show: Function,
  reorder: Function,
  items: any[],
  favourites: Array<string>,
  visible: string,
  loading: number,
  error: any,
}

export default class Menu extends React.Component<MenuProps, any> {
  constructor(props) {
    super(props)

    this.state = {
      tabs: [{
        key: '',
        text: 'Все операции',
      }, {
        key: 'fave',
        text: 'Избранное',
      }],
      autocompleteValue: '',
    }
  }

  componentDidMount() {
    this.props.load()
  }

  onOperationSelect = (item) => {

    if (item.enabled) {
      this.props.onComplete()
      return null
    }

    this.setState({
      autocompleteValue: '',
    })

    return Modal.error({
      title: 'Ошибка при выполнении операции',
      content: 'У вас нет доступа к данному пункту меню',
      okText: 'OK',
    })
  }

  filterFaveItems = ({ items }) => {
    const result = []
    this.props.favourites.forEach(id =>
      result.push(_.find(items.filter(item => item.fave), { id })))

    return result
  }

  render() {
    const { items, visible, favourites, error } = this.props
    const { fave, show, reorder } = this.props

    if (error) {
      const description = `
        Обратитесь к системному администратору.
        Информация об ошибке: ${error.message}
      `
      return (
        <Message
          type="error"
          text="Возникла ошибка при загрузке данных."
          description={description}
        />)
    }

    // Sort and order items in `fave` mode
    let visibleItems = items
    if (visible === 'fave') {
      visibleItems = this.filterFaveItems({ items })
    }

    let draggable = false
    if (visibleItems.length > 1 && visible === 'fave') {
      draggable = true
    }

    // Element which gonna be repeated in final list
    const ListItem = ({ item }) => (
      <Item
        title={item.name}
        fave={item.fave}
        enabled={item.enabled}
        onCardClick={() => this.onOperationSelect(item)}
        onStarClick={(e) => {
          e.stopPropagation()
          fave(item.id)
        }}
      />
    )

    const filterOptions = (inputValue, option) => {
      const wrapper = option.props.children
      return wrapper.props.children.toUpperCase().indexOf(inputValue.toUpperCase()) !== -1
    }

    return (
      <div className={styles.layout}>
        <Row className={styles.tabRow}>
          <Col span={18}>
            <Tabs
              items={this.state.tabs}
              onChange={(e) => show(e.target.value)}
            />
          </Col>
        </Row>
        <Row>
          <Col span={18}>
            <Row className={styles.itemsRow}>
              <List
                Item={ListItem}
                items={visibleItems}
                draggable={draggable}
                onSortEnd={({ oldIndex, newIndex }) => reorder(
                  arrayMove(favourites, oldIndex, newIndex)
                )}
              />
            </Row>
          </Col>
          <Col span={6}>
            <div style={{ paddingLeft: 16 }}>
              <Autocomplete
                filterOption={filterOptions}
                items={items}
                value={this.state.autocompleteValue}
                onSelect={(value, option) => this.onOperationSelect(items[option.props.index])}
              />
            </div>
          </Col>
        </Row>
      </div>
    )
  }
}

