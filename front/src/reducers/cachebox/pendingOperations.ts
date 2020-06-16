import api from 'services/api'

import { createAction } from 'redux-actions'

import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'
import { OVN } from 'interfaces/dto/OVN.interface'

const GET_OPERATIONS_PENDING: string = 'pendingOperations/GET_OPERATIONS_PENDING'
const GET_OPERATIONS_PENDING_TEMP: string = 'pendingOperations/GET_OPERATIONS_PENDING_TEMP'

const GET_OPERATIONS_DONE_TEMP: string = 'pendingOperations/GET_OPERATIONS_DONE_TEMP'
const GET_OPERATIONS_FAILED_TEMP: string = 'pendingOperations/GET_OPERATIONS_FAILED_TEMP'
const GET_OPERATIONS_DONE: string = 'pendingOperations/GET_OPERATIONS_DONE'
const GET_OPERATIONS_FAILED: string = 'pendingOperations/GET_OPERATIONS_FAILED'

const SET_OVN: string = 'pendingOperations/SET_OVN'

type FILTER = 'pendingOperations/filter'
const FILTER: FILTER = 'pendingOperations/filter';

export interface PendingOperations {
  filters: any,
  items: Array<{}>,
  loading: boolean,
  seed: Array<any>,
  ovn?: OVN,
}

const initialState: PendingOperations = {
  filters: {},
  items: [],
  loading: false,
  seed: [],
};

/** REDUCER */
export default function pendingOperations(state: PendingOperations = initialState,
  action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case GET_OPERATIONS_PENDING:
      return {
        ...state,
        loading: true,
      }
    case GET_OPERATIONS_DONE:
      return {
        ...state,
        loading: false,
        seed: action.payload,
        items: action.payload,
      }
    case GET_OPERATIONS_FAILED:
      return {
        ...state,
        loading: false,
      }
    case GET_OPERATIONS_PENDING_TEMP:
      return {
        ...state,
        loading: true,
      }
    case GET_OPERATIONS_DONE_TEMP:
      return {
        ...state,
        loading: false
      }
    case GET_OPERATIONS_FAILED_TEMP:
      return {
        ...state,
        loading: false,
      }
    case FILTER: {
      const filters = {
        ...state.filters,
        ...action.payload,
      };
      return {
        ...state,
        loading: false,
        items: state.seed.filter((item) => {
          let goes = true;
          for (const key in filters) {
            if (filters.hasOwnProperty(key)) {
              if (filters[key]) {
                goes = goes && item[key] === filters[key]
              }
            }
          }
          return goes
        }),
        filters,
      }
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

export const filter = createAction(FILTER)

export const nextStepOvn = (action: Function) => (ovn: OVN) => (dispatch: Function) => {
  dispatch(createAction(SET_OVN)(ovn))
  dispatch(action())
}

export function loadPendingOperations() {
  return (dispatch: Function) => {
    const options = {
      types: [GET_OPERATIONS_PENDING, GET_OPERATIONS_DONE, GET_OPERATIONS_FAILED],
      actionParams: {},
      params: {},
    }
    dispatch(api.operation.tasks.get(options));
  }
}
/*
export function loadPendingOperations() {
  return (dispatch: Function) => {
    const options = {
      types: [GET_OPERATIONS_PENDING_TEMP, GET_OPERATIONS_DONE_TEMP, GET_OPERATIONS_FAILED_TEMP],
      actionParams: {},
      params: {},
      onSuccess: (result) => {
        dispatch(createAction(GET_OPERATIONS_DONE_TEMP)())
        dispatch(loadPendingOperationsTrue())
      }
    }
    dispatch(api.operation.tasks.get(options));
  }
}
*/
