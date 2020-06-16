import * as _ from 'lodash'
import { createAction } from 'redux-actions'

import api from 'services/api'
import { OperationType } from 'interfaces/dto/OperationType.interface'
import { Subbranch } from 'interfaces/dto/Subbranch.interface'
import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

const GET_OPERATIONS_PENDING: string = 'operations/GET_OPERATIONS_PENDING'
const GET_OPERATIONS_DONE: string = 'operations/GET_OPERATIONS_DONE'
const GET_OPERATIONS_FAILED: string = 'operations/GET_OPERATIONS_FAILED'

const GET_FAVOURITES_PENDING: string = 'operations/GET_FAVOURITES_PENDING'
const GET_FAVOURITES_DONE: string = 'operations/GET_FAVOURITES_DONE'
const GET_FAVOURITES_FAILED: string = 'operations/GET_FAVOURITES_FAILED'

const SAVE_FAVOURITES_PENDING: string = 'operations/SAVE_REQUEST_PENDING'
const SAVE_FAVOURITES_DONE: string = 'operations/SAVE_REQUEST_DONE'
const SAVE_FAVOURITES_FAILED: string = 'operations/SAVE_REQUEST_FAILED'

const GET_OPERATOR_PENDING: string = 'operations/GET_OPERATOR_PENDING'
const GET_OPERATOR_DONE: string = 'operations/GET_OPERATOR_DONE'
const GET_OPERATOR_FAILED: string = 'operations/GET_OPERATOR_FAILED'

const GET_WORKPLACE_PENDING: string = 'operations/GET_WORKPLACE_PENDING'
const GET_WORKPLACE_DONE: string = 'operations/GET_WORKPLACE_DONE'
const GET_WORKPLACE_FAILED: string = 'operations/GET_WORKPLACE_FAILED'

const FAVE: string = 'operations/FAVE'
const SHOW: string = 'operations/SHOW'
const GO: string = 'operation/GO'

export interface Operations {
  loading: number,
  operationsLoaded: boolean,
  workplaceLoaded: boolean,
  faveLoaded: boolean,
  operatorLoaded: boolean,
  limit: string,
  visible: string,
  workplaceId: string,
  items: Array<OperationType>,
  favourites: Array<string>,
  subbranch: Subbranch | {},
}

const initialState: Operations = {
  loading: 0,
  operationsLoaded: false,
  workplaceLoaded: false,
  faveLoaded: false,
  operatorLoaded: false,
  limit: '0',
  visible: '',
  workplaceId: '',
  items: [],
  favourites: [],
  subbranch: {},
}

