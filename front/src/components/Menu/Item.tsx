import React from 'react'
import {
  Card,
  Rate,
  Icon,
  Tooltip
} from 'antd'

import cn from 'classnames'

const styles = require('./Item.less')

interface ItemProps {
  onCardClick: any,
  onStarClick: any,
  title: string,
  fave: boolean,
  enabled: boolean,
}

export default (props: ItemProps) => {
  const { title, fave, enabled } = props
  const { onCardClick, onStarClick } = props

  return (
    <div className={styles.cardWrapper}
         onClick={onCardClick}>
      <Card
        className={cn(styles.card, {
          [styles.cardWrapperDisabled]: !enabled,
        })}
        extra={
          <Rate
            count={1}
            value={fave ? 1 : 0}
            className={styles.rate}
            character={
              <Tooltip title={fave ? 'Убрать из избранного' : 'Добавить в избранное'}>
                <Icon
                  type={fave ? 'star' : 'star-o'}
                  onClick={onStarClick}
                />
              </Tooltip>
            }
          />
        }
      >
        <p className={styles.title}>{title}</p>
      </Card>
    </div>
  )
}
