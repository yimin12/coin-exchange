import request from "./request";
import md5 from 'js-md5';
import axios from "axios"
/*
 * 用户中心
 */

//修改绑定手机
export function serverBindMobile(countryCode, newMobilePhone, validateCode,oldValidateCode) {
  const url =  '/user/users/updatePhone';
  let data  = {
    countryCode,
    newMobilePhone,
    validateCode,
    oldValidateCode,
  };
  return request({
    url    : url,
    method : 'post',
    data,
  })
}
//修改绑定邮箱
export function updateEmail(oldEmail, newEmail, validateCode) {
  const url =  '/user/users/updateEmail';
  let data  = {
    oldEmail,
    newEmail,
    validateCode
  };
  return request({
    url    : url,
    method : 'post',
    data,
  })
}
//验证老手机
export function verifyOldPhone(oldPhone, newPhone, validateCode) {
  const url =  '/user/verify/old/phone';
  let data  = {
    oldPhone,
    newPhone,
    validateCode
  };
  return request({
    url    : url,
    method : 'post',
    data,
  })
}
//验证老邮箱
export function verifyOldEmail(oldEmail, newEmail, validateCode) {
  const url =  '/user/verify/old/email';
  let data  = {
    oldEmail,
    newEmail,
    validateCode
  };
  return request({
    url    : url,
    method : 'post',
    data,
  })
}


export function getchilds (token) {
  return request({
    url : '/user/users/invites',
    method: 'get',
    headers:{
      'Authorization': token,
    },
  })
}

//修改登录密码
export function updateUserPassword(data, token,countryCode) {
  data.oldpassword = md5(data.oldpassword);
  data.newpassword = md5(data.newpassword);
  return request({
    url : '/user/users/updateLoginPassword',
    method:'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}

//修改交易密码
export function updatePayPassword(data, token) {
  data.oldpassword = md5(data.oldpassword);
  data.newpassword = md5(data.newpassword);
  return request({
    url : '/user/users/updatePayPassword',
    method:'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}
//实名认证
export function setAuth(data, token) {
  return request({
    url : '/user/users/authAccount',
    method : 'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}
//高级认证
export function seniorAuth(data, token) {
  return request({
    url : '/user/users/authUser',
    method : 'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}

export function setUserBaseInfo(email, payPassword, username, token) {
  let data = {
    email,
    payPassword : md5(payPassword),
    username
  };
  return request({
    url : '/user/user/userBase',
    method : 'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}

export function setPayPassword(data, token) {
  data.payPassword = md5(data.payPassword);
  return request({
    url : '/user/users/setPayPassword',
    method : 'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}

// 添加自选
export function serverAddFavorite(symbol, type, token) {
  let data = {
    symbol,
    type
  };
  return request({
    url : '/exchange/userFavorites/addFavorite',
    method: 'post',
    headers:{
      'Authorization': token,
    },
    data,
  })
}
export function serverDeleteFavorite(symbol, type, token) {
  return request({
    url : '/exchange/userFavorites/'+ symbol,
    method: 'delete',
    headers:{
      'Authorization': token,
    },
  })
}
export function gaGenerate(token) {
  return request({
    url : '/user/user/ga/generate',
    method: 'get',
    headers:{
      'Authorization': token,
    }
  })
}
export function gaVerify(code,secret,token) {
  return request({
    url : '/user/user/ga/verify',
    method: 'post',
    headers:{
      'Authorization': token,
    },
    data:{code,secret}
  })
}

// 关闭google 验证
export function closeGaVerify(code) {
  return request({
    url : '/user/user/ga/cancel',
    method: 'post',
    data:{code}
  })
}
/**
 * 创建矿池
 */
export  function createPool(data) {
  return request({
    url: '/v2/s/mine/pool',
    method: 'post',
    data,
  })
}
/**
 * 矿池列表
 */
export  function minePool() {
  return request({
    url: '/v2/s/mine/pool',
    method: 'get',
  })
}









