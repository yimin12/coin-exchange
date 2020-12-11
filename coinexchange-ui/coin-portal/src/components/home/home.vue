<template>
  <div class="home-main" v-wechat-title="$t('m.navheader.home')" id="canvasNest">
    <NavHeader activeTab="1" :nobackground="true"></NavHeader>
    <!--banner-->
    <div class="img-container" ref="imgContainerRef">
			<div class="site-title">
				<h4>{{$t('m.home.siteTitle')}}</h4>
				<p>{{$t('m.home.siteTitleDesc')}}</p>
			</div>
			<img src="./img/banner_bg.jpg"/>
			
			 <div class="carousel-container" ref="swiperRef">
			  <swiper :options="swiperOption">
			    <!-- slides -->
			    <swiper-slide v-for="(item,index) in slideSmallImg" :key="index">
			      <a target="_blank" class="slide-image" :href="item.url" :alt="item.name"
			         :title="item.name">
			        <img :src="item.value">
			      </a>
			    </swiper-slide>
			    <!-- Optional controls -->
			    <div class="swiper-pagination" slot="pagination"></div>
			    <div class="swiper-button swiper-button-prev" slot="button-prev"></div>
			    <div class="swiper-button swiper-button-next" slot="button-next"></div>
			    <div class="swiper-scrollbar" slot="scrollbar"></div>
			  </swiper>
			</div>
    </div>

		<!-- <swiper :options="swiperOption" v-for="i in 2" :key="i" style="margin-bottom: 1rem;">
        <swiper-slide v-for="i in 9" :key="i">Slide {{ i + 1 }}</swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper> -->
    <!--content swiper table-->
    <div class="content">

      <!--swiper-->
     

      <!--<table> box-shadow: #424040 0px 0px 30px 5px ;-->
      <el-tabs v-model="activeName" type="card" @tab-click="handleTabClick">
        <el-tab-pane :key="item.areaName" :label="item.areaName === '自选'?favoriteMap[lang]:item.areaName"
                     :name="item.areaName"
                     v-for="item in marketList">
          <el-table
            :data="item.markets"
            class="home-table"
            row-class-name="table-row-class"
            header-cell-class-name="table-header-class"
            @row-click="handleRowClick"
          >
            <el-table-column
              lable="收藏"
              width="50"
            >
              <template slot-scope="scope">
                <div class="favorite_star">
                  <span class="el-rate__item favorite_this"
                        @click.stop="userFavorite(scope.row, $event)">
                    <i class="el-rate__icon" :class="[activeFavorite(scope.row.symbol)]"></i>
                  </span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('m.home.tradePairs')"
              align="center"
              width="180"
            >
              <template slot-scope="scope">
                <div class="tb-coin-name">
                  <img :src="scope.row.image" class="coin-img" alt="">
                  <span style="margin-left: 10px">{{ scope.row.name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :formatter="formatPrice"
              prop="price"
              :label="$t('m.home.price')"
              align="center"
              width="150"
							sortable
            >
              <template slot-scope="scope">
                <span v-if="scope.row.change>0" class="change-rise">{{scope.row.price}}</span>
                <span v-else-if="scope.row.change==0">{{scope.row.price}}</span>
                <span class="change-fall" v-else>{{scope.row.price}}</span>
              </template>
            </el-table-column>
						<el-table-column
						  prop="high"
						  :label="$t('m.home.maxPrice')"
						  align="center"
						  width="150"
							sortable
							>
						</el-table-column>
						<el-table-column
						  prop="low"
						  :label="$t('m.home.minPrice')"
						  align="center"
						  width="150"
							sortable
							>
						</el-table-column>
            <el-table-column
              prop="volume"
              :label="$t('m.home.volume')"
              align="center"
              width="150"
							sortable>
            </el-table-column>
            <el-table-column
              prop="amount"
              :label="$t('m.home.turnoverAmount')"
              align="center"
              width="250"
							sortable
            >
            </el-table-column>
            <el-table-column
              prop="change"
              :label="$t('m.home.change')"
              align="center"
              width="120"
            >
              <template slot-scope="scope">
                <span v-if="scope.row.change>0" class="change-rise">{{scope.row.change}}%</span>
                <span v-else-if="scope.row.change==0">{{scope.row.change}}%</span>
                <span class="change-fall" v-else>{{scope.row.change}}%</span>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    
		</div>
		<div style="clear: both;"></div>
		
		<div class="light-content">
			
				<div class="light-title">
					<h3>{{$t('m.home.lightTitle')}}</h3>
					<h4>{{$t('m.home.lightTitleDesc')}}</h4>
				</div>
			<div class="exchange-light">
				<div class="item">
					<img src="../../assets/home/light-1.png"/>
					<h4>{{$t('m.home.safe')}}</h4>
					<p>{{$t('m.home.safeDesc')}}</p>
				</div>
				<div class="item m-item">
					<img src="../../assets/home/light-2.png"/>
					<h4>{{$t('m.home.auth')}}</h4>
					<p>{{$t('m.home.authDesc')}}</p>
				</div>
				<div class="item m-item">
					<img src="../../assets/home/light-3.png"/>
					<h4>{{$t('m.home.stable')}}</h4>
					<p>{{$t('m.home.stableDesc')}}</p>
				</div>
			</div>
		</div>
		
		<div style="clear: both;"></div>

    <mFooter></mFooter>
    <div :class="['downapp',showDownapp?'show':'hide']">
      <div class="clearfix">
        <div class="app-info pull-left">
          <img :src="app_logo" alt="ZB APP">
          <p>APP 浏览行情酣畅交易指尖掌握</p>
        </div>
        <div class="app-btn pull-right">
          <a href="http://bxx.com/app/" target="_blank">立即下载</a>
          <span @click="handleAppClose">关闭</span>
        </div>
        <!--<div class="app-close" @onclick="handleAppClose"></div>-->
      </div>
    </div>

		<div class="service-box">
			<div class="service-wrap">
				<transition name="" mode="" enter-active-class="animated zoomInLeft" leave-active-class="animated zoomOutRight">
					<div class="kefu-icon service-icon sprites-service" id="service-icon" v-show="isShowServiceIcon" style="display: block;" @click="isShowServiceIcon = !isShowServiceIcon"></div>
				</transition>
				<transition name="" mode="" enter-active-class="animated zoomInRight" leave-active-class="animated zoomOutRight">
					<div class="service-main" id="service-main" v-show="!isShowServiceIcon">
						<div class="se-block" id="se-title" @click="isShowServiceIcon = !isShowServiceIcon">
							<span class="kefu-icon title-icon sprites-service"></span>{{$t('m.footer.putAway')}} &gt;&gt;</div>
						<div class="se-list">
							<router-link to="/s/order" class="list-item">
							<span class="icons-info"></span><a href="" target="_blank">{{$t('m.footer.order')}}</a>
							</router-link>
						</div>
					</div>
				</transition>
			</div>
		</div>
		
  </div>


</template>

<script type="text/ecmascript-6">
  import {mapGetters, mapMutations} from 'vuex'
  import {swiper, swiperSlide} from 'vue-awesome-swiper'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import {util} from 'common/js/util'
  import {OK} from 'api/config'
  import {homeApi} from 'api/home'
  import {tradeApi} from 'api/trade'
  import {serverAddFavorite, serverDeleteFavorite,mineData} from 'api/usercenter'
  import 'swiper/dist/css/swiper.css'
	import CanvasNest from 'canvas-nest.js';

  export default {
    name: 'carrousel',
    data() {
      return {
        // swiper
        slideSmallImg: [],
        swiperOption: {
          slidesPerView: 3,
          spaceBetween: 20,
          autoplay: {
            delay: 3000,
            disableOnInteraction: false
          },
          pagination: {
            el: '.swiper-pagination',
            clickable: true
          },
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
          }
        },
        // table
        activeName: '',
        activeIndex: 0,
        marketList: [],
        userFavorites: [],
        // downapp
        showDownapp: false,
        ios_qrcode: require("../../assets/home/ios_qrcode.png"),
        app_logo: require("../../assets/home/app-logo.png"),

        // favorite
        favoriteMap: {
          "zh-CN": "自选",
          "zh-HK": "自選",
          "en-US": "Optional"
        },
				
				isShowServiceIcon: true
      }
    },
    // 计算属性
    computed: {
      ...mapGetters(['token', 'lang']),
    },
    // 生命周期 -- HTML编译完成触发
    mounted() {
      this._getBanner();
      // this._getDocuments();
      // let isMobile = util.isMobile();
      // if (isMobile) {
      //   this.showDownapp=true;
      // }
      this._getMarketList();
      
			this.$nextTick(() => {
        let imgContainer = this.$refs.imgContainerRef
        let bigImg = this.$refs.bigImg
        // bigImg.onload = ()=>{
        //  imgContainer.style="min-height:0"
        // }
      })
			
			const config = {
				color: '255,255,255',
				count: 500,
				zIndex: 999
			};

			const cn = new CanvasNest(document.getElementById("canvasNest"), config);
	
    },
    // 生命周期 -- 实例销毁
    destroyed() {
      this.unsubscribeMarkets(this.activeName)
      this.unsubscribeAllMarkets()
    },
    // 事件处理
    methods: {
			serviceHandle(){
				
			},
      handleRowClick(row) {
        console.log("row", row)
        this.$router.push({
          path: "/trade",
          query: {
            symbol: row.symbol
          }
        })
      },

      async handleTabClick(tab) {
        if (tab.name === '自选') {
          //当用户登录后才订阅全部市场的
          this.subscribeAllMarkets()
        } else {
          this.activeIndex = parseInt(tab.index);
          this.subscribeMarkets(this.activeName);
          this.unsubscribeAllMarkets()
        }
      },
      async _getBanner() {
        let res = await homeApi.getBanner();
        let data = res.data;
        if (data.length > 0) {
          this.slideSmallImg = data;
        }else{
          this.$refs.swiperRef.style="display:none"
        }
      },
      async _getMarketList() {
        try {
          let res = await homeApi.getMarketListNew();

          if (this.token) {
            let data = await this.getFavoriteData();
            // let data = [];
            this.marketList = res.data.concat(data);

          } else {
            this.marketList = res.data;
          }
          this.activeName = this.marketList[0].areaName;
          this.subscribeMarkets(this.activeName);


        } catch (e) {
        }
      },
      async getFavoriteData() {
        this.userFavorites = [];
        let item = await tradeApi.serverFavorite(this.token);
        item.data[0].markets.map(v => {
          this.userFavorites.push(v.symbol.toLowerCase());
        });
        return item.data;
      },
      _getDocuments() {
        homeApi.getDoucments().then(res => {
        })
      },
      handleAppClose() {
        this.showDownapp = false;
      },
      async userFavorite(row, event) {
        let symbol = row.symbol.toLowerCase();
        let el = event.srcElement ? event.srcElement : event.target;
        if (this.token) {
          if (el.classList.contains('el-icon-star-off')) {
            await this.addFavorite(symbol);
            el.className = el.className.replace('star-off', 'star-on');
            this.$message({
              message: this.$t('m.addFavorite'),
              type: 'success'
            });
          } else {
            await this.deleteFavorite(symbol);
            el.className = el.className.replace('star-on', 'star-off');
            this.$message({
              message: this.$t('m.delFavorite'),
              type: 'success'
            });
          }
          // 刷新收藏列表
          this.getFavoriteData()
        } else {
          this.$message({
            type: "info",
            message: this.$t('m.unlogin')
          })
        }
      },
      async addFavorite(symbol) {
        return await serverAddFavorite(symbol, 1, this.token);
      },
      async deleteFavorite(symbol) {
        return await serverDeleteFavorite(symbol, 1, this.token);
      },
      // 激活收藏样式
      activeFavorite(symbol) {
        symbol = symbol.toLowerCase();
        return this.userFavorites.indexOf(symbol) !== -1 ? 'el-icon-star-on' : 'el-icon-star-off';
      },
      formatPrice(row) {
        return "$ " + row.currentPrice
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
            // console.log("过滤后的", filteredFavorites)
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
          // console.log("市场订阅",data.markets)
          if (data.markets) {
            let i = this.activeIndex;
            this.marketList[i].markets = data.markets;
          }
        });
      },
      unsubscribeMarkets(market) {
        this.$socket.unsubscribe(this.subscribePath(market), 'market-area');
      }
    },
    components: {
      NavHeader,
      swiper,
      swiperSlide,
      mFooter
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  .home-main {
    .favorite_star {
      height 20px
      line-height 1
      .favorite_this {
        cursor pointer
        .el-icon-star-on,
        .el-rate__icon:hover {
          color #d0960e
        }
      }
    }
    .table-row-class {
      background #1b1f28
    }
    .el-tabs--card > .el-tabs__header {
      border-bottom: none;
      margin 0
    }
    .el-tabs--card > .el-tabs__header .el-tabs__nav {
      border: none;
      border-radius 0
      background: #373E4F
      border-color: #373E4F
      width 100%
      display flex;
      justify-content: space-between;
    }

    .el-tabs--card > .el-tabs__header .el-tabs__item {
      color #fff
      font-size 16px
      height auto
      border 0
      display: block;
      width: 100%;
      text-align center
      padding 5px 0
    }

    .el-tabs--card > .el-tabs__header .el-tabs__item:first-child,
    .el-table .el-table__header tr:first-child > th:first-child {

    }
    .el-tabs--card > .el-tabs__header .el-tabs__item:last-child,
    .el-table .el-table__header tr:last-child > th:nth-child(5) {

    }
    .el-tabs--card > .el-tabs__header .el-tabs__item:hover,
    .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
      background #5A647E
      border-color #5A647E
      color: #fff

    }

    .el-table .el-table__header,
    .el-table .el-table__header tr {
      background-color: #373e4f
    }

    .el-table .el-table__header th {
      background-color: #373e4f
      color #AAABB1
      font-size 16px
      border-bottom: none
    }

    .el-table--enable-row-hover .el-table__body tr:hover > td {
      background-color: #5a647e
    }

    .el-table td {
      color: #fff
      border-bottom: 1px solid #454545
    }

    .el-table th {
      padding: 2px 0
    }
    .el-table td {
      min-height: 30px
    }
    .el-table__empty-block,
    .el-table--border::after, .el-table--group::after, .el-table::before {
      background-color: #1b1f28
    }

    .swiper-pagination-bullet {
      background-color #8597c0
    }

    .swiper-pagination-bullet-active {
      background: #7392FF
    }

    .slide-image {
      width 100%
      display block
      img {
        width 100%
      }
    }

    .show {
      display: block !important;
    }
    .hide {
      display: none !important;
    }
  }


