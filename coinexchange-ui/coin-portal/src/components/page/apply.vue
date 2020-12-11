<template>
  <div class="ucenter-wrap about-page" v-wechat-title="$t('m.footer.apply')">
    <NavHeader></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
              <h3 class="title">{{$t('m.footer.apply')}}</h3>
          </div>
          <div class="ucenter-body">
            <h3>{{$t('m.applyinfo')}}</h3>
            <p>{{$t('m.applyinfo1')}}<a v-bind:href="'mailto:'+email">{{email}}</a>， {{$t('m.applyinfo2')}}</p>
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
  .about-page{
    .ucenter-body{
      color #666666
      line-height 1.77
      padding-bottom 50px !important
      h3{
        font-weight bold
        margin-top 15px
        margin-bottom 20px
      }
      p{
        a{
          color #7392FF
        }
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
      return {
        email : 'pcn.li@hotmail.com'
      }
    },
    components : {NavHeader,sidebar,mFooter}
  }
</script>
