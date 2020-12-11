import Cookies from 'js-cookie'

const TokenKey = 'Authorization'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setUsername(username) {
  return Cookies.set(TokenKey,username)
}

export function getUsername(token) {
  return Cookies.get(TokenKey, token)
}
