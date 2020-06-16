import moneyToWords from 'utils/MoneyToWords'
const addon = ' 00 коп.'

describe('Должно работать преобразование числа в строку', () => {
  it('1-один', () => {
    const str = moneyToWords(1).slice(0, -8)
    expect(str).toBe('один рубль')
  })
  it('11 - одиннадцать рублей', () => {
    const str = moneyToWords(11).slice(0, -8)
    expect(str).toBe('одиннадцать рублей')
  })
  it('22-двадцать два рубля', () => {
    const str = moneyToWords(22).slice(0, -8)
    expect(str).toBe('двадцать два рубля')
  })
  it('534 - пятьсот тридцать четыре рубля', () => {
    const str = moneyToWords(534).slice(0, -8)
    expect(str).toBe('пятьсот тридцать четыре рубля')
  })
  it('12321 - двенадцать тысяч триста двадцать один рубль', () => {
    const str = moneyToWords(12321).slice(0, -8)
    expect(str).toBe('двенадцать тысяч триста двадцать один рубль')
  })
  it('9876543210', () => {
    const str = moneyToWords(9876543210).slice(0, -8)
    expect(str).toBe('девять миллиардов восемьсот семьдесят шесть' +
      ' миллионов пятьсот сорок три тысячи двести десять рублей')
  })
})