</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  @import "~common/stylus/mixin"

  .home-main {
    background: #0b0e1f;
    position relative
    .scanapp {
      display flex
      justify-content center
      flex-direction column
      align-items center
      position: fixed;
      right: 13px;
      bottom: 30%;
      width: 100px;
      height: 100px;
      background #fff;
      /*color:#D00;*/
      font-size: 12px;
      img {
        width: 80px;
        height: 80px;
      }
      z-index: 99999;
    }
    .downapp {
      display: none;
      background-color: rgba(13, 14, 14, 0.75);
      padding: 10px;
      padding-left: 20px;
      padding-right: 20px;
      position: fixed;
      left: 0;
      right: 0;
      bottom: 0;
      z-index: 920;
      .app-info {
        line-height: 1.5;
        height: 1.5;
        color: #fff;
        font-size: 30px;
        text-align: left;
        float: left;
        img {
          width: 50px;
          height: 50px;
          border-radius: 10px;
          overflow: hidden;
          float: left;
          box-shadow: 0 5px 5px #333;
          margin-right: 10px;
        }
        p {
          margin: 0;
          padding: 0;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      .app-btn {
        line-height: 1.5;
        height: 1.5;
        min-width: 80px;
        float: right;
        a, span {
          color: #fff;
          font-size: 30px;
          margin-left 10px;
        }

      }
      .app-close {
        width: 24px;
        height: 24px;
        line-height: 24px;
        /* border-radius: 50%;
         background-color: #de211d;*/
        text-align: center;
        /* color: #fff;*/
        position: absolute;
        top: -12px;
        right: 15px;
        z-index: 1010;
      }
    }
    .m-header {
      position: absolute;
      top: 0;
      z-index: 1
    }
    .img-container {
      overflow hidden
      position: relative;
      height 550px

      >img {
        width: 100%;
        height 550px


        display block
      }
			.site-title{
				width: 100%;
				position: absolute;
				top: 150px;
				color: #ffffff;
				text-align:center;
				
				h4{
					font-size: 38px;
				}
				p{
					padding-top: 25px;
					font-size: 25px;
				}
			}
      .center-container {
        margin-top 0px
        .dowload{
          >a{
            display inline-block
            padding 15px 0
            width 250px
            text-align center
            vertical-align middle
            font-size 18px
            color #fff
            img{
              display inline-block
              vertical-align middle
              padding 0 10px 0 0
            }
          }
          .ios{
            width 19px
            height 21px
          }
          .android{
            width 18px
            height 21px
          }
          .windows{
            width 17px
            height 17px
          }
        }
        color: #fff;
        text-align: center;
        transformCenter()
        /*margin 150px auto 0 auto;*/
        .title {
          font-size 65px
        }
        .subtitle {
          line-height 150px
          font-size 24px
          margin-top 20px
        }
        .btn-container {
          margin 0 auto
          margin-top 60px;
          display flex
          justify-content center
          font-size 24px
          width: 360px;
          .btn {
            min-width 170px
            height 50px
            line-height: 50px
            text-align center
            border-radius 3px
            cursor pointer
            margin-left 8px
            margin-right 8px
          }
          .btn-left {
            border: 1px solid #fff
            &:hover {
              border-color #7392FF
              color #7392FF
              box-shadow 0px 0px 10px 6px rgba(115, 146, 255, 0.5)
            }
          }
          .btn-right {
            background #7392FF
            &:hover {
              box-shadow 0px 0px 10px 6px rgba(115, 146, 255, 0.5)
            }
          }
        }

      }
      /*Banner缩略图*/
      .swiper-container {
        /*background #1B1F28*/
        /*height 150px*/
        width 1200px;
        margin 0 auto
        .swiper-slide {

          div {
            /*width: 380px;*/
            height: 130px;
            position: relative;
            overflow hidden
            border-radius 5px
            background rgba(255,255,255,0.5)
            img {
              display block
              height 100%
            }

          }

        }
        .swiper-slide:hover div {
          border-radius 5px
        }

      }
      .carousel-container {
        position: absolute;
        bottom 0
        width 100%;
				z-index: 9999;
        .controls{
          width 1300px
          margin 0 auto
          height 100%
          position absolute
          top 0
          right 0
          bottom 0
          left 0
          margin auto;
        }
        .fade-enter-active, .fade-leave-active {
          transition: opacity .5s;
        }
        .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
        {
          opacity: 0;
        }

        .slide-img-origin {
          position absolute
          left 0
          top 0

        }
        .swiper-container {
          padding 30px 0 40px 0
        }

        .swiper-scrollbar {
          display none
        }

        .swiper-button {
          background-repeat no-repeat
          width 29px
          height 74px
          margin-top -36px
          border-radius 3px
          background-size: 15px 25px;
          z-index 999
          /*&:not(.swiper-button-disabled):hover {
            background-color #3e4657
          }*/
        }

        .swiper-button-prev {
          /*background-image url("/static/swiper/left-arrow.png")*/
          background url(../../common/image/prve.png) no-repeat center
          background-size 29px 74px
          left: 10px;
          right: auto;
        }
        .swiper-button-next {
          background url(../../common/image/next.png) no-repeat center
          background-size 29px 74px
          left: auto;
          right: 10px;
        }
        .swiper-slide {
          img {
            margin 0 auto
          }
        }
      }


    }

    .content {
      width 1200px
      margin 0 auto
      z-index: 99999;
      position: relative;
			padding-top: 20px;
      .el-tabs {

        margin-top 25px
        padding-bottom: 55px;
        .coin-img {
          width 30px
          height 30px
          vertical-align middle
          float left
        }
        .change-rise {
          color: $color-global-buy
        }
        .change-fall {
          color: $color-global-sell
        }
      }

      .home-table {
        /* font-weight 900
         border: 1px solid $color-global-el-tb-border
         */
        font-size 16px
        .tb-coin-name {
          color #fff
          cursor: pointer
        }

      }

      .el-table td, .el-table th.is-leaf {
        text-align center
      }

      .hover-btn {
        border-color: #409EFF;
        color: #409EFF;
      }
    }
  }
  .content-tab{
    display: flex;
    justify-content space-between;
    background :#373e4f;
    padding:20px 0;
    >div{
      text-align center;
      font-size:16px;
      color: #fff;
      width 50%;
      >a{
        color #fff;
      }
    }
    .tab-l{
      border-right:1px #fff solid;
    }
  }
  .swiper-pagination{
    display none
  }
  .intro{
    text-align center
    padding 50px 0
    .intro-tit{
      font-size 24px
      color: #fff;
    }
    .intro-subtit{
      padding 50px 0
      font-size 18px
      color: #fff
    }
    .intro-client{
      display flex
      justify-content space-around
      .client-list{
        text-align center
        color: #fff
        >img{
          margin-bottom 25px
        }
        >span{
          display block
          width 130px
          height 30px
          line-height 30px
          border 1px #fff solid
          border-radius 15px
        }
      }
    }
  }
	.light-content{
		width: 100%;
		height: 750px;
		background: #0b0e1f;
		.light-title{
			width: 1500px;
			margin: 0px auto;
			color: #ffffff;
			text-align: center;
			padding: 80px 0px 20px 0px;
			h3{
				font-size: 25px;
				font-weight:bold;
				padding-bottom: 50px;
			}
		}
		.exchange-light{
			width: 1500px;
			margin: 0px auto;
			padding-top: 60px;
			clear:both;
			.prodect {
					position: relative;
					top: 9px;
					margin-right: 10px;
					display: inline-block;
					width: 34px;
					height: 34px;
					background: url(../../assets/home/prodect.png);
			}
			.servel-ge {
					position: relative;
					top: 9px;
					margin-right: 10px;
					display: inline-block;
					width: 34px;
					height: 34px;
					background: url(../../assets/home/servel-ge.png) no-repeat;
			}
			.option-time {
					position: relative;
					top: 9px;
					margin-right: 10px;
					display: inline-block;
					width: 34px;
					height: 34px;
					background: url(../../assets/home/optionTime.png) no-repeat;
			}
			.bi-xin {
					position: relative;
					top: 9px;
					margin-right: 10px;
					display: inline-block;
					width: 34px;
					height: 34px;
					background: url(../../assets/home/bi-xin.png);
			}
			.block-coin {
					position: relative;
					top: 9px;
					margin-right: 10px;
					display: inline-block;
					width: 34px;
					height: 34px;
					background: url(../../assets/home/block-coin.png);
			}
			.target-pri {
					position: relative;
					top: 9px;
					margin-right: 10px;
					display: inline-block;
					width: 34px;
					height: 34px;
					background: url(../../assets/home/target-pri.png);
			}
			.item{
				float:left;
				width: 500px;
				color: #fff;
				padding-top: 20px;
				text-align:center;
			}
			h4{
				font-weight: bold;
				height: 20px;
				line-height: 20px;
			}
			p{
				padding-top: 40px;
				font-size: 13px;
				width: 300px;
				margin: 0 auto;
				line-height: 25px;
			}
		}
		
	}
	.forever{
		color: #fff;
		.list{
			width: 1200px;
			margin: 0px auto;
			clear: both;
			
			.item{
				width: 400px;
				float: left;
				padding-bottom: 80px;
				margin-bottom: 50px;
				.cont-btc {
						display: block;
						width: 132px;
						height: 100px;
						background: url(../../assets/home/BTC.png) no-repeat;
						margin: 0 auto;
						margin-bottom: 28px;
				}
				.cont-eos {
						display: block;
						width: 132px;
						height: 100px;
						background: url(../../assets/home/EOS.png) no-repeat;
						margin: 0 auto;
						margin-bottom: 28px;
				}
				.cont-eth {
						display: block;
						width: 132px;
						height: 100px;
						background: url(../../assets/home/ETH.png) no-repeat;
						margin: 0 auto;
						margin-bottom: 28px;
				}
				h4{
					text-align: center;
					padding: 20px; 0px;
				}
				p{
					font-size: 14px;
					line-height: 25px;
					padding-left: 20px;
					padding-right: 20px;
				}
			}
		}
		h3{
			text-align: center;
			font-size: 26px;
			padding: 80px; 0px;
		}
	}
	
	.service-box {
		position: fixed;
		right: 0px;
		top: 30%;
		color: #6a6a6a;
		z-index: 9999;
	}

.service-main {
	-moz-border-radius: 5px 0 0 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px 0 0 5px;
	width: 140px;
	overflow: hidden;
	padding: 10px 15px;
	position: absolute;
	background: #fff;
	right: 0px;
	font-size: 13px;
}

.service-wrap {
	position: relative;
	width: 180px
}

.kefu-icon {
	cursor: pointer;
	display: inline-block;
	width: 48px;
	height: 48px;
	background: url("img/kefu.png");
	background-size: 48px 48px;
}

.service-icon {
	position: absolute;
	right: 10px
}

.se-block {
	cursor: pointer;
	height: 35px;
	line-height: 35px;
	padding-bottom: 10px;
	text-align: right;
	color: #808080
}

.se-block:hover {
	color: #1E4C80;
}

.se-block .title-icon {
	float: left;
	margin-top: 3px
	width: 31px;
	height: 31px;
	background-size: 31px 31px;
}

.se-list {
	border-top: 1px solid #e1e1e1;
	padding: 10px 0
}

.se-list span {
	display: inline-block;
	overflow: hidden;
	margin-right: 10px;
	vertical-align: middle;
	width: 20px;
	height: 20px;
	background: url("img/msg.png");
	background-size: 20px 20px;
}
.se-list a {
	color: #6a6a6a
}

.se-list a:hover {
	color: #1E4C80
}
</style>
<!--<style lang="stylus" rel="stylesheet/stylus">-->
  <!--.carousel-container {-->
    <!--z-index: 5;-->
  <!--}-->

  <!--.home-main {-->
    <!--.favorite_star {-->
      <!--height 20px-->
      <!--line-height 1-->
      <!--.favorite_this {-->
        <!--cursor pointer-->
        <!--.el-icon-star-on,-->
        <!--.el-rate__icon:hover {-->
          <!--color #d0960e-->
        <!--}-->
      <!--}-->
    <!--}-->
    <!--.table-row-class {-->
      <!--background #1b1f28-->
    <!--}-->
    <!--.el-tabs&#45;&#45;card > .el-tabs__header {-->
      <!--border-bottom: none;-->
    <!--}-->
    <!--.el-tabs&#45;&#45;card > .el-tabs__header .el-tabs__nav {-->
      <!--border: none;-->
    <!--}-->
    <!--.el-tabs&#45;&#45;card > .el-tabs__header .el-tabs__item {-->
      <!--color #AAABB1-->
      <!--border: 1px solid #AAABB1;-->
      <!--font-size 16px-->
      <!--height: 30px;-->
      <!--line-height: 30px;-->
    <!--}-->

    <!--.el-tabs&#45;&#45;card > .el-tabs__header .el-tabs__item:first-child,-->
    <!--.el-table .el-table__header tr:first-child > th:first-child {-->
      <!--border-radius: 4px 0 0 4px;-->
    <!--}-->
    <!--.el-tabs&#45;&#45;card > .el-tabs__header .el-tabs__item:last-child,-->
    <!--.el-table .el-table__header tr:last-child > th:nth-child(5) {-->
      <!--border-radius: 0 4px 4px 0;-->
    <!--}-->
    <!--.el-tabs&#45;&#45;card > .el-tabs__header .el-tabs__item:hover,-->
    <!--.el-tabs&#45;&#45;card > .el-tabs__header .el-tabs__item.is-active {-->
      <!--background: #7392FF-->
      <!--border-color: #7392FF-->
      <!--color: #fff-->
    <!--}-->

    <!--.el-table .el-table__header,-->
    <!--.el-table .el-table__header tr {-->
      <!--background-color: #1b1f28-->
    <!--}-->

    <!--.el-table .el-table__header th {-->
      <!--background-color: #272b34-->
      <!--color #AAABB1-->
      <!--font-size 16px-->
      <!--border-bottom: none-->
    <!--}-->

    <!--.el-table&#45;&#45;enable-row-hover .el-table__body tr:hover > td {-->
      <!--background-color: #1b1f28-->
    <!--}-->

    <!--.el-table td {-->
      <!--color: #fff-->
      <!--border-bottom: 1px solid #454545-->
    <!--}-->

    <!--.el-table th {-->
      <!--padding: 2px 0-->
    <!--}-->
    <!--.el-table td {-->
      <!--min-height: 30px-->
    <!--}-->
    <!--.el-table__empty-block,-->
    <!--.el-table&#45;&#45;border::after, .el-table&#45;&#45;group::after, .el-table::before {-->
      <!--background-color: #1b1f28-->
    <!--}-->

    <!--.swiper-pagination-bullet {-->
      <!--background-color #8597c0-->
    <!--}-->

    <!--.swiper-pagination-bullet-active {-->
      <!--background: #7392FF-->
    <!--}-->

    <!--.slide-image {-->
      <!--width 100%-->
      <!--display block-->
      <!--img {-->
        <!--width 100%-->
      <!--}-->
    <!--}-->

    <!--.show {-->
      <!--display: block !important;-->
    <!--}-->
    <!--.hide {-->
      <!--display: none !important;-->
    <!--}-->
  <!--}-->


