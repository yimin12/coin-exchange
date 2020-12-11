<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.navheader.entrustRecord')">
    <NavHeader activeTab="4"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <div class="ucenter-main no-sidebar">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.navheader.entrustRecord')}}</h3>
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
                prop="created"
                :label="$t('m.trade.time')"
                width="180"
                sortable>
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
                  <!--{{scope.row.marketType == 1 ? '币币交易' : '创新交易'}}-->
                  {{$t(`m.patch.tradeTypeMap[${scope.row.marketType}]`)}}
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
                prop="price"
                :label="$t('m.trade.entrustPrice')"
                align="center"
                sortable
              >
              </el-table-column>
              <el-table-column
                prop="volume"
                :label="$t('m.trade.entrustNum')"
                align="center"
                sortable
              >
              </el-table-column>
              <el-table-column
                prop="deal"
                :label="$t('m.trade.topVolume')"
                align="center"
                sortable
              >
                <template slot-scope="scope">
                  {{scope.row.marketType == 1 ? scope.row.deal : '-'}}
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
                prop="status"
                :label="$t('m.c2c.status')"
                align="center"
                >
                <template slot-scope="scope">
                  {{$t(`m.patch.tradeStatusMap.${scope.row.status}`)}}
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('m.exchange.operate')"
                align="center"
              >
                <template slot-scope="scope">
                  <el-button @click="handleCancelEntrust(scope.row.id)"
                             :disabled="scope.row.status!=0">
                    {{$t('m.exchange.cancel')}}
                  </el-button>
                </template>
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
  import {tradeApi} from 'api/trade'
  import {OK} from 'api/config'
  import {homeApi} from 'api/home'
  import {exchangeApi} from 'api/exchange'
  import {util} from 'common/js/util'
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        totalRecords: 0,
        symbol: '',
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
      // get the child page
      //let childPage = this.$route.params.page
      //console.log("子页面：", childPage);
      this._serverGetEntrustOrderList();
      this._getMarketList();
      // this._getForexMarketList();

    },
    computed: {
      ...mapGetters(['token'])
    },
    methods: {
      handleCancelEntrust(id) {
        console.log("取消的id", id);

        this.$confirm(this.$t('m.exchange.whetherCancelOrder'), this.$t('m.prompt'), {
          type: 'warning',
          confirmButtonText: this.$t('m.yes'),
          cancelButtonText: this.$t('m.no'),
          callback: action => {

            console.log("action:", action);
            if (action != "cancel") {
              tradeApi.serverCancelOrder(id, this.token).then(res => {
                this.$message(this.$t('m.exchange.cancelOrderCallback'))
                this._serverGetEntrustOrderList()
              }).catch((res) => {
                this.$message({
                  message: res.errmsg,
                  type: 'error'
                });
              })
            }
          }
        });
      },
      formatStatus(row, column) {
        return util.formatEntrustStatus(row.status)
      },
      handlePageChange(currentPage) {
        console.log("handlePageChange:", currentPage);
        this.page = currentPage
        this._serverGetEntrustOrderList();
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
        this._serverGetEntrustOrderList()

      },
      onTradeTypeChange(type) {
        console.log("onTradeTypeChange:", type);
        this.type = type
        this._serverGetEntrustOrderList()

      },
      _serverGetEntrustOrderList() {
        orderApi.serverGetEntrustOrderList(this.rows, this.page, this.symbol, this.type, this.token).then(res => {
          this.totalRecords = res.data.total;
          this.recordTbData = res.data.records;
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
      //     let newItem = {}
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
