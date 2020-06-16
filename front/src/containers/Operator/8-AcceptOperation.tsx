import React from 'react'
import { bindActionCreators } from 'redux'
import { RouteComponentProps } from 'react-router-dom'
import { connect } from 'react-redux'
import AcceptOperationForm, { AcceptOperationFormProps } from 'components/AcceptOperationForm'
import { completeOperation } from 'reducers/shared/acceptOperation'
import { onFinish } from 'reducers/operatorProcess'

interface AcceptOperationProps extends AcceptOperationFormProps, RouteComponentProps<any> {
}

@(connect(
  ({shared, operatorProcess}) => ({
    ...shared.acceptOperation,
    workplaceId: operatorProcess.data.workplaceId,
    taskId: shared.createNewOperation.task.id,
    amount: parseInt(shared.createNewOperation.task.amount, 10),
    packageId: shared.createNewOperation.packageId,
    operationTypeCode: shared.createNewOperation.operationTypeCode,
  }),
  dispatch => bindActionCreators({ completeOperation, onFinish }, dispatch)
) as any)
export default class AcceptOperation extends React.Component<AcceptOperationProps, any> {
  render() {
    return (<AcceptOperationForm {...this.props} />)
  }
}

