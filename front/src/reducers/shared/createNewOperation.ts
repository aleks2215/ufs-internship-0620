import { createAction } from 'redux-actions'
import moment from 'moment'
const DATE_FORMAT = 'DD.MM.YYYY HH:mm:ss'

import api from 'services/api'
import { Task } from 'interfaces/dto/Task.interface'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

import { Account } from 'interfaces/dto/Account.interface'
import { Representative } from 'interfaces/dto/Representative.interface'
import { CommissionRequest } from 'interfaces/dto/CommissionRequest.interface'
import { OVN } from 'interfaces/dto/OVN.interface'

const FK_KEY = '40101'

const LOAD_OVN: string = 'CreateNewOperation/LOAD_OVN'
const LOAD_OVN_SUCCESS: string = 'CreateNewOperation/LOAD_OVN_SUCCESS'
const LOAD_OVN_FAIL: string = 'CreateNewOperation/LOAD_OVN_FAIL'
const SET_OVN: string = 'CreateNewOperation/SET_OVN'

const LOAD_REPRESENTATIVE: string = 'CreateNewOperation/LOAD_REPRESENTATIVE'
const LOAD_REPRESENTATIVE_SUCCESS: string = 'CreateNewOperation/LOAD_REPRESENTATIVE_SUCCESS'
const LOAD_REPRESENTATIVE_FAIL: string = 'CreateNewOperation/LOAD_REPRESENTATIVE_FAIL'

const CONTINUE_URM_PENDING: string = 'CreateNewOperation/CONTINUE_URM_PENDING'
const CONTINUE_URM_DONE: string = 'CreateNewOperation/CONTINUE_URM_DONE'
const CONTINUE_URM_FAILED: string = 'CreateNewOperation/CONTINUE_URM_FAILED'

const CONTINUE_CACHE_PENDING: string = 'CreateNewOperation/CONTINUE_CACHE_PENDING'
const CONTINUE_CACHE_DONE: string = 'CreateNewOperation/CONTINUE_CACHE_DONE'
const CONTINUE_CACHE_FAILED: string = 'CreateNewOperation/CONTINUE_CACHE_FAILED'

const COMMISSION_LOAD_PENDING: string = 'CreateNewOperation/COMMISSION_LOAD_PENDING'
const COMMISSION_LOAD_DONE: string = 'CreateNewOperation/COMMISSION_LOAD_DONE'
const COMMISSION_LOAD_FAILED: string = 'CreateNewOperation/COMMISSION_LOAD_FAILED'

const ACCOUNT20202_PENDING: string = 'CreateNewOperation/ACCOUNT20202_PENDING'
const ACCOUNT20202_DONE: string = 'CreateNewOperation/ACCOUNT20202_DONE'
const ACCOUNT20202_FAILED: string = 'CreateNewOperation/ACCOUNT20202_FAILED'

const CHANGE_TRANSACTION: string = 'CreateNewOperation/CHANGE_TRANSACTION'
const CLEAR_TRANSACTION: string = 'CreateNewOperation/CLEAR_TRANSACTION'
const LOAD_TASK: string = 'CreateNewOperation/LOAD_TASK'
const LOAD_TASK_FROM_OVN: string = 'CreateNewOperation/LOAD_TASK_FROM_OVN'

export interface CreateNewOperation {
  urmConfirmed: boolean,
  cacheConfirmed: boolean,
  loading: number,
  commissionLoading: boolean,
  taskLoaded: boolean,
  operationTypeCode: string,
  ovn?: OVN,
  task?: Task,
  ovnBased: boolean,
  commission?: string,
  representative?: Representative,
}

const initialState = {
  urmConfirmed: false,
  cacheConfirmed: false,
  loading: 0,
  commissionLoading: false,
  taskLoaded: false,
  operationTypeCode: 'ToCardDeposit',
  ovnBased: false,
}

const getOvnNum = (min, max) => {
  return `${Math.floor(Math.random() * (max - min) + min)}${moment().format('DDMMYY')}`
}

const ovnTemplate = (accountInfo: Account, rep: Representative) => ({
  num: getOvnNum(10, 99),
  accountId: accountInfo.account.id,
  clientTypeFk: `${accountInfo.account.id}`.startsWith(FK_KEY),
  createdDate: moment().format(DATE_FORMAT),
  currencyType: accountInfo.account.currencyType,
  kpp: accountInfo.legalEntity.kpp,
  inn: accountInfo.legalEntity.inn,
  legalEntityShortName: accountInfo.legalEntity.shortName,
  repFio: `${rep.lastName} ${rep.firstName} ${rep.patronymic}`,
  symbols: [],
})

