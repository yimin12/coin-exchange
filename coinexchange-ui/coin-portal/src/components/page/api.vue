<template>
  <div class="ucenter-wrap api-page" v-wechat-title="$t('m.footer.api')">
    <NavHeader></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
              <h3 class="title">{{$t('m.footer.api')}}</h3>
          </div>
          <div class="ucenter-body">

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
  .api-page{
    .ucenter-body{
      color #666666
      line-height 1.77
      padding-bottom 50px !important
      h3{
        font-weight bold
        margin-top 15px
        margin-bottom 20px
      }
      ul{
        margin-left 25px
        li{
          list-style-type decimal
        }
      }
      p > span{
        margin-right 15px
      }
    }
  }
</style>
<script>
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/page/sidebar'
  import mFooter from 'components/m-footer/m-footer'

  export default {
    data () {
      return {}
    },
    components : {NavHeader,sidebar,mFooter}
  }
</script>
