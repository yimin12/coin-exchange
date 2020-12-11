export const mobile = state => {
  return state.mobile || sessionStorage.getItem("mobile")
}
export const username = state => {
  return state.username || sessionStorage.getItem("username")
}
export const isAuth = state => state.isAuth
export const trueName = state => state.trueName
export const setPayPassword = state => state.setPayPassword
export const inviteCode = state => {
  return state.inviteCode || sessionStorage.getItem("inviteCode")
}
export const idCard = state => state.idCard
export const idCardType = state => state.idCardType
export const realName = state => state.realName
export const authTime = state => state.authTime
export const token = state => {
  return state.token|| sessionStorage.getItem("token")
}
export const expireTime = state => {
  return state.expireTime || sessionStorage.getItem("expireTime")
}
export const countryCode = state => state.countryCode
export const seniorAuthStatus = state => state.seniorAuthStatus
export const seniorAuthDesc = state => state.seniorAuthDesc
export const lang = state => {
  return state.lang || localStorage.getItem("lang")||'zh-CN'
}
export const gaStatus = state => state.gaStatus
export const email = state => {
  return state.email || sessionStorage.getItem("email")
}

