<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.footer.notice')">

    <NavHeader></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.footer.notice')}}</h3>
          </div>
          <div class="ucenter-body">
            <p class="detail-title">{{detail.title}}</p>
            <p v-html="detail.content" class="detail-content"></p>
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
  import {homeApi} from 'api/home'
  import sidebar from 'components/page/sidebar'

  export default {
    data() {
      return {
        id: 0,
        detail: {}
      }
    },
    mounted() {
      this.id = this.$route.params.id
      console.log("详情页面id：" + this.id);
      this._getNoticeDetail()
      window.scrollTo(0, 0)
    },

    methods: {
      async _getNoticeDetail() {
        let res = await homeApi.getNoticeDetail(this.id);
        this.detail = res.data
        console.log("详情：",res.data);
      },
    },
    components: {
      NavHeader,
      mFooter,
      sidebar
    }
  }
</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
  .detail-title{
    margin: 0 auto
    margin-bottom 10px
    text-align center
    font-size 16px
  }
  .detail-content{
    line-height 25px
  }
</style>
