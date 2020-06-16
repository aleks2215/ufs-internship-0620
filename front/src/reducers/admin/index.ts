import { combineReducers } from 'redux'

import operations, { Operations } from './operations'
import report, { Report } from './report'

export interface Admin {
  operations: Operations,
  report: Report,
}

const rootReducer = combineReducers<Admin>({
  operations,
  report,
})

export default rootReducer
