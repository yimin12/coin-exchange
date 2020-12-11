import request from '@/utils/request'

export const serviceApi = {


  /**
   * 工单列表
   */
  getWorkIssueList(form, current, size) {
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
      url: '/admin/workIssues',
      method: 'get',
      params: params
    })
  },
  /**
   *
   获取工单详情
   * @param data
   */
  getOneNotice(id) {
    return request({
      url: '/admin/workIssues/info',
      method: 'get',
      params: {id}
    })
  },

  /**
   * 更新工单
   * @param data
   */
  updateWorkIssue(form) {
    let params = {}
    params.id = form.id
    params.answer = form.answer
    return request({
      url: '/admin/workIssues',
      method: 'patch',
      params
    })
  }
}

