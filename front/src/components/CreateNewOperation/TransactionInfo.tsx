import React from 'react'
import {
  Form,
  Col,
  Input,
  Row,
  Button,
  Modal,
} from 'antd'
const FormItem = Form.Item;

import Section from 'components/Section/Section'
import ClientCard from  './ClientCard'
import { Task } from 'interfaces/dto/Task.interface'
import { Representative } from 'interfaces/dto/Representative.interface'
import m2w from 'utils/MoneyToWords'

const styles = require('./CreateNewOperation.less')

export interface TransactionInfoProps {
  getFieldDecorator: Function,
  representative: Representative,
  task: Task,
  ovnBased?: boolean,
  readOnly?: boolean,
}

export default class TransactionInfo extends React.Component<TransactionInfoProps, any> {
  state = { visible: false }
  toggleModal = () => {
    this.setState({
      visible: !this.state.visible,
    });
  }
  handleCancel = (e) => {
    this.setState({
      visible: false,
    });
  }

  render() {
    const { getFieldDecorator, readOnly = false, ovnBased = false, representative } = this.props
    const task = { ...this.props.task, amountInWords: m2w(this.props.task.amount) }
    const disabledInput = (key) => (<FormItem>
      {(typeof task[key] === 'undefined') ?
        <Input disabled />
        :
        <Input disabled value={task[key]} />}
    </FormItem>)

    const requiredInput = (key, emptyMessage) => {
      if (readOnly) {
        return disabledInput(key)
      }
      let config = { rules: [{ required: true, message: emptyMessage }] }
      if (typeof task[key] !== 'undefined') {
        config = { ...config, initialValue: task[key] }
      }
      return (
        <FormItem>
          {getFieldDecorator(key, config)(
            <Input />
          )}
        </FormItem>
      )
    }

    const amountInput = () => {
      const key = 'amount'
      if (readOnly || ovnBased) {
        return (<Input disabled value={`${task.amount} 00 коп.`} />)
      }
      let config = {
        rules: [{ required: true, message: 'Укажите сумму операции' },
          { pattern: (new RegExp('^\\d+$')), message: 'сумма операции должна быть целым числом' }],
      }
      if (typeof task.amount !== 'undefined') {
        config = { ...config, initialValue: task.amount }
      }
      return (
        <FormItem>
          {getFieldDecorator(key, config)(
            <Input addonAfter=" 00 коп." />
          )}
        </FormItem>
      )
    }

    const label = (value) => (<span className={styles.label}>{value}</span>)
    return (
      <div className="transactionCard">
        <Section header="Основная информация">
          <Row>
            <Col span={24}>
              <Row>
                <Col span={10}>
                  {label('Объявление №')}
                  {ovnBased ?
                    label(task.num)
                    :
                    requiredInput('num', 'Укажите номер объявления')
                  }
                </Col>
                <Col span={14}>
                  {label(`Дата создания ${task.createdDate}`)}
                </Col>
              </Row>
              <Row>
                <Col span={3}>
                  {label('От кого')}
                </Col>
                <Col span={13}>
                  <Button type="dashed"
                          className={styles.fullwidth}
                          onClick={this.toggleModal}
                  >{task.repFio}</Button>
                  <Modal
                    title="Карточка клиента/представителя клиента"
                    visible={this.state.visible}
                    onCancel={this.handleCancel}
                    cancelText="Отмена"
                    footer={null}
                  >
                    <ClientCard {...representative} />
                  </Modal>
                </Col>
                <Col span={2} className={styles.rightAligned}>
                  <div className={styles.subHead}></div>
                  <div>{label('счет №')}</div>
                </Col>
                <Col span={6}>
                  <div className={styles.subHead}>Дебет</div>
                  {disabledInput('debetAccount')}
                </Col>
              </Row>
              <Row>
                <Col span={3}>
                  {label('Получатель')}
                </Col>
                <Col span={13}>
                  {disabledInput('legalEntityShortName')}
                </Col>
                <Col span={2} className={styles.rightAligned}>
                  <div className={styles.subHead}></div>
                  <div>{label('счет №')}</div>
                </Col>
                <Col span={6}>
                  <div className={styles.subHead}>Кредит</div>
                  {disabledInput('accountId')}
                </Col>
              </Row>
              <Row>
                <Col span={1}>
                  {label('ИНН')}
                </Col>
                <Col span={4}>
                  {disabledInput('inn')}
                </Col>
                <Col span={1}>
                  {label('КПП')}
                </Col>
                <Col span={4}>
                  {disabledInput('kpp')}
                </Col>
                <Col span={2} className={styles.rightAligned}>
                  {label('счет №')}
                </Col>
                <Col span={6}>
                  {disabledInput('accountId')}
                </Col>
                <Col span={6} />
              </Row>
              <Row>
                <Col span={6}>
                  {label('Наименование банка-вносителя')}
                </Col>
                <Col span={12}>
                  {disabledInput('senderBank')}
                </Col>
                <Col span={2} className={styles.rightAligned}>
                  {label('БИК')}
                </Col>
                <Col span={4}>
                  {disabledInput('senderBankBic')}
                </Col>
              </Row>
              <Row>
                <Col span={6}>
                  {label('Наименование банка-получателя')}
                </Col>
                <Col span={12}>
                  {disabledInput('recipientBank')}
                </Col>
                <Col span={2} className={styles.rightAligned}>
                  {label('БИК')}
                </Col>
                <Col span={4}>
                  {disabledInput('recipientBankBic')}
                </Col>
              </Row>
              <Row>
                <Col span={4}>
                  {label('Сумма операции')}
                </Col>
                <Col span={6}>
                  {amountInput()}
                </Col>
              </Row>
              <Row>
                <Col span={4}>
                  {label('Сумма прописью')}
                </Col>
                <Col span={20}>
                  {disabledInput('amountInWords')}
                </Col>
              </Row>
              <Row>
                {label('Источник поступления')}
              </Row>
              <Row>
                {ovnBased ?
                  disabledInput('source')
                  :
                  requiredInput('source', 'Укажите источник поступения')
                }
              </Row>
            </Col>
          </Row>
        </Section>
        { task.clientTypeFk ?
        <Section header="Дополнительная информация">
          <Row>
            {label('Наименование организации')}
          </Row>
          <Row>
            {requiredInput('organisationNameFk', 'Укажите наименование организации')}
          </Row>
          <Row>
            {label('Лицевой счет')}
          </Row>
          <Row>
            {requiredInput('personalAccountId', 'Укажите лицевой счет')}
          </Row>
        </Section>
          : null
        }
      </div>
    )
  }
}
