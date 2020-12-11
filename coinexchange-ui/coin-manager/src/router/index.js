import Vue from 'vue'
import Router from 'vue-router'

const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/** note: submenu only apppear when children.length>=1
 *   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
 **/

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
 **/
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/authredirect', component: _import('login/authredirect'), hidden: true},
  {path: '/404', component: _import('errorPage/404'), hidden: true},
  {path: '/401', component: _import('errorPage/401'), hidden: true},
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: _import('dashboard/index'),
      name: 'dashboard',
      meta: {title: 'dashboard', icon: 'dashboard', noCache: true}
    }]
  }
  // {
  //   path: '/documentation',
  //   component: Layout,
  //   redirect: '/documentation/index',
  //   children: [{
  //     path: 'index',
  //     component: _import('documentation/index'),
  //     name: 'documentation',
  //     meta: { title: 'documentation', icon: 'documentation', noCache: true }
  //   }
  //   ]
  // }


]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/website-operation',
    component: Layout,
    name: 'website-operation',
    // redirect: '/website-operation/article-manager',
    id: "1",
    meta: {
      title: '网站运营',
      icon: 'component',
      role: ["admin", "role"]
    },
    children: [
      {
        id: "9",
        path: 'article-manager', component: _import('website-operation/article-manager'),
        name: 'article-manager', meta: {title: '公告管理'}
      },
      {
        id: "10",
        path: 'resource-config', component: _import('website-operation/resource-config'),
        name: 'resource-config', meta: {title: '资源配置'}
      },
      {
        id: "11",
        path: 'service-list', component: _import('website-operation/service-list'),
        name: 'service-list', meta: {title: '客服工单'}
      },
    ]
  },
  {
    path: '/statistic',
    component: Layout,
    name: 'statistic',
    // redirect: '/statistic/operation-statistic/regist-statistic',
    meta: {
      title: '统计分析',
      icon: 'component'
    },
    id: "2",
    children: [
      {
        path: '/statistic/operation-statistic',
        component: _import('statistic/operation-statistic/index'),
        name: 'operation-statistic', meta: {title: "运营统计"},
        id: "12",
        children: [
          {
            path: '/statistic/operation-statistic/exchange-statistic',
            component: _import('statistic/operation-statistic/exchange-statistic/index'),
            name: 'exchange-statistic',
            meta: {title: '交易统计'},
            id: "13",
            children: [
              {
                id: "20",
                path: 'exchange-rank',
                component: _import('statistic/operation-statistic/exchange-statistic/exchange-rank'),
                name: 'exchange-rank',
                meta: {title: "交易量排行"}
              },
              // {
              //   id: "22",
              //   path: 'holds-rank',
              //   component: _import('statistic/operation-statistic/exchange-statistic/holds-rank'),
              //   name: 'holds-rank',
              //   meta: {title: "持仓排行"},
              // }
              ]
          },
          {
            path: 'regist-statistic',
            component: _import('statistic/operation-statistic/regist-statistic'),
            name: 'regist-statistic',
            meta: {title: "注册统计"},
            id: "14",
          },
          {
            path: 'login-statistic',
            component: _import('statistic/operation-statistic/login-statistic'),
            name: 'login-statistic',
            meta: {title: "登录统计"},
            id: "15",

          },
          {
            path: 'cny-recharge-statistic',
            component: _import('statistic/operation-statistic/cny-recharge-statistic'),
            name: 'cny-recharge-statistic',
            meta: {title: "GCN充值统计"},
            id: "16",

          },

          {
            path: 'cny-withdraw-statistic',
            component: _import('statistic/operation-statistic/cny-withdraw-statistic'),
            name: 'cny-withdraw-statistic',
            meta: {title: "GCN提现统计"},
            id: "17",

          },
          {
            path: 'coin-recharge-statistic',
            component: _import('statistic/operation-statistic/coin-recharge-statistic'),
            name: 'coin-recharge-statistic',
            meta: {title: "充币统计"},
            id: "18",
          },
          {
            path: 'coin-withdraw-statistic',
            component: _import('statistic/operation-statistic/coin-withdraw-statistic'),
            name: 'coin-withdraw-statistic',
            meta: {title: "提币统计"},
            id: "19",
          },
        ]
      }
    ]
  },
  {
    path: '/usercenter',
    component: Layout,
    // redirect: '/usercenter/user-manager/user-list',
    name: 'usercenter',
    meta: {
      title: '用户中心',
      icon: 'component'
    },
    id: "3",

    children: [
      {
        path: '/usercenter/user-manager',
        component: _import('usercenter/user-manager/index'),
        name: 'user-manager', meta: {title: "会员管理"},
        id: "21",

        children: [
          {
            path: 'user-list', component: _import('usercenter/user-list'),
            name: 'user-list', meta: {title: '用户列表'},
            id: "22",
          },

          {
            path: 'authentication', component: _import('usercenter/authentication'),
            name: 'authentication', meta: {title: '高级实名认证审核'},
            id: "23",
          },
          // {
          //   path: 'pool', component: _import('usercenter/pool'),
          //   name: 'pool', meta: {title: '矿池列表'},
          //   id: "24",
          // },
          {
            path: 'user-detail/:userId', component: _import('usercenter/user-detail'),
            name: 'user-detail', meta: {title: '用户详情'},
            // id:"3.1.1",
            hidden: true
          },
          {
            path: 'authentication-detail/:id',
            component: _import('usercenter/authentication-detail'),
            name: 'authentication-detail',
            meta: {title: '高级实名认证审核'},
            // id: "3.2.1",
            hidden: true
          },
        ]
      },
      // {
      //   path: '/usercenter/agent-manager',
      //   component: _import('usercenter/agent-manager/index'),
      //   redirect: '/usercenter/agent-check',
      //
      //   name: 'agent',
      //   id: "24",
      //   meta: {
      //     title: '代理商管理',
      //   },
      //   children: [
      //     {
      //       id: "27",
      //       path: 'agent-check', component: _import('usercenter/agent-manager/agent-check'),
      //       name: 'agent-check', meta: {title: '代理商注册审核'}
      //     },
      //     {
      //       id: "28",
      //       path: 'agent-list', component: _import('usercenter/agent-manager/agent-list'),
      //       name: 'agent-list', meta: {title: '代理商列表'},
      //     },
      //   ]
      // },
    ]
  },
  {
    path: '/finance',
    component: Layout,
    // redirect: '/finance/cny-recharge-examine',

    name: 'finance',
    meta: {
      title: '财务管理',
      icon: 'component'
    },
    id: "4",

    children: [
      {
        path: 'cny-recharge-examine', component: _import('finance/cny-recharge-examine'),
        name: 'cny-recharge-examine', meta: {title: '场外交易充值审核'},
        id: "25",
      },
      {
        path: 'cny-withdraw-examine', component: _import('finance/cny-withdraw-examine'),
        name: 'cny-withdraw-examine', meta: {title: '场外交易提现审核'},
        id: "26",
      },
      {
        path: 'coin-withdraw-examine', component: _import('finance/coin-withdraw-examine'),
        name: 'coin-withdraw-examine', meta: {title: '数字货币提现审核'},
        id: "27",
      }
    ]
  },
  {
    path: '/assets',
    component: Layout,
    // redirect: '/assets/account-assets',

    name: 'assets',
    meta: {
      title: '资金明细',
      icon: 'component'
    },
    id: "5",

    children: [
      {
        path: 'account-assets', component: _import('assets/account-assets'),
        name: 'account-assets', meta: {title: '账户资产'},
        id: "28",
      },

      {
        path: '/assets/exchange-record', component: _import('assets/exchange-record/index'),
        name: 'exchange-record', meta: {title: '交易记录', icon: 'example'},
        id: "29",
        children: [
          {
            path: 'entrust-manager', component: _import('assets/exchange-record/entrust-manager'),
            name: 'entrust-manager', meta: {title: '币币交易委托'},
            id: "32",
          },
          {
            path: 'turnover-record', component: _import('assets/exchange-record/turnover-record'),
            name: 'turnover-record', meta: {title: '币币交易成交'},
            id: "33",
          }
        ]
      },

      {
        path: '/assets/recharge-withdraw-record',
        component: _import('assets/recharge-withdraw-record/index'),
        name: 'recharge-withdraw-record',
        meta: {title: '充提记录', icon: 'example'},
        id: "30",

        children: [
          {
            path: 'cny-recharge-record',
            component: _import('assets/recharge-withdraw-record/cny-recharge-record'),
            name: 'cny-recharge-record',
            meta: {title: 'GCN充值'},
            id: "34",


          },
          {
            path: 'cny-withdraw-record',
            component: _import('assets/recharge-withdraw-record/cny-withdraw-record'),
            name: 'cny-withdraw-record',
            meta: {title: 'GCN提现'},
            id: "35",


          },
          {
            path: 'coin-recharge-record',
            component: _import('assets/recharge-withdraw-record/coin-recharge-record'),
            name: 'coin-recharge-record',
            meta: {title: '充币记录'},
            id: "36",


          },
          {
            path: 'coin-withdraw-record',
            component: _import('assets/recharge-withdraw-record/coin-withdraw-record'),
            name: 'coin-withdraw-record',
            meta: {title: '提币记录'},
            id: "37",

          }
        ]
      },
      {
        path: 'assets-flow', component: _import('assets/assets-flow'),
        name: 'assets-flow', meta: {title: '资金流水'},
        id: "31",
      },

    ]
  },
  {
    path: '/trade-config', component: Layout,
    // redirect: '/trade-config/coin-type-config',

    name: 'trade-config', meta: {title: '币币交易参数', icon: 'component'},
    id: "6",

    children: [
      {
        path: 'trade-area-config', component: _import('trade-config/trade-area-config'),
        name: 'trade-area-config', meta: {title: '交易区域配置'},
        id: "38",
      },
      {
        path: 'coin-type-config', component: _import('trade-config/coin-type-config'),
        name: 'coin-type-config', meta: {title: '币种类型配置'},
        id: "39"
      },
      {
        path: 'coin-config', component: _import('trade-config/coin-config'),
        name: 'coin-config_params', meta: {title: '币种配置'},
        id: "40",
      },
      {
        path: 'market-config', component: _import('trade-config/market-config'),
        name: 'market-config_params', meta: {title: '市场配置'},
        id: "41",
      },
      {
        path: '/trade-config/coin-config/coin-detail/:type/:id',
        component: _import('trade-config/coin-detail'),
        name: 'coin-detail',
        meta: {title: '币种配置详情', icon: 'component'},
        hidden: true
      },
    ]
  },
  {
    path: '/normal-config', component: Layout,
    // redirect: '/normal-config/bankcard-manager',

    name: 'normal-config', meta: {title: '参数配置', icon: 'component'},
    id: "7",

    children: [
      {
        path: 'bankcard-manager', component: _import('normal-config/bankcard-manager'),
        name: 'bankcard-manager', meta: {title: '公司银行卡管理'},
        id: "42",
      },
      {
        path: 'cny-recharge-config', component: _import('normal-config/cny-recharge-config'),
        name: 'cny-recharge-config', meta: {title: '系统参数配置'},
        id: "43",
      }
    ]
  },
  {
    path: '/system-config', component: Layout,
    // redirect: '/system-config/authority-manager/power-config',

    name: 'system-config', meta: {title: '系统管理', icon: 'component'},
    id: "8",
    children: [
      //todo
      // {
      //   path: 'system-log', component: _import('system-config/system-log'),
      //   name: 'system-log', meta: {title: '短信管理'},
      //   id: "43",
      // },
      {
        path: '/system-config/authority-manager',
        component: _import('system-config/authority-manager/index'),
        name: 'authority-manager',
        meta: {title: '权限管理'},
        id: "45",
        children: [
          {
            path: 'power-config',
            component: _import('system-config/authority-manager/power-config'),
            name: 'power-config',
            meta: {title: '功能权限配置'},
            id: "47"
          },
          {
            path: 'role-manager',
            component: _import('system-config/authority-manager/role-manager'),
            name: 'role-manager',
            meta: {title: '角色管理'},
            id: "48"
          },
          {
            path: 'role-config/:id',
            component: _import('system-config/authority-manager/role-config'),
            name: 'role-config',
            meta: {title: '配置角色'},
            hidden: true
          },
          {
            path: 'employee-manager',
            component: _import('system-config/authority-manager/employee-manager'),
            name: 'employee-manager',
            meta: {title: '员工管理'},
            id: "49"
          }
        ]
      },
      {
        path: 'system-log', component: _import('system-config/system-log'),
        name: 'system-log', meta: {title: '系统日志'},
        id: "46"
      }
    ]
  },
]