export default function createNewOperation(state: CreateNewOperation = initialState,
  action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case SET_OVN:
      return {
        ...state,
        ovn: action.payload,
      }
    case LOAD_OVN:
      return {
        ...state,
        loading: state.loading + 1,
      }
    case LOAD_OVN_SUCCESS:
      return {
        ...state,
        loading: state.loading - 1,
        ovn: action.payload,
      }
    case LOAD_OVN_FAIL:
      return {
        ...state,
        loading: state.loading - 1,
      }
    case LOAD_REPRESENTATIVE:
      return {
        ...state,
        loading: state.loading + 1,
      }
    case LOAD_REPRESENTATIVE_SUCCESS:
      return {
        ...state,
        loading: state.loading - 1,
        representative: action.payload,
      }
    case LOAD_REPRESENTATIVE_FAIL:
      return {
        ...state,
        loading: state.loading - 1,
      }
    case CONTINUE_URM_PENDING:
      return {
        ...state,
        loading: state.loading + 1,
        task: action.payload,
      }
    case CONTINUE_URM_DONE:
      return {
        ...state,
        loading: state.loading - 1,
        urmConfirmed: true,
        packageId: action.payload.id,
        task: {
          ...state.task,
          id: action.payload.toCardDeposits[0].id,
        },
      }
    case CONTINUE_URM_FAILED:
      return {
        ...state,
        loading: state.loading - 1,
      }
    case CONTINUE_CACHE_PENDING:
      return {
        ...state,
        loading: state.loading + 1,
        task: action.payload,
      }
    case CONTINUE_CACHE_DONE:
      return {
        ...state,
        loading: state.loading - 1,
        cacheConfirmed: true,
        packageId: action.payload.id,
        task: { ...state.task, id: action.payload.toCardDeposits[0].id },
      }
    case CONTINUE_CACHE_FAILED:
      return {
        ...state,
        loading: state.loading - 1,
      }
    case CLEAR_TRANSACTION:
      return {
        ...state,
        task: {
          ...state.task,
          amount: state.ovnBased ? state.task.amount : '',
          source: state.ovnBased ? state.task.source : '',
          personalAccountId: '',
          organisationNameFk: '',
        },
      }
    case CHANGE_TRANSACTION:
      return {
        ...state,
        urmConfirmed: false,
        cacheConfirmed: false,
      }
    case LOAD_TASK: {
      const {
        accountInfo, subbranch
      } = action.payload.taskValues

      let task: Task = {
        representativeId: state.representative.id,
        senderBank: subbranch.bankName,
        senderBankBic: subbranch.bic,
        recipientBank: subbranch.bankName,
        recipientBankBic: subbranch.bic,
        ...ovnTemplate(accountInfo, state.representative)
      }

      return {
        ...state,
        task,
        taskLoaded: true,
        ovnBased: false,
      }
    }
    case LOAD_TASK_FROM_OVN: {
      if (!state.representative || !state.ovn) {
        return state // Первый вызов
      }
      const subbranch = action.payload
      let task: Task = {
        representativeId: state.representative.id,
        senderBank: subbranch.bankName,
        senderBankBic: subbranch.bic,
        recipientBank: subbranch.bankName,
        recipientBankBic: subbranch.bic,
        ...state.ovn,
      }

      return {
        ...state,
        task,
        taskLoaded: true,
        ovnBased: true,
      }
    }
    case COMMISSION_LOAD_PENDING:
      return {
        ...state,
        commissionLoading: true,
      }
    case COMMISSION_LOAD_DONE:
      return {
        ...state,
        commission: action.payload,
        commissionLoading: false,
      }
    case COMMISSION_LOAD_FAILED:
      return {
        ...state,
        commissionLoading: false,
      }
    case ACCOUNT20202_DONE:
      return {
        ...state,
        task: { ...state.task, debetAccount: action.payload },
      }
    default:
      return state
  }
}

const getReducerState = (getState: Function) => getState().shared.createNewOperation

const getTask = (getState: Function) => getReducerState(getState).task
const getOperationTypeCode = (getState: Function) => getReducerState(getState).operationTypeCode

const getCard = (getState: Function) => getState().shared.cardConfirm.card

export const clearTransaction = createAction(CLEAR_TRANSACTION)

export const changeTransaction = createAction(CHANGE_TRANSACTION)

export const loadTask = createAction(LOAD_TASK)
export const loadTaskFromOVN = createAction(LOAD_TASK_FROM_OVN)

export function continueUrmForCachebox(values: any, workplaceId: string) {
  return (dispatch: Function, getState: Function) => {
    const task = getTask(getState)
    dispatch(createAction(CONTINUE_URM_PENDING)(task))
    dispatch(createAction(CONTINUE_URM_DONE)(
      {
        id: task.packageId,
        toCardDeposits: [{ id: task.taskId }],
      }))
    dispatch(loadAccount20202(workplaceId))
  }
}

export function continueCacheForCachebox(values: any, workplaceId: string, onAlter: Function) {
  return (dispatch: Function, getState: Function) => {
    const task = getTask(getState)
    dispatch(createAction(CONTINUE_CACHE_PENDING)(task))
    dispatch(createAction(CONTINUE_CACHE_DONE)(      {
      id: task.packageId,
      toCardDeposits: [{ id: task.taskId }],
    }))
    onAlter()
  }
}

