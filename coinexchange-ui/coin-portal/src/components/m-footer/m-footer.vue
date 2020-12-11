<template>
  <div class="m-footer">
    <div class="content">
      <!--<img src="../../assets/home/logon.png" alt="">-->
      <div class="content-list">
        <!--<router-link to="" class="list-item" v-bind:title="$t('m.footer.site')">-->
          <!--{{$t('m.footer.site')}}-->
        <router-link to="/s/notice" class="list-item" v-bind:title="$t('m.footer.notice')">
          {{$t('m.footer.notice')}}
        </router-link>

        <!--<router-link to="/s/api" class="list-item" v-bind:title="$t('m.footer.api')">-->
          <!--{{$t('m.footer.api')}}-->
        <!--</router-link>-->
        <el-popover
          placement="top"
          width="150"
          trigger="hover">
         <!-- <img :src="scanQrcodeSrc" alt="" class="scan-qrcode"> -->
          <router-link to="" class="list-item" v-bind:title="$t('m.footer.contact')" slot="reference">
            {{$t('m.footer.contact')}}
          </router-link>
        </el-popover>
        <router-link to="/s/term" class="list-item" v-bind:title="$t('m.footer.term')">
          {{$t('m.footer.term')}}
        </router-link>

        <router-link to="/s/about" class="list-item" v-bind:title="$t('m.footer.about')">
          {{$t('m.footer.about')}}
        </router-link>

        <!--<router-link to="/s/rate" class="list-item" v-bind:title="$t('m.footer.rate')">
          {{$t('m.footer.rate')}}
        </router-link>-->

        <router-link to="/s/help" class="list-item" v-bind:title="$t('m.footer.help')">
          {{$t('m.footer.help')}}
        </router-link>

        <!--<router-link to="/s/help" class="list-item" v-bind:title="$t('m.footer.help')">
          {{$t('m.footer.help')}}
        </router-link>
        <router-link to="/s/order" class="list-item" v-bind:title="$t('m.footer.order')">
          {{$t('m.footer.order')}}
        </router-link>
        <router-link to="/s/apply" class="list-item" v-bind:title="$t('m.footer.apply')">
          {{$t('m.footer.apply')}}
        </router-link>-->
        <el-popover
          placement="top"
          width="150"
          trigger="hover">
          <!-- <img :src="android_qrcode" alt="" class="scan-qrcode">
          <img :src="ios_qrcode" alt="" class="scan-qrcode"> -->
          <router-link to="" class="list-item" v-bind:title="$t('m.downApp')" slot="reference">
            {{$t('m.downApp')}}
          </router-link>
        </el-popover>
      </div>
      <p class="footer-bottom">© 2020 尚学堂 All Rights Reserved</p>

        <!--<div class="share-container">
          <img :src="scanQrcodeSrc" alt="" class="scan-qrcode" v-show="showQrcode" ref="scanQrcode">

          <img src="./img/wechat.png"  @mouseenter="handleMouseOver(1)"
               @mouseleave="handleMouseLeave(1)">
          <img src="./img/mobile.png"  @mouseenter="handleMouseOver(2)"
               @mouseleave="handleMouseLeave(2)">
          <img src="./img/telegram.png" @mouseenter="handleMouseOver(3)"
               @mouseleave="handleMouseLeave(3)">
        </div>-->


    </div>


  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data() {
      return {
        showQrcode:false,
        //ios_qrcode: require("../../assets/home/ios_qrcode.png"),
        //android_qrcode: require("../../assets/home/android_qrcode.png"),

        mobileSrc:require("../../assets/home/mobile-qr.png"),
        telegramSrc:require("../../assets/home/telegram-qr.png"),
        wechatSrc:require("../../assets/home/wechat-qr.png"),
        //scanQrcodeSrc:require("../../assets/home/mobile-qr.png")

      }
    },
  }
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  @import "~common/stylus/mixin"
  .m-footer {
    background $color-footer-bg;
    overflow hidden;
    .content {
      width 1200px
      margin 0 auto
      display: flex
      flex-direction column
      height: 205px
      align-items center
      position relative

      justify-content flex-start
      > img {
        margin-top 30px
      }

      .content-list {
        width 1200px
        margin 0 auto
        text-align center
        display flex
        justify-content space-around
        color white
        margin-top 20px
        padding-top 20px
        border-top 2px #b2b2b2 solid

        .list-item {
          cursor pointer
          display inline-block
          padding-right 15px
          margin-left 15px
          line-height:20px;
          height 20px;
          color white
          &:last-child{
            border-right none
          }
        }

      }

      .footer-bottom {
        font-size $font-size-small
        color $color-footer-subcolor
        text-align center
        height: 40px
        line-height: 40px
      }
      .scan-qrcode{
        width 150px;
        height 150px;
        transformCenterHorizontal()
        top 0

      }
      .share-container {
        display flex
        justify-content space-between
        width: 200px;
      }




    }


  }
</style>
