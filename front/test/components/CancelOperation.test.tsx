import React from 'react'
import cancelOperation from 'components/CancelOperation'

describe('Компонент CancelOperation', () => {

  it('Функция возвращает значение', () => {
    const spy = jest.fn()
    expect(cancelOperation(spy)).toBeDefined()
  })
})
