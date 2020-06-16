import { createAction } from 'redux-actions'

import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

const LOAD: string = 'operations/load'
const FAVE: string = 'operations/fave'
const SHOW: string = 'operations/show'

export interface Operations {
  tab: string,
  items: Array<{
    id: string,
    name: string,
    enabled: boolean,
    fave: boolean,
    category: string,
  }>,
  favourites: Array<string>,
}

const initialState: Operations = {
  tab: 'reportings',
  items: [],
  favourites: [],
}

/** REDUCER */
export default function reducer(state: Operations = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case LOAD:
      return {
        ...state,
        items: action.payload,
      }
    case SHOW:
      return {
        ...state,
        tab: action.payload,
      }
    default:
      return state
  }
}

export function load() {
  return {
    type: LOAD,
    payload: [{
      id: '0',
      name: 'Журнал операций с наличными денежнымы средствами',
      enabled: true,
      fave: true,
      category: 'reportings',
    }, {
      id: '1',
      name: 'Отчет по отправленным уведомлениям в комплаенс',
      enabled: true,
      fave: false,
      category: 'reportings',
    }, {
      id: '2',
      name: 'Отчет о выданных чековых книжках',
      enabled: false,
      fave: false,
      category: 'reportings',
    }],
  }
}

/** ACTIONS */
export const fave = createAction(FAVE)

export const show = createAction(SHOW)
