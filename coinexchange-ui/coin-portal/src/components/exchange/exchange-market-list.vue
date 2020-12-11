<template>
  <div class="product-list-main">
    <el-tabs v-model="activeName" type="card" class="trading-recode-tab" @tab-click="handleChangeArea">
      <el-tab-pane :label="item.name" :name="item.name" v-for="(item,index) in this.exchangeMarkets" :key="index">
        <el-table
          :data="item.markets"
          row-class-name="table-row-class"
          @row-click="handleRowClick"
          height="100%"
          :default-sort="{prop: 'date', order: 'descending'}"
        >
          <el-table-column
            prop="name"
            :label="$t('m.exchange.product')"
            align="center"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="price"
            :label="$t('m.exchange.currentPrice')"
            align="center"
            width="100"
          >
          </el-table-column>
          <el-table-column
            prop="change"
            :label="$t('m.exchange.operate')"
            align="center"
            width="180"
          >
            <template slot-scope="scope">
              <div class="btn-container">
                <div class="echarts-buy" @click.stop="handleExchange(1,scope)">{{scope.row.bidPrice}}
                </div>

                <div class="echarts-sell" @click.stop="handleExchange(2,scope)">{{scope.row.askPrice}}
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

  </div>
</template>
<script>
  import { mapGetters } from 'vuex'

  export default {
    props  : ["exchangeMarkets","defaultTitle"],
    computed:{
      ...mapGetters(['token'])
    },
    data() {
      return {
        activeName:'',
        //用于弹窗实时显示价格
        marketDataIndex:{
          // 区域列表索引
          areaIndex:0,
          // 市场列表索引
          marketIndex:0,
        },

        index:-1
      }
    },
    watch:{
      defaultTitle(curVal){
        this.activeName = curVal
      },
    },
    mounted() {
    },
    methods: {
      handleChangeArea(item){
        this.marketDataIndex.areaIndex = item.index
        console.log("切换",this.exchangeMarkets[item.index].coinId)
        this.$emit("changeExchangeArea",item)
      },
      handleExchange(type, scope) {
        this.marketDataIndex.marketIndex = scope.$index
        console.log("当前对话框索引 ",scope.$index,scope.row)
        let row = scope.row
        if (!this.token) {
          this.$router.push({path: '/login', query: {redirect: "/exchange"}});
        } else {
          let dialogData = {
            marketDataIndex:this.marketDataIndex,
            type: type,
            market:row.name,
            marketId:row.marketId,
            symbol:row.symbol,
            visible:true
          }
          this.$emit('showOrderDialog', dialogData);
        }
      },

      handleRowClick(row) {
        this.bus.$emit("changeMarket", row)
      },

    }
  }

</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"

  .product-list-main {
    width 100%
    background #1b1f28
    font-size: 12px;
    border-left 1px solid #4e4f59
    .btn-container {
      display flex
      justify-content space-between
      .echarts-buy {
        color: white
        cursor pointer
        background $color-global-buy
        border-radius 2px
        text-align center
        margin-right 10px;

        &:after {
          content ""
          display inline-block
          background-image url("../../assets/exchange/up.png")
          background-position center center
          background-repeat no-repeat
          background-size 90%
          width: 17px;
          height: 15px;
        }
        &:hover {
          opacity 0.8

        }
      }
      .echarts-sell {
        color: white
        cursor pointer
        background $color-global-sell
        border-radius 2px
        text-align center
        &:after {
          content ""
          display inline-block
          background-image url("../../assets/exchange/down.png")
          background-position center center
          background-repeat no-repeat
          background-size 90%
          vertical-align middle
          width: 16px;
          height: 15px;

        }
        &:hover {
          opacity 0.8
        }
      }
      .echarts-buy,
      .echarts-sell {
        height: 23px;
        line-height: 23px;
        opacity .7
        width 80px
      }
    }
    .el-table--enable-row-hover .el-table__body tr:hover > td {
      .echarts-buy,
      .echarts-sell {
        opacity 1
      }
    }
  }
</style>
