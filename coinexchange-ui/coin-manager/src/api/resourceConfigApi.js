import request from '@/utils/request'

//   fetchList(query) {
//   return request({
//     url: '/article/list',
//     method: 'get',
//     params: query
//   })
// }
export const resourceConfigApi = {
  /**
   * 新增资源配置
   * @param data
   */
  createWebConfig(data) {
    if(!data.status){
      data.status = 0;
    }
    return request({
      url: '/admin/webConfigs',
      method: 'post',
      data
    })
  },

  /**
   * 删除资源配置信息
   * @param ids
   */
  deleteWebConfig(ids) {
    return request({
      url: '/admin/webConfigs/delete',
      method: 'post',
      data: ids
    })
  },

  /**
   *网站配置信息列表
   */
  getWebConfigList(form, current, size) {
    let params = {}
    params.name = form.name
    if(form.type != -1){
      params.type = form.type
    }
    params.current = current
    params.size = size
    console.log("params",params)
    return request({
      url: '/admin/webConfigs',
      method: 'get',
      params: params
    })
  },
  /**
   *
   获取一个系统资讯公告信息
   * @param data
   */
  getOneNotice(id) {
    return request({
      url: '/notice/getOneObj',
      method: 'get',
      params: {id}
    })
  },

  /**
   * 更新配置信息
   * @param data
   */
  updateWebConfig(data) {
    return request({
      url: '/admin/webConfigs',
      method: 'patch',
      data
    })
  }
}

