import React from 'react'
import {
  Button,
  Col,
  Collapse,
  Icon,
  Row
} from 'antd'
import LegalEntity from './LegalEntity'
import PersonalEntity from './PersonalEntity'
import CardIndex1 from './CardIndex1'
import CardIndex2 from './CardIndex2'
import CloseableCollapse, { PairsItem } from './CloseableCollapse'
import { Account } from 'interfaces/dto/Account.interface'

const styles = require('./CardAccount.less')

const Panel = Collapse.Panel

export interface CardAccountFormProps {
  getCardAccount: Function,
  addSidePanel: Function,
  onComplete: Function,
  removeSidePanel: Function,
  loaded?: boolean,
  info?: Account,
  sidePanel: Array<PairsItem>,
  cardInfo: any,
}

export default class CardAccountForm extends React.Component<CardAccountFormProps, any> {
  componentDidMount() {
    if (this.props.loaded) {
      return
    }
    this.props.getCardAccount({ cardNumber: this.props.cardInfo.number })
  }

  render() {
    const { info, cardInfo, addSidePanel, onComplete, removeSidePanel } = this.props
    return (
      <div>
        <Row>
          <Col span={10}>
            {`${cardInfo.ownerFirstName} ${cardInfo.ownerLastName}`}
          </Col>
          <Col span={8} className={styles.rightAligned}>
            {`VISA ${cardInfo.number}`}
          </Col>
        </Row>
        <Row>
          <Col span={18}>
            <Collapse className={styles.accountCard} defaultActiveKey={['2']}>
              <Panel header="Общая информаци о юридическом лице" key="1">
                {info.legalEntity ?
                  <LegalEntity legalEntity={info.legalEntity} /> :
                  null
                }
              </Panel>
              <Panel header="Общая информация по счету" key="2">
                {info.account ? <PersonalEntity
                  addSidePanel={addSidePanel}
                  info={info.account}
                  seizures={info.seizures}
                  accountResidues={info.accountResidues}
                />
                  :
                  null
                }
              </Panel>
              <Panel header={`Картотека №1 - ${info.cardIndexes1.length} ожидают утверждения`} key="3">
                <CardIndex1 addSidePanel={addSidePanel} items={info.cardIndexes1} />
              </Panel>
              <Panel header={`Картотека №2 - ${info.cardIndexes2.length} в очереди`} key="4">
                <CardIndex2 addSidePanel={addSidePanel} items={info.cardIndexes2} />
              </Panel>
            </Collapse>
          </Col>
          <Col span={6}>
            <CloseableCollapse
              sidePanel={this.props.sidePanel}
              removePanel={removeSidePanel}
            />
          </Col>
        </Row>
        <Row>
          <Col span={18} className={styles.rightAligned}>
            <Button type="primary" disabled={!info.account} onClick={() => onComplete()}>
              Далее<Icon type="right" />
            </Button>
          </Col>
          <Col span={6} />
        </Row>
      </div>
    )
  }
}
