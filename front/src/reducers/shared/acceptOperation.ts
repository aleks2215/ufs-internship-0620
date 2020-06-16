import api from 'services/api'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

const CONFIRM_PENDING: string = 'AcceptOperation/CONFIRM_PENDING'
const CONFIRM_DONE: string = 'AcceptOperation/CONFIRM_DONE'
const CONFIRM_FAILED: string = 'AcceptOperation/CONFIRM_FAILED'

const CANCEL_PENDING: string = 'AcceptOperation/CANCEL_PENDING'
const CANCEL_DONE: string = 'AcceptOperation/CANCEL_DONE'
const CANCEL_FAILED: string = 'AcceptOperation/CANCEL_FAILED'

export interface AcceptOperation {
  complete: boolean,
  confirmed?: boolean,
  loadingConfirm: boolean,
  loadingCancel: boolean,
}

const initialState: AcceptOperation = {
  complete: false,
  loadingConfirm: false,
  loadingCancel: false,
}

export default function acceptOperation(state: AcceptOperation = initialState, action: ReduxAction = UNDEFINED_ACTION): AcceptOperation {
  switch (action.type) {
    case CONFIRM_PENDING:
      return {
        ...state,
        loadingConfirm: true,
      }
    case CONFIRM_DONE:
      return {
        ...state,
        confirmed: true,
        complete: true,
        loadingConfirm: false,
      }
    case CONFIRM_FAILED:
      return {
        ...state,
        loadingConfirm: false,
      }
    case CANCEL_PENDING:
      return {
        ...state,
        loadingCancel: true,
      }
    case CANCEL_DONE:
      return {
        ...state,
        confirmed: false,
        complete: true,
        loadingCancel: false,
      }
    case CANCEL_FAILED:
      return {
        ...state,
        loadingCancel: false,
      }
    default:
      return state
  }
}

export function completeOperation(params, confirm: boolean) {
  const options = {
    types: confirm ?
      [CONFIRM_PENDING, CONFIRM_DONE, CONFIRM_FAILED] :
      [CANCEL_PENDING, CANCEL_DONE, CANCEL_FAILED],
    actionParams: {},
    params: params,
  }
  if (confirm) {
    return api.operation.confirm(options)
  }
  return api.operation.cancel(options)
}
