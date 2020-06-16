import { CurrencyType } from './CurrencyType.interface'

export interface PersonalAccount {
  id: string,
  typeId: string | number,
  currencyType: CurrencyType,
  agreement: {
    id: string,
    openDate: string,
  },
  tbCode: string,
  gosbCode: string,
  vspCode: string,
  subbranchCode: string,
  lastTransactionDate: string,
  active: boolean,
  status: string,
  changeStatusDescription: string,
  clientTypeFk: boolean,
  seizured: boolean
}

export interface AccountResidues {
  currentBalance: string,
  expectedBalance: string,
  fixedBalance: string,
}

export interface LegalEntity {
  id: string,
  shortName: string,
  fullName: string,
  inn: string,
  ogrn: string,
  kpp: string,
  legalAddress: string,
  factAddress: string,
}

export interface CardIndex1 {
  docId: string,
  docType: string,
  recipientShortName: string,
  amount: string,
  comeInDate: string,
}

export interface CardIndex2 {
  docId: string,
  docType: string,
  recipientShortName: string,
  amount: string,
  totalAmount: string,
  paidPartly: boolean,
  partAmount?: string,
  comeInDate: string,
  priority: string,
  paids: Array<{ date: string, amount: string }>
}

export interface Seizure {
  type: number | string,
  reason: string,
  fromDate: string,
  toDate: string,
  amount: string,
  initiatorShortName: string,
}

export interface Account {
  account?: PersonalAccount,
  legalEntity?: LegalEntity,
  accountResidues?: AccountResidues,
  cardIndexes1: Array<CardIndex1>,
  cardIndexes2: Array<CardIndex2>,
  seizures?: Array<Seizure>,
}
