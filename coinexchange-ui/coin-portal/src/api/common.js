import request from "./request";

/*
 * 公共接口 V3
 */
export const commonApi = {
  SMS_TYPE: {
    //修改手机号码
    CHANGE_PHONE_VERIFY: "CHANGE_PHONE_VERIFY",
    //修改登录密码
    CHANGE_LOGIN_PWD_VERIFY: "CHANGE_LOGIN_PWD_VERIFY",
    //修改交易密码
    CHANGE_PAY_PWD_VERIFY: "CHANGE_PAY_PWD_VERIFY",
    //用户手机注册验证
    REGISTER_VERIFY: "REGISTER_VERIFY",
    // 用户找回密码验证
    FORGOT_VERIFY: "FORGOT_VERIFY",
    // 用户找回交易密码
    FORGOT_PAY_PWD_VERIFY: "FORGOT_PAY_PWD_VERIFY",
    // 注册成为代理商用户
    REGISTER_AGENT: "REGISTER_AGENT",
    // 线下充值拒绝短信
    UNDER_LINE_REFUSE: "UNDER_LINE_REFUSE",
    // 线下充值成功短信
    UNDER_LINE_SUCCESS: "UNDER_LINE_SUCCESS",
    // 提币申请
    WITHDRAW_APPLY: "WITHDRAW_APPLY",
    // 提币成功
    WITHDRAW_SUCCESS: "WITHDRAW_SUCCESS",
    // 提现申请
    CASH_WITHDRAWS: "CASH_WITHDRAWS",
    // 登录
    LOGIN: "LOGIN",
    // 验证老手机或 邮箱
    VERIFY_OLD_PHONE:"VERIFY_OLD_PHONE"

  },
  checkMobile(mobile, countryCode) {
    return request({
      url: '/user/users/checkTel',
      method: 'get',
      params: {mobile, countryCode}
    })
  },
  checkEmail(email) {
    return request({
      url: '/user/user/checkEmail',
      method: 'get',
      params: {email}
    })
  },

  // 用户信息
  serverUserinfo(token) {
    return request({
      url: '/user/users/current/info',
      method: 'get',
      headers: {
        'Authorization': token,
      },
    })
  },

  // 刷新token
  refreshToken(token) {
    return request({
      url: '/user/refreshToken',
      method: 'get',
      headers: {
        'Authorization': token,
      },
    })
  },

  // 改变语言
  changeLanguage(lang) {
    return request({
      url: '/user/user/lang?lang=' + lang
    })
  },

  sendSms(mobile, templateCode, countryCode = '+86', validateType) {
    let data = {}
    if (validateType === 1) {
      data = {email: mobile, templateCode}
    } else {
      data = {mobile, templateCode, countryCode}
    }
    return request({
      url: '/user/sms/sendTo',
      method: "post",
      data: data
    })
  },
  // 提交工单
  addWorkIssue(question, token) {
    return request({
      url: '/admin/workIssues/addWorkIssue',
      method: "post",
      headers: {
        'Authorization': token,
      },
      data: {
        question
      }
    })
  },

  // 获取工单列表
  getWorkIssueList(current, size, token) {
    return request({
      url: `/admin/workIssues/issueList`,
      params: {
        current: current,
        size: size
      },
      method: 'get',
      headers: {
        'Authorization': token
      }
    })
  },
  uuid(){
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
 
    var uuid = s.join("");
    return uuid;
  },
}