/** REDUCER */
export default function reducer(state: Operations = initialState, action: ReduxAction = UNDEFINED_ACTION) {
  switch (action.type) {
    case GET_OPERATIONS_PENDING:
      return {
        ...state,
        loading: state.loading + 1,
      }
    case GET_OPERATIONS_DONE:
      return {
        ...state,
        items: action.payload.map((item) => ({ ...item, fave: state.favourites.indexOf(item.id) >= 0 })),
        loading: state.loading - 1,
        operationsLoaded: true,
      }
    case GET_OPERATIONS_FAILED:
      return {
        ...state,
        error: action.payload,
        loading: state.loading - 1,
      }
    case GET_FAVOURITES_PENDING:
      return {
        ...state,
        loading: state.loading + 1,
      }
    case GET_FAVOURITES_DONE: {
      const favourites = action.payload || []
      return {
        ...state,
        items: state.items.map((item) => ({ ...item, fave: favourites.indexOf(item.id) >= 0 })),
        favourites,
        loading: state.loading - 1,
        faveLoaded: true,
      }
    }
    case GET_FAVOURITES_FAILED:
      return {
        ...state,
        error: action.payload,
        loading: state.loading - 1,
      }
    case SAVE_FAVOURITES_PENDING:
      return {
        ...state,
        favourites: action.payload,
        saving: true,
      }
    case SAVE_FAVOURITES_DONE:
      return {
        ...state,
        success: true,
        saving: false,
      }
    case SAVE_FAVOURITES_FAILED:
      return {
        ...state,
        error: action.payload,
        saving: false,
      }
    case GET_OPERATOR_DONE:
      return {
        ...state,
        workplaceId: action.payload.workplaceId,
        subbranch: action.payload.subbranch,
        operatorLoaded: true,
      }
    case GET_WORKPLACE_DONE: {
      const limitEntity = _.find(action.payload.categoryLimits, { categoryId: '1' })
      return {
        ...state,
        limit: limitEntity ? limitEntity.limit : '0',
        workplaceLoaded: true,
      }
    }
    case FAVE: {
      const id = action.payload
      const exist = _.includes(state.favourites, id)
      let newFavourites
      if (exist) {
        newFavourites = _.filter(state.favourites, itemId => itemId !== id)
      } else {
        newFavourites = [...state.favourites, id]
      }

      return {
        ...state,
        items: state.items.map((item) => {
          if (id === item.id) {
            return { ...item, fave: !item.fave }
          }
          return item
        }),
        favourites: newFavourites,
      }
    }
    case SHOW:
      return {
        ...state,
        visible: action.payload,
      }
    default:
      return state
  }
}

/** ACTIONS */
const getReducerState = (getState: Function) => getState().shared.operations
const getFavourites = (getState: Function) => getReducerState(getState).favourites

export function reorder(payload) {
  const options = {
    types: [SAVE_FAVOURITES_PENDING, SAVE_FAVOURITES_DONE, SAVE_FAVOURITES_FAILED],
    actionParams: payload,
    params: { typeIds: payload },
  }
  return api.operations.favourites.save(options)
}

export function fave(id) {
  return (dispatch: Function, getState: Function) => {
    dispatch(createAction(FAVE)(id))
    const favourites = getFavourites(getState)
    const options = {
      types: [SAVE_FAVOURITES_PENDING, SAVE_FAVOURITES_DONE, SAVE_FAVOURITES_FAILED],
      actionParams: favourites,
      params: { typeIds: favourites },
    }
    dispatch(api.operations.favourites.save(options))
  }
}

export const show = createAction(SHOW)

export const go = createAction(GO)

function loadOperations(payload = {}) {
  const options = {
    types: [GET_OPERATIONS_PENDING, GET_OPERATIONS_DONE, GET_OPERATIONS_FAILED],
    actionParams: payload,
    params: payload,
  }
  return api.operations.get(options)
}

function loadFavourites(payload = {}) {
  const options = {
    types: [GET_FAVOURITES_PENDING, GET_FAVOURITES_DONE, GET_FAVOURITES_FAILED],
    actionParams: payload,
    params: payload,
  }
  return api.operations.favourites.get(options)
}

function loadOperator(payload = {}) {
  const options = {
    types: [GET_OPERATOR_PENDING, GET_OPERATOR_DONE, GET_OPERATOR_FAILED],
    actionParams: payload,
    params: payload,
  }
  return api.operator.get(options)
}

function loadWorkplace(payload = {}) {
  const options = {
    types: [GET_WORKPLACE_PENDING, GET_WORKPLACE_DONE, GET_WORKPLACE_FAILED],
    actionParams: payload,
    params: payload,
  }
  return api.workplace.get(options)
}

export function load() {
  return (dispatch: Function, getState: Function) => {
    const { operationsLoaded, faveLoaded, operatorLoaded, workplaceLoaded } = getReducerState(getState)
    if (!operationsLoaded)
      dispatch(loadOperations())
    if (!faveLoaded)
      dispatch(loadFavourites())
    if (!operatorLoaded)
      dispatch(loadOperator())
    if (!workplaceLoaded)
      dispatch(loadWorkplace())
  }
}

