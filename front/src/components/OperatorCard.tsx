import React from 'react'
import {
  Col,
  Input,
  Row,
  Checkbox
} from 'antd'

import Section from 'components/Section/Section'
import { Representative } from 'interfaces/dto/Representative.interface'

const styles = require('./CreateNewOperation/CreateNewOperation.less')

export default (props) => {
  const disabledInput = value => (
    <Input disabled value={value} />
  )

  const label = (value) => (<span className={styles.label}>{value}</span>)

  return (
    <div style={{ minWidth: 300 }}>
      <Section header="">
        <Row>
          <Col span={7}>
            {label('Фамилия')}
          </Col>
          <Col span={16}>
            {disabledInput(props.lastName)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Имя')}
          </Col>
          <Col span={16}>
            {disabledInput(props.firstName)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Отчество')}
          </Col>
          <Col span={16}>
            {disabledInput(props.patronymic)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Телефон (внут.)')}
          </Col>
          <Col span={16}>
            {disabledInput('7135')}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Телефон (моб.)')}
          </Col>
          <Col span={16}>
            {disabledInput('+7-916-159-26-48')}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('E-mail')}
          </Col>
          <Col span={10}>
            {disabledInput(props.email)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Должность')}
          </Col>
          <Col span={16}>
            {disabledInput(props.position)}
          </Col>
        </Row>
      </Section>
    </div>
  )
}
