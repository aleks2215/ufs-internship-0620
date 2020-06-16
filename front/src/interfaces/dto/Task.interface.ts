import { CurrencyType } from './CurrencyType.interface'
import { OVN } from './OVN.interface'
import { Subbranch } from './Subbranch.interface'
import { CachSymbol } from './CachSymbol.interface'


export interface TaskValues {
  accountInfo: Account,
  subbranch: Subbranch,
  ovn?: OVN
}

export interface Task {
  representativeId: string,
  currencyType: CurrencyType,
  amountInWords?: string,
  senderBank?: string,
  senderBankBic?: string,
  recipientBank?: string,
  recipientBankBic?: string,
  source?: string,
  personalAccountId?: string,
  // OVN Part
  id?: string,
  num: string,
  status?: string,
  repFio?: string,
  legalEntityShortName: string,
  amount?: string,
  createdDate: string,
  inn: string,
  kpp?: string,
  accountId: string,
  senderAccountId?: string,
  recipientAccountId?: string,
  clientTypeFk?: boolean,
  organisationNameFk?: string,
  symbols: Array<CachSymbol> ,
}
