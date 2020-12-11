<template>
  <market-config
    :marketType="marketType"
    :searchListAPI="searchListAPI"
    :tradeAreaSelectAPI="tradeAreaSelectAPI"
    :coinSelectAPI="coinSelectAPI"
    :otherRules="otherRules"
    :otherRuleForm="otherRuleForm"
    :otherFiels="otherFiels"
    :coinidAPI="coinidAPI"
  />
</template>
<script>
  import marketConfig from './component/base-market-config.vue';
  import {marketApi} from "@/api/coinConfigApi";

  export default {
    components: {marketConfig},
    data() {
      return {
        // 市场类型，1：币币，2：创新
        marketType: 1,
        // 列表 API
        searchListAPI: marketApi.getCoinMarketList,
        // 交易区域API
        tradeAreaSelectAPI: marketApi.getTradeAreaAll,
        // 市场名称API
        coinSelectAPI: marketApi.getMarketAll,
        // 报价货币/基础货币
        coinidAPI: marketApi.getCoinAll,
        // 可修改字段
        otherFiels: [
          {
            props: 'tradeMin',
            name: '最小成交额'
          },
          {
            props: 'tradeMax',
            name: '最大成交额'
          },

        ],
        otherRules: {
          tradeMin: [
            {required: true, message: '请输入单笔最小成交额'},
          ],
          tradeMax: [
            {required: true, message: '请输入单笔最大成交额'},
          ],
        },
        otherRuleForm: {
          tradeMin: '',
          tradeMax: '',
        }
      }
    }
  }
</script>
