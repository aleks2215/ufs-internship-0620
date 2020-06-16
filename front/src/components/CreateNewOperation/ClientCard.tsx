import React from 'react'
import {
  Col,
  Input,
  Row,
  Checkbox
} from 'antd'

import Section from 'components/Section/Section'
import { Representative } from 'interfaces/dto/Representative.interface'

const styles = require('./CreateNewOperation.less')

export default (props: Representative) => {
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
            {label('Дата рождения')}
          </Col>
          <Col span={16}>
            {disabledInput(props.birthDate)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Тип ДУЛ')}
          </Col>
          <Col span={16}>
            {disabledInput(props.document.type)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Серия')}
          </Col>
          <Col span={7}>
            {disabledInput(props.document.series)}
          </Col>
          <Col span={4}>
            {label('Номер')}
          </Col>
          <Col span={5}>
            {disabledInput(props.document.number)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Место рождения')}
          </Col>
          <Col span={16}>
            {disabledInput(props.birthPlace)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Дата выдачи')}
          </Col>
          <Col span={16}>
            {disabledInput(props.document.issuedDate)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Выдан (орган)')}
          </Col>
          <Col span={16}>
            {disabledInput(props.document.issuedBy)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('ИНН/КИО')}
          </Col>
          <Col span={16}>
            {disabledInput(props.inn)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Индекс')}
          </Col>
          <Col span={16}>
            {disabledInput(props.postcode)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Адрес')}
          </Col>
          <Col span={16}>
            {disabledInput(props.address)}
          </Col>
        </Row>
        <Row>
          <Col span={7}>
            {label('Резидент')}
          </Col>
          <Col span={16}>
            <Checkbox checked={props.resident} />
          </Col>
        </Row>
      </Section>
    </div>
  )
}
