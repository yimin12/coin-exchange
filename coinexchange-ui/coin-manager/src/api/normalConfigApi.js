import request from '@/utils/request';

export const normalConfigApi = {
  /**
   * 新增银行卡
   * @param data
   */
  createBank(data) {
    return request({
      url: '/admin/adminBanks',
      method: 'post',
      data
    });
  },

  /**
   * 编辑状态
   * @param data
   */
  setBanks(params) {
    return request({
      url: '/admin/adminBanks/adminUpdateBankStatus',
      method: 'post',
      params: params
    });
  },


  /**
   * 获取银行卡列表
   * @param form
   * @param current
   * @param size
   */
  getBankList(form, current, size) {
    let params = {};
    params["bankCard"] = form["bankCard"];
    params.current = current;
    params.size = size;
    return request({
      url: '/admin/adminBanks',
      method: 'get',
      params: params
    });
  },

  /**
   * 更新银行卡
   * @param data
   */
  updateBank(data) {
    return request({
      url: '/admin/adminBanks',
      method: 'patch',
      data
    });
  },

  /**
   * 新增配置
   * @param data
   */
  createConfig(data) {
    return request({
      url: '/admin/configs',
      method: 'post',
      data
    });
  },


  /**
   * 获取配置列表
   * @param current
   * @param size
   */
  getConfigList(current, size, form = {}) {
    let params = {
      ...form,
    };
    params.current = current;
    params.size = size;
    return request({
      url: '/admin/configs',
      method: 'get',
      params: params
    });
  },

  /**
   * 更新配置
   * @param data
   */
  updateConfig(data) {
    return request({
      url: '/admin/configs',
      method: 'patch',
      data
    });
  },

  /**
   * 新增系统权限
   **/
  createSysPrivileges(data) {
    return request({
      url: '/admin/privileges',
      method: 'post',
      data: data
    })
  },

  /**
   * 编辑系统权限
   */
  editSysPrivileges(data) {
    return request({
      url: '/admin/privileges',
      method: 'patch',
      data: data
    })
  },

};
