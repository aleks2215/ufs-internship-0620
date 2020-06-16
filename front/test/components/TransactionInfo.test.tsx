import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import { Form } from 'antd'
const FormItem = Form.Item

import TransactionInfo, { TransactionInfoProps } from 'components/CreateNewOperation/TransactionInfo'
import Section from 'components/Section/Section'

const fieldDecoratorMock = () => (component) => component

describe('Компонент <TransactionInfo />', () => {
  const readOnly = false
  const props: TransactionInfoProps = {
    getFieldDecorator: fieldDecoratorMock,
    task: {
      representativeId: '0',
      num: '1',
      createdDate: 'now',
      clientTypeFk: true,
      currencyType: 'RUB',
      accountId: '1',
      legalEntityShortName: '2',
      inn: '3',
      symbols: [],
    },
    representative: {
      inn: '',
      lastName: 'ln',
      firstName: '',
      birthDate: '',
      birthPlace: '',
      resident: true,
      document: {
        type: '',
        series: '',
        number: '',
        issuedBy: '',
        issuedDate: '',
      },
    },
    readOnly,
  }
  const wrapper = shallow(<TransactionInfo {...props} />)
  const renderWrapper = render(<TransactionInfo {...props} />)

  it('использует <Section/> 2 раза', () => {
    expect(wrapper.find(Section)).toHaveLength(2)
  })

  it('отображает компонент <FormItem/> 2 раза', () => {
    expect(wrapper.find(FormItem)).toHaveLength(16)
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
