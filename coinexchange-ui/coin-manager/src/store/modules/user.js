import {loginApi, getUserInfo} from '@/api/login'
import {getToken, setToken, removeToken} from '@/utils/auth'
import md5 from 'js-md5'

const user = {
  state: {
    user: '',
    status: '',
    code: '',
    token: getToken(),
    name: '',
    avatar: '',
    introduction: '',
    roles: [],
    setting: {
      articlePlatform: []
    },
    ids: [],

  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },
    SET_STATUS: (state, status) => {
      state.status = status
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_ROUTER_IDS: (state, ids) => {
      state.ids = ids
    },
  },

  actions: {
    // 用户名登录router-ids.json
    LoginByUsername({commit}, userInfo) {
      const username = userInfo.username.trim()
      // const password = md5(userInfo.password)
      const password = userInfo.password
      return new Promise((resolve, reject) => {
        loginApi.login(username, password).then(response => {
          console.log('登录res：', response)
          commit('SET_TOKEN', response.token)
          sessionStorage.token = response.token
          // 获取菜单数据
          const ids = response.menus.map((item) => {
            return item.id
          })
          console.log('生成的ids', ids)
          commit('SET_ROUTER_IDS', ids)
          sessionStorage.ids = ids
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({commit, state}) {
      return new Promise((resolve, reject) => {


        getUserInfo(state.token).then(response => {


          // 根据后台返回的菜单列表生成路由表
          if (!response) { // 由于mockjs 不支持自定义状态码只能这样hack
            reject('error')
          }
          const data = response
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          commit('SET_INTRODUCTION', data.introduction)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 第三方验证登录
    // LoginByThirdparty({ commit, state }, code) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_CODE', code)
    //     loginByThirdparty(state.status, state.email, state.code).then(response => {
    //       commit('SET_TOKEN', response.data.token)
    //       setToken(response.data.token)
    //       resolve()
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 登出
    LogOut({commit, state}) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        // removeToken()
        sessionStorage.removeItem("token")
        resolve()
      })
    },

    // 前端 登出
    FedLogOut({commit}) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        sessionStorage.removeItem("token")
        resolve()
      })
    },

    // 动态修改权限
    ChangeRoles({commit}, role) {
      return new Promise(resolve => {
        commit('SET_TOKEN', role)
        setToken(role)
        getUserInfo(role).then(response => {
          const data = response
          commit('SET_ROLES', data.roles)
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          commit('SET_INTRODUCTION', data.introduction)
          resolve()
        })
      })
    }
  }
}

export default user
