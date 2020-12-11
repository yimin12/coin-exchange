import {param2Obj} from '@/utils'

const userMap = {
  admin: {
    roles: ['admin'],
    token: 'admin',
    introduction: '我是超级管理员',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin',
    menus: [
      {
        "name": "网站运营",
        "childs": [
          {
            "name": "公告管理",
          },
          {
            "name": "文章类型",
          },
          {
            "name": "资源配置",
          },
          {
            "name": "客服工单",
          }
        ]
      },
      {
        "name": "统计分析",
        "childs": [
          {
            "name": "运营统计",
            "childs": [
              {
                "name": "注册统计",
              },
              {
                "name": "登录统计",
              },
              {
                "name": "场外交易充值统计",
              },
              {
                "name": "场外交易提现统计",
              },
              {
                "name": "充币统计",
              },
              {
                "name": "提现统计",
              }
            ]
          },
          {
            "name": "交易统计",
            "childs": [
              {
                "name": "交易量排行",
              },
              {
                "name": "持仓排行",
              }
            ]
          }
        ]
      },
      {
        "name": "用户管理",
        "childs": [
          {
            "name": "用户管理",
          },
          {
            "name": "实名认证审核",
          }
        ],
      },
      {
        "name": "财务管理",
        "childs": [
          {
            "name": "场外交易充值审核",
          },
          {
            "name": "场外交易提现审核",
          },
          {
            "name": "数字货币提现审核",
          }
        ],
      },
      {
        "name": "资金明细",
        "childs": [
          {
            "name": "账户资产",
          },
          {
            "name": "交易记录",

            "childs": [
              {
                "name": "委托管理",
              },
              {
                "name": "成交记录",
              }
            ]
          },
          {
            "name": "充提记录",
            "childs": [
              {
                "name": "场外交易充值",
              },
              {
                "name": "场外交易提现",
              },
              {
                "name": "充币记录",
              },
              {
                "name": "提币记录",
              }
            ]
          },
          {
            "name": "资金流水",
          }
        ],
      },
      {
        "name": "币币交易参数",
        "childs": [
          {
            "name": "币种配置",
          },
          {
            "name": "交易市场",
          },
        ],
      },
      {
        "name": "参数配置",
        "childs": [
          {
            "name": "公司银行卡管理",
          },
          {
            "name": "合作渠道管理",
          },
          {
            "name": "场外交易充值配置",
          },
          {
            "name": "场外交易提现配置",
          },
          {
            "name": "分佣奖励",
          },
        ],
      },
      {
        "name": "创新交易参数",
        "childs": [
          {
            "name": "币种配置",
          },
          {
            "name": "交易市场",
          }
        ],
      },
      {
        "name": "系统配置",
        "childs": [
          {
            "name": "权限管理",
            "childs": [
              {
                "name": "组织机构管理",
              },
              {
                "name": "角色管理",
              },
              {
                "name": "员工管理",
              },
              {
                "name": "流程配置",
              }
            ]
          },
          {
            "name": "系统日志",
          },
        ],
      },
    ]
  },
  editor: {
    roles: ['editor'],
    token: 'editor',
    introduction: '我是编辑',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}

export default {
  loginByUsername: config => {
    console.log("config",config)
    // "{"username":"admin","password":"1111111"}"
    const {username} = JSON.parse(config.body)
    let data = {}
    data.data =userMap[username]
    data.errcode = 0;
    return data
  },
  // getUserInfo: config => {
  //   const { token } = param2Obj(config.url)
  //   if (userMap[token]) {
  //     return userMap[token]
  //   } else {
  //     return false
  //   }
  // },
  getUserInfo: config => {
    const {token} = param2Obj(config.url)
    let data = {}
    data.data =userMap[token]
    data.errcode = 0;
    if (userMap[token]) {
      return data
    } else {
      return false
    }
  },


  logout: () => 'success'
}
