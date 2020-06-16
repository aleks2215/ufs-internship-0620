import React from 'react'
import {
  Button,
  Col,
  Row
} from 'antd'
import Section from './Section/Section'

const styles = require('./Shared.less')

export interface ForwardToCashofficeFormProps {
  onFinish: Function,
  taskId: string,
  packageId: string,
}

export default class ForwardToCashofficeForm extends React.Component<ForwardToCashofficeFormProps, any> {
  render() {
    return (
      <Col span={16}>
        <Section header="Сеанс завершен">
          <Row className={styles.paragraph}>
            <Col span={24}>
              Клиент был перенаправлен в кассу для продолжения операции. Текущий сеанс обслуживания завершен
            </Col>
          </Row>
          <Row className={styles.paragraph}>
            <Col span={24}>
              Вы можете передать кассиру информацию об операции(ях):
            </Col>
          </Row>
          <Row className={styles.paragraph}>
            <Col span={4}>
              ID пакета операций:
            </Col>
            <Col span={20}>
              {this.props.packageId}
            </Col>
          </Row>
          <Row className={styles.paragraph}>
            <Col span={4}>
              ID операции:
            </Col>
            <Col span={20}>
              {this.props.taskId}
            </Col>
          </Row>
          <Row className={styles.centerAligned}>
            <Button className={styles.greenButton} onClick={() => this.props.onFinish()}>
              Вернуться в главное меню
            </Button>
          </Row>
        </Section>
      </Col>
    )
  }
}
