import request from '@/utils/request'
import {formUtils} from '@/utils/formUtil'
export const financeApi = {


  /**
   * 场外交易充值管理列表
   */
  getFinanceRechargeList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params,form);
    params.current = current
    params.size = size
    return request({
      url: '/finance/cashRecharges/records',
      method: 'get',
      params: params
    })
  },

  /**
  * cny 充值审核操作
  */
  checkFinanceRecharge(data){
    return request({
      url:'/finance/cashRecharge/cashRechargeUpdateStatus',
      method:'post',
      data:data
    })
  },

  /**
  * 场外交易提现审核列表
  */
  getFinanceWithdrawalsList(form, current, size) {
    let params = {}
    formUtils.formDateRange(params,form);
    params.current = current
    params.size = size
    return request({
      url: '/finance/cashWithdrawals/records',
      method: 'get',
      params: params
    })
  },

  /**
  * cny 提现审核操作
  **/
  checkFinanceWithdrawals(data){
    return request({
      url:'/finance/cashWithdrawals/updateWithdrawalsStatus',
      method:'post',
      data:data
    })
  },

  /**
  * 虚拟币提现列表
  */
  getCoinWithdraw(form,current,size){
    let params = {}
    formUtils.formDateRange(params,form);
    params.current = current
    params.size = size
    return request({
      url: '/finance/coinWithdraws/records',
      method: 'get',
      params: params
    })
  },

  /**
  * 虚拟币审核操作
  */
  checkCoinWithdraw(data){
    return request({
      url:'/finance/coinWithdraws/audit',
      method:'post',
      data:data
    })
  },
  /* 手动打款接口*/
  manualWithdraw(data){
    return request({
      url: '/finance/coinWithdraws',
      method:'patch',
      data:data
    })
  },

  /**
  * 虚拟币充值列表
  */
  getCoinRecharge(form, current, size) {
    let params = {}
    formUtils.formDateRange(params,form);
    params.current = current
    params.size = size
    return request({
      url:'/finance/coinRecharges/records',
      method:'get',
      params:params
    })
  }
}
