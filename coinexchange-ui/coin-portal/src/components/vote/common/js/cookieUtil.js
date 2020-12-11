export const cookieUtil = {
  //获取cookie
  getCookie(name) {
    var arr, reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)')
    if (arr = document.cookie.match(reg))
      return (arr[2])
    else
      return null
  },
//设置cookie,增加到vue实例方便全局调用
  setCookie (c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + '=' + escape(value) + ((expiredays == null) ? '' : ';expires=' + exdate.toGMTString())
  },
  //删除cookie
  delCookie (name) {
    var exp = new Date()
    exp.setTime(exp.getTime() - 1)
    var cval = this.getCookie(name)
    if (cval != null)
      document.cookie = name + '=' + cval + ';expires=' + exp.toGMTString()
  }
}
