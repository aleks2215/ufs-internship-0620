import React from 'react'
import {
  Collapse,
  Col,
  Form,
  Row,
  Button
} from 'antd'

import TransactionInfo from './TransactionInfo'
import cancelOperation from '../CancelOperation'
import { CreateNewOperationFormProps } from './CreateNewOperation'
import { Representative } from 'interfaces/dto/Representative.interface'

const styles = require('./CreateNewOperation.less')

export interface ConfirmNewOperationProps extends CreateNewOperationFormProps {
  onComplete: Function,
  onCancel: Function,
  changeTransaction: any,
  representative: Representative,
  commission: string,
  packageId?: string,
  operationTypeCode?: string,
  form?: any,
}

@Form.create<any>()
export default class ConfirmNewOperation extends React.Component<ConfirmNewOperationProps, any> {
  handleSubmit = (e) => {
    e.preventDefault()
    this.props.form.validateFields((err) => {
      if (!err) {
        this.props.onComplete()
      }
    })
  }

  render() {
    const { getFieldDecorator } = this.props.form
    const { task, representative, onCancel, commission, changeTransaction } = this.props
    return (
      <div>
        <Form onSubmit={this.handleSubmit}>
          <Row>
            <Col span={18}>
              <Collapse className={styles.panel} defaultActiveKey={['1']}>
                <Collapse.Panel header="Реквизиты операции" key="1">
                  <TransactionInfo
                    getFieldDecorator={getFieldDecorator}
                    task={task}
                    representative={representative}
                    readOnly
                  />
                </Collapse.Panel>
              </Collapse>
              <Row className={styles.rightAligned}>
                <Button htmlType="submit" className={styles.greenButton}>
                  Перейти к подтверждению
                </Button>
                <Button
                  className={styles.orangeButton}
                  onClick={changeTransaction}
                >
                  Изменить
                </Button>
                <Button className={styles.redButton} onClick={() => cancelOperation(onCancel)}>
                  Отменить операцию
                </Button>
              </Row>
            </Col>
            <Col span={6}>
              <Collapse className={styles.panel} defaultActiveKey={['1']}>
                <Collapse.Panel header="Комиссия" key="1">
                  <div>Размер комиссии</div>
                  <div>{`${commission} рублей 00 коп.`}</div>
                </Collapse.Panel>
              </Collapse>
            </Col>
          </Row>
        </Form>
      </div>
    )
  }
}
