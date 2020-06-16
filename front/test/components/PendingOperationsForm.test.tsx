import React from 'react'
import {
  shallow,
  render
} from 'enzyme'
import toJson from 'enzyme-to-json'
import {
  Table,
  AutoComplete,
} from 'antd'

import PendingOperationsForm, { PendingOperationsFormProps } from 'components/PendingOperationsForm'
import Section from 'components/Section/Section'

describe('Компонент PendingOperationsForm', () => {
  const items = [{
    id: '1',
    packageId: '89731236',
    incomingDate: 'dd.mm.yyyy hh24:mi:ss',
    credentials: 'Петров Петр Петрович',
    clientType: 'ЮЛ',
    operationType: 'Взнос наличных на счет корпоративной/бюджетной карты',
  }, {
    id: '2',
    packageId: '89731238',
    incomingDate: 'dd.mm.yyyy hh24:mi:ss',
    credentials: 'Петров Петр Петрович',
    clientType: 'ЮЛ',
    operationType: 'Взнос наличных на счет корпоративной/бюджетной карты',
  },]

  const props: PendingOperationsFormProps = {
    load: () => {
    },
    loadPendingOperations: () => {
    },
    nextStepOvn: () => {
    },
    filter: () => true,
    pendingOperations: {
      loaded: true,
      items: items,
      seed: items,
    }
  }

  const shallowWrapper = shallow(<PendingOperationsForm {...props} />)
  const renderWrapper = render(<PendingOperationsForm {...props} />)

  it('отображает компонент <Table/>', () => {
    expect(shallowWrapper.find(Table)).toHaveLength(1)
  })

  it('отображает компонент <Section/>', () => {
    expect(shallowWrapper.find(Section)).toHaveLength(1)
  })

  it('отображает компонент <AutoComplete/>', () => {
    expect(shallowWrapper.find(AutoComplete)).toHaveLength(2)
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
