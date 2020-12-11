import request from '@/utils/request'

//   fetchList(query) {
//   return request({
//     url: '/article/list',
//     method: 'get',
//     params: query
//   })
// }

// 币币交易参数 --> 币种配置
export const coinConfigApi = {

  // 获取币币交易参数列表
  getCoinList(form, current = 1, size = 10) {
    let params = {}
    for (let item in form) {
      if (form[item]) {
        params[item] = form[item]
      }
    }
    params.current = current
    params.size = size
    return request({
      url: '/finance/coins',
      method: 'get',
      params: params
    })
  },

  // 启用/禁用 币种 1:启用；2:禁用
  setStatus(data) {
    return request({
      url: '/finance/coins/setStatus',
      method: 'post',
      data,
    })
  },


  // 获取币种信息
  getCoin(id) {
    return request({
      url: `/finance/coins/info/${id}`,
      method: 'get',
    })
  },

  // 新增币种信息
  addCoin(data) {
    return request({
      url: '/finance/coins',
      method: 'post',
      data
    })
  },

  // 修改币种信息
  editCoin(data) {
    return request({
      url: '/finance/coins',
      method: 'patch',
      data
    })
  },


  // 获取钱包配置信息
  getCoinConfig(id) {
    return request({
      url: `/finance/coinConfigs/info/${id}`,
      method: 'get',
    })
  },

  // 新增钱包配置
  addCoinConfig(data) {
    return request({
      url: '/finance/coinConfigs',
      method: 'post',
      data
    })
  },

  // 修改钱包归集地址
  editCoinConfig(data) {
    return request({
      url: '/finance/coinConfigs',
      method: 'patch',
      data
    })
  },

  // 钱包归集提币地址表格
  getAdminAddressByCoinId(id, current = 1, size = 10) {
    let params = {
      coinId: id,
      current,
      size,
    };
    return request({
      url: `/finance/adminAddress`,
      method: 'get',
      params,
    })
  },

  // 钱包归集地址类型
  getAdminAddresstype() {
    return request({
      url: '/finance/coins/allQbb'
    })
  },

  // 新增钱包归集地址
  addAdminAddress(data) {
    return request({
      url: '/finance/adminAddress',
      method: 'post',
      data,
    })
  },
  // 修改钱包归集地址
  editAdminAddress(data) {
    return request({
      url: '/finance/adminAddress',
      method: 'patch',
      data,
    })
  },
  // 获取所有币种类型
  getCoinTypeAll(status = 1) {
    return request({
      url: '/finance/coinTypes/all',
      method: 'get',
      params: {status}
    })
  },
  // 获取币种类型列表
  getCoinType(form, current, size) {
    let params = {...form};
    params.current = current
    params.size = size
    return request({
      url: '/finance/coinTypes',
      method: 'get',
      params
    })
  },
  // 获取所有币种类型
  deleteCoinType(data) {
    return request({
      url: '/finance/coinTypes/delete',
      method: 'post',
      data
    })
  },
  // 新增币种类型
  createCoinType(data) {
    return request({
      url: '/finance/coinTypes',
      method: 'post',
      data
    })
  },
  //更新币种类型
  updateCoinType(data) {
    return request({
      url: '/finance/coinTypes',
      method: 'patch',
      data
    })
  },
  //设置币种类型状态
  setCoinTypeStatus(data) {
    return request({
      url: '/finance/coinTypes/setStatus',
      method: 'post',
      data
    })
  },
}

// 币币交易参数 --> 交易市场
export const marketApi = {

  // 获取币币交易市场列表
  getCoinMarketList(form, current = 1, size = 10) {
    let params = {...form};
    params.current = current
    params.size = size
    return request({
      url: '/exchange/markets',
      method: 'get',
      params,
    })
  },

  // 启用/禁用 币种 1:启用；2:禁用
  setStatus(data) {
    return request({
      url: '/exchange/markets/setStatus',
      method: 'post',
      data,
    })
  },

  // 获取所有币币交易市场名称
  getMarketAll() {
    return request({
      url: '/exchange/markets/all',
      method: 'get',
    })
  },

  // 获取所有币币交易币种
  getCoinAll(status = 1) {
    return request({
      url: '/finance/coins/all',
      method: 'get',
      params: {status,}
    })
  },

  // 获取所有币币交易区域
  getTradeAreaAll(status = 1) {
    return request({
      url: '/exchange/tradeAreas/all',
      method: 'get',
      params: {status}
    })
  },

  // 新增币种
  addMarket(data) {
    return request({
      url: '/exchange/markets',
      method: 'post',
      data,
    })
  },

  // 修改币种
  editMarket(data) {
    return request({
      url: '/exchange/markets',
      method: 'patch',
      data
    })
  },

};

// 创新交易
export const forexConfigApi = {

  // 获取创新交易 --> 币种配置列表
  getForexCoinList(form, current = 1, size = 10) {
    let params = {}
    for (let item in form) {
      if (form[item]) {
        params[item] = form[item]
      }
    }
    params.current = current
    params.size = size
    return request({
      url: '/forexCoin',
      method: 'get',
      params: params
    })
  },

  // 获取创新交易 --> 市场配置列表
  getCoinMarketList(form, current = 1, size = 10) {
    let params = {...form}
    params.current = current
    params.size = size
    return request({
      url: '/market/forex',
      method: 'get',
      params
    })
  },

  // 启用/禁用 币种 1:启用；2:禁用
  setStatus(data) {
    return request({
      url: '/forexCoin/setStatus',
      method: 'post',
      data,
    })
  },

  // 获取所有创新交易市场名称
  getMarketAll() {
    return request({
      url: '/market/forex/all',
      method: 'get',
    })
  },

  // 获取所有创新交易币种
  getCoinAll(status = 1) {
    return request({
      url: '/forexCoin/all',
      method: 'get',
      params: {status}
    })
  },

  getCoinById(id, status = 1) {
    return request({
      url: `/forexCoin/load`,
      method: 'get',
      params: {
        tradeAreaId: id,
        status,
      }
    })
  },

  // 获取所有创新交易区域
  getTradeAreaAll(status = 1) {
    return request({
      url: '/forex/area/all',
      method: 'get',
      params: {status}
    })
  },

  // 新增币种
  addForexCoin(data) {
    return request({
      url: '/forexCoin',
      method: 'post',
      data,
    })
  },

  // 修改币种
  editForexCoin(data) {
    return request({
      url: '/forexCoin',
      method: 'put',
      data,
    })
  },
};
