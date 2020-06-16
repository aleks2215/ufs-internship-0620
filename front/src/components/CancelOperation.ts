import { Modal } from 'antd'

export default (onOk) => Modal.confirm({
  title: 'Отмена операции',
  content: 'Вы действительно хотите отменить выполнение операции?\nВесь прогресс будет потерян',
  okText: 'Да',
  cancelText: 'Отмена',
  width: 600,
  onOk() {
    onOk()
  },
})
