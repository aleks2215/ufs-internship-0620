import { combineReducers } from 'redux'

import operations, { Operations } from './operations'
import cardConfirm, { CardConfirm } from './cardConfirm'
import cardAccount, { CardAccount } from './cardAccount'
import ovnSelect, { OvnSelect } from './ovnSelect'
import createNewOperation, { CreateNewOperation } from './createNewOperation'
import acceptPos, { AcceptPos } from './acceptPos'
import acceptOperation, { AcceptOperation } from './acceptOperation'

export interface Shared {
  operations: Operations,
  cardConfirm: CardConfirm,
  cardAccount: CardAccount,
  ovnSelect: OvnSelect,
  createNewOperation: CreateNewOperation,
  acceptPos: AcceptPos,
  acceptOperation: AcceptOperation,
}

const rootReducer = combineReducers<Shared>({
  operations,
  cardConfirm,
  cardAccount,
  ovnSelect,
  createNewOperation,
  acceptPos,
  acceptOperation,
})

export default rootReducer
