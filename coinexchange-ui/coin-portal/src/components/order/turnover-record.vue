<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.navheader.turnoverRecord')">
    <NavHeader activeTab="4"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <div class="ucenter-main no-sidebar">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.navheader.turnoverRecord')}}</h3>
            <div class="right-bar">
              <el-select v-model="value1" size="small" :placeholder="$t('m.trade.market')"
                         @change="onMarketChange">
                <el-option :label="$t('m.all')" :value="0">{{$t('m.all')}}</el-option>
                <el-option
                  v-for="item in marketSelectOptions"
                  :key="item.key"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-select v-model="value2" size="small" :placeholder="$t('m.trade.entrustType')"
                         @change="onTradeTypeChange">
                <el-option :label="$t('m.all')" :value="0">{{$t('m.all')}}</el-option>
                <el-option :label="$t('m.trade.buy')" :value="1">{{$t('m.trade.buy')}}</el-option>
                <el-option :label="$t('m.trade.sell')" :value="2">{{$t('m.trade.sell')}}</el-option>
                <!--<el-option :label="$t('m.trade.washSale')" :value="3">{{$t('m.trade.washSale')}}</el-option>-->

              </el-select>
            </div>
          </div>
          <div class="ucenter-body">
            <el-table
              :data="recordTbData"
              header-cell-class-name="table-header"
              slot="empty"
            >
              <el-table-column
                prop="createTime"
                :label="$t('m.trade.time')"
                width="180"
                sortable>
                >
              </el-table-column>
              <el-table-column
                prop="marketName"
                :label="$t('m.trade.market')"
              >
              </el-table-column>
              <el-table-column
                prop="marketName"
                :label="$t('m.patch.marketType')"
              >
                <template slot-scope="scope">
                  {{$t(`m.patch.tradeTypeMap[${scope.row.marketType}]`)}}
                </template>
              </el-table-column>

              <el-table-column
                :label="$t('m.patch.forexType')"
              >
                <template slot-scope="scope">
                  {{$t(`m.patch.forexTypeMap[${scope.row.tradeType}]`)}}
                </template>
              </el-table-column>


              <el-table-column
                prop="type"
                :label="$t('m.exchange.business')"
                align="center"
                :formatter="formatBuySell"
              >
              </el-table-column>
              <el-table-column
                prop="turnoverPrice"
                :label="$t('m.exchange.price')"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="turnoverNum"
                :label="$t('m.trade.transVolume')"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="turnoverAmount"
                :label="$t('m.trade.totalTrans')"
                align="center"
              >
                <template slot-scope="scope">
                  {{scope.row.marketType == 1 ? scope.row.turnoverAmount : '-'}}
                </template>
              </el-table-column>
              <!--<el-table-column-->
                <!--prop="marketName"-->
                <!--:label="$t('m.patch.margin')"-->
              <!--&gt;-->
                <!--<template slot-scope="scope">-->
                  <!--{{scope.row.marketType == 1 ? '-' : scope.row.lockMargin}}-->
                <!--</template>-->
              <!--</el-table-column>-->
              <el-table-column
                prop="fee"
                :label="$t('m.exchange.fee')"
                align="center"
                sortable
              >
              </el-table-column>
            </el-table>
            <el-pagination
              class="pager-container"
              background
              layout="prev, pager, next"
              :current-page.sync="page"
              :page-size="rows"
              :total="totalRecords"
              @current-change="handlePageChange"
            >
            </el-pagination>
          </div>
        </div>
      </div>
    </div>

    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>
  </div>
</template>

<script>
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import financeHeader from 'components/finance/finance-header'
  import {orderApi} from 'api/order'
  import {OK} from 'api/config'
  import {homeApi} from 'api/home'
  import {util} from 'common/js/util'
  import {exchangeApi} from 'api/exchange'
  import {mapGetters} from 'vuex'


  export default {
    data() {
      return {
        totalRecords: 0,
        symbol: 0,
        rows: 10,
        type: 0,
        page: 1,
        marketSelectOptions: [],
        value1: '',
        value2: '',
        recordTbData: [],
        marketList: [],
        // forexMarketList: []
      }
    },
    mounted() {
      this._serverGetTurnoverOrderList();
      this._getMarketList()
      // this._getForexMarketList()
      // get the child page
      // let childPage = this.$route.params.page
      // console.log("子页面：",childPage);


    },
    computed: {
      ...mapGetters(['token'])
    },
    methods: {
      handlePageChange(currentPage) {
        console.log("handlePageChange:", currentPage);
        this.page = currentPage
        this._serverGetTurnoverOrderList();
      },
      formatDate(row, column) {
        return util.date2DateTimeString2(new Date(row.createTime))
      },
      formatBuySell(row, column) {
        if (row.type == 1) {
          return this.$t('m.trade.buy')
        } else {
          return this.$t('m.trade.sell')
        }
      },
      onMarketChange(symbol) {
        console.log("change:", symbol);
        this.symbol = symbol
        this._serverGetTurnoverOrderList()

      },
      onTradeTypeChange(type) {
        console.log("onTradeTypeChange:", type);
        this.type = type
        this._serverGetTurnoverOrderList()

      },
      _serverGetTurnoverOrderList() {
        orderApi.serverGetTurnoverOrderList(this.rows, this.page, this.symbol, this.type, this.token).then(res => {
          this.totalRecords = res.data.total
          this.recordTbData = res.data.records
        })
      },
      _getMarketList() {

        homeApi.getMarketList().then(data => {
          this.marketList = data
          console.log("市场列表：", this.marketList);
          for (let i = 0; i < data.length; i++) {
            let item = data[i]
            let newItem = {}
            newItem.label = item.title
            newItem.value = item.symbol
            this.marketSelectOptions.push(newItem)
          }
        })
      },
      // async _getForexMarketList() {
      //   let data = await exchangeApi.forexMarketList()
      //   this.forexMarketList = data
      //   console.log("外汇市场列表：", this.forexMarketList);
      //   for (let i = 0; i < this.forexMarketList.length; i++) {
      //     let item = this.forexMarketList[i]
      //     let newItem = {};
      //     newItem.label = item.name;
      //     newItem.value = item.symbol;
      //     newItem.key = item.marketId ? item.marketId : item.symbol;
      //     this.marketSelectOptions.push(newItem)
      //   }
      // },
    },

    components: {
      NavHeader,
      mFooter,
      financeHeader
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
