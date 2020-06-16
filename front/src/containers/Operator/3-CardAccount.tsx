import React from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { RouteComponentProps } from 'react-router-dom'
import { Spin } from 'antd'

import CardAccountForm, { CardAccountFormProps } from 'components/CardAccount/CardAccountForm'
import {
  getCardAccount,
  addSidePanel,
  removeSidePanel,
} from 'reducers/shared/cardAccount'

import { onComplete } from 'reducers/operatorProcess'

export interface CardAccountProps extends CardAccountFormProps, RouteComponentProps<any> {
  loading: boolean
}

@(connect(
  ({shared}) => ({
    ...shared.cardAccount,
    cardInfo: shared.cardConfirm.card,
  }),
  dispatch => bindActionCreators({ getCardAccount, addSidePanel, onComplete, removeSidePanel }, dispatch)
) as any)
export default class CardAccount extends React.Component<CardAccountProps, any> {
  render() {
    return (<Spin spinning={this.props.loading}>
      <CardAccountForm {...this.props} />
    </Spin>)
  }
}
