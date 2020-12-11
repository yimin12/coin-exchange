import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import { cookieUtil } from 'common/js/cookieUtil'

Vue.use(Router)

const Home       = (resolve) => {
  import('components/home/home').then((module) => {
    resolve(module)
  })
}
const Protocol       = (resolve) => {
  import('components/protocol/protocol').then((module) => {
    resolve(module)
  })
}
const NoticeList       = (resolve) => {
  import('components/page/notice/notice-list').then((module) => {
    resolve(module)
  })
}
const NoticeDetail       = (resolve) => {
  import('components/page/notice/notice-detail').then((module) => {
    resolve(module)
  })
}
const Trade      = (resolve) => {
  import('components/trade/trade').then((module) => {
    resolve(module)
  })
}
//个人中心
const Usercenter = (resolve) => {
  import('components/usercenter/usercenter').then((module) => {
    resolve(module)
  })
}

//个人中心 身份认证
const UsercenterModifyId = (resolve) => {
  import('components/usercenter/modify-id').then((module) => {
    resolve(module)
  })
}
//个人中心 身份认证 高级
const UsercenterModifyIdSenior = (resolve) => {
  import('components/usercenter/modify-id-senior').then((module) => {
    resolve(module)
  })
}
//个人中心 手机认证
const UsercenterModifyPhone = (resolve) => {
  import('components/usercenter/modify-phone').then((module) => {
    resolve(module)
  })
}
//个人中心 密码修改
const UsercenterModifyPassword = (resolve) => {
  import('components/usercenter/modify-password').then((module) => {
    resolve(module)
  })
}
//个人中心 交易密码修改
const UsercenterModifyPasswordTrading = (resolve) => {
  import('components/usercenter/modify-password-trading').then((module) => {
    resolve(module)
  })
}
//交易密码设置
const UsercenterSetPasswordTrading = (resolve) => {
  import('components/usercenter/set-password-trading').then((module) => {
    resolve(module)
  })
}
//个人中心 设置谷歌验证
const UsercenterSetPhoneGoogle = (resolve) => {
  import('components/usercenter/set-google-auth').then((module) => {
    resolve(module)
  })
}
//个人中心 修改谷歌验证
const UsercenterModifyPhoneGoogle = (resolve) => {
  import('components/usercenter/modify-google-auth').then((module) => {
    resolve(module)
  })
}
//个人中心 邮箱验证
const UsercenterModifyMail = (resolve) => {
  import('components/usercenter/modify-mail').then((module) => {
    resolve(module)
  })
}
//个人中心 邀请奖励
const UsercenterInviteReward = (resolve) => {
  import('components/usercenter/invite-reward').then((module) => {
    resolve(module)
  })
}


const Finance    = (resolve) => {
  import('components/finance/finance').then((module) => {
    resolve(module)
  })
}
const BtcxToCny  = (resolve) => {
  import('components/finance/btcx-to-cny').then((module) => {
    resolve(module)
  })
}
const CnyManager = (resolve) => {
  import('components/finance/cny-manager').then((module) => {
    resolve(module)
  })
}
const C2C = (resolve) => {
  import('components/c2c/c2c').then((module) => {
    resolve(module)
  })
}
const AddressManager = (resolve) => {
  import('components/finance/address-manager').then((module) => {
    resolve(module)
  })
}

const EntrustRecord      = (resolve) => {
  import('components/order/entrust-record').then((module) => {
    resolve(module)
  })
}
const TurnoverRecord      = (resolve) => {
  import('components/order/turnover-record').then((module) => {
    resolve(module)
  })
}
const RechargeRecord      = (resolve) => {
  import('components/order/recharge-record').then((module) => {
    resolve(module)
  })
}
const WithdrawRecord      = (resolve) => {
  import('components/order/withdraw-record').then((module) => {
    resolve(module)
  })
}
const Login      = (resolve) => {
  import('components/login/login').then((module) => {
    resolve(module)
  })
}
const Regist   = (resolve) => {
  import('components/login/regist').then((module) => {
    resolve(module)
  })
}
const EmailRegist   = (resolve) => {
  import('components/login/emailRegist').then((module) => {
    resolve(module)
  })
}
const ForgetPassword   = (resolve) => {
  import('components/login/forget-password').then((module) => {
    resolve(module)
  })
}
const ForgetPasswordNext   = (resolve) => {
  import('components/login/forget-password-next').then((module) => {
    resolve(module)
  })
}
const ForgetTradePassword   = (resolve) => {
  import('components/usercenter/forget-trade-password').then((module) => {
    resolve(module)
  })
}
const ForgetTradePasswordNext   = (resolve) => {
  import('components/usercenter/forget-trade-password-next').then((module) => {
    resolve(module)
  })
}
//完善用户信息
const AddInfo = (resolve) => {
  import('components/usercenter/complete-userinfo').then((module) => {
    resolve(module)
  })
}
const Pool = (resolve) => {
  import('components/usercenter/pool').then((module) => {
    resolve(module)
  })
}

