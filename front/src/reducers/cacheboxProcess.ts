import {
  createAction,
} from 'redux-actions'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'
import { BusinessStep } from 'interfaces/BusinessStep.interface'

import { cacheboxProcess } from 'services/processes'
import { updateLocation } from './router'
import { resetProcess } from './auth'
import { Subbranch } from 'interfaces/dto/Subbranch.interface'
import { OVN } from 'interfaces/dto/OVN.interface'

export const ADD_DATA: string = 'cacheboxProcess/ADD_DATA'
export const NEXT_STEP: string = 'cacheboxProcess/NEXT_STEP'

export interface CacheboxProcess {
  businessStep: BusinessStep,
  data: {
    workplaceId?: string,
    limit?: string,
    subbranch?: Subbranch,
    ovn?: OVN,
  }
}

const initialState: CacheboxProcess = {
  businessStep: cacheboxProcess.step1,
  data: {}
}

export default function router(state: CacheboxProcess = initialState, action: ReduxAction = UNDEFINED_ACTION) {
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
    const currentStep: BusinessStep = getState().cacheboxProcess.businessStep
    goNext(dispatch, cacheboxProcess.findStep(currentStep.onAlter))
  }
}

export function onComplete() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().cacheboxProcess.businessStep
    goNext(dispatch, cacheboxProcess.findStep(currentStep.onComplete))
  }
}

export function onCancel() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().cacheboxProcess.businessStep
    goNext(dispatch, cacheboxProcess.findStep(currentStep.onCancel))
    dispatch(resetProcess())
  }
}

/* отмена без сброса процесса*/
export function onSimpleCancel() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().cacheboxProcess.businessStep
    goNext(dispatch, cacheboxProcess.findStep(currentStep.onCancel))
  }
}

export function onFinish() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().cacheboxProcess.businessStep
    goNext(dispatch, cacheboxProcess.findStep(currentStep.onComplete))
    dispatch(resetProcess())
  }
}

export function onCompleteFirstStep() {
  return (dispatch: Function, getState: Function) => {
    const currentStep: BusinessStep = getState().cacheboxProcess.businessStep
    const { workplaceId, subbranch, limit } = getState().shared.operations
    const { ovn } = getState().cachebox.pendingOperations

    dispatch(addData({ workplaceId, subbranch, limit, ovn }))
    goNext(dispatch, cacheboxProcess.findStep(currentStep.onComplete))
  }
}
