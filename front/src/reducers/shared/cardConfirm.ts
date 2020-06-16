import { createAction } from 'redux-actions'

import api from 'services/api'
import { CardCredentials } from 'interfaces/dto/CardCredentials.interface'
import { CardInfo } from 'interfaces/dto/CardInfo.interface'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

type LOAD = 'VerifyCreditCard/LOAD'
const LOAD: string = 'VerifyCreditCard/LOAD'

type LOAD_SUCCESS = 'VerifyCreditCard/LOAD_SUCCESS'
const LOAD_SUCCESS: string = 'VerifyCreditCard/LOAD_SUCCESS'

type LOAD_VALIDATION_FAILED = 'LOAD_VALIDATION_FAILED'
const LOAD_VALIDATION_FAILED: string = 'LOAD_VALIDATION_FAILED'

type LOAD_FAIL = 'VerifyCreditCard/LOAD_FAIL'
const LOAD_FAIL: string = 'VerifyCreditCard/LOAD_FAIL'

export interface CardConfirm extends CardCredentials {
  loading: boolean,
  verified: boolean,
  card: CardInfo | {},
}

const initialState: CardConfirm = {
  number: '',
  pin: '',
  loading: true,
  verified: false,
  card: {},
}

export default function cardConfirm(state: CardConfirm = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  const payload = action.payload;
  switch (action.type) {
    case LOAD:
      return {
        ...state,
        number: payload.number,
        pin: payload.pin,
        loading: true,
      }
    case LOAD_SUCCESS:
      return {
        ...state,
        loading: false,
        verified: true,
        card: payload,
      }
    case LOAD_VALIDATION_FAILED:
      return {
        ...state,
        loading: false,
        verified: false,
      }
    case LOAD_FAIL:
      return {
        ...state,
        loading: false,
        verified: false,
      }
    default:
      return state;
  }
}

export function confirmCard(params: {number: string, pin: string}, onComplete: Function) {
  return (dispatch: Function) => {
    const options = {
      types: [LOAD, LOAD_SUCCESS, LOAD_VALIDATION_FAILED, LOAD_FAIL],
      actionParams: params,
      params,
      onSuccess: (result: any) => {
        dispatch(createAction(LOAD_SUCCESS)(result))
        onComplete()
      }
    }
    dispatch(api.creditCard.verify(options))
  }
}
