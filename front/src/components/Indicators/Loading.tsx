import React from 'react'
import { Spin } from 'antd'

const styles = require('./Loading.less')

export default () => (
  <div className={styles.loading}>
    <Spin />
  </div>
)
