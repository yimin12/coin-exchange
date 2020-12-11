import request from "./request";
let qs = require('qs');

export const orderApi = {

  /**
   * 获取成交记录
   * type 0 全部
   * 1 买入
   * 2 卖出
   * 3 自买自卖
   *
   */
  serverGetTurnoverOrderList(size, current, symbol, type, token) {
    let data = {
      size,
      current,
      symbol,
      type,
    };
    return request({
      url: '/exchange/turnoverOrders',
      method: 'post',
      headers: {
        'Authorization': token,
      },
      params: data
    });
  },
  /**
   * 获取委托记录
   */
  serverGetEntrustOrderList(size, current, symbol, type, token)
  {
    let data  = {
      size,
      current,
      symbol,
      type
    }
    return request({
      url : '/exchange/entrustOrders',
      method : 'get',
      headers: {
        'Authorization': token,
      },
      params: data
    });
  },

}






