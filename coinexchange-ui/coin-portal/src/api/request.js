import axios from 'axios'
import {Message} from 'element-ui'
import {errorCodeMap} from "../common/js/errorCodeMap";
import {commonApi} from "./common";
import store from "../store";
import router from '../router'

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 10000 // request timeout
})
// 刷新token的url
const refreshTokenUrl = "/user/login/refreshToken";
// 请求创新交易资金的url
const requestForexFunds = "/v2/s/account/funds/"
// request interceptor
service.interceptors.request.use(config => {
  const data = getExpireTime();
  if (data) {
    let {expire, updateTime} = data;

    let nowTime = +new Date();
    let activeTime = (nowTime - updateTime);
    // console.log("activeTime", activeTime,expire,new Date(nowTime),new Date(updateTime));
    // 如果当前活跃时间减去令牌更新时间小于过期时间（毫秒）
    if (activeTime < expire) {
        // 避免频繁调用接口  当快过期的时候刷新
      if(activeTime>=(4*expire/5)){
        let token = store.getters.refreshToken
        // 当前接口不是刷新token 和轮训资产的接口 ，且token不为空
        if (config.url.indexOf(refreshTokenUrl) ===-1 &&config.url.indexOf(requestForexFunds)===-1  && token !== "") {
          refreshToken(token)
        }
      }
    } else {
      // 在刷新之前需要先清空token
      store.commit('SET_EXPIRE_TIME', '')
      store.comiit("SET_TOKEN",'')
    }
  }
  // Do something before request is sent
  if (store.getters.token) {
    config.headers['Authorization'] = store.getters.token // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
  }


  return config
}, error => {
  // Do something with request error
  console.log("interceptor", error) // for debug
  Promise.reject(error)
})
// respone interceptor
service.interceptors.response.use(
  response => {
    const res = response.data;
    let currentLang = localStorage.getItem("lang") || "zh-CN"
    let errMsg = errorCodeMap[currentLang][res.code]
    if (res.code !== 200) {
      Message({
        message: errMsg ? errMsg : res.errmsg,
        type: 'error',
        duration: 1000
      });
      if (res.errcode === 40001) {
        router.push("/login")
      }
      return Promise.reject(res);
    } else {
      return res;
    }
  },
  error => {
    console.log('请求失败：' , error)// for debug
    if(error.response.status === 429){
      Message({
        message: "请求太频繁，请稍后再试！",
        type: 'error',
        duration: 1 * 1000
      })
    }
    return Promise.reject(error)
  })

function getExpireTime() {
  let data = store.getters.expireTime
  return (data) ? JSON.parse(data) : null;
}

function refreshToken(token) {
  commonApi.refreshToken(token).then(res => {
    let data = res.data;
    let expireTime = JSON.stringify({
      updateTime: +new Date(),
      // expire:5*1000
      expire: data.expire * 1000 
    });
    store.commit('SET_EXPIRE_TIME', expireTime)
    store.commit('SET_TOKEN', data.access_token)

  })
}

export default service
