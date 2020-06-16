import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import { Input } from 'antd'

import { Representative } from 'interfaces/dto/Representative.interface'
import ClientCard from 'components/CreateNewOperation/ClientCard'

describe('Компонент ClientCard', () => {
  const position: string = 'position'
  const client: Representative = {
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
  }

  const wrapper = shallow(<ClientCard {...client} />)
  const renderWrapper = render(<ClientCard {...client} />)

  it('использует свойства <ClientCard />', () => {
    expect(wrapper.find(Input).first().prop('value')).toBe(client.lastName)
  })

  it('отображает поля карточки: <Input />', () => {
    expect(wrapper.find(Input)).toHaveLength(13)
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})



