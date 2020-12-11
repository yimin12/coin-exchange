import { datePickerOptions } from '@/utils/elementUtil'
import { formUtils } from '@/utils/formUtil'
import { commonApi } from '@/api/commonApi'

/**
 * 全局key-value映射
 */
export default {
  filters: {
    elTagFilter(status) {
      const statusMap = {
        0: 'danger',
        1: 'success',
        2: 'info',
        3: 'primary',
        4: 'warning',
      }
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        0: '错误状态',
        1: '启用',
        2: '禁用',
        true: '启用',
        false: '禁用'
      }
      return statusMap[status]
    },
    resourceTypeFilter(type) {
      const statusMap = {
        1: '主banner',
        2: '小banner'
      }
      return statusMap[type]
    },
    // 场外交易充值审核 充值来源类型 alipay，支付宝；cai1pay，财易付；bank，银联；
    rechargeTypeFilter(type) {
      const rechargeType = {
        linepay: '在线充值',
        alipay: '支付宝',
        cai1pay: '财易付',
        bank: '银联'
      }
      return rechargeType[type]
    },
    // 场外交易充值审核 状态 0-待审核；1-审核通过；2-拒绝；3-充值成功；
    rechargeStatusFilter(type) {
      const rechargeStatus = {
        0: '待审核',
        1: '审核通过',
        2: '拒绝',
        3: '充值成功'
      }
      return rechargeStatus[type]
    },
     // 场外交易充值审核 状态 0-待审核；1-审核通过；2-拒绝；3-充值成功；
     withdrawStatusFilter(type) {
      const withDrawStatus = {
        0: '待审核',
        1: '审核通过',
        2: '拒绝',
        3: '提现成功'
      }
      return withDrawStatus[type]
    },
     // 场外交易充值审核 充值来源类型 alipay，支付宝；cai1pay，财易付；bank，银联；
     businessTypeFilter(type) {
      const map = {
        recharge_into: '充值',
        withdrawals_out: '提现审核通过',
        order_create: '下单',
        order_turnover: '成交',
        order_turnover_poundage: '成交手续费',
        order_cancel: '撤单',
        register_reward: '注册奖励',
        invite_reward:'邀请奖励',
        withdrawals: '提币冻结解冻',
        recharge: '充人民币' ,
        withdrawals_poundage: '提币手续费',
        cny_btcx_exchange: '兑换' ,
        bonus_into: '奖励充值' ,
        bonus_freeze: '奖励冻结'
      }
      return map[type]
    },
    // 虚拟币提现管理列表0-审核中；1-审核通过；2-拒绝；3-提币成功；4：撤销；5-打币中；
    coninWithdrawTypeFilter(type) {
      const coninWithdrawType = {
        0: '审核中',
        1: '审核通过',
        2: '拒绝',
        3: '提币成功',
        4: '撤销',
        5: '打币中',
      }
      return coninWithdrawType[type]
    },
    // 资金流水列表收付类型1 入账 2 出账
    accountFlowDirectionFilter(type) {
      const accountFlowDirection = {
        1: '入账',
        2: '出账',
      }
      return accountFlowDirection[type]
    },
    // 币币交易列表状态 0未成交 1已成交 2已取消 4异常单
    entrustManagerListStatusFilter(type) {
      const map = {
        0: '未成交',
        1: '已成交',
        2: '已取消',
        4: '异常单',
      }
      return map[type]
    },
    // 币币交易列表交易方式 1-买入；2-卖出
    entrustManagerListTradtypeFilter(type) {
      const map = {
        1: '买入',
        2: '卖出',
      }
      return map[type]
    },
    // 创新交易列表价格类型(1 市价 2 限价)
    invovateListFilter(type) {
      const map = {
        1: '市价',
        2: '限价',
      }
      return map[type]
    },
    // 创新交易列表订单类型1 开仓 2 平仓
    invovateOrderTypeFilter(type) {
      const map = {
        1: '开仓',
        2: '平仓',
      }
      return map[type]
    },



  },
  data() {
    return {}
  },
  methods: {

  }
}
