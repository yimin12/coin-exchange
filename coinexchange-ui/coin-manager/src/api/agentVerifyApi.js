import request from '@/utils/request';


export const agentVerifyApi = {

  /**
   * 获取代理商注册审核列表
   * @param form
   * @param current
   * @param size
   */
  getAgentSellerList(form, current, size) {
    let params = {};
    for (let item in form) {
      params[item] = form[item];
    }
    params.current = current;
    params.size = size;
    console.log("结果", params);
    return request({
      url   : '/user/reviewSubagentList',
      method: 'post',
      params: params
    });
  },

  /**
   * 代理商注册审核认证
   * @param id
   * @param reviewsStatus
   * @param note
   */
  updateVerify(id, reviewsStatus, note) {
    return request({
      url   : '/user/updateReviewsStatus',
      method: 'post',
      params: {id, reviewsStatus, note}
    });
  },

  /**
   * 获取代理商列表
   * @param form
   * @param current
   * @param size
   */
  getSellerList(form, current, size) {
    let params = {};
    for (let item in form) {
      if(form[item]){
        params[item] = form[item];
      }
    }
    params.current = current;
    params.size = size;
    return request({
      url   : '/user/subagentList',
      method: 'post',
      params: params
    });
  },

  /**
   * 获取当前代理信息
   * @param id
   */
  getCurrentAgent(id) {
    return request({
      url   : '/user/getSubagentInfo',
      method: 'get',
      params: {id}
    });
  },

  /**
   * 更新编辑代理信息
   * @param form
   * @param current
   * @param size
   */
  postAgentInfo(form, current, size) {
    let params = {};
    for (let item in form) {
      params[item] = form[item];
    }
    params.current = current;
    params.size = size;
    return request({
      url   : '/user/updatesubagent',
      method: 'post',
      params: params
    });
  },

  /**
  * 启用禁用用户
  **/
  getOperateUser(id,status){
    return request({
      url:'/user/updateStatus',
      method:'get',
      params:{id,status}
    })
  }

};
