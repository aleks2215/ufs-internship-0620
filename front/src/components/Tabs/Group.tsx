import React from 'react'
import { Radio } from 'antd'

interface TabsProps {
  items: any[],
  onChange: any,
  defaultValue?: string,
}

export default (props: TabsProps) => {
  const { items, defaultValue = '' } = props
  const { onChange } = props

  return (
    <Radio.Group
      defaultValue={defaultValue}
      onChange={onChange}
    >
      {items.map((item, i) => (
          <Radio.Button value={item.key} key={i}>
            {item.text}
          </Radio.Button>
        )
      )}
    </Radio.Group>
  )
}
