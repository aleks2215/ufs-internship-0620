import React from 'react'
import {
  Row,
  Col
} from 'antd'
import renderPair from './RenderPair'
import { LegalEntity } from 'interfaces/dto/Account.interface'

export interface LegalEntityProps {
  legalEntity: LegalEntity
}

export default function LegalEntity({ legalEntity }: LegalEntityProps) {
  const pairs = [['Наименование', legalEntity.fullName],
    ['Сокращенное наименование', legalEntity.shortName],
    ['ИНН', legalEntity.inn],
    ['ОГРН', legalEntity.ogrn],
    ['КПП', legalEntity.kpp],
    ['Юридический адрес', legalEntity.legalAddress],
    ['Фактический адрес', legalEntity.factAddress]]

  return (
    <Row>
      <Col span={23}>
        {pairs.map((item, i) => renderPair(item[0], item[1], i))}
      </Col>
    </Row>
  )
}
