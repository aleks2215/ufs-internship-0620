import React from 'react'
import { bindActionCreators } from 'redux'
import { RouteComponentProps } from 'react-router-dom'
import { connect } from 'react-redux'
import CardConfirmForm, { CardConfirmFormProps } from 'components/CardConfirmForm'
import { confirmCard } from 'reducers/shared/cardConfirm'
import { onComplete } from 'reducers/cacheboxProcess'

interface VerifyCreditCardProps extends CardConfirmFormProps,
  RouteComponentProps<any> {
}

@(connect(
  ({shared, cacheboxProcess}) => ({
    ...shared.cardConfirm,
    card: {number: cacheboxProcess.data.ovn.card.number, pin: ''}
  }),
  dispatch => bindActionCreators({confirmCard, onComplete}, dispatch)
) as any)
export default class VerifyCreditCard extends React.Component<VerifyCreditCardProps, any> {
  render() {
    return <CardConfirmForm {...this.props} />
  }
}
