import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'

import Section from 'components/Section/Section'

describe('Компонент <Section />', () => {
  const hi = 'hi'

  const wrapper = shallow(<Section header={hi}><textarea>text</textarea></Section>)
  const renderWrapper = render(<Section header={hi}><textarea /></Section>)

  it('использует свойство header', () => {
    expect(wrapper.find('div').first().text()).toBe(hi)
  })

  it('отображает дочерние компоненты', () => {
    expect(wrapper.find('textarea').text()).toBe('text')
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
