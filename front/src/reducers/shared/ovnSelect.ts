import { createAction } from 'redux-actions'

import api from 'services/api'
import { OVN } from 'interfaces/dto/OVN.interface'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

const SET_OVN: string = 'OvnSelect/OVN_BASED_OPERATION'

const LOAD: string = 'OvnSelect/LOAD'
const LOAD_SUCCESS: string = 'OvnSelect/LOAD_SUCCESS'
const LOAD_FAIL: string = 'OvnSelect/LOAD_FAIL'

export interface OvnSelect {
  loaded: boolean,
  ovnList: Array<OVN>,
  ovn?: OVN,
}

const initialState: OvnSelect = {
  loaded: false,
  ovnList: [],
}

export default function OvnSelect(state: OvnSelect = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case LOAD:
      return {
        ...state,
        loading: true,
      }
    case LOAD_SUCCESS:
      return {
        ...state,
        loading: false,
        loaded: true,
        ovnList: action.payload,
      }
    case LOAD_FAIL:
      return {
        ...state,
        loading: false,
        loaded: false,
      }
    case SET_OVN:
      return {
        ...state,
        ovn: action.payload,
      }
    default:
      return state
  }
}

const nextStep = (isNew: boolean, action: Function, ovn?: OVN) => (dispatch: Function) => {
  if (!isNew)
    dispatch(createAction(SET_OVN)(ovn))

  dispatch(action())
}

export const nextStepNew = (action: Function) => () => nextStep(true, action)

export const nextStepOvn = (action: Function) => (ovn: OVN) => nextStep(false, action, ovn)

export function getOvnList({ id, status }) {
  const options = {
    types: [LOAD, LOAD_SUCCESS, LOAD_FAIL],
    actionParams: {},
    params: { accountId: id, status },
  }
  return api.ovn.get(options)
}
