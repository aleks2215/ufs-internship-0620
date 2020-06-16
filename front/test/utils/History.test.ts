import React from 'react'
import { history } from 'utils/History'

describe('History', () => {

  it('Объект history должен быть не пустым', () => {
    expect(Object.keys(history).length).toBeGreaterThan(0)
  })
})
