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
  continueUrmForCachebox,
  continueCacheForCachebox,
  loadCommission,
  loadRepresentativeForCach,
} from 'reducers/shared/createNewOperation'
import { onAlter, onComplete, onCancel } from 'reducers/cacheboxProcess'

export interface CreateNewOperationProps extends CreateNewOperationFormProps, ConfirmNewOperationProps,
  RouteComponentProps<any> {
  confirmed: boolean,
  cacheConfirmed: boolean,
  urmConfirmed: boolean,
  loading: number,
}

@(connect(
  ({ shared, cachebox, cacheboxProcess }) => (
    {
      ...shared.createNewOperation,
      subbranch: cacheboxProcess.data.subbranch,
      workplaceId: cacheboxProcess.data.workplaceId,
      limit: cacheboxProcess.data.limit,
      ovn: cachebox.pendingOperations.ovn,
    }),
  dispach => bindActionCreators({
    changeTransaction, clearTransaction, loadCommission,
    continueUrm:continueUrmForCachebox, continueCache:continueCacheForCachebox, loadRepresentativeAndTask: loadRepresentativeForCach,
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