const VoteLayout = (resolve) => {
  import('components/vote/layout').then((module) => {
    resolve(module)
  })
}
const VoteHome = (resolve) => {
  import('components/vote/home').then((module) => {
    resolve(module)
  })
}
const VoteHelp = (resolve) => {
  import('components/vote/help').then((module) => {
    resolve(module)
  })
}
// 外汇
const Exchange = (resolve) => {
  import('components/exchange/exchange').then((module) => {
    resolve(module)
  })
}

const router = new Router({
  routes: [
    {
      path    : '/',
      redirect: '/home',
    },
    {
      path     : '/home',
      component: Home,
      // children: [
      //   {
      //     path: ':id',
      //     component: Disc
      //   }
      // ]
    },
    {
      path     : '/protocol',
      component: Protocol,
    },
    {
      path     : '/s/notice',
      component: NoticeList,
    },
    {
      path     : '/s/notice/notice-detail',
      component: NoticeDetail,
      children: [
        {
          path: '/s/notice/notice-detail/:id',
          component: NoticeDetail
        }
      ]
    },
    {
      path     : '/trade',
      component: Trade,
      children: [
        {
          path: '/trade/:symbol',
          component: Trade
        }
      ]
    },
    {
      path     : '/c2c',
      meta: {
        requireAuth: true,
      },
      component: C2C,
    },
    //用户中心
    {
      path     : '/usercenter',
      meta: {
        requireAuth: true,
      },
      component: Usercenter,
    },
    {
      path    : '/usercenter/modify-id',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyId,
    },
    {
      path    : '/usercenter/modify-id-senior',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyIdSenior,
    },
    {
      path    : '/usercenter/modify-phone',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyPhone,
    },
    {
      path    : '/usercenter/modify-password',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyPassword,
    },
    {
      path    : '/usercenter/modify-password-trading',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyPasswordTrading,
    },
    {
      path    : '/usercenter/set-password-trading',
      meta: {
        requireAuth: true,
      },
      component: UsercenterSetPasswordTrading,
    },
    {
      path    : '/usercenter/set-google-auth',
      meta: {
        requireAuth: true,
      },
      component: UsercenterSetPhoneGoogle,
    },
    {
      path    : '/usercenter/modify-google-auth',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyPhoneGoogle,
    },
    {
      path    : '/usercenter/modify-mail',
      meta: {
        requireAuth: true,
      },
      component: UsercenterModifyMail,
    },

    {
      path    : '/usercenter/invite-reward',
      meta: {
        requireAuth: true,
      },
      component: UsercenterInviteReward,
    },

    {
      path     : '/finance',
      meta: {
        requireAuth: true,
      },
      component: Finance,
    },
    {
      path     : '/finance/btcx-to-cny',
      meta: {
        requireAuth: true,
      },
      component: BtcxToCny,
    },
    // 改版了
    // {
    //   path     : '/finance/cny-manager',
    //   meta: {
    //     requireAuth: true,
    //   },
    //   component: CnyManager,
    // },

    {
      path     : '/finance/cny-manager',
      meta: {
        requireAuth: true,
      },
      component: CnyManager,
    },
    {
      path     : '/finance/address-manager',
      meta: {
        requireAuth: true,
      },
      component: AddressManager,
      children: [
        {
          path: '/finance/address-manager/:id',
          component: AddressManager
        }
      ]
    },

    // 委托记录 entrust-record
    {
      path     : '/order/entrust-record',
      meta: {
        requireAuth: true,
      },
      component: EntrustRecord
    },
    // 成交记录 turnover-order
    {
      path     : '/order/turnover-record',
      meta: {
        requireAuth: true,
      },
      component: TurnoverRecord
    },

    // 充值记录 recharge-record
    {
      path     : '/order/recharge-record',
      meta: {
        requireAuth: true,
      },
      component: RechargeRecord,
      children: [
        {
          path: '/order/recharge-record/:id',
          component: RechargeRecord
        }
      ]
    },
    // 提现记录 withdraw-record
    {
      path     : '/order/withdraw-record',
      meta: {
        requireAuth: true,
      },
      component: WithdrawRecord,
      children: [
        {
          path: '/order/withdraw-record/:id',
          component: WithdrawRecord
        }
      ]
    },
    //登录
    {
      path     : '/login',
      component: Login,
      children: [
        {
          path: '/login/:vote',
          component: Login
        }
      ]
    },
    //注册
    {
      path     : '/regist',
      component: Regist,
      children: [
        {
          path: '/regist/:inviteCode',
          component: Regist
        }
      ]
    },
    //邮箱注册
    {
      path     : '/emailregist',
      component: EmailRegist,
      children: [
        {
          path: '/emailregist/:inviteCode',
          component: EmailRegist
        }
      ]
    },
    //忘记密码
    {
      path     : '/forget-password',
      component: ForgetPassword,
    },
    //忘记密码下一步 过时
    {
      path     : '/forget-password-next',
      component: ForgetPasswordNext,
      children: [
        {
          path: '/forget-password-next/:id',
          component: ForgetPasswordNext
        }
      ]
    },
    //忘记交易密码
    {
      path     : '/forget-trade-password',
      component: ForgetTradePassword,
      meta: {
        requireAuth: true,
      },
    },
    //忘记交易密码下一步  过时了
    {
      path     : '/forget-trade-password-next',
      component: ForgetTradePasswordNext,
      meta: {
        requireAuth: true,
      },
      children: [
        {
          path: '/forget-trade-password-next/:id',
          component: ForgetTradePasswordNext
        }
      ]
    },
    //完善用户信息
    {
      path    : '/usercenter/complete-userinfo',
      meta: {
        requireAuth: true,
      },
      component: AddInfo,
    },
    // {
    //   path    : '/usercenter/pool',
    //   meta: {
    //     requireAuth: true,
    //   },
    //   component: Pool,
    // },
    {
      path    : '/vote',
      meta: {
        requireAuth: true,
      },
      component: VoteLayout,
      children: [
        {
          path: '/',
          name:'vote',
          meta: {
            requireAuth: true,
          },
          component: VoteHome
        },
        {
          path: 'help',
          component: VoteHelp
        }
      ]
    },

    //外汇
    {
      path    : '/exchange',
      component: Exchange,
    },
    {
      path    : '/s/about',
      component : (resolve) => {
        import('components/page/about').then((module) => {
          resolve(module)
        })
      }
    },
    {
      path    : '/s/help',
      component : (resolve) => {
        import('components/page/help').then((module) => {
          resolve(module)
        })
      }
    },
    {
      path    : '/s/rate',
      component : (resolve) => {
        import('components/page/rate').then((module) => {
          resolve(module)
        })
      }
    },
    {
      path    : '/s/term',
      component : (resolve) => {
        import('components/page/term').then((module) => {
          resolve(module)
        })
      }
    },
    {
      path    : '/s/api',
      component : (resolve) => {
        import('components/page/api').then((module) => {
          resolve(module)
        })
      }
    },
    {
      path    : '/s/apply',
      component : (resolve) => {
        import('components/page/apply').then((module) => {
          resolve(module)
        })
      }
    },
    {
      path    : '/s/order',
      component : (resolve) => {
        import('components/page/order').then((module) => {
          resolve(module)
        })
      }
    }
  ]
});
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
    if (store.getters.token) {  // 通过vuex state获取当前的token是否存在
      next();
    }
    else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  }
  else {
    next();
  }
})
export default router;
