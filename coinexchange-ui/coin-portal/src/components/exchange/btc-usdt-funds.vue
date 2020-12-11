<template>
  <div class="key-value-main">

    <div class="key-value-item">
      <span class="name">{{$t('m.exchange.bookBalance')}}</span>
      <span class="value">{{fundsData.totalAmount}}</span>
    </div>
    <!--<div class="key-value-item">-->
      <!--<span class="name">实际净值</span>-->
      <!--<span class="value">${{fundsData.actuaNetValue}}</span>-->
    <!--</div>-->
    <div class="key-value-item">
      <span class="name">{{$t('m.exchange.profit')}}</span>
      <span class="value">{{fundsData.floatProfit}}</span>
    </div>
    <div class="key-value-item">
      <span class="name">{{$t('m.exchange.totalProfit')}}</span>
      <span class="value">{{fundsData.totalProfit}}</span>
    </div>
    <div class="key-value-item">
      <span class="name">{{$t('m.exchange.occupancyMargin')}}</span>
      <span class="value">{{fundsData.lockMargin}}</span>
    </div>
    <div class="key-value-item">
      <span class="name">{{$t('m.exchange.availableMargin')}}</span>
      <span class="value">{{fundsData.balanceAmount}}</span>
    </div>

  </div>
</template>
<script>
  export default {
    props: ['fundsData'],
    data() {
      return {}
    },
    mounted() {
      console.log("子控件接收到的值", this.key);
    }
  }

</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .key-value-main {
    margin-left 10px
    font-size 12px
    color: white
    display flex
    .name {
      margin-right: 10px;
    }
    .value {
      margin-right 10px
    }
  }
</style>
