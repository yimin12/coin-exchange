import request from "./request";
import md5 from 'js-md5';
/*
 * 注册登录
 */
export const loginRegist = {
  /**
   * 登录
   */
  login(data) {
    data.password = md5(data.password);
    return request({
      url: '/user/login',
      method: 'post',
      data,
    })
  },
  /**
   * 注册
   */
  register(data, geetest_challenge,uuid, geetest_validate, geetest_seccode ,registType) {
    let {countryCode,  password, invitationCode, validateCode,email,mobile} = data;
    alert(uuid)
    let reqData = {
      countryCode,
      password : md5(password),
      invitionCode: invitationCode,
      validateCode,
      geetest_challenge,
      geetest_validate,
      uuid,
      geetest_seccode
    }
    if(registType === 0 ){
      reqData.mobile = mobile
    }else{
      reqData.email = email
    }
    return request({
      url: '/user/users/register',
      method: 'post',
      data: reqData
    })
  },

  /**
   * 获取图片验证码
   */
  getValidateCodeImg() {
    console.log('getValidateCodeImg')
    const url = '/platform/user/getValidateCodeImg.m?t=' + Math.random()
    return axios.get(url).then((res) => {
      return Promise.resolve(res.data)
    })
  },
  /**
   * 获取usessionid
   */
  getUsessionId() {
    console.log('getUsessionId')
    const url = '/platform/user/getUsessionId.m'
    return axios.get(url).then((res) => {
      return Promise.resolve(res.data)
    })
  },
  /**
   *
   *获取用户信息
   */
  serverGetUser() {
    console.log('serverGetUser')
    const url = '/trade/home/serverGetUser.o'
    return axios.get(url).then((res) => {
      return Promise.resolve(res.data)
    })
  },
  /**
   *
   *退出登录
   */
  logout() {
    return request({
      url: '/user/logout',
      method: 'get',
    })
  },

  // 设置新的登录密码
  setPassword(data){
    data.password = md5(data.password);
    return request({
      url: '/user/users/setPassword',
      method: 'post',
      data,
    });
  },
}
