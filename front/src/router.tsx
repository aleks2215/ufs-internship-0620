import React from 'react'
import { Router, Route } from 'react-router-dom'
import { operatorProcess } from 'services/processes'
import { history } from 'utils/History'
import Layout from 'containers/Layout'

import {
  Operations as AdminOperations,
  Report as AdminReport,
} from 'containers/Admin'

import {
  Operations as CacheboxOperations,
  VerifyCreditCard as CacheboxVerifyCreditCard,
  CardAccount as CacheboxCardAccount,
  OvnSelect as CacheboxOvnSelect,
  CreateNewOperation as CacheboxCreateNewOperation,
  AcceptPos as CacheboxAcceptPos,
  AcceptOperation as CacheboxAcceptOperation,
  ForwardToCashoffice as CacheboxForwardToCashoffice,
} from 'containers/Cachebox'

import {
  Operations,
  VerifyCreditCard,
  CardAccount,
  OvnSelect,
  CreateNewOperation,
  AcceptPos,
  AcceptOperation,
  ForwardToCashoffice,
} from 'containers/Operator'
import { cacheboxProcess } from 'services/processes/CacheboxProcess'

export default () => {
  const pages: any = (
    <div>
      <Route path={ operatorProcess.step1.location } exact component={Operations} />
      <Route path={ operatorProcess.step2.location } component={VerifyCreditCard} />
      <Route path={ operatorProcess.step3.location } component={CardAccount} />
      <Route path={ operatorProcess.step4.location } component={OvnSelect} />
      <Route path={ operatorProcess.step5.location } component={CreateNewOperation} />
      <Route path={ operatorProcess.step6.location } component={ForwardToCashoffice} />
      <Route path={ operatorProcess.step7.location } component={AcceptPos} />
      <Route path={ operatorProcess.step8.location } component={AcceptOperation} />

      <Route path={ cacheboxProcess.step1.location } exact component={CacheboxOperations} />
      <Route path={ cacheboxProcess.step2.location } component={CacheboxVerifyCreditCard} />
      <Route path={ cacheboxProcess.step3.location } component={CacheboxCardAccount} />
      <Route path={ cacheboxProcess.step4.location } component={CacheboxOvnSelect} />
      <Route path={ cacheboxProcess.step5_1.location } component={CacheboxCreateNewOperation} />
      <Route path={ cacheboxProcess.step6.location } component={CacheboxForwardToCashoffice} />
      <Route path={ cacheboxProcess.step7.location } component={CacheboxAcceptPos} />
      <Route path={ cacheboxProcess.step8.location } component={CacheboxAcceptOperation} />

      <Route exact path="/admin" component={AdminOperations} />
      <Route path="/admin/report" component={AdminReport} />
    </div>
  )

  return (
    <Router history={history}>
      <Layout pages={pages} />
    </Router>
  )
}
