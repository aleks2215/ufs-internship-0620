import React from 'react'
import {
  Alert,
  Form,
  Button,
  Row,
  Col
} from 'antd';
const FormItem = Form.Item;

const styles = require('./Shared.less')

export interface AcceptPosFormProps {
  confirmOperation: Function,
  amount: number | string,
  message: string,
  taskId: string,
}

export default class AcceptPosForm extends React.Component<AcceptPosFormProps, any> {
  render() {
    const { taskId, amount, confirmOperation } = this.props;
    return (
      <div>
        <Row>
          <Col span={8} />
          <Col span={8}>
            <Form>
              <FormItem>
                <Alert
                  message="Подтверждение операции клиентом на POS-терминале"
                  description={`Подтвердите операцию на сумму ${amount} руб. 00 коп.`}
                  type="info"
                  showIcon
                />
              </FormItem>
              <FormItem className={styles.rightAligned}>
                <Button
                  className={styles.redButton}
                  onClick={() => confirmOperation(taskId, false)}
                >
                  Отмена
                </Button>
                <Button
                  className={styles.greenButton}
                  onClick={() => confirmOperation(taskId, true)}
                >
                  Подтвердить
                </Button>
              </FormItem>
            </Form>
          </Col>
          <Col span={8} />
        </Row>
      </div>
    );
  }
}
