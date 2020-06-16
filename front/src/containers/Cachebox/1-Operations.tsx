import React from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { RouteComponentProps } from 'react-router-dom'
import { Spin } from 'antd'

import { load } from 'reducers/shared/operations'
import {
  filter,
  loadPendingOperations,
  nextStepOvn
} from 'reducers/cachebox/pendingOperations'
import { onCompleteFirstStep } from 'reducers/cacheboxProcess'

import {
  Router
} from 'reducers/router'

import PendingOperationsForm, { PendingOperationsFormProps } from 'components/PendingOperationsForm'

interface OperationsProps extends PendingOperationsFormProps, Router, RouteComponentProps<any> {
  loading: number,
  pendingOperations: {
    loaded: boolean,
    items: Array<any>,
    seed: Array<any>,
    loading: boolean,
  },
}

@(connect(
  ({ shared, cachebox }) => ({
    ...shared.operations,
    pendingOperations: cachebox.pendingOperations
  }),
  dispatch => bindActionCreators({ filter, load, loadPendingOperations, nextStepOvn: nextStepOvn(onCompleteFirstStep) },
    dispatch)
) as any )
export default class Operations extends React.Component<OperationsProps, any> {
  render() {
    const loading = this.props.loading > 0 || this.props.pendingOperations.loading
    return (
      <Spin spinning={loading}>
        <PendingOperationsForm {...this.props} />
      </Spin>
    )
  }
}

