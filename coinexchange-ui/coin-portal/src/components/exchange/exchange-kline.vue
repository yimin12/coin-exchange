<template>
  <div class="exchange-kline-main">
    <iframe id="kline-win" scrolling="no" style="width: 100%;height: 100%"></iframe>

  </div>
</template>
<script>
  import {util} from 'common/js/util'
  import {exchangeApi} from 'api/exchange'
  import {mapGetters} from 'vuex'
  // import BtcUsdtFunds from 'components/btc-usdt-funds'

  // 测试数据

  export default {
    props: ["exchangeDialogData","fundsData"],

    data() {
      return {
        rawData      : [],
        period       : '1min',
        symbol       : 'BTC/CAD',
        currentMarket: '',
      }
    },
    computed:{
      ...mapGetters(['lang'])
    },
    created() {
      //监听产品列表切换产品的事件
      this.bus.$on("changeMarket", (currentMarket) => {
        this.currentMarket = currentMarket;
        this.initKline()
      });
      this.bus.$on("change:language", (lang)=>{
        let kline = document.getElementById('kline-win');
        lang = lang.toLowerCase();
        if(kline){
          let cw = kline.contentWindow;
          cw.chart_switch_language && cw.chart_switch_language(lang);
        }
      })
    },
    mounted() {

    },

    methods: {
      initKline() {
        let kline = document.getElementById('kline-win')
        kline.src = `../static/new_kline/forex.html?symbol=${this.currentMarket.symbol}`;
        let lang = this.lang.toLowerCase();
        if (kline.attachEvent){
          kline.attachEvent("onload", ()=>{
            kline.contentWindow.chart_switch_language(lang)
          })
        } else {
          kline.onload = ()=>{
            kline.contentWindow.chart_switch_language(lang)
          }
        }

      },
    }
  }

</script>
<style lang="stylus" rel="stylesheet/stylus">
  .exchange-kline-main {
    .time-select {
      margin-left: 10px;
      width: 100px;
      .el-input__inner {
        background-color #272A34
        border-color #272A34
        color #aaabb1
      }
      &:hover .el-input__inner {
        color #fff
      }
    }
  }

</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .exchange-kline-main {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    .echarts-item {
      flex: 1;
    }
  }
</style>
