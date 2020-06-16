import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import AcceptOperationForm, { AcceptOperationFormProps } from 'components/AcceptOperationForm'
import Section from 'components/Section/Section'
import { Alert } from 'antd'

describe('Компонент AcceptOperationForm', () => {
  const props: AcceptOperationFormProps = {
    completeOperation: () => {
    },
    onFinish: () => {
    },
    amount: 100,
    confirmed: false,
    complete: false,
    workplaceId: '',
    taskId: '',
    packageId: '',
    operationTypeCode: '',
    operationId: '',
    loadingConfirm: true,
    loadingCancel: true,
  }
  const shallowWrapper = shallow(<AcceptOperationForm {...props} />)
  const renderWrapper = render(<AcceptOperationForm {...props} />)

  it('отображает форму для завершенной операции', () => {
    shallowWrapper.setProps({ complete: true })
    shallowWrapper.update()
    expect(shallowWrapper.find(Alert)).toHaveLength(1)
  })

  it('использует свойства <AcceptOperationForm />', () => {
    expect(shallowWrapper.find(Section).prop('header')).toBe('Прием наличности')
  })

  it('отображает компонент <Section />', () => {
    expect(shallowWrapper.find(Section)).toHaveLength(1)
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
