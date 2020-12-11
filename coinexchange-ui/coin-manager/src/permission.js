import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import {getToken} from '@/utils/auth' // getToken from cookie
NProgress.configure({showSpinner: false})// NProgress Configuration

// permissiom judge function
function hasPermission(roles, permissionRoles) {
  if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}

const whiteList = ['/login', '/authredirect']// no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (sessionStorage.token) { // determine if there has token
    /* has token*/
    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      // 防止直接地址栏输入路径访问页面
      // let pathId = pathIds[to.name]
      // console.log("路由to", to.name, pathId)

      // if(routerIds.indexOf(pathId) === -1){
      // next({path:'/401'})
      // alert("无权限")
      // }
      store.commit('SET_ROLES', ['admin'])
      store.commit('SET_NAME', "admin")
      store.commit('SET_AVATAR', "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
      store.commit('SET_INTRODUCTION', "我是超级管理员")
      // if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
      //   store.dispatch('GetUserInfo').then(res => { // 拉取user_info
      //     const roles = res.roles // note: roles must be a array! such as: ['editor','develop']
      // const data = res
      if(!store.getters.sideMenu){
        store.dispatch('GenerateRoutes').then(() => { // 根据roles权限生成可访问的路由表
          router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
          // next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        })

      }

      next();

      // }).catch((err) => {
      //   console.log("bug33333")
      //   store.dispatch('FedLogOut').then(() => {
      //     Message.error(err || 'Verification failed, please login again')
      //     next({ path: '/' })
      //   })
      // })
      // } else {
      //   // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
      //   if (hasPermission(store.getters.roles, to.meta.roles)) {
      //     next()//
      //   } else {
      //     next({ path: '/401', replace: true, query: { noGoBack: true }})
      //   }
      //   // 可删 ↑
      // }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next('/login') // 否则全部重定向到登录页
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
