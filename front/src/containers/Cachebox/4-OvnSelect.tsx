import React from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { RouteComponentProps } from 'react-router-dom'
import { Spin } from 'antd'

import OvnSelectForm, { OvnSelectFormProps } from 'components/OvnSelectForm'
import {
  getOvnList,
  nextStepNew,
  nextStepOvn
} from 'reducers/shared/ovnSelect'
import { onComplete } from 'reducers/cacheboxProcess'

interface OvnSelectProps extends OvnSelectFormProps, RouteComponentProps<any> {
  loading: boolean
}

@(connect(
  ({ shared }) => ({
    ...shared.ovnSelect,
    account: shared.cardAccount.info.account || {},
  }),
  dispatch => bindActionCreators(
    { getOvnList, nextStepNew: nextStepNew(onComplete), nextStepOvn: nextStepOvn(onComplete) }, dispatch)
) as any)
export default class OvnSelect extends React.Component<OvnSelectProps, any> {
  render() {
    return (<Spin spinning={this.props.loading}>
      <OvnSelectForm {...this.props} />
    </Spin>)
  }
}
