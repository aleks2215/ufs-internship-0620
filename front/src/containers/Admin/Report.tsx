import React from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { RouteComponentProps } from 'react-router-dom'
import { Moment } from 'moment'

import {
  Layout,
  Row,
  Col,
  Table,
  AutoComplete,
  Input,
  Icon,
  Button,
  DatePicker,
  Modal,
} from 'antd'

import {
  loadReport,
  filter
} from 'reducers/admin/report'

import ClientCard from 'components/CreateNewOperation/ClientCard'
import OperatorCard from 'components/OperatorCard'

const styles = require('./Report.less')

const { RangePicker } = DatePicker
interface ReportProps extends RouteComponentProps<any> {
  loadReport: Function,
  filter: Function,
  seed: Array<any>,
  items: Array<any>,
  loading: boolean,
  fromDate: Moment,
  toDate: Moment,
}

@(connect(({ admin }) => admin.report,
  dispatch => bindActionCreators({ loadReport, filter }, dispatch)) as any)
export default class Report extends React.Component<ReportProps, any> {
  toggleModalOperator = (operator) => () => {
    this.setState({
      ...this.setState,
      operator: operator,
      visibleOperator: !this.state.visibleOperator,
    });
  }
  handleCancelOperator = (e) => {
    this.setState({
      ...this.state,
      operator: undefined,
      visibleOperator: false,
    });
  }
  toggleModalClient = (representative) => () => {
    this.setState({
      ...this.setState,
      representative: representative,
      visibleClient: !this.state.visibleClient,
    });
  }
  handleCancelClient = (e) => {
    this.setState({
      ...this.state,
      representative: undefined,
      visibleClient: false,
    });
  }
  state = {
    representative: undefined,
    operator: undefined,
    visibleClient: false,
    visibleOperator: false,
    columns: [{
      title: 'Код ТБ',
      dataIndex: 'tbCode',
    }, {
      title: 'Код (Г)ОСБ',
      dataIndex: 'osbCode',
    }, {
      title: 'Код ВСП',
      dataIndex: 'vspCode',
    }, {
      title: 'Сотрудник',
      dataIndex: 'operator',
      render: (x) => (<a onClick={this.toggleModalOperator(x)}>{x.fullName}</a>)
    }, {
      title: 'Идентификатор операции',
      dataIndex: 'id',
    }, {
      key: 'category',
      title: 'Категория операции',
      dataIndex: 'category',
      render: () => ('Приходная'),
    }, {
      title: 'Операция',
      dataIndex: 'type',
    }, {
      title: 'Дата/время операции',
      dataIndex: 'createdDate',
    }, {
      title: 'ИНН клиента',
      dataIndex: 'inn',
    }, {
      title: 'Клиент',
      dataIndex: 'legalEntityShortName',
    }, {
      title: 'ФИО клиента/представителя',
      dataIndex: 'representative',
      render: (x) => (<a onClick={this.toggleModalClient(x)}>{`${x.lastName} ${x.firstName} ${x.patronymic}`}</a>)
    }, {
      title: 'Сумма операции, руб.',
      dataIndex: 'amount',
    }, {
      title: 'Комиссия, руб.',
      dataIndex: 'commission',
    }, {
      title: 'Статус операции',
      dataIndex: 'status',
    }],
  }

  componentDidMount() {
    const { fromDate, toDate, loadReport } = this.props
    loadReport([fromDate, toDate])
  }

  onRangeChange = (dates) => {
    this.props.loadReport(dates)
  }

  render() {
    const { items, seed, loading, fromDate, toDate } = this.props

    const autocompleteCategoryItems =
      [...new Set(seed.map(item => item.type))]

    const autocompleteEmployerItems =
      [...new Set(seed.map(item => item.operatorFullName))]

    return (
      <Layout style={{ marginTop: 12 }}>
        <Row style={{ marginBottom: 8 }}>
          <Col span={24}>
            <AutoComplete
              className={styles.autocomplete}
              optionLabelProp="value"
              allowClear
              transitionName=""
              dataSource={(autocompleteCategoryItems.map((item, i) => (
                  <AutoComplete.Option key={i} value={item}>
                    {item}
                  </AutoComplete.Option>
                )
              ) as any)}
              onSelect={(category) => {
                filter({ category })
              }}
              onChange={(category) => {
                if (!category) {
                  filter({ category: false })
                }
              }}
            >
              <Input
                placeholder="Категория операции"
                prefix={
                  <Icon type="search" className={styles.icon} />
                }
              />
            </AutoComplete>
            <AutoComplete
              className={styles.autocomplete}
              optionLabelProp="value"
              key="operatorFullName"
              allowClear
              transitionName=""
              dataSource={(autocompleteEmployerItems.map((item, i) => (
                  <AutoComplete.Option key={i} value={item}>
                    {item}
                  </AutoComplete.Option>
                )
              ) as any)}
              onSelect={(operatorFullName) => {
                filter({ operatorFullName })
              }}
              onChange={(operatorFullName) => {
                if (!operatorFullName) {
                  filter({ operator: false })
                }
              }}
            >
              <Input
                placeholder="ФИО Сотрудника"
                prefix={
                  <Icon type="search" className={styles.icon} />
                }
              />
            </AutoComplete>
          </Col>
        </Row>
        <Row>
          <Col span={24}>
            <span className={styles.rangeLabel}>Журнал операций</span>
            <RangePicker
              format="DD.MM.YYYY"
              onChange={this.onRangeChange}
              defaultValue={[fromDate, toDate]}
            />
          </Col>
        </Row>
        <Row>
          <Col span={24}>
            <Table
              rowKey="id"
              loading={loading}
              columns={this.state.columns}
              dataSource={items}
              scroll={{ x: 1920 }}
              rowSelection={{ type: 'checkbox' }}
              bordered
              locale={{
                emptyText: 'Нет данных',
              }}
            />
          </Col>
        </Row>
        <Row className={styles.buttons}>
          <Col span={24}>
            <Button className={styles.button} type="primary">Печать</Button>
            <Button className={styles.button}>Сторно</Button>
          </Col>
        </Row>
        { this.state.visibleClient ?
          <Modal
            title="Карточка клиента/представителя клиента"
            visible={this.state.visibleClient}
            onCancel={this.handleCancelClient}
            closable
            cancelText="Отмена"
            footer={null}
          >
            <ClientCard {...this.state.representative} />
          </Modal>
        : null}
        { this.state.visibleOperator ?
          <Modal
            title="Карточка сотрудника"
            visible={this.state.visibleOperator}
            closable
            onCancel = {this.handleCancelOperator}
            cancelText="Отмена"
            footer={null}
          >
            <OperatorCard {...this.state.operator} />
          </Modal> : null }
      </Layout>
    )
  }
}
