import React from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { RouteComponentProps } from 'react-router-dom'
import { Spin } from 'antd'

import Menu, { MenuProps } from 'components/Menu/Menu'

import {
  load,
  fave,
  show,
  reorder,
} from 'reducers/shared/operations'

import { Router } from 'reducers/router'

import { onCompleteFirstStep } from 'reducers/operatorProcess'

interface OperationsProps extends MenuProps, Router, RouteComponentProps<any> {}

@(connect(
  ({ shared }) => shared.operations,
  dispatch => bindActionCreators({ load, fave, show, reorder, onComplete: onCompleteFirstStep }, dispatch)
) as any)
export default class Operations extends React.Component<OperationsProps, any> {
  render() {
    return ( <Spin spinning={this.props.loading > 0}>
        <Menu {...this.props} />
      </Spin>
    )
  }
}

