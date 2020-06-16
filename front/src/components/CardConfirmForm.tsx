import React from 'react'
import {
  Form,
  Icon,
  Input,
  Button,
  Row,
  Col
} from 'antd'

const FormItem = Form.Item

export interface CardConfirmFormProps {
  confirmCard(params: {number: string, pin: string}, onComplete: Function),
  onComplete(),
  card: any,
  form?: any,
}

export interface CardDetails {
  number: string,
  pin: string,
}

@Form.create<any>()
export default class CardConfirmForm extends React.Component<CardConfirmFormProps, any> {
  handleSubmit = (e) => {
    e.preventDefault()
    this.props.form.validateFields((err) => {
      if (!err) {
        /* tslint:disable-next-line */
        const { number, pin } = this.props.form.getFieldsValue() as CardDetails
        this.props.confirmCard({ number, pin }, this.props.onComplete)
      }
    })
  }

  render() {
    const { getFieldDecorator } = this.props.form
    const card = this.props.card
    return (
      <div>
        <Row>
          <Col span={8} />
          <Col span={8}>
            <Form onSubmit={this.handleSubmit} className="login-form">
              <FormItem>
                {getFieldDecorator('number', {
                  rules: [{ required: true, message: 'Введите номер карты' },
                    {
                      pattern: new RegExp('^[0-9]{16}'),
                      message: 'Номер карты должен состоять из 16 символов 0-9',
                    }],
                  validateTrigger: 'onSubmit',
                  initialValue: card.number,
                })(
                  <Input placeholder="Номер карты" />
                )}
              </FormItem>
              <FormItem>
                {getFieldDecorator('pin', {
                  rules: [{ required: true, message: 'Введите ПИН-код' },
                    {
                      pattern: new RegExp('^[0-9]{4}'),
                      message: 'ПИН-код должен состоять из 4 символов 0-9',
                    }],
                  validateTrigger: 'onSubmit',
                })(
                  <Input
                    prefix={<Icon type="lock" style={{ fontSize: 13 }} />}
                    type="password" placeholder="ПИН-код"
                  />
                )}
              </FormItem>
              <FormItem>
                <Button type="primary" htmlType="submit">
                  Отправить
                </Button>
              </FormItem>
            </Form>
          </Col>
          <Col span={8} />
        </Row>
      </div>
    )
  }
}
