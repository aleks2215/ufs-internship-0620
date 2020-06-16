import React from 'react'
import { bindActionCreators } from 'redux'
import { RouteComponentProps } from 'react-router-dom'
import { connect } from 'react-redux'

import ForwardToCashofficeForm, { ForwardToCashofficeFormProps } from 'components/ForwardToCashofficeForm'
import { onFinish } from 'reducers/cacheboxProcess'

interface ForwardToCashofficeProps extends ForwardToCashofficeFormProps, RouteComponentProps<any> {
}

@(connect(
  ({shared}) => ({
    taskId: shared.createNewOperation.task.id,
    packageId: shared.createNewOperation.packageId
  }),
  dispatch => bindActionCreators({ onFinish }, dispatch)
) as any)
export default class ForwardToCashoffice extends React.Component<ForwardToCashofficeProps, any> {
  render() {
    return (<ForwardToCashofficeForm {...this.props} />);
  }
}
