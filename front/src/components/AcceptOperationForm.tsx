import React from 'react'
import {
  Alert,
  Button,
  Col,
  Input,
  Form,
  Row
} from 'antd'
import Section from './Section/Section'
import m2w from 'utils/MoneyToWords'

const styles = require('./Shared.less')

const FormItem = Form.Item

const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 16 },
}

export interface AcceptOperationFormProps {
  completeOperation: Function,
  onFinish: Function,
  amount: number,
  confirmed: boolean,
  complete: boolean,
  workplaceId: string,
  taskId: string,
  packageId: string,
  operationTypeCode: string,
  operationId: string,
  loadingConfirm: boolean,
  loadingCancel: boolean,
}

export default class AcceptOperationForm extends React.Component<AcceptOperationFormProps, any> {
  complete = (confirm) => {
    const { packageId, taskId, workplaceId, operationTypeCode } = this.props
    this.props.completeOperation({ packageId, taskId, workplaceId, operationTypeCode }, confirm)
  }

  renderIncomplete = (amount: number, loadingConfirm: boolean, loadingCancel: boolean) => (
    <Col span={16}>
      <Section header="Прием наличности">
        <Row>
          <FormItem className={styles.formItem} label="Сумма к приему" {...formItemLayout}>
            <Input size="large" disabled value={`${amount},00`} className={styles.bold} />
          </FormItem>
          <FormItem className={styles.formItem} label="Сумма прописью" {...formItemLayout}>
            <Input size="large" disabled value={m2w(amount)} className={styles.bold} />
          </FormItem>
        </Row>
      </Section>
      <Row className={styles.rightAligned}>
        <Button
          className={styles.greenButton}
          loading={loadingConfirm}
          onClick={() => this.complete(true)}
        >
          Завершить операцию
        </Button>
        <Button
          className={styles.redButton}
          loading={loadingCancel}
          onClick={() => this.complete(false)}>
          Отменить операцию
        </Button>
      </Row>
    </Col>
  );

  renderComplete = () => (
    <Col span={16}>
      <Section header="Прием наличности">
        <Alert
          message={`Операция успешно ${this.props.confirmed ? 'завершена' : 'отменена'}`}
          description="Вы можете вернуться в главное меню"
          type="success"
        />
      </Section>
      <Row>
        <Button className={styles.greenButton} onClick={() => this.props.onFinish()}>
          Вернуться в главное меню
        </Button>
      </Row>
    </Col>
  )

  render() {
    const {amount, loadingConfirm, loadingCancel} = this.props
    return (this.props.complete ? this.renderComplete() : this.renderIncomplete(amount, loadingConfirm, loadingCancel))
  }
}
