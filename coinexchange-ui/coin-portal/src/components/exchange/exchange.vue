<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/trade"
  @import "exchange.styl"
</style>

<template>
  <div class="trading-view exchange" v-wechat-title="$t('m.navheader.exchange')">
    <NavHeader activeTab="7" class="trading-header" ref="navHeader"></NavHeader>
    <div class="trading-container">
      <div class="trading-wrap">
        <div class="trading-chart two-col">
          <div class="exchange-operation">
            <div class="exchange-name">{{currentMarket.name}}</div>
            <div class="change change-up">
              {{currentMarket.change}}%
            </div>
            <div class="price-diff price-diff-up">
              {{currentMarket.spread}}{{$t('m.exchange.point')}}
            </div>

            <div class="exchange-funds-info">
              <marquee direction="left" scrollamount=3 onmouseover="this.stop()" onmouseout="this.start()">
                <btc-usdt-funds :fundsData="fundsData"></btc-usdt-funds>
              </marquee>
            </div>
          </div>

          <!--k线-->
          <exchange-kline
            :exchangeDialogData="exchangeDialogData" ref="exchangeKline">
          </exchange-kline>
        </div>
        <div class="trading-market one">
          <exchange-market-list refs="market-list" @showOrderDialog="showOrderDialog" class="trading-displayer"
                                :exchangeMarkets="exchangeMarkets"
                                :defaultTitle="defaultTitle"
                                @changeExchangeArea="changeExchangeArea"
          ></exchange-market-list>
        </div>
      </div>
      <div class="trading-recode">
        <el-tabs v-model="activeName" type="card" class="trading-recode-tab">
          <el-tab-pane :label="$t('m.exchange.positionDetails')" name="first">
            <tb-holds @showOrderDialog="showOrderDialog" :positionDetails="positionDetails"></tb-holds>
          </el-tab-pane>
          <el-tab-pane :label="$t('m.exchange.positionSummary')" name="second">
            <tb-gathers @showOrderDialog="showOrderDialog" :positionSummary="positionSummary"></tb-gathers>

          </el-tab-pane>
          <el-tab-pane :label="$t('m.exchange.openToday')" name="third">
            <tb-today-closeout @showOrderDialog="showOrderDialog"
                               :todayCloseoutData="todayCloseoutData"></tb-today-closeout>
          </el-tab-pane>
          <el-tab-pane :label="$t('m.exchange.entrustToday')" name="forth">
            <tb-today-entrusts @showOrderDialog="showOrderDialog"
                               :entrustOrders="entrustOrders"></tb-today-entrusts>

          </el-tab-pane>
          <el-tab-pane :label="$t('m.exchange.dealToday')" name="fifth">
            <tb-today-turnovers :turnoverOrders="turnoverOrders"></tb-today-turnovers>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <!--交易弹窗-->
    <exchange-dialog :exchangeDialogData="exchangeDialogData"
                     @handleCloseDialog="handleCloseDialog"
                     @submitOrder="submitOrder" ref="exchangeDialog"
    ></exchange-dialog>
  </div>
