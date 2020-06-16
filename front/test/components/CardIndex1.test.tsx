import React from 'react'
import {
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'
import { Button } from 'antd'

import CardIndex1, { CardIndex1Props } from 'components/CardAccount/CardIndex1'

describe('Компонент CardIndex1', () => {

  const props: CardIndex1Props = {
    items: [{
      docId: '98710245', docType: 'Платежное поручение', recipientShortName: 'Газпром', amount: '20000',
      comeInDate: '01.05.2017'
    }, {
      docId: '98710246', docType: 'Платежное поручение', recipientShortName: 'Газпром', amount: '20000',
      comeInDate: '01.05.2017'
    }],
    addSidePanel: (x, y) => {
    },
  }

  const renderWrapper = render(<CardIndex1 {...props} />)
  const shallowWrapper = shallow(<CardIndex1 {...props} />)

  it('выполняется рендер всех item', () => {
    expect(shallowWrapper.children()).toHaveLength(2)
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
