import React from 'react'
import { Alert } from 'antd'

interface ErrorProps {
  message?: string,
  description?: string,
  type?: 'info' | 'success' | 'warning' | 'error',
}

export default (props: ErrorProps) => {
  const {
    message = '',
    description = '',
    type = 'info',
  } = props

  return (
    <Alert
      message={message}
      description={description}
      type={type}
      showIcon
    />
  )
}
