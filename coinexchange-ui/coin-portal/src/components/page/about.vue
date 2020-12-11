<template>
  <div class="ucenter-wrap about-page" v-wechat-title="$t('m.footer.about')">
    <NavHeader></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
              <h3 class="title">{{$t('m.footer.about')}}</h3>
          </div>
          <div class="ucenter-body" v-html="$t('m.about')">
            <!--<h3>关于我们</h3>
            <p>pcn交易平台是一家为全球用户提供数字资产交易的数字货币交易平台。聚合全球优质区块链资产，秉持着“用户至上”的服务理念，致力于为全球用户提供安全、专业、诚信、优质的区块链资产兑换服务，进而推动区块链资产的安全流动与更多的场景化应用。</p>
            <h3>我们的宗旨</h3>
            <p>
              <span>客户至上</span>
              <span>开放创新</span>
              <span>积极进取</span>
            </p>
            <h3>我们的优势</h3>
            <ul>
              <li>核心交易处理技术<br/>经权威认证的内存撮合技术，处理速度高达140万单/秒，远高于行业平均水平。</li>
              <li>专业产品运营团队<br/>管理成员均来自知名区块链团队，有多年数字资产行业经验，熟悉数字资产运营管理。</li>
              <li>重塑数字资产平台服务体系<br/>币币交易、场外交易、创新交易一站式服务，坚持极致的用户体验和安全的金融属性。</li>
            </ul>
            <h3>我们的服务</h3>
            <ul>
              <li>智能交易<br/>采用多币种交易，7x24小时自动监测行情变化，满足策略条件，自动发起交易，无需盯盘就能轻松斩获收益。</li>
              <li>风险控制</li>
            </ul>-->
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
