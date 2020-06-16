import { combineReducers } from 'redux'

import admin, { Admin } from './admin'
import auth, {
  RESET,
  RESET_PROCESS,
  AuthInfo
} from './auth'
import cachebox, { Cachebox } from './cachebox'
import shared, { Shared } from './shared'
import router, { Router } from './router'
import cacheboxProcess, { CacheboxProcess } from './cacheboxProcess'
import operatorProcess, { OperatorProcess } from './operatorProcess'

export interface RootState {
  admin?: Admin,
  auth?: AuthInfo,
  cachebox?: Cachebox,
  shared?: Shared,
  cacheboxProcess?: CacheboxProcess,
  operatorProcess?: OperatorProcess,
  router?: Router,
}

const appReducer = combineReducers<RootState>({
  admin,
  auth,
  cachebox,
  shared,
  cacheboxProcess,
  operatorProcess,
  router,
})

const rootReducer = (state, action) => {
  if (action.type === RESET) {
    return appReducer({}, action)
  }
  if (action.type === RESET_PROCESS) {
    return appReducer({ auth: state.auth, router: state.router }, action)
  }
  return appReducer(state, action)
}

export default rootReducer