<!--</style>-->
<!--<style scoped lang="stylus" rel="stylesheet/stylus">-->
  <!--@import "~common/stylus/variable"-->
  <!--@import "~common/stylus/mixin"-->

  <!--.home-main {-->
    <!--background #1b1f28;-->
    <!--position relative-->
    <!--.scanapp {-->
      <!--display flex-->
      <!--justify-content center-->
      <!--flex-direction column-->
      <!--align-items center-->
      <!--position: fixed;-->
      <!--right: 13px;-->
      <!--bottom: 30%;-->
      <!--width: 100px;-->
      <!--height: 100px;-->
      <!--background #fff;-->
      <!--/*color:#D00;*/-->
      <!--font-size: 12px;-->
      <!--img {-->
        <!--width: 80px;-->
        <!--height: 80px;-->
      <!--}-->
      <!--z-index: 99999;-->
    <!--}-->
    <!--.downapp {-->
      <!--display: none;-->
      <!--background-color: rgba(13, 14, 14, 0.75);-->
      <!--padding: 10px;-->
      <!--padding-left: 20px;-->
      <!--padding-right: 20px;-->
      <!--position: fixed;-->
      <!--left: 0;-->
      <!--right: 0;-->
      <!--bottom: 0;-->
      <!--z-index: 920;-->
      <!--.app-info {-->
        <!--line-height: 1.5;-->
        <!--height: 1.5;-->
        <!--color: #fff;-->
        <!--font-size: 30px;-->
        <!--text-align: left;-->
        <!--float: left;-->
        <!--img {-->
          <!--width: 50px;-->
          <!--height: 50px;-->
          <!--border-radius: 10px;-->
          <!--overflow: hidden;-->
          <!--float: left;-->
          <!--box-shadow: 0 5px 5px #333;-->
          <!--margin-right: 10px;-->
        <!--}-->
        <!--p {-->
          <!--margin: 0;-->
          <!--padding: 0;-->
          <!--white-space: nowrap;-->
          <!--overflow: hidden;-->
          <!--text-overflow: ellipsis;-->
        <!--}-->
      <!--}-->
      <!--.app-btn {-->
        <!--line-height: 1.5;-->
        <!--height: 1.5;-->
        <!--min-width: 80px;-->
        <!--float: right;-->
        <!--a, span {-->
          <!--color: #fff;-->
          <!--font-size: 30px;-->
          <!--margin-left 10px;-->
        <!--}-->

      <!--}-->
      <!--.app-close {-->
        <!--width: 24px;-->
        <!--height: 24px;-->
        <!--line-height: 24px;-->
        <!--/* border-radius: 50%;-->
         <!--background-color: #de211d;*/-->
        <!--text-align: center;-->
        <!--/* color: #fff;*/-->
        <!--position: absolute;-->
        <!--top: -12px;-->
        <!--right: 15px;-->
        <!--z-index: 1010;-->
      <!--}-->
    <!--}-->
    <!--.m-header {-->
      <!--position: absolute;-->
      <!--top: 0;-->
      <!--z-index: 1-->
    <!--}-->
    <!--.img-container {-->
      <!--position: relative;-->
      <!--min-height:900px-->
      <!--img {-->
        <!--width: 100%;-->
      <!--}-->
      <!--.center-container {-->
        <!--width: 600px;-->
        <!--height: 255px-->
        <!--color: #fff;-->
        <!--text-align: center;-->
        <!--transformCenter()-->

        <!--.title {-->
          <!--font-size 50px-->
        <!--}-->
        <!--.subtitle {-->
          <!--font-size 24px-->
          <!--margin-top 20px-->
        <!--}-->
        <!--.btn-container {-->
          <!--margin 0 auto-->
          <!--margin-top 60px;-->
          <!--display flex-->
          <!--justify-content center-->
          <!--font-size 24px-->
          <!--width: 360px;-->
          <!--.btn {-->
            <!--min-width 170px-->
            <!--height 50px-->
            <!--line-height: 50px-->
            <!--text-align center-->
            <!--border-radius 3px-->
            <!--cursor pointer-->
            <!--margin-left 8px-->
            <!--margin-right 8px-->
          <!--}-->
          <!--.btn-left {-->
            <!--border: 1px solid #fff-->
            <!--&:hover {-->
              <!--border-color #7392FF-->
              <!--color #7392FF-->
              <!--box-shadow 0px 0px 10px 6px rgba(115, 146, 255, 0.5)-->
            <!--}-->
          <!--}-->
          <!--.btn-right {-->
            <!--background #7392FF-->
            <!--&:hover {-->
              <!--box-shadow 0px 0px 10px 6px rgba(115, 146, 255, 0.5)-->
            <!--}-->
          <!--}-->
        <!--}-->
      <!--}-->
    <!--}-->

    <!--.content {-->
      <!--width 1200px-->
      <!--margin 0 auto-->
      <!--z-index: 3-->
      <!--position: relative;-->
      <!--/*Banner缩略图*/-->
      <!--.swiper-container {-->
        <!--background #1B1F28-->
        <!--min-height 180px-->
        <!--.swiper-slide {-->

          <!--.slide-image {-->
            <!--width: 380px;-->
            <!--height: 180px;-->
            <!--position: relative;-->
            <!--overflow hidden /*    transform-style: preserve-3d;-->
                <!--transition: all 1.8s ease;*/-->
            <!--border: 3px solid #404247-->
            <!--border-radius 5px /*img:nth-child(2) {-->
              <!-- -webkit-transform: rotate3d(0, 1, 0, 180deg) translateZ(1px);-->
            <!--}*/-->
          <!--}-->

        <!--}-->
        <!--.swiper-slide:hover .slide-image {-->
          <!--/* -webkit-transform: rotate3d(0, 1, 0, 180deg)*/-->
          <!--border: 3px solid #7392FF-->
          <!--border-radius 5px-->
        <!--}-->

      <!--}-->
      <!--.carousel-container {-->

        <!--padding-bottom 10px-->

        <!--.fade-enter-active, .fade-leave-active {-->
          <!--transition: opacity .5s;-->
        <!--}-->
        <!--.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */-->
        <!--{-->
          <!--opacity: 0;-->
        <!--}-->

        <!--.slide-img-origin {-->
          <!--position absolute-->
          <!--left 0-->
          <!--top 0-->

        <!--}-->
        <!--.swiper-container {-->
          <!--padding 30px 0 40px-->
          <!--// padding-bottom 30px-->
          <!--/*background: rgba(255, 255, 255, 0.5)*/-->
          <!--/*opacity 0.5*/-->
        <!--}-->

        <!--.swiper-scrollbar {-->
          <!--display none-->
        <!--}-->

        <!--.swiper-button {-->
          <!--background-repeat no-repeat-->
          <!--width 28px-->
          <!--height 32px-->
          <!--margin-top -16px-->
          <!--border-radius 3px-->
          <!--background-size: 15px 25px;-->
          <!--z-index 999-->
          <!--&:not(.swiper-button-disabled):hover {-->
            <!--background-color #3e4657-->
          <!--}-->
        <!--}-->

        <!--.swiper-button-prev {-->
          <!--background-image url("/static/swiper/left-arrow.png")-->
          <!--left: 10px;-->
          <!--right: auto;-->
        <!--}-->
        <!--.swiper-button-next {-->
          <!--background-image url("/static/swiper/right-arrow.png")-->
          <!--left: auto;-->
          <!--right: 10px;-->
        <!--}-->
        <!--.swiper-slide {-->
          <!--img {-->
            <!--margin 0 auto-->
          <!--}-->
        <!--}-->
      <!--}-->

      <!--.el-tabs {-->

        <!--margin-top 20px-->
        <!--padding-bottom: 55px;-->
        <!--.coin-img {-->
          <!--width 30px-->
          <!--height 30px-->
          <!--vertical-align middle-->
        <!--}-->
        <!--.change-rise {-->
          <!--color: $color-global-buy-->
        <!--}-->
        <!--.change-fall {-->
          <!--color: $color-global-sell-->
        <!--}-->
      <!--}-->

      <!--.home-table {-->
        <!--/* font-weight 900-->
         <!--border: 1px solid $color-global-el-tb-border-->
         <!--*/-->
        <!--font-size 16px-->
        <!--.tb-coin-name {-->
          <!--&gt;img{-->
            <!--float left-->
          <!--}-->
          <!--color #fff-->
          <!--cursor: pointer-->
        <!--}-->

      <!--}-->

      <!--.el-table td, .el-table th.is-leaf {-->
        <!--text-align center-->
      <!--}-->

      <!--.hover-btn {-->
        <!--border-color: #409EFF;-->
        <!--color: #409EFF;-->
      <!--}-->
      <!--.pool-container {-->
        <!--margin-bottom 40px-->
        <!--.invite-button {-->
          <!--margin-bottom 14px-->
          <!--border-radius 2px-->
          <!--background #6787FF-->
          <!--width 85px-->
          <!--height 30px-->
          <!--line-height 30px-->
          <!--text-align center-->
          <!--color #fff-->
          <!--border none-->
        <!--}-->
        <!--.poolInfo {-->
          <!--/*height: 100px;*/-->
          <!--margin 20px 0-->
          <!--color: #fff-->
          <!--display flex-->
          <!--justify-content space-between-->
          <!--.item {-->
            <!--width 380px-->
            <!--/*height 100px*/-->
            <!--padding 10px 0-->
            <!--border: 1px solid #414142-->
            <!--display flex-->
            <!--flex-direction column-->
            <!--justify-content center-->
            <!--align-items center-->
            <!--justify-items center-->
            <!--p {-->
              <!--margin 10px-->
            <!--}-->
            <!--.accumulate{-->
              <!--color #6787FF-->
              <!--font-size 18px-->
            <!--}-->

          <!--}-->
        <!--}-->

        <!--.user-invite-info {-->
          <!--height 50px-->
          <!--line-height 50px-->
          <!--border 1px solid #414142-->
          <!--color #fff-->
          <!--margin-bottom 14px-->
          <!--padding-left 10px-->
          <!--span{-->
            <!--&:before{-->
              <!--content ""-->
              <!--display inline-block-->
              <!--background url("../../assets/home/notice.png") no-repeat-->
              <!--width: 30px;-->
              <!--height: 25px;-->
              <!--vertical-align middle-->

            <!--}-->
          <!--}-->
        <!--}-->
      <!--}-->

    <!--}-->
  <!--}-->

<!--</style>-->
