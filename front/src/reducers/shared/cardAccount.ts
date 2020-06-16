import * as _ from 'lodash'
import { createAction } from 'redux-actions'

import api from 'services/api'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'
import { Account } from 'interfaces/dto/Account.interface'

const LOAD_ACCOUNT: string = 'CardAccount/LOAD_ACCOUNT'
const LOAD_ACCOUNT_SUCCESS: string = 'CardAccount/LOAD_ACCOUNT_SUCCESS'
const LOAD_ACCOUNT_FAIL: string = 'CardAccount/LOAD_ACCOUNT_FAIL'

const ADD_SIDE_PANEL: string = 'CardAccount/ADD_SIDE_PANEL'
const REMOVE_SIDE_PANEL: string = 'CardAccount/REMOVE_SIDE_PANEL'

export interface CardAccount {
  number: string,
  info: Account,
  loaded: boolean,
  loading: boolean,
  sidePanel: Array<any>,
}

const initialState: CardAccount = {
  number: '',
  info: {
    cardIndexes1: [],
    cardIndexes2: [],
  },
  loaded: false,
  loading: false,
  sidePanel: [],
}

export default function cardAccount(state: CardAccount = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case LOAD_ACCOUNT:
      return {
        ...state,
        loading: true,
      }
    case LOAD_ACCOUNT_SUCCESS:
      return {
        ...state,
        loading: false,
        loaded: true,
        info: action.payload,
      }
    case LOAD_ACCOUNT_FAIL:
      return {
        ...state,
        loading: false,
        loaded: false,
      }
    case ADD_SIDE_PANEL:
      if (_.find(state.sidePanel, { header: action.payload.header })) {
        return state;
      }
      return {
        ...state,
        sidePanel: [...state.sidePanel, action.payload],
      }
    case REMOVE_SIDE_PANEL:
      return {
        ...state,
        sidePanel: _.without(state.sidePanel, action.payload),
      }
    default:
      return state
  }
}

export const addSidePanel = createAction(ADD_SIDE_PANEL, (pairs, header) => ({ pairs, header }))

export const removeSidePanel = createAction(REMOVE_SIDE_PANEL)

export function getCardAccount(params: any) {
  return (dispatch: Function) => {
    const options = {
      types: [LOAD_ACCOUNT, LOAD_ACCOUNT_SUCCESS, LOAD_ACCOUNT_FAIL],
      actionParams: params,
      params,
    }
    dispatch(api.creditCard.account.get(options))
  }
}
