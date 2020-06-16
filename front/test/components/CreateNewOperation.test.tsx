import React from 'react'
import {
  render,
  shallow,
  mount,
} from 'enzyme'
import toJson from 'enzyme-to-json'

import CreateNewOperation, { CreateNewOperationFormProps } from 'components/CreateNewOperation/CreateNewOperation'
import Section from 'components/Section/Section'

const mockFn = () => {
}
describe('Компонент CreateNewOperation', () => {
  const taskValues = {
    accountInfo: {
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
    subbranch: {
      bankName: '',
      bic: '',
      gosbCode: '',
      inn: '',
      level: '',
      subbranchCode: '',
      tbCode: '',
      vspCode: '',
    },
  }

  const props: CreateNewOperationFormProps = {
    continueUrm: mockFn,
    continueCache: mockFn,
    onCancel: mockFn,
    onAlter: mockFn,
    clearTransaction: mockFn,
    loadRepresentativeAndTask: mockFn,
    limit: '100',
    taskLoaded: false,
    ovnBased: false,
    subbranch: taskValues.subbranch,
    representative: {
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
    },
  }

  const task = {
    senderBank: '',
    senderBankBic: '',
    recipientBank: '',
    recipientBankBic: '',
    currencyType: '',
    num: '1',
    accountId: '',
    legalEntityShortName: '',
    inn: '',
    createdDate: '18.05.2017 18:00:00',
    repFio: `1 2 3`,
    symbols: [],
  }
  const wrapper = shallow(<CreateNewOperation {...props} />)
  const renderWrapper = render(<CreateNewOperation {...props} />)

  it('отображает компонент Form', () => {
    const html = wrapper.html()
    expect(html.match(/class="ant-form\sant-form-horizontal"/g) || [])
      .toHaveLength(1)
  })

  it('не отображает компонент <TransactInfo /> при отсутствии задачи', () => {
    const html = wrapper.html()
    expect(html.match(/class="transactionCard"/g) || [])
      .toHaveLength(0)
  })

  it('отображает 3 кнопки если задача загружена ', () => {
    wrapper.setProps({ taskLoaded: true })
    wrapper.update()
    const html = wrapper.html()
    expect(html.match(/class="ant-btn/g) || [])
      .toHaveLength(3)
  })

  it('отображает компонент <TransactInfo /> при наличии задачи>', () => {
    wrapper.setProps({ task, taskLoaded: true })

    wrapper.update()
    const html = wrapper.html()
    expect(html.match(/class="transactionCard"/g) || [])
      .toHaveLength(1)
  })

  it('отображает компоненты Section', () => {
    expect(wrapper.find(Section))
      .toHaveLength(0)
  })

  it('запрашивает loadRepresentativeAndTask если не загружено', () => {
    const spy = jest.fn()
    const getCreateNewOperationSpy = { ...props, loadRepresentativeAndTask: spy }
    mount(<CreateNewOperation {...getCreateNewOperationSpy} />)
    expect(spy).toBeCalled()
  })

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
