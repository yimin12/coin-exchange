export const formUtils = {
  // 处理时间段选择
  formDateRange(params,form){
    for (let item in form) {
      if (form[item]) {
        // 处理选择时间区域
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
  }
}