</template>
<script>
  import NavHeader from 'components/nav-header/nav-header'
  import BtcUsdtFunds from 'components/exchange/btc-usdt-funds'
  import ExchangeKline from 'components/exchange/exchange-kline'
  import ExchangeDialog from 'components/exchange/exchange-dialog'
  import TbHolds from 'components/exchange/tb-holds'
  import TbGathers from 'components/exchange/tb-gathers'
  import TbTodayCloseout from 'components/exchange/tb-today-closeout'
  import TbTodayEntrusts from 'components/exchange/tb-today-entrusts'
  import TbTodayTurnovers from 'components/exchange/tb-today-turnovers'
  import ExchangeMarketList from 'components/exchange/exchange-market-list'
  import {exchangeApi} from 'api/exchange'
  import {cookieUtil} from 'common/js/cookieUtil'
  import {mapGetters} from 'vuex'

  export default {
    created() {
      this.bus.$on("changeMarket", (currentMarket) => {
        this.currentMarket = currentMarket
      });
    },
    computed: {
      ...mapGetters(['token']),
    },
    mounted() {
      this.getMarketArea();
      if (this.token) {
        this.subscribeBottomTable()
      }
    },
    components: {
      NavHeader,
      BtcUsdtFunds,
      ExchangeKline,
      ExchangeDialog,
      TbHolds,
      TbGathers,
      TbTodayCloseout,
      TbTodayEntrusts,
      TbTodayTurnovers,
      ExchangeMarketList
    },
    data() {
      return {
        coinId: 1,
        usessionId: '',
        // 弹窗需要的数据
        exchangeDialogData: '',
        exchangeType: -1,
        activeName: 'first',
        areaName: '',
        marketId: "",
        exchangeMarkets: [],

        defaultTitle: '',
        marketAreaIndex: 0,
        // 当前市场
        currentMarket: '',

        fundsData: {
          totalAmount: 0,
          floatProfit: 0,
          totalProfit: 0,
          lockMargin: 0,
          balanceAmount: 0,
        },
        // 持仓明细
        positionDetails: [],
        // 持仓汇总
        positionSummary: [],
        // 今日平仓
        todayCloseoutData: [],
        // 今日委托
        entrustOrders: [],
        // 今日成交
        turnoverOrders: [],
        marketDataIndex: '',
        marketIdDic: {},
        fundsInterval: null

      }
    },


    destroyed() {
      clearInterval(this.fundsInterval);
      this.unsubscribeMarkets(this.areaName);
      // 持仓明细
      this.unsubscribeHolds();
      //  持仓汇总
      this.unsubscribeGathers();
      //  今日平仓
      this.unsubscribeTodayCloseout();
      //  今日委托
      this.unsubscribeTodayEntrusts();
      //  今日成交
      this.unsubscribeTodayTurnovers();
    },
    methods: {
      //模拟数据变化
      mockPriceChange() {

        for (var i = 0; i < this.exchangeMarkets[0].markets.length; i++) {
          this.exchangeMarkets[0].markets[i].buyPrice = Number((this.exchangeMarkets[0].markets[0].buyPrice + Math.random()).toFixed(2))
          this.exchangeMarkets[0].markets[i].price = Number((this.exchangeMarkets[0].markets[0].price + Math.random()).toFixed(2))
          this.exchangeMarkets[0].markets[i].sellPrice = Number((this.exchangeMarkets[0].markets[0].sellPrice + Math.random()).toFixed(2))
        }

      },
      /**
       * 生成交易市场marketId=>dialogIndex的映射
       */
      generateMarketDict() {
        for (let i = 0; i < this.exchangeMarkets.length; i++) {
          let item = this.exchangeMarkets[i];
          for (let j = 0; j < item.markets.length; j++) {
            let marketId = item.markets[j].marketId
            this.marketIdDic[marketId] = {
              areaIndex: i,
              marketIndex: j
            }
          }
        }
        console.log("创新交易市场字典：",this.marketIdDic)
      },
      getMarketArea() {
        exchangeApi.forexMarketArea().then(res => {
          res.data.map((v) => {
            if (!v.hasOwnProperty('markets')) {
              v.markets = []
            }
          });
          this.exchangeMarkets = res.data;
          this.coinId = this.exchangeMarkets[0].coinId;

          this.loopFunds();
          this.defaultTitle = this.exchangeMarkets[0].name;
          this.areaName = this.exchangeMarkets[0].name;
          this.subscribeMarkets(this.defaultTitle);
        })
      },
      // 所有市场行情
      subscribeMarkets(coinName) {
        coinName = coinName.toLowerCase();
        this.$socket.subscribe(`market.${coinName}.ticker.tc`, "market-area-tc");
        this.$socket.on("market-area-tc", (data) => {
          // console.log("订阅到当前市场", data)
          this.exchangeMarkets[this.marketAreaIndex].markets = data.markets;
          // 弹窗打开的情况下
          if (this.marketDataIndex) {

            let item = data.markets[this.marketDataIndex.marketIndex];
            let price = item.price
            let buyPrice = item.bidPrice
            let sellPrice = item.askPrice
            let baseMargin = item.baseMargin

            let realTimePrice = {
              buyPrice: buyPrice,
              sellPrice: sellPrice,
              price: price,
              baseMargin: baseMargin
            }
            this.$refs.exchangeDialog.realTimePrice = realTimePrice
          }
          // 第一次初始化当前市场
          if (!this.currentMarket) {
            // 取usdt交易区的第一个交易市场
            this.generateMarketDict();
            this.currentMarket = data.markets[0];
          }
          // console.log("markets 返回结果：", this.exchangeMarkets)
          if (!this.$refs.exchangeKline.currentMarket) {
            this.$refs.exchangeKline.currentMarket = data.markets[0];
            this.$refs.exchangeKline.initKline()
            console.log("markets 第一次赋值：", this.exchangeMarkets)
          }
        });
      },
      unsubscribeMarkets(coinName) {
        coinName = coinName.toLowerCase();
        this.$socket.unsubscribe(`market.${coinName}.ticker.tc`, "market-area-tc");
      },
      subscribeBottomTable() {

        // 持仓明细
        this.subscribeHolds();
        //  持仓汇总
        this.subscribeGathers();
        //  今日平仓
        this.subscribeTodayCloseout();
        //  今日委托
        this.subscribeTodayEntrusts();
        //  今日成交
        this.subscribeTodayTurnovers();

        // 第一次初始化
        this._orderHolds()
        this._orderGathers()
        this._todayCloseout()
        this._todayEntrusts()
        this._todayTurnovers()
      },
      /**
       * 轮训资金账户
       */
      loopFunds() {
        this._accountFunds()
        this.fundsInterval = setInterval(() => {
          this._accountFunds()
        }, 5000)
      },
      async _accountFunds() {
        // console.log("轮训资金账户",this.token)
        if (this.token) {
          let res = await exchangeApi.accountFunds(this.coinId, this.token)
          this.fundsData = res.data
        }
      },
      /**
       * 持仓明细
       */
      subscribeHolds() {
        if (this.token) {
          this.$socket.subscribe("position.details.update", "position-detail", this.token);
          this.$socket.on("position-detail", (data) => {
            console.log("持仓明细订阅：", data)
            this._orderHolds()
          })
        }
      },
      async _orderHolds() {
        let res = await exchangeApi.orderHolds(this.token)
        console.log("持仓明细列表：", res.data)
        this.positionDetails = res.data
      },

      unsubscribeHolds() {
        if (this.token) {
          this.$socket.unsubscribe("position.details.update", "position-detail", this.token);
        }
      },
      /**
       * 持仓汇总
       */
      subscribeGathers() {
        if (this.token) {
          this.$socket.subscribe("position.summary.update", "position-summary", this.token);
          this.$socket.on("position-summary", (data) => {
            console.log("持仓汇总订阅", data)
            this._orderGathers()
          })
        }
      },
      async _orderGathers() {
        let res = await exchangeApi.orderGathers(this.token)
        console.log("持仓汇总列表：", res.data)
        this.positionSummary = res.data

      },
      unsubscribeGathers() {
        if (this.token) {
          this.$socket.unsubscribe("position.summary.update", "position-summary", this.token);
        }
      },

      /**
       * 今日平仓
       */
      subscribeTodayCloseout() {
        if (this.token) {
          this.$socket.subscribe("closeposition.orders.update", "closeposition-orders", this.token);
          this.$socket.on("closeposition-orders", (data) => {
            console.log("今日平仓通知", data)
            this._todayCloseout()
          })
        }
      },
      async _todayCloseout() {
        let res = await exchangeApi.todayCloseout(this.token)
        console.log("今日平仓列表：", res.data)
        this.todayCloseoutData = res.data
      },
      unsubscribeTodayCloseout() {
        if (this.token) {
          this.$socket.unsubscribe("closeposition.orders.update", "closeposition-orders", this.token);
        }
      },
      /**
       * 今日委托
       */
      subscribeTodayEntrusts() {
        if (this.token) {
          this.$socket.subscribe("entrust.orders.update", "entrust-orders", this.token);
          this.$socket.on("entrust-orders", (data) => {
            console.log("今日委托订阅", data)
            this._todayEntrusts()
          })
        }
      },
      async _todayEntrusts() {
        let res = await exchangeApi.todayEntrusts(this.token)
        console.log("今日委托列表：", res.data)
        this.entrustOrders = res.data
      },
      unsubscribeTodayEntrusts() {
        if (this.token ) {
          this.$socket.unsubscribe("entrust.orders.update", "entrust-orders", this.token);
        }
      },
      /**
       * 今日成交
       */
      subscribeTodayTurnovers() {
        if (this.token) {
          this.$socket.subscribe("turnover.orders.update", "turnover-orders", this.token);
          this.$socket.on("turnover-orders", (data) => {
            console.log("今日成交订阅：", data)
            this._todayTurnovers()
          })
        }
      },
      async _todayTurnovers() {
        let res = await exchangeApi.todayTurnovers(this.token)
        console.log("今日成交列表：", res.data)
        this.turnoverOrders = res.data
      },
      unsubscribeTodayTurnovers() {
        if (this.token) {
          this.$socket.unsubscribe("turnover.orders.update", "turnover-orders", this.token);
        }
      },

      handleCloseDialog() {
        this.marketDataIndex = ''
      },
      /**
       * 显示交易对话框
       */
      showOrderDialog(dialogData) {
        console.log("对话框数据", dialogData);
        // 点击了右侧买入卖出按钮
        if (dialogData.marketDataIndex) {
          this.marketDataIndex = dialogData.marketDataIndex;
        } else {
          this.marketDataIndex = this.marketIdDic[dialogData.marketId]
        }

        // 实时更新对话框所需的索引
        //把对话框的状态切换到第一个
        this.$refs.exchangeDialog.currentDialog = 1
        // 弹窗所需的数据
        this.exchangeDialogData = dialogData
      }
      ,
      async submitOrder(data) {
        let res = await exchangeApi.entrustOrder(data, this.token);
        this.$message({
          type: 'success',
          message: "成功"
        });
        console.log("res", res.result)
      },

      /**
       * 交易对列表切换交易区 同时切换资金账户
       * @param coinId
       */
      changeExchangeArea(item) {
        if (!this.token) {
          return
        }
        let index = item.index;
        this.coinId = this.exchangeMarkets[index].coinId;
        this.marketAreaIndex = index;
        this.areaName = item.name;
        this.subscribeMarkets(item.name);
      }
    },
  }
</script>
