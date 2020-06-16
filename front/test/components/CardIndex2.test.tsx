import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import { Button } from 'antd'

import CardIndex2, { CardIndex2Props } from 'components/CardAccount/CardIndex2'

describe('Компонент CardIndex2', () => {

  const props: CardIndex2Props = {
    items: [{
      docId: '1',
      docType: '2',
      recipientShortName: '3',
      amount: '4',
      totalAmount: '5',
      paidPartly: false,
      comeInDate: '01.05.2017',
      priority: '1',
      paids: [{ date: '1', amount: '2' }]
    }],
    addSidePanel: (x, y) => {
    },
  }

  const renderWrapper = render(<CardIndex2 {...props} />)
  const shallowWrapper = shallow(<CardIndex2 {...props} />)

  it('выполняется рендер всех item', () => {
    expect(shallowWrapper.children()).toHaveLength(1)
  })

  it('работает кнопка', () => {
    const spy = jest.fn()
    shallowWrapper.setProps({ addSidePanel: spy })
    shallowWrapper.update()
    shallowWrapper.find(Button).at(0).simulate('click', {
      preventDefault() {
      }
    })
    expect(spy).toBeCalled()
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
