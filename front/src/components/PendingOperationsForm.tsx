import React from 'react'
import {
  Layout,
  Row,
  Col,
  Table,
  AutoComplete,
  Input,
  Icon,
  Button,
  Modal
} from 'antd'
import Section from './Section/Section'

import OperationSearchForm from 'components/OperationSearchForm'
import { OVN } from 'interfaces/dto/OVN.interface'

const styles = require('./Shared.less')

const columns = [
  {
    title: 'ID пакета',
    dataIndex: 'packageId',
  },
  {
    title: 'ID задачи',
    dataIndex: 'taskId',
  },
  {
    title: 'Клиент',
    dataIndex: 'legalEntityShortName',
  },
  {
    title: 'ФИО Клиента/Представителя',
    dataIndex: 'repFio',
  },
  {
    title: 'Дата/время',
    dataIndex: 'createdDate',
  },
  {
    title: 'Номер счета',
    dataIndex: 'accountId',
  },
  {
    title: 'Сумма',
    dataIndex: 'amount',
  },
  {
    title: 'Тип операции',
    dataIndex: 'operationType',
    render: () => 'Взнос наличных на счет корпоративной/бюджетной карты',
  }
]

export interface PendingOperationsFormProps {
  load: Function,
  loadPendingOperations: Function,
  nextStepOvn: Function,
  filter: Function,
  pendingOperations: {
    items: Array<any>,
    seed: Array<any>,
  }
}

export default class PendingOperationsForm extends React.Component<PendingOperationsFormProps, any> {
  state = { showSearchByClientForm: false, selectedRows: [] }

  handleCancel = () => {
    this.setState({
      ...this.state,
      showSearchByClientForm: false,
    })
  }

  toggleModal = () => {
    this.setState({
      ...this.state,
      showSearchByClientForm: !this.state.showSearchByClientForm,
    })
  }

  rowSelection = {
    type: ('radio' as any),
    onChange: (key: string[], selectedRows: Array<OVN>) => {
      this.setState({
        ...this.state,
        selectedRows
      })
    },
  }

  nextStepOvn = () => {
    this.props.nextStepOvn(this.state.selectedRows[0])
  }

  componentDidMount() {
    const { load, loadPendingOperations } = this.props
    load()
    loadPendingOperations()
  }

  render() {
    const rowSelected = this.state && this.state.selectedRows && this.state.selectedRows.length > 0

    const {
      filter,
      pendingOperations: { seed },
    } = this.props

    const autocompletePackageIdItems =
      [...new Set(seed.map(item => item.packageId))]

    const autocompleteTaskIdItems =
      [...new Set(seed.map(item => item.taskId))]

    return (
      <Layout style={{ marginTop: 12 }}>
        <Row style={{ marginBottom: 8 }}>
          <Col span={24}>
            <AutoComplete
              className={styles.autocomplete}
              optionLabelProp="value"
              allowClear
              key="packageId"
              transitionName=""
              dataSource={(autocompletePackageIdItems.map((item, i) => (
                  <AutoComplete.Option key={i} value={item}>
                    {item}
                  </AutoComplete.Option>
                )
              ) as any)}
              onSelect={(packageId) => {
                filter({ packageId })
              }}
              onChange={(packageId) => {
                if (!packageId) {
                  filter({ packageId: false })
                }
              }}
            >
              <Input
                placeholder="Id пакета..."
                prefix={
                  <Icon type="search" className={styles.icon} />
                }
              />
            </AutoComplete>
            <AutoComplete
              className={styles.autocomplete}
              optionLabelProp="value"
              allowClear
              key="taskId"
              transitionName=""
              dataSource={(autocompleteTaskIdItems.map((item, i) => (
                  <AutoComplete.Option key={i} value={item}>
                    {item}
                  </AutoComplete.Option>
                )
              ) as any)}
              onSelect={(taskId) => {
                filter({ taskId })
              }}
              onChange={(taskId) => {
                if (!taskId) {
                  filter({ taskId: false })
                }
              }}
            >
              <Input
                placeholder="Id операции..."
                prefix={
                  <Icon type="search" className={styles.icon} />
                }
              />
            </AutoComplete>
            <Button style={{ float: 'right' }} type="primary" onClick={this.toggleModal}>
              Поиск по клиенту/представителю
            </Button>
          </Col>
        </Row>
        <Row>
          <Section header="Незавершенные операции">
            <Modal
              footer={[
                <Button key="submit" type="primary" size="large">
                  Найти
                </Button>
              ]}
              title="Поиск операции по клиенту/представителю"
              visible={this.state.showSearchByClientForm}
              onCancel={this.handleCancel}
            >
              <OperationSearchForm />
            </Modal>
            <Table
              rowKey="taskId"
              rowSelection={this.rowSelection}
              columns={columns}
              dataSource={this.props.pendingOperations.items}
              locale={{
                filterConfirm: 'ОК',
                filterReset: 'Сбросить',
                emptyText: 'Данные отсутствуют',
              }}
            />
          </Section>
        </Row>
        <Row className={styles.rightAligned}>
          <Button className={rowSelected ? styles.greenButton : ''} disabled={ !rowSelected }
                  onClick={this.nextStepOvn}>
            Открыть операцию
          </Button>
        </Row>
      </Layout>
    )
  }
}
