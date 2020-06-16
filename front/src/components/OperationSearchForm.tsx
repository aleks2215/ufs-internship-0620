import React from 'react'
import {Col, Form, Input, Row, Select} from 'antd';
const FormItem = Form.Item;
const Option = Select.Option;

const styles = require('./Shared.less');

export interface OperationSearchFormProps {
}

export default class OperationSearchForm extends React.Component<OperationSearchFormProps, any> {

  render() {

    const input = (placeholder?) => (
      <FormItem>
        <Input placeholder={placeholder}/>
      </FormItem>
    );

    const label = (value) => (<span className={styles.label}>{value}</span>);

    return (
      <div>
        <Row>
          {label('Введите данные по клиенту')}
        </Row>
        <Row style={{marginTop: 15}}>
          <Col span={4}>
            {label('Фамилия')}
          </Col>
          <Col offset={1} span={16}>
            {input()}
          </Col>
        </Row>
        <Row>
          <Col span={4}>
            {label('Имя')}
          </Col>
          <Col offset={1} span={16}>
            {input()}
          </Col>
        </Row>
        <Row>
          <Col span={4}>
            {label('Отчество')}
          </Col>
          <Col offset={1} span={16}>
            {input()}
          </Col>
        </Row>
        <Row style={{marginTop: 15}}>
          {label('Введите данные ДУЛ: ')}
        </Row>
        <Row>
          <Col span={4}>
            <span className={styles.label}>Тип ДУЛ</span>
          </Col>
          <Col offset={1} span={16} >
            <Select defaultValue="1" style={{display: 'block'}}>
              <Option value="1">Паспорт гражданина РФ</Option>
              <Option value="2">Загранпаспорт гражданина РФ</Option>
              <Option value="3">Военный билет</Option>
              <Option value="4">Паспорт моряка</Option>
            </Select>
          </Col>
        </Row>
        <Row>
          <Col className={styles.label} span={4}>
            {input('Серия')}
          </Col>
          <Col offset={1} span={16}>
            {input('Номер')}
          </Col>
        </Row>
      </div>
    );
  }
}
