import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'

import CardConfirmForm, { CardConfirmFormProps } from 'components/CardConfirmForm'

describe('Компонент CardConfirm', () => {
  const number = '1111111111'
  const pin = '1111'
  const card = { number, pin }
  const confirmCard = () => {
  }

  const props: CardConfirmFormProps = {
    onComplete: () => {
    },
    confirmCard,
    card,
  }

  const wrapper = shallow(<CardConfirmForm {...props} />)
  const renderWrapper = render(<CardConfirmForm {...props} />)

  it('использует свойства <CardConfirmForm />', () => {
    expect(wrapper.prop('card')).toBe(card)
  })

  it('отображает компонент Form', () => {
    const html = wrapper.html()
    expect(html.match(/class="ant-form\sant-form-horizontal\slogin-form"/g) || [])
      .toHaveLength(1)
  })

  it('отображает 3 компонента FieldItem', () => {
    const html = wrapper.html()
    expect(html.match(/"ant-row\sant-form-item"/g) || [])
      .toHaveLength(3)
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
