import * as _ from 'lodash'
import { BusinessStep } from 'interfaces/BusinessStep.interface'

const STEP_1_LOCATION: string = '/operator'
const STEP_2_LOCATION: string = '/operator/cardConfirm'
const STEP_3_LOCATION: string = '/operator/cardAccount'
const STEP_4_LOCATION: string = '/operator/ovnSelect'
const STEP_5_LOCATION: string = '/operator/createNewOperation'
const STEP_6_LOCATION: string = '/operator/forwardToCashoffice'
const STEP_7_LOCATION: string = '/operator/acceptPos'
const STEP_8_LOCATION: string = '/operator/acceptOperation'

class OperatorProcess {
  step1: BusinessStep = {
    location: STEP_1_LOCATION,
    onComplete: STEP_2_LOCATION,
  }

  step2: BusinessStep = {
    location: STEP_2_LOCATION,
    onComplete: STEP_3_LOCATION,
  }

  step3: BusinessStep = {
    location: STEP_3_LOCATION,
    onComplete: STEP_4_LOCATION,
  }

  step4: BusinessStep = {
    location: STEP_4_LOCATION,
    onComplete: STEP_5_LOCATION,
  }

  step5: BusinessStep = {
    location: STEP_5_LOCATION,
    onComplete: STEP_7_LOCATION,
    onCancel: STEP_1_LOCATION,
    onAlter: STEP_6_LOCATION
  }

  step6: BusinessStep = {
    location: STEP_6_LOCATION,
    onComplete: STEP_1_LOCATION,
  }

  step7: BusinessStep = {
    location: STEP_7_LOCATION,
    onComplete: STEP_8_LOCATION,
    onCancel: STEP_5_LOCATION,
  }

  step8: BusinessStep = {
    location: STEP_8_LOCATION,
    onComplete: STEP_1_LOCATION,
  }

  steps: Array<BusinessStep> = [this.step1, this.step2, this.step3, this.step4, this.step5, this.step6, this.step7, this.step8]

  findStep(location: string) {
    return _.find(this.steps, { location })
  }
}

export const operatorProcess = new OperatorProcess()
