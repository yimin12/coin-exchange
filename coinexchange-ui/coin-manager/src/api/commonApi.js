import request from '@/utils/request'
import {formUtils} from '@/utils/formUtil'

export const commonApi = {
  /**
   * 获取所有币种信息
   */
  getCoinAll(status = 1) {
    return request({
      url: '/finance/coins/all',
      method: 'get',
      params: {status}
    })
  },

  /**
   * 获取所有交易市场
   */
  getMarketAll() {
    return request({
      url: '/exchange/markets/all',
      method: 'get'
    })
  },

  /**
   * 导出excel
   */
  exportExcel(url, form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: url,
      method: 'get',
      params: params
    })
  },

}
