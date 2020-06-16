import React from 'react'
import {
  AutoComplete,
  Input,
  Icon
} from 'antd'

const styles = require('./Autocomplete.less')

const { Option } = AutoComplete

interface AutocompleteProps {
  value: string,
  items: any[],
  onSelect: any,
  filterOption?: any,
}
export default (props: AutocompleteProps) => {
  const { value, items, onSelect, filterOption } = props

  const dataSource = items.map((item) =>
    (<Option
        key={item.id}
        value={item.name}
        disabled={!item.enabled}
      >
        <div className={styles.option}>
          {item.name}
        </div>
      </Option>
    ) as any
  )

  return (
    <div className={styles.autocomplete}>
      <AutoComplete
        dataSource={dataSource}
        optionLabelProp="value"
        allowClear
        value={value}
        transitionName=""
        onSelect={onSelect}
        filterOption={filterOption}
      >
        <Input
          placeholder="Поиск"
          prefix={
            <Icon type="search" className={styles.icon} />
          }
        />
      </AutoComplete>
    </div>
  )
}
