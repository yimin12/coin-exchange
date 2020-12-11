export const util = {
  isMobile() {
    var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == 'ipad';
    var bIsIphone = sUserAgent.match(/iphone os/i) == 'iphone os';
    var bIsMidp = sUserAgent.match(/midp/i) == 'midp';
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == 'rv:1.2.3.4';
    var bIsUc = sUserAgent.match(/ucweb/i) == 'web';
    var bIsCE = sUserAgent.match(/windows ce/i) == 'windows ce';
    var bIsWM = sUserAgent.match(/windows mobile/i) == 'windows mobile';
    var bIsAndroid = sUserAgent.match(/android/i) == 'android';

    if (bIsIpad || bIsIphone || bIsMidp || bIsUc7 || bIsUc || bIsCE || bIsWM || bIsAndroid) {
      return true;
    } else {
      return false;
    }
  },
  getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min)
  },
  getUUID() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
      s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
  
    var uuid = s.join("");
    return uuid ;

  },
  shuffle(arr) {
    let _arr = arr.slice()
    for (let i = 0; i < _arr.length; i++) {
      let j = getRandomInt(0, i)
      let t = _arr[i]
      _arr[i] = _arr[j]
      _arr[j] = t
    }
    return _arr
  },
  debounce(func, delay) {
    let timer

    return function (...args) {
      if (timer) {
        clearTimeout(timer)
      }
      timer = setTimeout(() => {
        func.apply(this, args)
      }, delay)
    }
  },
  date2DateTimeString(date) {
    return date == null ? '' : (date.getFullYear() + '-' + (date.getMonth() + 1)
      + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
  },
  date2DateTimeString2(date) {
    return date == null ? '' : ((date.getMonth() + 1)
      + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
  },
  /**
   * 格式化提现地址
   */
  coinAddrFormat(coinAddr) {
    var text = ''
    if (coinAddr == '') {
      return text
    }
    text = coinAddr.replace(coinAddr.substr(5, 25), '****')
    return text
  },
  /**
   * 格式化银行卡
   */
  bankcardFormat(bankcard) {
    var text = '';
    if (bankcard == '' || bankcard == undefined) {
      return text
    }
    var startNumber = bankcard.substr(0,6);
    var endNumber = bankcard.substr(-4);
    text = bankcard.replace(/\d/g, '*');
    text = text.replace(/^\*{6}/g, startNumber);
    text = text.replace(/\*{4}$/g, endNumber);
    return text
  },
  /**
   * 格式化充值类型
   */
  formatRechargeType(type) {
    switch (type) {
      case 'bank':
        return "第三方支付";
      case 'alipay':
        return "支付宝";
      case 'cai1pay':
        return "财易付";
      case 'linepay':
        return "人工到账";
    }
    return "未知";
  },
  /**
   * 格式化状态提币的状态
   */
  statusFormat(status) {
    switch (status) {
      case 0:
        return '审核中'
      case 1:
        return '转出成功'
      case 2:
        return '拒绝'
      case 3:
        return '撤销'
      case 4:
        return '审核通过'
      case 5:
        return '打币成功'
    }
  },
  // 格式化充币状态
  rechargeCoinStatusFormat(confirm, status) {
    if (confirm < 1) {
      return '确认中，还需等待' + Math.abs(confirm) + '个确认数：'
    } else {
      if (status === 0) {
        return "待入账"
      } else if (status === 1) {
        return "充值失败"
      } else if (status === 2) {
        return "到账失败"
      } else {
        return "到账成功"
      }
    }
  },
  /**
   * 格式化委托记录的状态
   */
  formatEntrustStatus(status) {
    switch (status) {
      case 0:
        return '未成交'
      case 1:
        return '已成交'
      case 2:
        return '已取消'
      case 4:
        return '异常单'
    }
  },
  /**
   * 格式化cny充值记录状态
   */
  formatRechargeStatus(status) {
    switch (status) {
      case 0:
        return "待审核";
      case 1:
        return "审核通过";
      case 2:
        return "拒绝";
      case 3:
        return "充值成功";
    }
  },
  /**
   * 格式化cny提现记录状态
   */
  formatWithdrawStatus(status) {
    switch (status) {
      case 0:
        return '待审核'
      case 1:
        return '审核通过'
      case 2:
        return '拒绝'
      case 3:
        return '提现成功'
    }
  },
  rechargeFormat() {
    let num = Math.random().toFixed(2)//获取两位随机数
    return parseFloat(num.replace('0.', '.'))
  },
  // 将小数位科学计算法表示数转数字
  scientificToNumber(num) {
    var str = num.toString();
    var reg = /^(\d+)(e)([\-]?\d+)$/;
    var arr, len,
      zero = '';

    /*6e7或6e+7 都会自动转换数值*/
    if (!reg.test(str)) {
      return num;
    } else {
      /*6e-7 需要手动转换*/
      arr = reg.exec(str);
      len = Math.abs(arr[3]) - 1;
      for (var i = 0; i < len; i++) {
        zero += '0';
      }
      return '0.' + zero + arr[1];
    }
  },
  formatPhone(phone) {
    if (!phone) {
      return '';
    }
    const count = Math.floor(phone.length / 3);
    if (count < 3) {
      return '';
    }
    const length = phone.length;
    const res =
      `${phone.substr(0, count)}${'*'.repeat(length - 1 - count * 2)}${phone.substr(length - count)}`;
    return res;
  }
}

