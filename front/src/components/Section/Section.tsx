import React from 'react'

const styles = require('./Section.less')

interface SectionProps {
  header: string,
  children?: any,
}

export default (props: SectionProps) => (
  <section className={styles.section}>
    <div className={styles.header}>{props.header}</div>
    <div className={styles.container}>
      {props.children}
    </div>
  </section>
);
