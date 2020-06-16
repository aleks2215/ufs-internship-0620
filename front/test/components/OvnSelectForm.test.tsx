import React from 'react'
import {
  mount,
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import {
  Table,
  Button
} from 'antd'

import OvnSelectForm, { OvnSelectFormProps } from 'components/OvnSelectForm'
import Section from 'components/Section/Section'

describe('Компонент <OvnSelectForm />', () => {
  const props: OvnSelectFormProps = {
    getOvnList: () => {
    },
    nextStepNew: () => {
    },
    nextStepOvn: () => {
    },
    account: {
      id: '1',
      status: '0'
    },
    loaded: true,
    ovnList: [],
  }
  const shallowWrapper = shallow(<OvnSelectForm {...props} />)
  const renderWrapper = render(<OvnSelectForm {...props} />)

  it('использует <Section/>', () => {
    expect(shallowWrapper.find(Section)).toHaveLength(1)
  })

  it('отображает компонент <Table/>', () => {
    expect(shallowWrapper.find(Table)).toHaveLength(1)
  })

  it('отображает 2 кнопки <Button />', () => {
    expect(shallowWrapper.find(Button)).toHaveLength(2)
  })

  it('запрашивает getOvnList если не загружено', () => {
    const spy = jest.fn()
    const getOvnListSpy = { ...props, getOvnList: spy, loaded: false }
    mount(<OvnSelectForm { ...getOvnListSpy} />)
    expect(spy).toBeCalled()
  })

  it('работает кнопка Создать новую операцию', () => {
    const spy = jest.fn()
    shallowWrapper.setProps({ nextStepNew: spy })
    shallowWrapper.update()
    shallowWrapper.find(Button).at(0).simulate('click', {
      preventDefault() {
      }
    })
    expect(spy).toBeCalled()
  })

  it('кнопка На основании выбранного ОВН disabled, если ничего не выбрано', () => {
    shallowWrapper.setState({})
    shallowWrapper.update()
    expect(shallowWrapper.find(Button).at(1).prop('disabled')).toBeTruthy()
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
