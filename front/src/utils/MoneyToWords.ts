const STRING_MAIN = [['', '', ''],
  [['один', 'одна'], ['десять', 'одиннадцать', 'двенадцать',
    'тринадцать', 'четырнадцать', 'пятнадцать', 'шестнадцать',
    'семнадцать', 'восемнадцать', 'девятнадцать'], 'сто'],
  [['два', 'две'], 'двадцать', 'двести'],
  ['три', 'тридцать', 'триста'],
  ['четыре', 'сорок', 'четыреста'],
  ['пять', 'пятьдесят', 'пятьсот'],
  ['шесть', 'шестьдесят', 'шестьсот'],
  ['семь', 'семьдесят', 'семьсот'],
  ['восемь', 'восемьдесят', 'восемьсот'],
  ['девять', 'девяносто', 'девятьсот']]

const STRING_POST = [['копейка', 'копейки', 'копеек'],
  ['рубль', 'рубля', 'рублей'],
  ['тысяча', 'тысячи', 'тысяч'],
  ['миллион', 'миллиона', 'миллионов'],
  ['миллиард', 'миллиарда', 'миллиардов'],
]

const plural = (num: number, forms) => {
  const nonZero = num % 10 >= 2 && num % 10 <= 4 &&
  (num % 100 < 10 || num % 100 >= 20) ? 1 : 2
  const idx = (num % 10 === 1 && num % 100 !== 11) ? 0 : nonZero
  return forms[idx]
}

const m999 = (num: number, float: number) => {
  let sumTrailing = ''
  let trailing = STRING_MAIN[Math.floor(num / 100) % 10][2]

  if (trailing) {
    sumTrailing = `${sumTrailing}${trailing} `
  }
  const digit = Math.floor(num / 10) % 10
  trailing = STRING_MAIN[digit][1]
  if (trailing instanceof Array) {
    trailing = trailing[num % 10]
    if (trailing) {
      sumTrailing = `${sumTrailing}${trailing}`
    }
  } else {
    if (trailing) {
      sumTrailing = `${sumTrailing}${trailing} `
    }
    trailing = STRING_MAIN[num % 10][0]
    if (trailing instanceof Array) trailing = trailing[float === 0 || float === 2 ? 1 : 0]
    if (trailing) sumTrailing += trailing
  }

  return `${sumTrailing} ${plural(num, STRING_POST[float])}${(float > 1 ? ' ' : '')}`
}

export default function moneyToWords(input: number | string | undefined): string {
  if (typeof(input) === 'undefined') {
    return ''
  }
  let num: number = 0
  if (typeof(input) === 'string') {
    try {
      num = parseInt(input, 10)
    } catch ( e ) {
      return ''
    }
  } else {
    num = input
  }
  let interval = Math.floor(num + 0.005)
  const floating = Math.floor(((num - interval) * 100) + 0.5)
  let str = ''
  for (let j = 1; interval > 0.9999; interval /= 1000) {
    str = m999(Math.floor(interval % 1000), j) + str
    j++
  }
  if (floating > 0) {
    str = `${str} ${m999(floating, 0)}`
  }
  str = str.replace('  ', ' ')
  return `${str} 00 коп.`
}
