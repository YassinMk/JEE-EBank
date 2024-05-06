export interface AccountDetails {
  accountId: string
  balance: number
  currrentPage: number
  totalPages: number
  pageSize: number
  accountOperationDTOS: AccountOperation[]
}



export interface AccountOperation {
  id: number
  operationDate: string
  amount: number
  type: string
  description: string
}
