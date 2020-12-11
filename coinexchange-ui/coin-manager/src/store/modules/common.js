import {commonApi} from '@/api/commonApi'
import {coinConfigApi} from '@/api/coinConfigApi'

const common = {
  state: {
    coinAll: '',
    marketAll: '',
    coinTypeAll: ''
  },

  mutations: {
    SET_COINALL: (state, payload) => {
      state.coinAll = payload;
      sessionStorage.coinAll = JSON.stringify(payload);
    },
    SET_MARKETALL: (state, payload) => {
      state.marketAll = payload;
      sessionStorage.marketAll = JSON.stringify(payload);
    },

    SET_COIN_TYPE_ALL: (state, payload) => {
      state.coinTypeAll = payload;
      sessionStorage.coinTypeAll = JSON.stringify(payload);

    },
  },

  actions: {
    // 获取所有币种信息
    CoinAll({
              commit
            }, payload) {
      commonApi.getCoinAll(1).then((res) => {
        commit('SET_COINALL', res)
      })
    },
    // 所有交易市場信息
    MarketAll({
                commit
              }, payload) {
      commonApi.getMarketAll().then((res) => {
        commit('SET_MARKETALL', res)
      })
    },
    // 所有币种类型
    CoinTypeAll({commit}) {
      coinConfigApi.getCoinTypeAll(1).then((res) => {
        commit('SET_COIN_TYPE_ALL', res)
      })
    }
  },
}
export default common
