import * as _ from 'lodash'
import { BusinessStep } from 'interfaces/BusinessStep.interface'

const STEP_1_LOCATION: string = '/cachebox'
const STEP_2_LOCATION: string = '/cachebox/cardConfirm'
const STEP_3_LOCATION: string = '/cachebox/cardAccount'
const STEP_4_LOCATION: string = '/cachebox/ovnSelect'
const STEP_5_LOCATION: string = '/cachebox/createNewOperation'
const STEP_5_1_LOCATION: string = '/cachebox/checkNewOperation'
const STEP_6_LOCATION: string = '/cachebox/forwardToCashoffice'
const STEP_7_LOCATION: string = '/cachebox/acceptPos'
const STEP_8_LOCATION: string = '/cachebox/acceptOperation'

class CacheboxProcess {
  step1: BusinessStep = {
    location: STEP_1_LOCATION,
    onComplete: STEP_2_LOCATION,
  }

  step2: BusinessStep = {
    location: STEP_2_LOCATION,
    onComplete: STEP_5_1_LOCATION,
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

  step5_1: BusinessStep = {
    location: STEP_5_1_LOCATION,
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
    onCancel: STEP_5_1_LOCATION,
  }

  step8: BusinessStep = {
    location: STEP_8_LOCATION,
    onComplete: STEP_1_LOCATION,
  }

  steps: Array<BusinessStep> = [this.step1, this.step2, this.step3,
    this.step4, this.step5, this.step5_1, this.step6, this.step7, this.step8]

  findStep(location: string) {
    return _.find(this.steps, { location })
  }
}

export const cacheboxProcess = new CacheboxProcess()
