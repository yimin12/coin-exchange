<template>
  <div class="ucenter-wrap term-page" v-wechat-title="$t('m.footer.term')">
    <NavHeader></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
              <h3 class="title">{{$t('m.footer.term')}}</h3>
          </div>
          <div class="ucenter-body" v-html="$t('m.serviceAgreement')">

          </div>
        </div>
      </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>
  </div>
</template>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
  .term-page{

    .ucenter-body{
      padding-bottom 50px !important
      color #666666
      line-height 1.7
      font-size 14px
      a{
        color #7392FF
      }
      li{
        margin-top 6px
      }
    }
    .ucenter-body h3{
      margin-top 30px
      margin-bottom 10px
      font-size 15px
    }
    p{
      text-indent 30px
      margin-bottom 10px
      a{
        color #7392FF
      }
    }
  }
</style>
<script>
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/page/sidebar'
  import mFooter from 'components/m-footer/m-footer'

  export default {
    components : {NavHeader,sidebar,mFooter}
  }
</script>
