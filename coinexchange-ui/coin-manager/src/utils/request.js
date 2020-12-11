import axios from 'axios'
import {
  Message,
  MessageBox
} from 'element-ui'
import store from '@/store'
import {
  getToken
} from '@/utils/auth'
import {codeMap} from "@/config/constants"
// create an axios instance
const service = axios.create({
  baseURL: "/", // api的base_url
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(config => {
  // Do something before request is sent
  // if (store.getters.token) {
  if(sessionStorage.token != undefined ){
    config.headers['Authorization'] = sessionStorage.token // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
  }
  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
  Promise.reject(error)
})
service.interceptors.response.use( response => {
    const res = response.data;
    // 处理excel文件content-type: text/csv;charset=GBK
    if (response.headers && (response.headers['content-type'] === 'text/csv;charset=GBK' || response.headers['content-type'] === 'application/csv;charset=GBK')) {
      return response
    }
    if (res.errcode && res.errcode !== 0) {
      let msg = res.errmsg
      if(res.errcode === 40001){
        msg = codeMap[res.errcode]
        store.dispatch('FedLogOut').then(() => {
          location.reload();
        });
      }else if(res.errcode === 41001){
        msg = codeMap[res.errcode]
        store.dispatch('FedLogOut').then(() => {
        });
      }

      Message({
        message: msg,
        type: 'error',
        duration: 1000
      });
      return Promise.reject('error');
    } else {
      if (res.data) {
        return res.data

      } else {
        return res
      }
    }
  },
  error => {
    if(error.response.status === 403){
      MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('FedLogOut').then(() => {
          location.reload();// 为了重新实例化vue-router对象 避免bug
        });
      })
    }
    Message({
      message: error.message,
      type: 'error',
      duration: 1000
    })
    return Promise.reject(error)
  }
  ),
  // respone interceptor--2
  service.interceptors.response.use(
    response => {
      const res = response ;
      if(res.code == undefined){
        return  response;
      }
      if (res.code !== 200) {
        Message({
          message: res.msg,
          type: 'error',
          duration: 5 * 1000
        });
        // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
        if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
          MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            store.dispatch('FedLogOut').then(() => {
              location.reload();// 为了重新实例化vue-router对象 避免bug
            });
          })
        }
        return Promise.reject('error');
      } else {
        return response.data;
      }
    })

export default service
