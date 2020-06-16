import {
  api,
  postData
} from 'utils/FetchData'

describe('FetchData', () => {

  it('Должен вызываться на spot dispatch', () => {
    const dispatch = jest.fn()

    const action = postData('', {
      types: ['1', '2', '3', '4'],
      actionParams: {},
      params: {},
      onSuccess: () => {
      }
    })
    action(dispatch)

    expect(dispatch).toHaveBeenCalled()
  })

  it('api не пуст', () => {
    expect(api).not.toBeNull()
  })

  it('api.post не пуст', () => {
    expect(api.post).not.toBeNull()
  })

})
