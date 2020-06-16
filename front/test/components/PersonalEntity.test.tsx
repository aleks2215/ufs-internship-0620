import React from 'react'
import { render } from 'enzyme'
import toJson from 'enzyme-to-json'

import PersonalEntity, { PerosnalEntityProps } from 'components/CardAccount/PersonalEntity'

describe('Компонент PersonalEntity', () => {

  const props: PerosnalEntityProps = {
    info: {
      id: 1,
      typeId: 1,
      currencyType: 'RUB',
      agreement: {
        id: '1',
        openDate: '1',
      },
      tbCode: '1',
      gosbCode: '1',
      vspCode: '1',
      subbranchCode: '1',
      lastTransactionDate: '1',
      active: true,
      status: '1',
      changeStatusDescription: '1',
      clientTypeFk: true,
      seizured: true
    },
    accountResidues: {
      currentBalance: '1',
      expectedBalance: '1',
      fixedBalance: '1',
    },
    addSidePanel: (x, y) => {
    },
    seizures: [{
      type: 1,
      reason: '1',
      fromDate: '1',
      toDate: '1',
      amount: '1',
      initiatorShortName: '1',
    }]
  }

  const renderWrapper = render(<PersonalEntity {...props} />)

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
