import axios from 'axios'
let qs = require('qs')
import request from './request'

//创新交易
export const exchangeApi = {

// 资金账户
  accountFunds(coinId,token) {
    return request({
      url: `/v2/s/account/funds/${coinId}`,
      method: 'get',
      headers: {
        'Authorization': token,
      }
    })
  },

  forexMarketList() {
    const url = '/v2/s/forex/market'
    return axios({
      url   : url,
      method: 'get'
    }).then((res) => {
      return Promise.resolve(res.data)
    })
  },

  forexMarketArea(){
    return request({
      url : '/v2/s/forex_area',
      method : 'get',
    })
  },

  // 委托下单
  entrustOrder(data, token) {
    return request({
      url   : '/v2/s/forex/order/entrusts',
      method: 'post',
      headers:{
        'Authorization': token,
      },
      data  : data
    })
  },
  // 撤销委托
  entrustCancel(orderId, token) {
    return request({
      url   : `/v2/s/forex/order/entrusts/${orderId}`,
      method: 'delete',
      headers:{
        'Authorization': token,
      },
    })
  },

  serverFavorite(token) {
    return request({
      url: '/v2/s/forex/market/favorite',
      method: 'get',
      headers:{
        'Authorization': token,
      },
    })
  },
  // 持仓明细
  orderHolds(token) {
    return request({
      url: '/v2/s/forex/order/holds',
      method: 'get',
      headers: {
        'Authorization': token,
      }
    })
  },
  // 持仓汇总
  orderGathers(token) {
    return request({
      url: '/v2/s/forex/order/gathers',
      method: 'get',
      headers: {
        'Authorization': token,
      }
    })
  },
  // 今日平仓
  todayCloseout(token) {
    return request({
      url: '/v2/s/forex/order/today_closeout',
      method: 'get',
      headers: {
        'Authorization': token,
      }
    })
  },
  // 今日委托
  todayEntrusts(token) {
    return request({
      url: '/v2/s/forex/order/today_entrusts',
      method: 'get',
      headers: {
        'Authorization': token,
      }
    })
  },
  // 今日成交
  todayTurnovers(token) {
    return request({
      url: '/v2/s/forex/order/today_turnovers',
      method: 'get',
      headers: {
        'Authorization': token,
      }
    })
  }


}
