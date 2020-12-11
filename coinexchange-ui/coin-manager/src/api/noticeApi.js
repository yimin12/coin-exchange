import request from '@/utils/request'

//   fetchList(query) {
//   return request({
//     url: '/article/list',
//     method: 'get',
//     params: query
//   })
// }
export const noticeApi = {
  /**
   * 新增公告
   * @param data
   */
  createNotice(data) {
    return request({
      url: '/admin/notices',
      method: 'post',
      data
    })
  },

  /**
   * 删除系统资讯公告信息
   * @param ids
   */
  deleteNotice(data) {
    return request({
      url: '/admin/notices/delete',
      method: 'post',
      data
    })
  },
  updateStatus(id,status){
    return request({
      url:'/admin/notices/updateStatus',
      method:'post',
      params:{id,status}
    })
  },

  /**
   * 系统资讯公告信息列表
   */
  getNoticeList(form, current, size) {
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
    params.current = current
    params.size = size
    console.log("结果", params)
    return request({
      url: '/admin/notices',
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
      url: '/admin/notices',
      method: 'get',
      params: {id}
    })
  },

  /**
   * 更新公告
   * @param data
   */
  updateNotice(data) {
    return request({
      url: '/admin/notices',
      method: 'patch',
      data
    })
  }
}

