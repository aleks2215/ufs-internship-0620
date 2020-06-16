import React from 'react'
import {
  Button,
  Collapse,
  Col,
  Form,
  Modal,
  Radio,
  Row
} from 'antd'

import TransactionInfo from './TransactionInfo'
import cancelOperation from 'components/CancelOperation'
import { Task } from 'interfaces/dto/Task.interface'
import { Representative } from 'interfaces/dto/Representative.interface'
import { Subbranch } from 'interfaces/dto/Subbranch.interface'
import { OVN } from 'interfaces/dto/OVN.interface'
import { loadCommission } from '../../reducers/shared/createNewOperation'

const styles = require('./CreateNewOperation.less')

export interface CreateNewOperationFormProps {
  continueUrm: Function,
  continueCache: Function,
  onCancel: Function,
  clearTransaction: Function,
  onAlter: Function,
  loadCommission: Function,
  loadRepresentativeAndTask: Function,
  ovnBased: boolean,
  taskLoaded: boolean
  commissionLoading: boolean,
  subbranch: Subbranch,
  ovn?: OVN,
  representative: Representative,
  limit: string,
  task?: Task
  workplaceId?: string,
  form?: any,
}

const checkLimits = (amount, limit) => {
  try {
    const amountInt = parseInt(amount, 10)
    const limitInt = parseInt(limit, 10)
    return amountInt <= limitInt
  } catch ( e ) {
    return false
  }
}

@Form.create<any>()
export default class CreateNewOperation extends React.Component<CreateNewOperationFormProps, any> {
  constructor(props) {
    super(props)
    this.onOk = this.onOk.bind(this)
  }

  state = {
    confirmType: 0,
  }

  componentWillMount() {
    const { subbranch, ovn, taskLoaded, loadRepresentativeAndTask } = this.props
    if (!taskLoaded) {
      loadRepresentativeAndTask(subbranch, ovn)
    }
  }

  onChange = (e) => {
    this.setState({
      confirmType: e.target.value,
    })
  }

  onOk = (values) => {
    const { workplaceId, onAlter, continueUrm, continueCache } = this.props
    const isUrm = this.state && this.state.confirmType === 1
    if (isUrm) {
      continueUrm(values, workplaceId)
    } else {
      continueCache(values, workplaceId, onAlter)
    }
  }

  confirmDialogContent = (isLimited) => (
    <Radio.Group onChange={this.onChange}>
      { isLimited ? <Radio value={1}>Продолжить операцию на данном УРМ</Radio> : null }
      <Radio value={2}>Перенаправить клиента в кассу для продолжения операции</Radio>
    </Radio.Group>
  )

  confirm = () => {
    const { form, limit } = this.props
    const values = form.getFieldsValue()
    form.validateFields((err) => {
      if (!err) {
        this.props.loadCommission(values, this.handleSubmit)
      }
    })
  }

  handleSubmit = (values: any, isUrm: boolean) => {
    const { form, task, limit } = this.props
    const onOk = this.onOk
    const isLimited = checkLimits(values.amount || task.amount, limit)
    Modal.confirm({
      title: 'Выбор рабочего места продолжения операции',
      content: this.confirmDialogContent(isLimited && isUrm),
      okText: 'ОК',
      cancelText: 'Отмена',
      width: 600,
      onOk() {
        onOk(values)
      },
    })

  }

  handleClear = () => {
    const { form, clearTransaction } = this.props
    form.resetFields()
    clearTransaction()
  }

  render() {
    const { getFieldDecorator } = this.props.form
    const { ovnBased, onCancel, representative, task, commissionLoading } = this.props
    return (
      <div>
        <Form>
          <Row>
            <Col span={18}>
              <Collapse className={styles.panel} defaultActiveKey={['1']}>
                <Collapse.Panel header="Реквизиты операции" key="1">
                  {task ?
                    <TransactionInfo
                      getFieldDecorator={getFieldDecorator}
                      task={task}
                      representative={representative}
                      ovnBased={ovnBased}
                    />
                    :
                    null
                  }
                </Collapse.Panel>
              </Collapse>
              {this.props.taskLoaded ? <Row className={styles.rightAligned}>
                <Button loading={commissionLoading} onClick={this.confirm} className={styles.greenButton}>Подтвердить</Button>
                <Button
                  className={styles.orangeButton}
                  onClick={this.handleClear}
                >
                  Очистить форму
                </Button>
                <Button
                  className={styles.redButton}
                  onClick={() => cancelOperation(onCancel)}
                >
                  Отменить операцию
                </Button>
              </Row> : <Row />
              }
            </Col>
            <Col span={6} />
          </Row>
        </Form>
      </div>
    )
  }
}

