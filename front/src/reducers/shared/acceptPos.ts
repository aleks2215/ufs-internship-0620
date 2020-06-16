import { createAction } from 'redux-actions'

import api from 'services/api'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

const CONFIRM_POS_PENDING: string = 'AcceptPos/CONFIRM_POS_PENDING'
const CONFIRM_POS_DONE: string = 'AcceptPos/CONFIRM_POS_DONE'
const CONFIRM_POS_FAILED: string = 'AcceptPos/CONFIRM_POS_FAILED'

export interface AcceptPos {
  message: string,
  cancel?: boolean,
}

const initialState: AcceptPos = {
  message: '',
  cancel: false,
}

export default function acceptPos(state: AcceptPos = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case CONFIRM_POS_PENDING:
      return {
        ...state,
        cancel: !action.payload.confirm
      }
    case CONFIRM_POS_DONE:
      return {
        ...state,
        nextStep: true,
      }
    default:
      return state
  }
}

export const confirmOperation = (onComplete: Function, onCancel: Function) =>
  (taskId: string, confirm: boolean) => (dispatch: Function) => {
    if (confirm) {
      const options = {
        types: [CONFIRM_POS_PENDING, CONFIRM_POS_DONE, CONFIRM_POS_FAILED],
        actionParams: { confirm },
        params: { taskId, confirm },
        onSuccess: (result: any) => {
          dispatch(createAction(CONFIRM_POS_DONE)(result))
          dispatch(onComplete())
        }
      }
      dispatch(api.creditCard.operationConfirm(options))
    } else {
      dispatch(onCancel())
    }
  }
