import request from './request'

export const tradeApi = {
  // 未完成委托
  serverGetEntrustOrderList(current, size, symbol, token) {
    return request({
      url: `/exchange/entrustOrders/${symbol}`,
      method: 'get',
      headers: {
        'Authorization': token,
      },
      params:{
        current: current ,
        size: size 
      }
    })
  },
  // 历史委托订单
  serverGetTurnoverOrderList(current, size, symbol, token) {
    return request({
      url: `/exchange/entrustOrders/history/${symbol}`,
      method: 'get',
      headers: {
        'Authorization': token,
      },
      params:{
        current: current ,
        size: size
      }
    })
  },
  // 币币交易 委托下单
  serverCreateOrder(price, symbol, type, volume, token) {
    let data = {
      price,
      symbol,
      type,
      volume,
    };
    return request({
      url: '/exchange/entrustOrders',
      method: 'post',
      headers: {
        'Authorization': token,
      },
      data,
    })
  },
  //撤销委托
  serverCancelOrder(orderId, token) {
    return request({
      url: `/exchange/entrustOrders/${orderId}`,
      method: 'delete',
      headers: {
        'Authorization': token,
      },
    })
  },
  //获取当前用户当前市场 可交易额度
  getUserAccount(symbol, token) {
    return request({
      url: '/finance/account/asset/' + symbol,
      method: 'get',
      headers: {
        'Authorization': token,
      },
    })
  },
  //个人收藏交易市场信息
  serverFavorite(token) {
    return request({
      url: '/exchange/tradeAreas/market/favorite',
      method: 'get',
      headers: {
        'Authorization': token,
      },
    })
  },

  //获取深度数据
  getDepth(symbol,mergeType) {
    return request({
      url: `/exchange/markets/depth/${symbol}/${mergeType}`,
      method: 'get',
    })
  },
  // 获取最新成交列表
  getTrades(symbol) {
    return request({
      url: `/exchange/markets/trades/${symbol}`,
      method: 'get',
    })
  }


}
