import React from 'react'
import { Alert } from 'antd'

interface MessageProps {
  text?: string,
  description?: string,
  type?: 'info' | 'success' | 'warning' | 'error',
}

export default (props: MessageProps) => {
  const {
    text = '',
    description = '',
    type = 'info',
  } = props

  return (
    <Alert
      message={text}
      description={description}
      type={type}
      showIcon
    />
  )
}
