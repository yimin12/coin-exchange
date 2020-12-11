import request from '@/utils/request';

export const userApi = {
  // 获取邀请用户列表
  getDirectInviteidList(data, current, size) {
    return request({
      url: '/user/users/directInvites',
      method: 'get',
      params: {
        ...data,
        current,
        size,
      }
    })
  },
  /**
   * 用户列表
   * ga_status 0 未启用 1 启用
   */
  getUserList(form, current, size) {
    let params = {};
    for (let item in form) {
      if (form[item]) {
        if (item === "dateRange") {
          let startTime = form.dateRange[0];
          let endTime = form.dateRange[1];
          params["startTime"] = startTime;
          params["endTime"] = endTime;
        } else {
          params[item] = form[item];
        }
      }
    }
    params.current = current;
    params.size = size;
    console.log("结果", params);
    return request({
      url: 'user/users',
      method: 'get',
      params: params
    });
  },
  // 获取用户详情
  getUserDetail(id) {
    return request({
      url: '/user/users/info',
      method: 'get',
      params: {id},
    })
  },
  // 获取用户银行卡列表
  getBankList(id, current, size) {
    return request({
      url: 'user/userBanks',
      method: 'get',
      params: {usrId: id, current, size}
    })
  },
  // 禁用/启用 银行卡
  updateBankStatus(id, status) {
    return request({
      url: `user/userBanks/status`,
      method: 'post',
      params: {id, status},
    })
  },
  // 更新银行卡
  updateBank(data) {
    return request({
      url: 'user/userBanks',
      method: 'patch',
      data,
    })
  },
  // 获取提币地址
  getWalletList(userId, current, size) {
    return request({
      url: 'user/userWallets',
      method: 'get',
      params: {userId, current, size}
    })
  },
  // 获取钱包地址
  getUserAddress(userId, current, size) {
    return request({
      url: '/user/userAddress',
      method: 'get',
      params: {userId, current, size}
    })
  },
  /**
   * 更新用户信息
   * @param data
   */
  updateUser(data) {
    const keys = ["id", "username", "mobile", "countryCode", "realName", "mobile", "email", "idCard", "authStatus", "status"]
    let reqData = {}
    keys.map((key) => {
      reqData[key] = data[key]
    });
    return request({
      url: '/user/users',
      method: 'patch',
      data: reqData
    });
  },
  /**
   *禁/启用户
   */
  updateStatus(id, status) {
    return request({
      url: '/user/users/status',
      method: 'post',
      params: {id, status}
    });
  },

  /**
   * 实名认证审核列表
   * type 1 普通用户 2 代理商
   * reviewStatus  0 待审核 1 通过 2 拒绝
   * status : 0 禁用 1 启用
   * agent_note: 代理商拒绝原因
   * authStatus: 0 未认证 1初级 2高级
   *
   */
  getUserAuthList(form, current, size) {
    let params = {}
    for (let item in form) {
      if (form[item]) {
        if (item === "dateRange") {
          let startTime = form.dateRange[0]
          let endTime = form.dateRange[1]
          params["startTime"] = startTime
          params["endTime"] = endTime
        } else {
          params[item] = form[item]
        }
      }
    }
    params.current = current;
    params.size = size;
    return request({
      url: '/user/users/auths',
      method: 'get',
      params: params
    });
  },

  /**
   * 获取认证详情
   * @param id
   */
  selUserAuthDetail(id) {
    return request({
      url: '/user/users/auth/info',
      method: 'get',
      params: {
        id
      }
    });
  },

  /**
   * 用户认证审核记录列表
   * @param id
   */
  getUserAuthRecordList(userId) {
    let current = 1
    let size = 5
    return request({
      url: '/user/getUserAuthRecordList',
      method: 'get',
      params: {
        userId,
        current,
        size
      }
    });
  },

  /**
   * 审核认证
   * @param id
   * @param review
   * @param note
   */
  updateVerify(id, authStatus, remark, authCode) {
    if(authStatus!=2){
      remark = null ;
    }
    return request({
      url: '/user/users/auths/status',
      method: 'post',
      params: {id, authStatus, remark, authCode}
    });
  },

  /**
   * 获取矿池列表
   */
  minePollList(form, current, size) {
    let params = {};
    for (let item in form) {
      params[item] = form[item];
    }
    params.current = current;
    params.size = size;
    return request({
      url: '/mine/pool',
      method: 'get',
      params: params
    });
  },

  /**
   * 审核矿池
   */
  verifyPool(data) {
    return request({
      url: '/mine/pool',
      method: 'put',
      data,
    })
  },
};

