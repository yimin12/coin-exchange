import request from '@/utils/request'
import {formUtils} from '@/utils/formUtil'

export const assetsApi = {

  // 虚拟币充值
  rechargeCoin(data) {
    return request({
      url: '/account/rechargeCoin',
      method: 'post',
      data,
    })
  },
  /**
   *获取账户资产列表
   */
  getAccountAssetsList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/account',
      method: 'get',
      params: params
    })
  },

  /**
   *账户资金列表导出xls
   */
  accountAssetsExport(form) {
    let params = {}
    formUtils.formDateRange(params, form);
    return request({
      url: '/account/exportList',
      method: 'get',
      params: params
    })
  },

  /**
   * 资金状态操作设置
   */
  accountAssetsOpearte(data) {
    return request({
      url: '/account/setStatus',
      method: 'post',
      data: data
    })
  },

  /**
   *获取账户资产流水管理列表
   */
  getAccountAssetsFlowList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/finance/accountDetails/records',
      method: 'get',
      params: params
    })
  },

  /**
   *获取币币交易委托管理
   */
  getEntrustManagerList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/entrustOrder',
      method: 'get',
      params: params
    })
  },

  /**
   * 获取币币交易成交记录列表
   */
  getTurnoverRecordList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/turnoverOrder',
      method: 'get',
      params: params
    })
  },

  /**
   * 获取创新委托管理列表
   **/
  getInnovateEntrustManagerList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/forexEntrustOrder',
      method: 'get',
      params: params
    })
  },

  /**
   * 获取创新成交记录列表
   **/
  getInnovateRecordList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params, form);
    params.current = current
    params.size = size
    return request({
      url: '/turnoverOrder',
      method: 'get',
      params: params
    })
  },


}
