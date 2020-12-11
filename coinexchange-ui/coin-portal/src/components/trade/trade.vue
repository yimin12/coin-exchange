<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/trade"
</style>
<template>
  <div class="trading-view exchange" v-wechat-title="$t('m.navheader.trade')">
    <NavHeader activeTab="2" class="trading-header" ref="navHeader"></NavHeader>
    <div class="trading-container">

      <div class="trading-wrap">

        <div class="trading-chart two-col">

          <div class="trading-kline">
            <div class="wgt-dropdown" :class="switchMarketListDropdown?'active':''"
                 @mouseover='selectorMarketlistMsover' @mouseout='selectorMarketlistMsout'>
              <div class="dropdown-tit">
                <a>{{currentMarket.name}}</a>
              </div>
              <div class="txt-container">
                <div class="dropdown-txt hook-market-list">
                  <el-input v-model="marketSearch"
                            size="mini"
                            prefix-icon="el-icon-search"
                            class="search">
                  </el-input>
                  <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="trading-recode-tab">
                    <el-tab-pane :label="item.areaName" :name="item.areaName" v-for="(item,index) in marketList" :key="index">
                      <table class="trade-table">
                        <thead>
                        <tr>
                          <th>{{$t('m.trade.market')}}</th>
                          <th>{{$t('m.trade.price')}}</th>
                          <th>{{$t('m.trade.change24hr')}}</th>
                          <th>{{$t('m.trade.volume24hr')}}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(innerItem,index) in item.markets" :key="index" @click="marketChange(innerItem)"
                            v-show="marketSearch==='' || innerItem.name.indexOf(marketSearch.toUpperCase())>-1">
                          <td v-html="innerItem.name"></td>
                          <td v-html="innerItem.price"></td>
                          <td >{{innerItem.change}}%</td>
                          <td v-html="innerItem.volume"></td>
                        </tr>
                        </tbody>
                      </table>
                    </el-tab-pane>

                  </el-tabs>
                </div>
              </div>
            </div>
            <div class="full-screen">
              <a href="javascript:;"
                 @click="isUnfold = !isUnfold"
                 class="unfold"
                 v-text="isUnfold?$t('m.trade.unfold'):$t('m.trade.fold')">
              </a>
            </div>
            <iframe id="kline-win" scrolling="no" style="width: 100%;height: 100%"></iframe>
          </div>
          <div class="trading-recode" v-show="isUnfold">
            <el-tabs type="card" class="trading-recode-tab">
              <el-tab-pane :label="$t('m.trade.uncompletedEntrust')">
                <div class="trading-table-scroll">
                  <table class="trading-table">
                    <thead>
                    <tr>
                      <th>{{$t('m.trade.entrustTime')}}</th>
                      <th>{{$t('m.trade.entrustType')}}</th>
                      <th>{{$t('m.trade.entrustPrice')}} / {{$t('m.trade.transAvgPrice')}}</th>
                      <th>{{$t('m.trade.entrustAmount')}} / {{$t('m.trade.transVolume')}}</th>
                      <th>{{$t('m.trade.totalEntrust')}} / {{$t('m.trade.totalDealEntrust')}}</th>
                      <th>{{$t('m.trade.entrustStatus')}}</th>
                      <th>{{$t('m.trade.entrustOperate')}}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item,index) in entrustTbData" :key="index">
                      <td>{{item.created}}</td>
                      <td>{{$t(`m.trade.transTypeMap.${item.type}`)}}</td>
                      <td>{{item.price}} / {{item.dealAvgPrice}}</td>
                      <td>{{item.volume}} / {{item.dealVolume}}</td>
                      <td>{{item.amount}} / {{item.dealAmount}}</td>
                      <td>{{$t(`m.trade.transStatusMap.${item.status}`)}}</td>
                      <td>
                        <el-button size="mini" @click="cancelOrder(item.orderId)">{{$t('m.trade.cancel')}}</el-button>
                      </td>
                    </tr>
                    </tbody>
                  </table>
                  <p class="no-data" v-if="entrustTbData.length===0">{{$t('m.nodata')}}</p>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="$t('m.trade.entrustHistory')">

                <div class="trading-table-scroll">
                  <table class="trading-table">
                    <thead>
                    <tr>
                      <th v-text="$t('m.trade.entrustTime')"></th>
                      <th v-text="$t('m.trade.transType')"></th>
                      <th>{{$t('m.trade.transPrice')}} / {{$t('m.trade.transAvgPrice')}}</th>
                      <th>{{$t('m.trade.transVolume')}} / {{$t('m.trade.actualTransVolume')}}</th>
                      <th>{{$t('m.trade.totalTrans')}} / {{$t('m.trade.totalDealTrans')}}</th>
                      <th v-text="$t('m.trade.transStatus')"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item,index) in turnoverTbData" :key="index">
                      <td v-text="item.created"></td>
                      <td>{{$t(`m.trade.transTypeMap.${item.type}`)}}</td>
                      <td>{{item.price}} / {{item.dealAvgPrice}}</td>
                      <td>{{item.volume}} / {{item.dealVolume}}</td>
                      <td>{{item.amount}} / {{item.dealAmount}}</td>
                      <td>{{$t(`m.trade.transStatusMap.${item.status}`)}}</td>
                    </tr>
                    </tbody>
                  </table>
                  <p class="no-data" v-if="turnoverTbData.length===0">{{$t('m.nodata')}}</p>
                </div>

              </el-tab-pane>
            </el-tabs>
          </div>
        </div>

        <div class="trading-market two">
          <div class="trade-box market-buy">
            <div class="trade-wall">

              <!--深度数据区域-->
              <div id="wgt-depth-data-title" class="info-title">
                <span>{{$t('m.trade.levels')}}</span>
                <span>{{$t('m.trade.topPrice')}}</span>
                <span>{{$t('m.trade.entrustNum')}}</span>
              </div>


              <!--深度数据-->
              <div class="wgt-depth-data" id="depth-data-item">
                <div class="origin">
                  <!--<transition-group name="flip-list" tag="ul" mode='out-in' class="above">-->
                  <ul class="above">
                    <li v-for="(list,index) in depthsData.asks" :key="index"
                        v-on:click="depthAsksClick(list.price,list.volume)">
                      <!--.reverse()-->
                      <div class="data-item">
                            <span>{{$t('m.trade.sell')}}({{(depthsData.asks.length - index)
                            <10? '0'+(depthsData.asks.length - index) : depthsData.asks.length - index}})</span>
                        <span v-html="formatNumber(list.price,currentMarket.priceScale)" class="number-font" style="text-align: center"></span>
                        <span v-html="formatNumber(list.volume,currentMarket.numScale)" class="number-font" style="text-align: right"></span>
                      </div>
                      <div class="bg" :style="{width:(list.volume/asksDepth*100) + '%'}"></div>
                    </li>
                  </ul>
                  <!--</transition-group>-->
                  <div class="middle">
                    <span>{{depthsData.price}} {{currentMarket.priceUnit}}
                      <b style="color:#999">≈</b>
                    </span>
                    <span>{{depthsData.cnyPrice}} CNY</span>
                    <a href="javascript:;"
                       class="merge-depth">
                      {{$t('m.trade.mergeDepth')}}
                      <select v-model="chooseMergeDepth" @change="subscribeDepths">
                        <option v-for="(item,index) in currentMarket.mergeDepth" :key="index" v-bind:value="item.mergeType"
                                v-text="scientificToNumber(item.value)"></option>
                      </select>
                    </a>
                  </div>
                  <!--<transition-group name="flip-list" tag="ul" mode="out-in" class="below">-->
                  <ul class="below">

                    <li v-for="(list,index) in depthsData.bids" :key="index"
                        v-on:click="depthBidsClick(list.price,list.volume)">
                      <div class="data-item">
                        <span>{{$t('m.trade.buy')}}({{index
                            <9? '0'+(index+1) : index+1}})</span>
                        <!--index<9? '0'+(index+1) : index+1-->
                        <span v-html="formatNumber(list.price,currentMarket.priceScale)" class="number-font" style="text-align: center"></span>
                        <span v-html="formatNumber(list.volume,currentMarket.numScale)" class="number-font" style="text-align: right"></span>
                      </div>
                      <div class="bg" :style="{width:(list.volume/bidsDepth*100) + '%'}"></div>
                    </li>
                  </ul>
                  <!--</transition-group>-->
                </div>
              </div>
            </div>
            <div class="trade-form">

              <div class="transaction">

                <div class="wgt-transactor active" :class="{active:token}" id="hook-transactor-buy">
                  <!--!currentBuyItem.disabled-->
                  <div class="list">
                    <label>
                      <span>{{$t('m.trade.balance')}}</span>：
                      <b class="hook-canuse-amount">{{userAccount.buyAmount}}</b> {{userAccount.buyUnit}}</label>
                  </div>
                  <div class="list">
                    <label>{{$t('m.trade.maxBuy')}}：
                      <b class="hook-canuse-amount">{{currentBuyItem.canBuyAmount}}</b>
                      {{userAccount.sellUnit}}</label>
                  </div>
                  <div class="list">
                    <div class="inputitem">
                      <span>{{$t('m.trade.price')}}</span>
                      <input type="text" v-model="currentBuyItem.price" @keyup="checkInputPrice('buy',$event)" class="input hook-price">
                    </div>
                  </div>
                  <div class="list">
                    <div class="inputitem">
                      <span>{{$t('m.trade.amount')}}</span>
                      <input type="text" v-model="currentBuyItem.amount"
                             @keyup="buyAmountLimit($event)"
                             class="input hook-amount">
                    </div>
                  </div>
                  <!--滑块-->
                  <div class="level-slider transactor-slider">
                    <div class="block">
                      <el-slider v-model="sliderRate.buy"
                                 @change="sliderBuyChange"
                                 :disabled="currentBuyItem.price=='0'"></el-slider>

                    </div>
                  </div>
                  <div class="list total-item">
                    <template v-if="currentMarket.numMax > 0">
                      <template v-if="currentBuyItem.amount <= currentMarket.numMax">
                        <label class="control-label"
                               data-lang="righter-transactor-count">{{$t('m.trade.estimated')}}</label>
                        <b class="hook-select-total" v-text="sliderRate.rateBuyAmount"></b>
                        <span class="hook-market-name2">{{userAccount.buyUnit}}</span>
                      </template>
                      <template v-else>
                        <p style="color:red">{{$t('m.trade.maxTradeBuyNum')}}{{currentMarket.numMax}}</p>
                      </template>
                    </template>
                    <template v-else>
                      <label class="control-label"
                             data-lang="righter-transactor-count">{{$t('m.trade.estimated')}}</label>
                      <b class="hook-select-total" v-text="sliderRate.rateBuyAmount"></b>
                      <span class="hook-market-name2">{{userAccount.buyUnit}}</span>
                    </template>
                  </div>
                  <!--<div class="hook-rates">-->
                  <!--&lt;!&ndash;成交才收, 撤单退回&ndash;&gt;-->
                  <!--<span class="span-rates" v-html="currentMarket.buyFeeRate">0</span>-->
                  <!--<span class="span-rate-total" v-html="currentBuyItem.rateCount">0</span>-->
                  <!--</div>-->
                  <div class="list">
                    <button type="button"
                            class="subbtn hook-subbtn"
                            data-type="1"
                            :disabled="currentBuyItem.amount > currentMarket.numMax && currentMarket.numMax > 0"
                            v-on:click="createOrder(1)">{{$t('m.trade.buy')}}
                    </button>
                  </div>
                </div>
              </div>

            </div>
          </div>
          <div class="trade-box market-sell">
            <div class="trade-wall">
              <!--交易信息区域-->
              <div class="info-title">
                <span>{{$t('m.trade.time')}}</span>
                <span>{{$t('m.trade.topPrice')}}</span>
                <span>{{$t('m.trade.topVolume')}}</span>
              </div>
              <!--交易信息-->
              <div class="wgt-trades-data" id="trades-data-item">
                <!--<transition-group mode="out-in" name="flip-list" tag="ul">-->
                <ul>
                  <li v-for="(list,index) in tradesData" :key="index+list.time" :class="list.type=='1' ? 'b' : 's' "
                      @click="depthBidsClick(list.price, list.volume)">
                      <span class="number-font">
                        <i>{{list.time}}</i>
                      </span>
                    <span v-html="formatNumber(list.price,currentMarket.priceScale)" class="number-font" style="text-align: center"></span>
                    <!--<i>128500</i>.00-->
                    <span v-html="formatNumber(list.volume,currentMarket.numScale)" class="number-font" style="text-align: right"></span>
                    <!--<i>0.0038</i>000-->
                  </li>
                  <!--<li class="s"></li>-->
                  <!--</transition-group>-->
                </ul>
              </div>
            </div>
            <div class="trade-form">
              <div class="transaction">
                <div class="wgt-transactor active" :class="{active:token}" id="hook-transactor-sell">
                  <div class="list">
                    <label>
                      <span>{{$t('m.trade.balance')}}</span>：
                      <b class="hook-canuse-amount" v-html="userAccount.sellAmount">--</b>
                      <span class="hook-market-name">{{userAccount.sellUnit}}</span>
                    </label>
                  </div>
                  <div class="list">
                    <label>{{$t('m.trade.maxSell')}}：
                      <b class="hook-canuse-amount">{{currentSellItem.canSellAmount}}</b>
                      {{userAccount.buyUnit}}</label>
                  </div>
                  <div class="list">
                    <div class="inputitem">
                      <span data-lang="righter-transactor-price2">{{$t('m.trade.sellPrice')}}</span>
                      <input type="text" v-model="currentSellItem.price" @keyup="checkInputPrice('sell',$event)"  class="input hook-price" >
                    </div>
                  </div>
                  <div class="list">
                    <div class="inputitem">
                      <span data-lang="righter-transactor-amount2">{{$t('m.trade.sellAmount')}}</span>
                      <input type="text"  v-model="currentSellItem.amount" @keyup="sellAmountLimit($event)"
                             class="input hook-amount" >
                    </div>
                  </div>
                  <!--滑块-->
                  <div class="level-slider transactor-slider">
                    <div class="block">
                      <el-slider v-model="sliderRate.sell"
                                 @change="sliderSellChange"
                                 :disabled="currentSellItem.price=='0'"></el-slider>
                    </div>
                  </div>
                  <div class="list total-item">
                    <template v-if="currentMarket.numMax > 0">
                      <template v-if="currentSellItem.amount <= currentMarket.numMax">
                        <label class="control-label"
                               data-lang="righter-transactor-count">{{$t('m.trade.estimated')}}</label>
                        <b class="hook-select-total" v-text="sliderRate.rateSellAmount"></b>
                        <span class="hook-market-name2">{{userAccount.buyUnit}}</span>
                      </template>
                      <template v-else-if="currentMarket.numMax">
                        <p style="color:red">{{$t('m.trade.maxTradeSellNum')}}{{currentMarket.numMax}}</p>
                      </template>
                    </template>
                    <template v-else>
                      <label class="control-label"
                             data-lang="righter-transactor-count">{{$t('m.trade.estimated')}}</label>
                      <b class="hook-select-total" v-text="sliderRate.rateSellAmount"></b>
                      <span class="hook-market-name2">{{userAccount.buyUnit}}</span>
                    </template>
                  </div>
                  <!--<div class="hook-rates">-->
                  <!--&lt;!&ndash;成交才收, 撤单退回&ndash;&gt;-->
                  <!--<span class="span-rates" v-html="currentMarket.sellFeeRate">0</span>-->
                  <!--<span class="span-rate-total" v-html="currentSellItem.rateCount">0</span>-->
                  <!--</div>-->
                  <div class="list">
                    <button type="button"
                            class="subbtn hook-subbtn"
                            data-type="2"
                            :disabled="currentSellItem.amount > currentMarket.numMax && currentMarket.numMax > 0"
                            v-on:click="createOrder(2)">{{$t('m.trade.sell')}}
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>
  </div>
