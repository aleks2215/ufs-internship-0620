import React from 'react'
import { bindActionCreators } from 'redux'
import { RouteComponentProps } from 'react-router-dom'
import { connect } from 'react-redux'
import AcceptPosForm, { AcceptPosFormProps } from 'components/AcceptPosForm'
import { confirmOperation } from 'reducers/shared/acceptPos'
import {
  onComplete,
  onSimpleCancel
} from 'reducers/operatorProcess'

interface AcceptPosProps extends AcceptPosFormProps, RouteComponentProps<any> {
}

@(connect(
  ({ shared }) => ({
    ...shared.acceptPos,
    taskId: shared.createNewOperation.task.id,
    amount: parseInt(shared.createNewOperation.task.amount, 10),
  }),
  dispatch => bindActionCreators({ confirmOperation: confirmOperation(onComplete, onSimpleCancel) }, dispatch)
) as any)
export default class AcceptPos extends React.Component<AcceptPosProps, any> {
  render() {
    return (<AcceptPosForm {...this.props} />);
  }
}
