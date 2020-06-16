import { PersonalDocument } from './PersonalDocument.interface'

export interface Representative {
  id?: string,
  inn: string,
  lastName: string,
  firstName: string,
  patronymic?: string,
  birthDate: string,
  birthPlace: string,
  resident: boolean,
  phoneWork?: string,
  phoneMobilev?: string,
  email?: string,
  address?: string,
  postcode?: string,
  document: PersonalDocument,
}
