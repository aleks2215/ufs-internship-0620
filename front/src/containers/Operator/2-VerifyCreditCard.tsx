import React from 'react'
import { bindActionCreators } from 'redux'
import { RouteComponentProps } from 'react-router-dom'
import { connect } from 'react-redux'
import CardConfirmForm, { CardConfirmFormProps } from 'components/CardConfirmForm'
import { confirmCard } from 'reducers/shared/cardConfirm'
import { onComplete } from 'reducers/operatorProcess'

interface VerifyCreditCardProps extends CardConfirmFormProps,
  RouteComponentProps<any> {
}

@(connect(
  ({shared}) => shared.cardConfirm,
  dispatch => bindActionCreators({confirmCard, onComplete}, dispatch)
) as any)
export default class VerifyCreditCard extends React.Component<VerifyCreditCardProps, any> {
  render() {
    return <CardConfirmForm {...this.props} />
  }
}
