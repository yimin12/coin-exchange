import gtInit from './geetest.gt'
import request from "./request";

export default {
  getGtCaptcha (uuid) {
    if(!uuid){
      uuid = 'liang'
    }
    let url = '/user/gt/register';
    return request({
      method: 'get',
      url: url,
      params: {uuid} 
    })
  },
}
