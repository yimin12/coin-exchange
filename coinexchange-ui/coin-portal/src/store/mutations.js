import * as types from './mutation-types'

const mutations = {
  // [types.SET_ISLOGIN](state, isLogin) {
  //   state.isLogin = isLogin
  // },
  [types.SET_MOBILE](state, mobile) {
    state.mobile = mobile
    if (mobile === '') {
      sessionStorage.removeItem("mobile")
    } else {
      sessionStorage.setItem("mobile", mobile)
    }
  },
  [types.SET_USERNAME](state, username) {
    state.username = username
    if (username === '') {
      sessionStorage.removeItem("username")
    } else {
      sessionStorage.setItem("username", username)
    }
  },
  [types.SET_ISAUTH](state, isAuth) {
    state.isAuth = isAuth
    sessionStorage.setItem("isAuth", isAuth)
  },
  [types.SET_TRUENAME](state, trueName) {
    state.trueName = trueName
  },
  [types.SET_PAYPASSWORD](state, setPayPassword) {
    state.setPayPassword = setPayPassword
  },
  [types.SET_IDCARD](state, idCard) {
    state.idCard = idCard
  },
  [types.SET_INVITECODE](state, inviteCode) {
    state.inviteCode = inviteCode
    if (inviteCode === '') {
      sessionStorage.removeItem("inviteCode")
    } else {
      sessionStorage.setItem("inviteCode", inviteCode)
    }
  },
  [types.SET_IDCARDTYPE](state, idCardType) {
    state.idCardType = idCardType
  },
  [types.SET_REALNAME](state, realName) {
    state.realName = realName
  },
  [types.SET_AUTHTIME](state, authTime) {
    state.authTime = authTime
  },
  [types.SET_TOKEN](state, token) {
    state.token = token
    if (token === '') {
      sessionStorage.removeItem("token")
    } else {
      sessionStorage.setItem("token", token)
    }
  },
  [types.SET_REFRESH_TOKEN](state, token) {
    state.token = token
    if (token === '') {
      sessionStorage.removeItem("refresh_token")
    } else {
      sessionStorage.setItem("refresh_token", token)
    }
  },
  [types.SET_COUNTRYCODE](state, countryCode) {
    state.countryCode = countryCode
  },
  [types.SET_SENIOR_AUTH_STATUS](state, seniorAuthStatus) {
    state.seniorAuthStatus = seniorAuthStatus
  },
  [types.SET_SENIOR_AUTH_DESC](state, seniorAuthDesc) {
    state.seniorAuthDesc = seniorAuthDesc
  },
  [types.SET_LANG](state, lang) {
    state.lang = lang
    localStorage.setItem("lang", lang)
  },
  [types.SET_EXPIRE_TIME](state, expireTime) {
    state.expireTime = expireTime
    if (expireTime === '') {
      sessionStorage.removeItem("expireTime")
    } else {
      sessionStorage.setItem("expireTime", expireTime)
    }
  },
  [types.SET_GA_STATUS](state, gaStatus) {
    state.gaStatus = gaStatus
  },
  [types.SET_EMAIL](state, email) {
    state.email = email
    if (email === '') {
      sessionStorage.removeItem("email")
    } else {
      sessionStorage.setItem("email", email)
    }
  }
}
export default mutations
