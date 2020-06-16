import * as _ from 'lodash'
import api from 'services/api'
import moment, { Moment } from 'moment'

import { createAction } from 'redux-actions'

import {
  ReduxAction,
  UNDEFINED_ACTION
} from 'interfaces/ReduxAction.interface'

const DATE_FORMAT = 'DD.MM.YYYY'

const FILTER: string = 'admin/report/filter'

const GET_REPORT_PENDING: string = 'admin/report/GET_OPERATIONS_PENDING'
const GET_REPORT_DONE: string = 'admin/report/GET_OPERATIONS_DONE'
const GET_REPORT_FAILED: string = 'admin/report/GET_OPERATIONS_FAILED'

export interface Report {
  seed: Array<any>,
  data: Array<any>,
  filters: any,
  loading: boolean,
  fromDate: Moment,
  toDate: Moment,
}

const initialState: Report = {
  seed: [],
  data: [],
  filters: {},
  loading: false,
  fromDate: moment(),
  toDate: moment(),
}

const mapItems = (items) => {
  return _.map(items, item => {
    const { operation, deposit, operator = { subbranch: {}, fullName: '' } } = item
    const { subbranch = { tbCode: '13', osbCode: '8593', vspCode: '0102' } } = operator
    const { representative = { lastName: '', firstName: '', patronymic: '', document: {} } } = item
    return {
      operator: {...operator, ...item.user},
      operatorFullName: operator.fullName,
      ...subbranch,
      representative,
      ...deposit,
      commission: item.commission,
      ...operation
    }
  })
}

export default (state: Report = initialState, action: ReduxAction = UNDEFINED_ACTION) => {
  switch (action.type) {
    case GET_REPORT_PENDING:
      return {
        ...state,
        fromDate: action.payload[0],
        toDate: action.payload[1],
        loading: true,
      }
    case GET_REPORT_DONE: {
      const items = mapItems(action.payload)
      return {
        ...state,
        loaded: true,
        loading: false,
        seed: items,
        items,
      }
    }
    case GET_REPORT_FAILED:
      return {
        ...state,
        loading: false,
      }
    case FILTER: {
      const filters = {
        ...state.filters,
        ...action.payload,
      }
      return {
        ...state,
        loading: false,
        data: state.seed.filter((item) => {
          let goes = true
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
    default:
      return state
  }
}

export const filter = createAction(FILTER)

export function loadReport(dates: Array<Moment>) {
  return (dispatch: Function, getState: Function) => {
    if (!dates || dates.length !== 2) {
      return
    }
    const options = {
      types: [GET_REPORT_PENDING, GET_REPORT_DONE, GET_REPORT_FAILED],
      actionParams: {},
      params: { fromDate: dates[0].format(DATE_FORMAT), toDate: dates[1].format(DATE_FORMAT) },
    }
    dispatch(api.report.get(options))
  }
}
