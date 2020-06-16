import React from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom'
import { Spin } from 'antd'

import OperationForm, {
  CreateNewOperationFormProps
} from 'components/CreateNewOperation/CreateNewOperation'

import ConfirmNewOpeation, { ConfirmNewOperationProps } from 'components/CreateNewOperation/ConfirmNewOpeation'

import {
  changeTransaction,
  clearTransaction,
  continueUrm,
  continueCache,
  loadCommission,
  loadRepresentativeAndTask,
} from 'reducers/shared/createNewOperation'
import { onAlter, onComplete, onCancel } from 'reducers/operatorProcess'

export interface CreateNewOperationProps extends CreateNewOperationFormProps, ConfirmNewOperationProps,
  RouteComponentProps<any> {
  confirmed: boolean,
  cacheConfirmed: boolean,
  urmConfirmed: boolean,
  loading: number,
}

@(connect(
  ({ shared, operatorProcess }) => (
    {
      ...shared.createNewOperation,
      subbranch: operatorProcess.data.subbranch,
      workplaceId: operatorProcess.data.workplaceId,
      limit: operatorProcess.data.limit,
      ovn: shared.ovnSelect.ovn,
    }),
  dispach => bindActionCreators({
    changeTransaction, clearTransaction, loadCommission,
    continueUrm, continueCache, loadRepresentativeAndTask,
    onCancel,
    onAlter,
    onComplete,
  }, dispach)
) as any)
export default class CreateNewOperation extends React.Component<CreateNewOperationProps, any> {
  render() {
    const { urmConfirmed, loading } = this.props
    return (
      <Spin spinning={loading > 0}>
        {urmConfirmed ? (<ConfirmNewOpeation {...this.props} />)
          : (<OperationForm {...this.props} />)}
      </Spin>)
  }
}

