import { combineReducers } from 'redux'

import pendingOperations, { PendingOperations } from 'reducers/cachebox/pendingOperations'

export interface Cachebox {
  pendingOperations: PendingOperations,
}

const rootReducer = combineReducers<Cachebox>({
  pendingOperations,
})

export default rootReducer
