export const exchangeUtil = {
  formatEntrustType(row){
    if(row.priceType == 1){
      if(row.type == 1){
        return "市价买入"
      }else{
        return "市价卖出"
      }
    }else{
      if(row.type == 1){
        return "限价买入"
      }else{
        return "限价卖出"
      }
    }
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
  }
}
