import React from 'react'
import { connect } from 'react-redux'
import { RouteComponentProps } from 'react-router-dom'

import {
  fave,
  show,
  load
} from 'reducers/admin/operations'

import {
  Layout,
  Row,
  Col
} from 'antd'

import Item from 'components/Menu/Item'
import List from 'components/Menu/List'
import Tabs from 'components/Tabs/Group'

const styles = require('./Operations.less')

interface MenuProps extends RouteComponentProps<any> {
  dispatch: Function,
  items: any[],
  tab: string,
}

@(connect(({ admin }) => admin.operations) as any)
export default class Operations extends React.Component<MenuProps, any> {

  static defaultProps = ({
    items: [],
    tab: 'reportings',
  } as any)

  constructor(props) {
    super(props)

    this.state = {
      tabs: [{
        key: 'business-parameters',
        text: 'Бизнес параметры',
      }, {
        key: 'reportings',
        text: 'Отчетность',
      }],
      autocompleteValue: '',
    }
  }

  componentDidMount() {
    const { dispatch } = this.props
    dispatch(show('reportings'))
    dispatch(load())
  }

  render() {
    const { history, tab } = this.props
    const { dispatch } = this.props

    const items = this.props.items.filter(item => item.category === tab)

    const ListItem = ({ item }) => (
      <Item
        title={item.name}
        fave={item.fave}
        enabled={item.enabled}
        onCardClick={() => {
          if (item.enabled) {
            return history.push('/admin/report')
          }
          return false
        }}
        onStarClick={(e) => {
          e.stopPropagation()
          dispatch(fave(item.id))
        }}
      />
    )

    return (
      <Layout className={styles.layout}>
        <Row className={styles.tabRow}>
          <Col span={18}>
            <Tabs
              items={this.state.tabs}
              defaultValue={tab}
              onChange={(e) => dispatch(show(e.target.value))}
            />
          </Col>
        </Row>
        <Row>
          <Col span={18}>
            <Row className={styles.itemsRow}>
              <List
                Item={ListItem}
                items={items}
                draggable={false}
              />
            </Row>
          </Col>
          <Col span={6}>
            <div style={{ paddingLeft: 16 }}>
            </div>
          </Col>
        </Row>
      </Layout>
    )
  }
}

