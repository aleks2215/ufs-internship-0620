import React from 'react'
import {
  Button,
  Row,
  Table
} from 'antd';
import Section from './Section/Section'
import { OVN } from 'interfaces/dto/OVN.interface'
const styles = require('./Shared.less')

const columns = [{
  title: '№ ОВН',
  dataIndex: 'num',
}, {
  title: 'Сумма',
  dataIndex: 'amount',
}, {
  title: 'Дата создания',
  dataIndex: 'createdDate',
}, {
  title: 'ИНН',
  dataIndex: 'inn',
}, {
  title: 'Наименование ЮЛ',
  dataIndex: 'legalEntityShortName',
}, {
  title: 'Представитель',
  dataIndex: 'repFio',
}];

export interface OvnSelectFormProps {
  getOvnList: Function,
  nextStepNew: any,
  nextStepOvn: any,
  account: {
    id: string,
    status: string
  }
  loaded: boolean,
  ovnList: Array<any>,
}

export default class OvnSelectForm extends React.Component<OvnSelectFormProps, any> {
  constructor(props) {
    super(props)
    this.nextStepOvn = this.nextStepOvn.bind(this)
  }

  rowSelection = {
    type: ('radio' as any),
    onChange: (key: string[], selectedRows: Array<OVN>) => {
      this.setState({ selectedRows })
    },
  }

  nextStepOvn = () => {
    this.props.nextStepOvn(this.state.selectedRows[0])
  }

  componentDidMount() {
    const { loaded, account, getOvnList } = this.props

    if (loaded) {
      return
    }
    getOvnList(account)
  }

  render() {
    const disabledOVN = !this.state || !this.state.selectedRows || this.state.selectedRows.length === 0
    return (
      <div>
        <Section header="Объявления на взнос наличными">
          <Table
            rowKey="id"
            rowSelection={this.rowSelection}
            columns={columns}
            dataSource={this.props.ovnList}
            locale={{
              filterConfirm: 'ОК',
              filterReset: 'Сбросить',
              emptyText: 'Данные отсутствуют',
            }}
          />
        </Section>
        <Row className={styles.rightAligned}>
          <Button className={styles.greenButton} onClick={this.props.nextStepNew}>
            Создать новую операцию
          </Button>
          <Button className={disabledOVN ? '' : styles.greenButton} disabled={ disabledOVN } onClick={this.nextStepOvn}>
            На основании выбранного ОВН
          </Button>
        </Row>
      </div>
    );
  }
}
