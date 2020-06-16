import {
  api as client,
  postData
} from 'utils/FetchData'

const post = (url: string) => (options: { types: Array<string>, actionParams: any, params: any, onSuccess?(result: any) }) => postData(
  url,
  options)

const api = {
  client,
  auth: {
    login: post('/login'),
    logout: post('/logout'),
  },

  creditCard: {
    verify: post('/pos/verify'),
    operationConfirm: post('/pos/operationConfirm'),
    account: {
      get: post('/account/byCardNumber'),
    },
  },

  operation: {
    continueUrm: post('/operation/continueUrm'),
    continueCash: post('/operation/continueCash'),
    confirm: post('/operation/confirm'),
    cancel: post('/operation/cancel'),
    tasks: {
      get: post('/operation/tasksForwarded')
    }
  },

  operations: {
    get: post('/operationTypes/'),
    favourites: {
      save: post('/operationTypes/saveFavourites'),
      get: post('/operationTypes/getFavourites'),
    },
    cashSymbols: {
      get: post('/operationTypes/cashSymbols'),
    },
  },

  operator: {
    get: post('/operator/'),
  },

  ovn: {
    get: post('/announcement/'),
    getById: post('/announcement/byId'),
    commission: {
      get: post('announcement/commission'),
    },
    account20202: post('announcement/account20202'),
  },

  representative: {
    get: post('/representative/byCardNumber'),
    search: post('/representative/search'),
  },

  workplace: {
    get: post('/workplace/'),
  },

  report: {
    get: post('/report/operationJournal')
  }
}

export default api
