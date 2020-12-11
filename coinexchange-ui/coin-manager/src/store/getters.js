const getters = {
  sidebar: state => state.app.sidebar,
  language: state => state.app.language,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => sessionStorage.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  introduction: state => state.user.introduction,
  status: state => state.user.status,
  roles: state => state.user.roles,
  setting: state => state.user.setting,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
  errorLogs: state => state.errorLog.logs,

  // 顶部菜单

  // topMenu:state => state.permission.topMenu || sessionStorage.topMenu ? JSON.parse(sessionStorage.topMenu) : '',

  topMenu:(state) => {
    console.log(state.permission)
    return  state.permission.topMenu || sessionStorage.topMenu ? JSON.parse(sessionStorage.topMenu) : ''
  },
  ids:state => state.user.ids,
  sideMenu:state => state.permission.sideMenu,

  // 获取所有币种类型
  getCoinTypeAll:(state) =>{
    return state.common.coinTypeAll || sessionStorage.coinTypeAll ? JSON.parse(sessionStorage.coinTypeAll) : '';
  },
  // 获取所有币种信息
  getCoinAll:(state) => {
    return state.common.coinAll || sessionStorage.coinAll ? JSON.parse(sessionStorage.coinAll) : '';
  },
  // 获取所有交易市场信息
  getMarketAll:(state) => {
    return state.common.marketAll || sessionStorage.marketAll ? JSON.parse(sessionStorage.marketAll) : '';
  }
}
export default getters