</template>


<script>
  import 'swiper/dist/css/swiper.css'
  import NavHeader from 'components/nav-header/nav-header'
  import {util} from 'common/js/util'
  import {tradeApi} from 'api/trade'

  import {
    homeApi
  } from 'api/home'

  import {OK} from 'api/config'
  import {stampToDate} from 'common/js/date'
  import {mapGetters} from 'vuex'

  export default {
    components: {
      NavHeader
    },
    computed: {
      ...mapGetters(['token', 'lang'])
    },
    data() {
      return {
//        currentMarket: {id: null, title: '--', fulltitle: '--'},    //当前市场
        currentMarket: {},    //当前市场
        timeStp: 0,

        //选项卡
        turnoverTbData: [],    //成交记录
        entrustTbData: [],    //委托定单
        //深度数据
        depthsData: {},

        domWidth: 0,
        asksDepth: 0,
        bidsDepth: 0,

        //交易信息
        tradesData: [],

        isUnfold: true,
        //交易面板
        userAccount: {
          buyAmount: 0,
          buyFreeze: 0,
          buyRate: 0,
          buyUnit: '',
          price: 0,
          sellAmount: 0,
          sellFreeze: 0,
          sellRate: 0,
          sellUnit: '',
          accountName: '',
          exchangeName: '',
        },    //用户关联
        buyRate: 0,
        sellRate: 0,
        currentBuyItem: {price: '', amount: '', disabled: true, rateCount: 0, canBuyAmount: ''},    //当前买的信息(价格／数量／面板禁用／费率计算／可买入量)
        currentSellItem: {price: '', amount: '', disabled: true, rateCount: 0, canSellAmount: ''},    //当前卖的信息
        sliderRate: {buy: 0, sell: 0, rateBuyAmount: 0, rateSellAmount: 0},    //滑块比率及相关

        marketList: [],    //市场列表
        switchMarketListDropdown: false,
        marketMapById: {},
        marketSearch: '',
        chooseMergeDepth: 'DEFAULT',
        activeName: '',
        activeIndex: 0,
        currentSubscribePath: '',

        depthsSubscribe: null,
        tradesSubscribe: null,

        currentExchangeItem: {buyTurnover: 0, sellTurnover: 0},

        preciseNum: 6,
        turnoverPageQuery: {
          page: 1,
          size: 100,
          totalRecords: 0
        },
        entrustPageQuery: {
          page: 1,
          size: 100,
          totalRecords: 0
        },
        userFavorites: [],
        symbol:'' //当前市场名
      }
    },
    filters: {
      formatDate: function (value, type) {
        let date = new Date(value);
        return stampToDate(date, type);
      }
    },
    created() {
      this.bus.$on("change:language", (lang) => {
        let kline = document.getElementById('kline-win')
        lang = lang.toLowerCase();
        if (kline) {
          let cw = kline.contentWindow;
          cw.chart_switch_language && cw.chart_switch_language(lang);
        }
      })
    },
    mounted() {
      this._getMarkList();
      this.symbol = this.$route.query.symbol
    },
    destroyed() {
      this.unsubscribeDepths();
      this.unsubscribeTrades();
      this.unsubscribeMarkets(this.activeName)
      this.unsubscribeEntrust()
      this.unsubscribeTurnover()
      this.unsubscribeAllMarkets()
    },
    methods: {
      async _getDepth(){
        let symbol = this.currentMarket.symbol;
        let mergeDepth = this.chooseMergeDepth;
        let res = await tradeApi.getDepth(symbol,mergeDepth)
        this.depthsData = res.data
        this.processDepthData()
      },
      async _getTrades(){
        let symbol = this.currentMarket.symbol;
        let res = await tradeApi.getTrades(symbol)
        this.tradesData = res.data;
      },
      scientificToNumber(value) {
        return util.scientificToNumber(value);
      },
      // maxNum 最大整数 scale 保留几位小数
      filterValue(value, event, maxNum = 8, scale = 2) {
        if(typeof value === 'number') {
          value = value.toString();
        }
        if(event.keyCode === 16 || event.keyCode === 32){
          value = value.replace(/[^\d+|\.{0,1}]/g,'');
        }
        if(/^\d+(?=\.{0,1}\d+$|\.{0,1}$|$)/.test(value)) {
          value = value.replace(/^0+/,'0');
        }else{
          value = value.replace(/[^\d+|\.{0,1}]/g,'');
        }
        // 如果存在小数点
        if(value.indexOf('.') !== -1) {
          let arr = value.split('.');
          if(arr.length > 2) {
            let lastStr = value.charAt(value.length-1);
            if(lastStr === '.') {
              value = value.slice(0,value.length-1);
            }
          }else{
            let tempArr = new Array(2);
            if(arr[0]) {
              let len = arr[0].length;
              if(len > maxNum) {
                tempArr[0] = arr[0].slice(0, 8);
              }else{
                tempArr[0] = arr[0];
              }
            }
            if(arr[1]) {
              let len = arr[1].length; // 小数位数
              if(len > scale) {
                tempArr[1] = arr[1].slice(0, scale);
              }else{
                tempArr[1] = arr[1];
              }
            }
            value = tempArr.join('.');
          }
        }else{
          let len = value.length;
          if(len > maxNum) {
            value = value.slice(0, maxNum);
          }
        }
        return value;
      },
      checkInputPrice(type, event) {
        let value = event.srcElement ? event.srcElement.value : event.target.value;
        let {priceScale} = this.currentMarket;
        value = this.filterValue(value, event, 8, priceScale);
        if(type === 'buy') {
          this.computedBuyAmount(value);
        }else if(type === 'sell') {
          this.computedSellAmount(value);
        }
      },
      computedBuyAmount(value) {
        let {priceScale, buyFeeRate} = this.currentMarket;
        this.currentBuyItem.price = value;
        if(value > 0) {
          this.currentBuyItem.canBuyAmount = (this.userAccount.buyAmount / (1 + buyFeeRate) / value).toFixed(priceScale);
          this.sliderRate.buy = this.currentBuyItem.amount / this.currentBuyItem.canBuyAmount * 100;
          this.sliderRate.rateBuyAmount = (this.currentBuyItem.amount * value).toFixed(priceScale);
        }else{
          this.currentBuyItem.canBuyAmount = 0;
          this.sliderRate.buy = 0;
        }
      },
      computedSellAmount(value) {
        let {sellAmount} = this.userAccount;
        let {priceScale, sellFeeRate} = this.currentMarket;
        this.currentSellItem.price = value;
        let totalAmount = (sellAmount * value) * (1 + sellFeeRate);
        this.currentSellItem.canSellAmount = (totalAmount).toFixed(priceScale);
        this.sliderRate.rateSellAmount = (this.currentSellItem.amount * value).toFixed(priceScale);
      },
      // 延迟2s刷新接口
      refreshRecord() {
        console.log("刷新4接口");
        //刷新自己的交易记录和委托记录 socket会推送消息过来

        // this._serverGetTurnoverOrderList();
        // this._serverGetEntrustOrderList();

        console.log("当前Symbol", this.currentMarket.symbol);
        this._getUserAccount(this.currentMarket.symbol);
      },
      async handleClick(tab) {
        if(tab.label === '自选') {
          let item = await this.getFavoriteData();
          this.marketList[tab.index].markets = item[0].markets;
          this.subscribeAllMarkets()
        }else{
          this.activeIndex = parseInt(tab.index);
          this.subscribeMarkets(this.activeName);
          this.unsubscribeAllMarkets()
        }
      },
      async getFavoriteData () {
        this.userFavorites = [];
        let item = await tradeApi.serverFavorite(this.token);
        item.data[0].markets.map(v=>{
          this.userFavorites.push(v.symbol.toLowerCase());
        });
        return item.data;
      },
      isNumber(val) {
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if (regPos.test(val) || regNeg.test(val)) {
          return true;
        } else {
          return false;
        }
      },

      //市场列表下拉框
      formatPrice(currentPrice, currentPriceCNY) {
        currentPrice = Number(currentPrice);
        currentPriceCNY = Number(currentPriceCNY);
        var p1 = currentPrice.toFixed(4);
        var p2 = ""

        if (currentPriceCNY != null) {
          p2 = currentPriceCNY.toFixed(6);
        }
//        return "$ " + p1 + "/ <span>￥" + p2 + "</span>";
        return p1;
      },
      formatRange(currentPrice, closePrice) {
        return '<i>' + Number(((currentPrice - closePrice) / closePrice * 100).toFixed(2)) + '%</i>';
      },
      selectorMarketlistMsover: function () {
        this.switchMarketListDropdown = true
      },
      selectorMarketlistMsout: function () {
        this.switchMarketListDropdown = false
      },
//      marketListRowClick      : function (row) {
//        this.marketChange(row.id, row.title)
//      },
      marketChange(currentMarket) {
        this.currentMarket = currentMarket;
        console.log("marketChange", currentMarket);
        this.switchMarketListDropdown = false;
        this.timeStp = new Date().getTime();
        //依赖marketId
        this._getUserAccount(this.currentMarket.symbol);
        this._serverGetTurnoverOrderList();
        this._serverGetEntrustOrderList();

        this.subscribeEntrust();
        this.subscribeTurnover();

        this._getDepth()
        this._getTrades()

        this.subscribeDepths();
        this.subscribeTrades();

        // this.subscribeEntrust();

        let kline = document.getElementById('kline-win');
        let lang = this.lang.toLowerCase();
        kline.src = `../static/new_kline/trade.html?v=${this.timeStp}&symbol=${this.currentMarket.symbol}`
        if (kline.attachEvent) {
          kline.attachEvent("onload", () => {
            kline.contentWindow.chart_switch_language(lang)
          })
        } else {
          kline.onload = () => {
            kline.contentWindow.chart_switch_language(lang)
          }
        }

        console.log('---------------------费率', this.buyRate, this.sellRate);

        this.currentSellItem.price = '';
        this.currentSellItem.amount = '';
        this.currentBuyItem.price = '';
        this.currentBuyItem.amount = '';
        this.currentBuyItem.canBuyAmount = '';
        this.currentSellItem.canSellAmount = '';
        this.sliderRate.rateBuyAmount = 0;
        this.sliderRate.rateSellAmount = 0;
        this.sliderRate.buy = 0;
        this.sliderRate.sell = 0;

      },

      depthAsksClick(price, amount) {

        this.currentBuyItem.amount = amount;
        this.currentSellItem.amount = amount;
        this.computedBuyAmount(price);
        this.computedSellAmount(price);
      },
      depthBidsClick(price, amount) {

        this.currentBuyItem.amount = amount;
        this.currentSellItem.amount = amount;
        this.computedBuyAmount(price);
        this.computedSellAmount(price);
      },
      // 显示小数位，不做四舍五入
      limitDecimal(value, numScale) {
        value = value.toString();
        let numArr = [];
        if(value.indexOf('.') !==-1) {
          let arr = value.split('.');
          numArr.push(arr[0]);
          if(arr[1]) {
            let len = arr[1].length;
            if(len > numScale) {
              numArr.push(arr[1].slice(0,numScale));
            }else{
              numArr.push(arr[1]);
            }
          }
        }else{
          numArr.push(value);
        }
        return numArr.join('.');
      },
      //交易滑块事件
      //新滑块事件
      sliderBuyChange(val) {
        let {priceScale, numScale, buyFeeRate} = this.currentMarket;
        this.sliderRate.buy = val;
        let rateBuyAmount = this.currentBuyItem.canBuyAmount * (val / 100) || 0;
        this.currentBuyItem.amount = this.limitDecimal(rateBuyAmount, numScale);
        this.sliderRate.rateBuyAmount = (this.currentBuyItem.price * this.currentBuyItem.amount).toFixed(priceScale);
        this.currentBuyItem.rateCount = (this.sliderRate.rateBuyAmount * buyFeeRate).toFixed(priceScale);    //费率计算
        return val;
      },
      sliderSellChange(val) {
        let {priceScale, numScale} = this.currentMarket;
        this.sliderRate.sell = val;
        let amount = this.limitDecimal(this.userAccount.sellAmount * (val / 100), numScale);
        let rateSellAmount = this.currentSellItem.price * amount || 0;
        this.sliderRate.rateSellAmount = (rateSellAmount).toFixed(priceScale);
        this.currentSellItem.amount = amount || 0;
        return val;
      },
      buyAmountLimit(event) {
        let canBuy = this.currentBuyItem.canBuyAmount,
         {numMax, numMin, numScale, buyFeeRate, priceScale} = this.currentMarket;
        let value = event.srcElement ? event.srcElement.value : event.target.value;
        value = this.filterValue(value, event, 8, numScale);
        let numValue = Number(value);
        // numMax 最大买入量大于0就做限制
        if(this.token) {
          if(numMax > 0) {
            if(numMax > canBuy) {
              if(numValue >= canBuy) {
                value = canBuy.toString();
              }
            }else{
              if(numValue >= numMax) {
                value = numMax.toString();
              }
            }
          }else{
            if(numValue >= canBuy) {
              value = canBuy.toString();
            }
          }
        }
        this.currentBuyItem.amount = value;
        this.sliderRate.buy = value / canBuy * 100;
        this.sliderRate.rateBuyAmount = (this.currentBuyItem.price * value).toFixed(priceScale);
      },
      sellAmountLimit(event) {
        let canSell = this.userAccount.sellAmount,
          {numMax, numMin, numScale, sellFeeRate, priceScale} = this.currentMarket;
        let value = event.srcElement ? event.srcElement.value : event.target.value;
        value = this.filterValue(value, event, 8, numScale);
        let numValue = Number(value);
        if(this.token) {
          if(numMax > 0) {
            if(numMax > canSell) {
              if(numValue >= canSell) {
                value = canSell.toString();
              }
            }else{
              if(numValue >= numMax) {
                value = numMax.toString();
              }
            }
          }else{
            if(numValue >= canSell) {
              value = canSell.toString();
            }
          }
        }
        this.currentSellItem.amount = value;
        this.sliderRate.sell = value / canSell * 100;
        this.sliderRate.rateSellAmount = (this.currentSellItem.price * value).toFixed(priceScale);
      },
      //交易提交
      createOrder(type) {
        // type 1 买入 type 2 卖出
        if(!this.token) {
          this.$message({
            message: this.$t('m.unlogin'),
            type: 'error'
          });
          this.$router.push("/login");
        }else{

          let {numMin, numMax, symbol} = this.currentMarket;
          let {price, amount} = type == 1 ? this.currentBuyItem : this.currentSellItem;

          if(type === 1) {
            if(numMin > 0 && amount < numMin) {
              this.currentBuyItem.amount = numMin.toString();
              this.$message({
                message: this.$t('m.trade.minBuyAmountError'),
                error: 'error'
              });
              return;
            }
            if(numMax > 0 && amount > numMax) {
              this.currentBuyItem.amount = numMax.toString();
              this.$message({
                message: this.$t('m.trade.maxBuyAmountError'),
                error: 'error'
              });
              return;
            }

            if(this.sliderRate.rateBuyAmount > this.userAccount.buyAmount) {
              this.$message({
                message: this.$t('m.amountError'),
                type: 'error'
              });
              return;
            }
          }else if(type === 2) {
            if(numMin > 0 && amount < numMin) {
              this.currentSellItem.amount = numMin.toString();
              this.$message({
                message: this.$t('m.trade.minSellAmountError'),
                error: 'error'
              });
              return;
            }
            if(numMax > 0 && amount > numMax) {
              this.currentSellItem.amount = numMax.toString();
              this.$message({
                message: this.$t('m.trade.maxSellAmountError'),
                error: 'error'
              });
              return;
            }
            if(this.currentSellItem.amount > this.userAccount.sellAmount) {
              this.$message({
                type   : "error",
                message: this.$t('m.amountError')
              })
              return;
            }
          }

          if(price > 0 && amount > 0) {
            //提交订单
            tradeApi.serverCreateOrder(price, symbol, type, amount, this.token).then((res) => {
              this.$message({
                message: this.$t('m.trade.entrustSuccess'),
                type: 'success'
              });
              if(type === 1) {
                this.currentBuyItem.price = '';
                this.currentBuyItem.amount = '';
              } else {
                this.currentSellItem.price = '';
                this.currentSellItem.amount = '';
              }
              setTimeout(() => {
                this.refreshRecord()
              }, 3000)
            }).catch((res) => {
              this.$message({
                message: res.errmsg,
                type: 'error'
              });
            })
          } else {
            this.$message({
              message: this.$t('m.trade.plzEnterPriceAndAmount'),
              type: 'error'
            });
          }
        }
      },
      formatNumber(value, scale) {
        let result = ['',''];
        let num = value.toString();
        if(num.indexOf('.') !== -1) {
          let arr = num.split('.');
          result[0] = arr[0];
          if(arr[1].length === scale) {
            result[0] += '.'+arr[1];
          }else if(arr[1].length > scale) {
            result[1] = '.';
            result[1] += arr[1].substring(0, scale-1);
          }else if(arr[1].length < scale) {
            let zeros = (scale+1) - arr[1].length;
            result[0] += '.'+arr[1];
            result[1] = Array(zeros).join(0);
          }
        }else{
          result[0] = num;
          result[1] = '.'+(Array(scale+1).join(0));
        }
        return '<i class="test">' + result[0] + '</i>' + result[1];
      },
      turnoverPageChange(currentPage) {
        this.turnoverPageQuery.page = currentPage
        this._serverGetTurnoverOrderList()
      },
      entrustPageChange(currentPage) {
        this.entrustPageQuery.page = currentPage
        this._serverGetEntrustOrderList()
      },
      _serverGetTurnoverOrderList() {
        if (this.token) {
          tradeApi.serverGetTurnoverOrderList(this.turnoverPageQuery.page, this.turnoverPageQuery.size, this.currentMarket.symbol, this.token).then((res) => {
            console.log("历史委托：", res);
            let result = res.data;
            this.turnoverPageQuery.page = result.current
            this.turnoverPageQuery.totalRecords = result.total
            this.turnoverTbData = result.records
          })
        }
      },
      async _serverGetEntrustOrderList() {
        if (this.token) {
          let res = await tradeApi.serverGetEntrustOrderList(this.entrustPageQuery.page, this.entrustPageQuery.size, this.currentMarket.symbol, this.token)
          let result = res.data;
          this.entrustPageQuery.page = result.current;
          this.entrustPageQuery.totalRecords = result.total;
          console.log("未完成委托：", result);
          this.entrustTbData = result.records
        }
      },
      //撤销定单
      cancelOrder(orderId) {

        this.$confirm(this.$t('m.trade.cancelHint'), this.$t('m.prompt'), {
          confirmButtonText: this.$t('m.yes'),
          cancelButtonText: this.$t('m.no'),
          type: 'warning'
        }).then(() => {
          tradeApi.serverCancelOrder(orderId, this.token).then((res) => {
            this.$message({
              message: this.$t('m.trade.cancelSuccess'),
              type: 'success'
            });
            this._serverGetEntrustOrderList();
          });
        });
      },
      async _getMarkList() {
        try {
          let res = await homeApi.getMarketListNew();
          if (this.token) {
            let data = await this.getFavoriteData();
            this.marketList = res.data.concat(data)
          } else {
            this.marketList = res.data;
          }
          console.log("全部市场",this.marketList)
          this.activeName = this.marketList[0].areaName;
          this.subscribeMarkets(this.activeName);
          this.currentMarket = this.marketList[0].markets[0]
          if(this.symbol !== undefined){
             if(this.getCurrentMarket()!=null){
               this.currentMarket = this.getCurrentMarket()
             }
          }
          this.marketChange(this.currentMarket);
        } catch (e) {
        }
      },
      getCurrentMarket(){
        for(let i =0;i <this.marketList.length;i++){
            for(let j =0 ; j< this.marketList[i].markets.length;j++){
              if(this.marketList[i].markets[j].symbol === this.symbol){
                return this.marketList[i].markets[j]
              }
            }
        }
        return null
      },
      _getUserAccount(symbol) {

        if (this.token) {
          tradeApi.getUserAccount(symbol, this.token).then((res) => {
            this.userAccount = res.data;
          })
        }
      },
      subscribeDepths(kline) {
        let symbol = this.currentMarket.symbol.toLowerCase();
        let mergeDepth = this.chooseMergeDepth;

        this.$socket.subscribe(`market.${symbol}.depth.${mergeDepth}`, 'market-depth');

        this.$socket.on('market-depth', (data) => {
          this.depthsData = data.tick;
          if(kline && kline.contentWindow) {
            kline.contentWindow.set_current_depth(this.depthsData);
          }
          this.processDepthData()
        })
      },
      processDepthData(){
        let {asks, bids} = this.depthsData;
        let {priceScale, buyFeeRate, sellFeeRate,numScale} = this.currentMarket;
        if(this.currentBuyItem.price === '' && bids.length > 0 && this.currentBuyItem.canBuyAmount === '') {
          let {buyAmount} = this.userAccount;
          if(asks[0]){
            this.currentBuyItem.price = asks[0].price;
            //交易面板 计算可交易量
            // 扣除手续费之后的可买量算法
            this.currentBuyItem.canBuyAmount = (buyAmount / (1 + buyFeeRate) / this.currentBuyItem.price).toFixed(numScale);
          }
        }
        if(this.currentSellItem.price=== '' && asks.length > 0 && this.currentSellItem.canSellAmount === '') {
          let {sellAmount} = this.userAccount;
          if(bids[0]){
            let bidPrice = bids[0].price;
            this.currentSellItem.price = bidPrice;
            this.currentSellItem.canSellAmount = (bidPrice * sellAmount) * (1 + sellFeeRate).toFixed(numScale);
          }
        }
        // console.log("深度数据",asks,bids)
        this.domWidth = document.querySelector('#depth-data-item').clientWidth;
        this.asksDepth = this.bidsDepth = 0;
        for (let i = 0; i < asks.length; i++) {
          this.asksDepth = Math.max(this.asksDepth, asks[i].volume);
        }
        for (let i = 0; i < bids.length; i++) {
          this.bidsDepth = Math.max(this.bidsDepth, bids[i].volume);
        }
        this.depthsData.asks.reverse();
      },
      unsubscribeDepths() {
        let symbol = this.currentMarket.symbol.toLowerCase();
        let mergeDepth = this.chooseMergeDepth;
        this.$socket.unsubscribe(`market.${symbol}.depth.${mergeDepth}`, 'market-depth');
      },
      subscribeTrades() {
        let symbol = this.currentMarket.symbol.toLowerCase();
        this.$socket.subscribe(`market.${symbol}.trade.detail`, 'market-trade');
        this.$socket.on('market-trade', (data) => {
          // console.log("成交记录",data)
          this.tradesData = data.data;
        })
      },
      unsubscribeTrades() {
        let symbol = this.currentMarket.symbol.toLowerCase();
        this.$socket.unsubscribe(`market.${symbol}.trade.detail`, 'market-trade');
      },
      subscribePath(market) {
        market = market.toLowerCase();
        return `market.${market}.ticker`;
      },


      /**
       * 订阅全部的市场对
       */
      subscribeAllMarkets() {
        this.$socket.subscribe("market.ticker", 'all-market-area');
        this.$socket.on('all-market-area', (data) => {
          // console.log("全部市场订阅", data)
          if (data.markets) {
            let filteredFavorites = data.markets.filter((item) => {
              let itemSymbol = item.symbol.toLowerCase();
              return this.userFavorites.indexOf(itemSymbol) !== -1;
            })
            // console.log("过滤后的",filteredFavorites )
            let len = this.marketList.length;
            this.marketList[len - 1].markets = filteredFavorites;
          }
        });
      },
      unsubscribeAllMarkets() {
        this.$socket.unsubscribe("market.ticker", 'all-market-area');
      },

      subscribeMarkets(market) {
        this.$socket.subscribe(this.subscribePath(market), 'market-area');
        this.$socket.on('market-area', (data) => {
          // console.log("所有市场socket:",data)
          let index = this.activeIndex;
          this.marketList[index].markets = data.markets;
        });
      },
      unsubscribeMarkets(market) {
        this.$socket.unsubscribe(this.subscribePath(market), 'market-area');
      },
      // 订阅未完成委托
      subscribeEntrust() {
        this.$socket.subscribe(`order.pending.update`, 'order-pending', this.token);
        this.$socket.on('order-pending', (data) => {
          // console.log("未完成委托事件", data)
          this._serverGetEntrustOrderList()
        })
      },
      unsubscribeEntrust() {
        this.$socket.unsubscribe(`order.pending.update`, 'order-pending');
      },
      //订阅历史委托
      subscribeTurnover() {
        this.$socket.subscribe(`order.finished.update`, 'order-finished', this.token);
        this.$socket.on('order-finished', (data) => {
          // console.log("成交记录事件", data)
          this._serverGetTurnoverOrderList()
        })
      },
      unsubscribeTurnover() {
        this.$socket.unsubscribe(`order.finished.update`, 'order-finished');
      },
    },
  }
</script>
