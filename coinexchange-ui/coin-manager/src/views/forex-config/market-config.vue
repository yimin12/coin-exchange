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
  import marketConfig from '../trade-config/component/base-market-config.vue';
  import {forexConfigApi} from "@/api/coinConfigApi";

  export default {
    components: {marketConfig},
    data() {
      return {
        // 市场类型，1：币币，2：创新
        marketType: 2,
        // 列表 API
        searchListAPI: forexConfigApi.getCoinMarketList,
        // 交易区域API
        tradeAreaSelectAPI: forexConfigApi.getTradeAreaAll,
        // 市场名称API
        coinSelectAPI: forexConfigApi.getMarketAll,
        // 报价货币/基础货币
        coinidAPI: forexConfigApi.getCoinById,
        // 可修改字段
        otherFiels: [
          {
            props: 'contractUnit',
            name: '合约单位'
          },
          {
            props: 'marginRate',
            name: '保证金比例'
          },
          /*{
            props: 'pointValue',
            name: '点'
          },*/
          {
            type: 'string',
            props: 'fxcmSymbol',
            name: '福汇API交易对'
          },
          {
            type: 'string',
            props: 'yahooSymbol',
            name: '雅虎API交易对'
          },
          {
            type: 'string',
            props: 'aliyunSymbol',
            name: '阿里云API交易对'
          },
        ],
        otherRules: {
          contractUnit: [
            {required: true, message: '请输入合约单位'},
          ],
          marginRate: [
            {required: true, message: '请输入保证金比例'},
          ],
          /*pointValue: [
            {required: true, message: '请输入点'},
          ],*/
          fxcmSymbol: [
            {required: true, message: '请输入福汇API交易对'},
          ],
          yahooSymbol: [
            {required: true, message: '请输入雅虎API交易对'},
          ],
          aliyunSymbol: [
            {required: true, message: '请输入阿里云API交易对'},
          ],
        },
        otherRuleForm: {
          contractUnit:'',
          marginRate: '',
          // pointValue: '',
          fxcmSymbol: '',
          yahooSymbol: '',
          aliyunSymbol: '',
        }
      }
    }
  }
</script>
