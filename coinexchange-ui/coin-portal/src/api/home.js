import request from "./request";
import axios from 'axios'
let qs  = require('qs')

export const homeApi = {
  // 新的首页 包含交易区的market接口
  getMarketListNew(){
    return request({
      url : '/exchange/tradeAreas/markets',
      method : 'get',
    })
  },
  getMarketList(){
    return request({
      // url    : '/v2/s/trade/market/all',
      url    : '/exchange/markets/all',
      method : 'get',
    }).then((res) => {
      return Promise.resolve(res.data)
    })
  },
  // 获取banner
  getBanner(){
    return request({
      url : '/admin/webConfigs/banners',
      method : 'get'
    })
  },

  getDoucments(){
    return request({
      url : '/admin/webConfigs/documents',
      method : 'get'
    })
  },
  getNoticeList (current,size) {
    return request({
      url : `/admin/notices/simple`,
      method : 'get',
      params:{
        current: current ,
        size: size 
      }
    })
  },
  getNoticeDetail (noticeId) {
    return request({
      url : `/admin/notices/simple/${noticeId}`,
      method : 'get'
    })
  }
}
