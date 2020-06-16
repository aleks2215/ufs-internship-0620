import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import {
  Alert,
  Button
} from 'antd'

import AcceptPosForm, { AcceptPosFormProps } from 'components/AcceptPosForm'

describe('Компонент AcceptPosForm', () => {
  const message = 'Подтвердите операцию на сумму 100 руб. 00 коп.'
  const props: AcceptPosFormProps = {
    amount: '100',
    confirmOperation: (onComplete, onCancel) => {
    },
    message,
    taskId: '',
  }

  const wrapper = shallow(<AcceptPosForm {...props} />)
  const renderWrapper = render(<AcceptPosForm {...props} />)

  it('использует свойства <AcceptPosForm />', () => {
    expect(wrapper.find(Alert).prop('description')).toBe(message)
  })

  it('отображает 2 кнопки <Button />', () => {
    expect(wrapper.find(Button)).toHaveLength(2)
  })

  it('работает кнопка Подтвердить', () => {
    const spy = jest.fn()
    wrapper.setProps({ confirmOperation: spy })
    wrapper.update()
    wrapper.find(Button).at(0).simulate('click', {
      preventDefault() {
      }
    })
    expect(spy).toBeCalled()
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})