export function continueUrm(values: any, workplaceId: string) {
  return (dispatch: Function, getState: Function) => {
    const tempTask = { ...getTask(getState), ...values }

    const options = {
      types: [CONTINUE_URM_PENDING, CONTINUE_URM_DONE, CONTINUE_URM_FAILED],
      actionParams: tempTask,
      params: {
        deposit: { ...tempTask, card: getCard(getState) },
        workplaceId,
      },
      onSuccess: (result: any) => {
        dispatch(createAction(CONTINUE_URM_DONE)(result))
        //dispatch(loadCommission(commissionRequest))
        dispatch(loadAccount20202(workplaceId))
      }
    }
    dispatch(api.operation.continueUrm(options))
  }
}

export function loadCommission(values: any, onComplete: Function) {
  return (dispatch: Function, getState: Function) => {
    const tempTask = { ...getTask(getState), ...values }

    const commissionRequest: CommissionRequest = {
      accountId: tempTask.accountId,
      amount: tempTask.amount,
      typeCode: getOperationTypeCode(getState),
    }

    const options = {
      types: [COMMISSION_LOAD_PENDING, COMMISSION_LOAD_DONE, COMMISSION_LOAD_FAILED],
      actionParams: {},
      params: commissionRequest,
      onSuccess: (result: any) => {
        dispatch(createAction(COMMISSION_LOAD_DONE)(result))
        onComplete(values, true)
      },
      onFailed: (result: any) => {
        dispatch(createAction(COMMISSION_LOAD_FAILED)(result))
        onComplete(values, false)
      }
    }
    dispatch(api.ovn.commission.get(options))
  }
}

function loadAccount20202(workplaceId) {
  const options = {
    types: [ACCOUNT20202_PENDING, ACCOUNT20202_DONE, ACCOUNT20202_FAILED],
    actionParams: {},
    params: { workplaceId },
  }
  return api.ovn.account20202(options)
}

export function continueCache(values: any, workplaceId: string, onAlter: Function) {
  return (dispatch: Function, getState: Function) => {
    const tempTask = { ...getTask(getState), ...values }

    const options = {
      types: [CONTINUE_CACHE_PENDING, CONTINUE_CACHE_DONE, CONTINUE_CACHE_FAILED],
      actionParams: tempTask,
      params: {
        deposit: { ...tempTask, card: getCard(getState) },
        workplaceId: workplaceId,
      },
      onSuccess: (result: any) => {
        dispatch(createAction(CONTINUE_CACHE_DONE)(result))
        onAlter()
      }
    }
    dispatch(api.operation.continueCash(options))
  }
}

export function loadRepresentativeAndTask(subbranch, ovn) {
  return (dispatch: Function, getState: Function) => {
    const ovnBased = !!ovn

    if (ovnBased) {
      dispatch(loadOVN(subbranch, ovn.id))
    }

    const options = {
      types: [LOAD_REPRESENTATIVE, LOAD_REPRESENTATIVE_SUCCESS, LOAD_REPRESENTATIVE_FAIL],
      actionParams: {},
      params: { cardNumber: getState().shared.cardConfirm.card.number },
      onSuccess: (result) => {
        dispatch(createAction(LOAD_REPRESENTATIVE_SUCCESS)(result))
        if (ovnBased) {
          dispatch(loadTaskFromOVN(subbranch))
        } else {
          const taskValues = {
            accountInfo: getState().shared.cardAccount.info,
            subbranch,
          }
          dispatch(loadTask({ taskValues }))
        }
      }
    }

    dispatch(api.representative.get(options))
  }
}

export function loadOVN(subbranch, id) {
  return (dispatch: Function) => {
    const options = {
      types: [LOAD_OVN, LOAD_OVN_SUCCESS, LOAD_OVN_FAIL],
      actionParams: {},
      params: { announcementId: id },
      onSuccess: (result) => {
        dispatch(createAction(LOAD_OVN_SUCCESS)(result))
        dispatch(loadTaskFromOVN(subbranch))
      }
    }
    dispatch(api.ovn.getById(options))
  }
}

export function loadRepresentativeForCach(subbranch, ovn) {
  return (dispatch: Function, getState: Function) => {
    dispatch(createAction(SET_OVN)(ovn))

    const options = {
      types: [LOAD_REPRESENTATIVE, LOAD_REPRESENTATIVE_SUCCESS, LOAD_REPRESENTATIVE_FAIL],
      actionParams: {},
      params: { cardNumber: getState().shared.cardConfirm.card.number },
      onSuccess: (result) => {
        dispatch(createAction(LOAD_REPRESENTATIVE_SUCCESS)(result))
        dispatch(loadTaskFromOVN(subbranch))
      }
    }

    dispatch(api.representative.get(options))
  }
}
