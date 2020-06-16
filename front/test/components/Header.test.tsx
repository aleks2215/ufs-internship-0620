import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import {
  Icon,
  Tooltip
} from 'antd'

import Header from 'components/Auth/Header'
import { UserInfo } from 'interfaces/dto/UserInfo.interface'

describe('Компонент Header', () => {
  const position: string = 'position'
  const user: UserInfo = {
    employeeId: '1',
    firstName: '1',
    lastName: '1',
    login: '1',
    patronymic: '1',
    position: '1',
    roleId: '1',
    sessionId: '1',
  }

  const props = {
    onLogout: () => {
    },
    loading: false,
    user,
  }
  const name = `${user.lastName} ${user.firstName} ${user.patronymic}`

  const wrapper = shallow(<Header {...props} />)
  const renderWrapper = render(<Header {...props} />)

  it('использует свойства <Header />', () => {
    expect(wrapper.find('span').text()).toBe(name)
  })

  it('отображает иконку выхода <Icon />', () => {
    expect(wrapper.find(Icon)).toHaveLength(1)
  })

  it('работает кнопка Выхода', () => {
    const spy = jest.fn()
    wrapper.setProps({ onLogout: spy })
    wrapper.update()
    wrapper.find('a').simulate('click', {
      preventDefault() {
      }
    })
    expect(spy).toBeCalled()
  })

  it('отображает 2 блока позиции и инициалы', () => {
    const html = wrapper.html()
    expect(html.match(/class="position"/g) || [])
      .toHaveLength(1)
    expect(html.match(/class="initials"/g) || [])
      .toHaveLength(1)
  })

  it('тип иконки меняется в зависмости от того идет ли загрузка', () => {
    wrapper.setProps({ loading: true })
    wrapper.update()
    expect(wrapper.find(Icon).prop('type')).toBe('loading')
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})



