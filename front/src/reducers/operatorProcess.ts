import {
  createAction,
} from 'redux-actions'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'
import { BusinessStep } from 'interfaces/BusinessStep.interface'

import { Account } from 'interfaces/dto/Account.interface'
import { CardInfo } from 'interfaces/dto/CardInfo.interface'

import { operatorProcess } from 'services/processes'
import { updateLocation } from './router'
import { resetProcess } from './auth'
import { Subbranch } from 'interfaces/dto/Subbranch.interface'

export const ADD_DATA: string = 'operatorProcess/ADD_DATA'
export const NEXT_STEP: string = 'operatorProcess/NEXT_STEP'

export interface OperatorProcess {
  businessStep: BusinessStep,
  data: {
    workplaceId?: string,
    limit?: string,
    subbranch?: Subbranch,
    account?: Account,
    cardInfo?: CardInfo,
  }
}

const initialState: OperatorProcess = {
  businessStep: operatorProcess.step1,
  data: {}
}

export default function router(state: OperatorProcess = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case ADD_DATA:
      return {
        ...state,
        data: { ...state.data, ...action.payload }
      }
    case NEXT_STEP:
      return {
        ...state,
        businessStep: action.payload,
      }
    default:
      return state
  }
}

const goNext = (dispatch: Function, nextStep: BusinessStep) => {
  dispatch(createAction(NEXT_STEP)(nextStep))
  dispatch(updateLocation(nextStep.location))
}

const addData = createAction(ADD_DATA)

export function onAlter() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().operatorProcess.businessStep
    goNext(dispatch, operatorProcess.findStep(currentStep.onAlter))
  }
}

export function onComplete() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().operatorProcess.businessStep
    goNext(dispatch, operatorProcess.findStep(currentStep.onComplete))
  }
}

export function onCancel() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().operatorProcess.businessStep
    goNext(dispatch, operatorProcess.findStep(currentStep.onCancel))
    dispatch(resetProcess())
  }
}

/* отмена без сброса процесса*/
export function onSimpleCancel() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().operatorProcess.businessStep
    goNext(dispatch, operatorProcess.findStep(currentStep.onCancel))
  }
}

export function onFinish() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().operatorProcess.businessStep
    goNext(dispatch, operatorProcess.findStep(currentStep.onComplete))
    dispatch(resetProcess())
  }
}

export function onCompleteFirstStep() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().operatorProcess.businessStep
    const { workplaceId, subbranch, limit } = getState().shared.operations
    dispatch(addData({ workplaceId, subbranch, limit }))
    goNext(dispatch, operatorProcess.findStep(currentStep.onComplete))
  }
}
