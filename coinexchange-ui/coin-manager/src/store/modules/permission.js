import { asyncRouterMap, constantRouterMap } from '@/router'
import store from "@/store"

/**asyncRouterMap
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.indexOf(role) >= 0)
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(asyncRouterMap, roles) {
  const accessedRouters = asyncRouterMap.filter(route => {
    if (hasPermission(roles, route)) {
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, roles)
      }
      return true
    }
    return false
  })
  return accessedRouters
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: [],
    topMenu:[],
    sideMenu:null

  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    },
    SET_TOPMENU: (state, topMenu) => {
      state.topMenu = topMenu
    },

    SET_SIDE_MENU:(state,siderMenu ) => {
      state.sideMenu = siderMenu
    },

  },
  actions: {
    GenerateRoutes({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOPMENU',asyncRouterMap)
        sessionStorage.topMenu = JSON.stringify(asyncRouterMap)
        commit('SET_ROUTERS', asyncRouterMap)

        let ids = sessionStorage.ids.split(",")
        commit('SET_ROUTER_IDS',ids)
        GetFirstMenu(asyncRouterMap,ids);

        resolve()
      })
    },
    setSideMenu({ commit }, data){
      return new Promise(resolve => {
        commit('SET_SIDE_MENU',data)
        resolve()
      })
    }
  }
}
/**
 * 获取当前用户能看到的第一个菜单
 */
function GetFirstMenu(asyncRouterMap,ids) {
  let firstId = ids[0];
  for(let i = 0 ;i<asyncRouterMap.length;i++){
    let menuItem = asyncRouterMap[i]
    if(menuItem.id ===firstId ) {
      store.commit("SET_SIDE_MENU",menuItem)
    }
  }

}




export default permission
