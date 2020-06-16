import React from 'react'
import { render } from 'enzyme'
import toJson from 'enzyme-to-json'

import LegalEntity, { LegalEntityProps } from 'components/CardAccount/LegalEntity'

describe('Компонент LegalEntity', () => {

  const props: LegalEntityProps = {
    legalEntity: {
      id: '', fullName: 'OOO ДНК', shortName: 'OOO ДНК', inn: '01221412', ogrn: '102312412', kpp: '121412412',
      legalAddress: 'Ярославль', factAddress: 'Ярославль'
    }
  }

  const renderWrapper = render(<LegalEntity {...props} />)

  it('совпадает со своим снимком', () => {
    expect(toJson(renderWrapper)).toMatchSnapshot()
  })
})
