import React from 'react'
import {
  shallow,
  render
} from 'enzyme'
import toJson from 'enzyme-to-json'
import {
  Table,
  Button,
  AutoComplete,
  Form,
} from 'antd'

const FormItem = Form.Item
import OperationSearchForm, { OperationSearchFormProps } from 'components/OperationSearchForm'
import Section from 'components/Section/Section'

describe('Компонент PendingOperationsForm', () => {

  const props: OperationSearchFormProps = {}

  const shallowWrapper = shallow(<OperationSearchForm {...props} />)
  const renderWrapper = render(<OperationSearchForm {...props} />)

  it('отображает компоненты <FormItem/>', () => {
    expect(shallowWrapper.find(FormItem)).toHaveLength(5)
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
