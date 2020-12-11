export const selectOption = {
  // 类型
  type: {
    'xnb': '人民币',
    'default': '比特币系列',
    'eth': '以太坊',
    'ethToken': '以太坊代币'
  },
  // 钱包类型
  wallet_type: {
    'rgb': '认购币',
    'qbb': '钱包币'
  },
  // 状态
  status: {
    0: '禁用',
    1: '启用',
  },
  //是否自动打币
  isAuto:{
    0: "自动打币",
    1: "手动打币"
  },
  // 充值状态
  rechargeFlag: {
    0: '关闭',
    1: '开启',
  },
  // 提现状态
  withdrawFlag: {
    0: '关闭',
    1: '开启',
  },
  // 钱包归集地址状态
  adminAddressStatus: {
    1: '归账',
    2: '打款',
    3: ' 手续费',
    4: '充值'
  },
  // 合并深度
  mergeDepth: {
    0.01: 0.01,
    0.001: 0.001,
    0.0001: 0.0001,
  },
  tradeWeekData: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日',],
  mergeDepthData: ["1", "0.1", "0.01", "0.001", "0.0001", "0.00001", "0.000001", "0.0000001", "0.00000001", "0.000000001"],
  createMergeDepthData: (scale = 0, mergeDepthData) => {
    const result = [];
    let all = parseInt(scale) + 1;
    for (let i = 0; i < all; i++) {
      result[i] = mergeDepthData[i];
    }
    return result;
  }
}
