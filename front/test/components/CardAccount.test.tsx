import React from 'react'
import {
  mount,
  render,
  shallow
} from 'enzyme'
import toJson from 'enzyme-to-json'

import { Collapse } from 'antd'

import {
  Account,
} from 'interfaces/dto/Account.interface'
import CardAccountForm, { CardAccountFormProps } from 'components/CardAccount/CardAccountForm'
import PersonalEntity from 'components/CardAccount/PersonalEntity'
import LegalEntity from 'components/CardAccount/LegalEntity'
import CardIndex1 from 'components/CardAccount/CardIndex1'
import CardIndex2 from 'components/CardAccount/CardIndex2'

const mockFn = () => {
}

describe('Компонент CardAccountForm', () => {

  const account: Account = {
    legalEntity: {
      id: '', fullName: 'OOO ДНК', shortName: 'OOO ДНК', inn: '01221412', ogrn: '102312412', kpp: '121412412',
      legalAddress: 'Ярославль', factAddress: 'Ярославль'
    },
    account: {
      id: '4782 9871 9873 897009',
      typeId: 'Расчетный счет',
      agreement: {
        id: '124567',
        openDate: '',
      },
      status: '1',
      lastTransactionDate: '18.06.2001',
      currencyType: 'RUB',
      active: true,
      tbCode: '',
      gosbCode: '',
      vspCode: '',
      subbranchCode: '',
      changeStatusDescription: '',
      clientTypeFk: true,
      seizured: true,
    },
    accountResidues: {
      currentBalance: '1',
      expectedBalance: '2',
      fixedBalance: '3',
    },
    cardIndexes1: [{
      docId: '98710245', docType: 'Платежное поручение', recipientShortName: 'Газпром', amount: '20000',
      comeInDate: '01.05.2017'
    }, {
      docId: '98710246', docType: 'Платежное поручение', recipientShortName: 'Газпром', amount: '20000',
      comeInDate: '01.05.2017'
    }],
    cardIndexes2: [{
      docId: '1',
      docType: '2',
      recipientShortName: '3',
      amount: '4',
      totalAmount: '5',
      paidPartly: false,
      comeInDate: '01.05.2017',
      priority: '1',
      paids: [{ date: '1', amount: '2' }]
    }]
  }

  const props: CardAccountFormProps = {
    loaded: true,
    info: account,
    sidePanel: [{ pairs: ['0', '1'] }],
    cardInfo: {},
    getCardAccount: mockFn,
    addSidePanel: (x, y) => {
    },
    removeSidePanel: mockFn,
    onComplete: mockFn,
  }

  const renderWrapper = render(<CardAccountForm {...props} />)
  const shallowWrapper = shallow(<CardAccountForm {...props} />)

  it('отображает контент - компонент <Collapse />', () => {
    expect(shallowWrapper.find(Collapse)).toHaveLength(1)
  })

  it('отображает контент - компонент <LegalEntity />', () => {
    expect(shallowWrapper.find(LegalEntity)).toHaveLength(1)
  })

  it('отображает контент - компонент <PersonalEntity />', () => {
    expect(shallowWrapper.find(PersonalEntity)).toHaveLength(1)
  })

  it('отображает контент - компонент <CardIndex1 />', () => {
    expect(shallowWrapper.find('CardIndex1')).toHaveLength(1)
  })

  it('отображает контент - компонент <CardIndex2 />', () => {
    expect(shallowWrapper.find('CardIndex2')).toHaveLength(1)
  })

  it('не отображает юридическую информация о лице если ее нет', () => {
    shallowWrapper.setProps({ info: { ...props.info, legalEntity: undefined } })
    shallowWrapper.update()
    expect(shallowWrapper.find(LegalEntity) || []).toHaveLength(0)
  })

  it('не отображает общую информацию по счету если ее нет', () => {
    shallowWrapper.setProps({ info: { ...props.info, account: undefined } })
    shallowWrapper.update()
    expect(shallowWrapper.find(PersonalEntity) || []).toHaveLength(0)
  })

  it('запрашивает getCardAccount если не загружено', () => {
    const spy = jest.fn()
    const getCardAccountSpy = { ...props, getCardAccount: spy, loaded: false }
    mount(<CardAccountForm { ...getCardAccountSpy} />)
    expect(spy).toBeCalled()
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
