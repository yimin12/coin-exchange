<template>
  <div class="ucenter-wrap rate-page" v-wechat-title="$t('m.footer.rate')">
    <NavHeader></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
              <h3 class="title">{{$t('m.footer.rate')}}</h3>
          </div>
          <div class="ucenter-body">
            <h3>{{$t('m.rechargeExpense')}}</h3>
            <table class="rate-table">
              <thead>
                <tr>
                  <th>{{$t('m.currency')}}</th>
                  <!--<th>{{$t('m.USDTfee')}}</th>-->
                  <!--<th>{{$t('m.BTCfee')}}</th>-->
                  <!--<th>{{$t('m.ETHfee')}}</th>-->
                  <th>{{$t('m.WithdrawalCharge')}}</th>
                  <th>{{$t('m.DailyWithdrawalLimit')}}</th>
                  <!--<th>{{$t('m.Chargefee')}}</th>-->
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>FOFT</td>
                  <td>0.2%+8FOFT</td>
                  <td>200000</td>
                </tr>
                <tr>
                  <td>BTC</td>
                  <td>0.001BTC</td>
                  <td>100</td>
                </tr>
                <tr>
                  <td>ETH</td>
                  <td>0.003ETH</td>
                  <td>3000</td>
                </tr>
                <tr>
                  <td>LTC</td>
                  <td>0.002LTC</td>
                  <td>10000</td>
                </tr>
                <tr>
                  <td>TRX</td>
                  <td>0.1TRX</td>
                  <td>1000000</td>
                </tr>
                <tr>
                  <td>QTUM</td>
                  <td>0.1QTUM</td>
                  <td>100000</td>
                </tr>
                <tr>
                  <td>KJL</td>
                  <td>0.2%+50KJL</td>
                  <td>500000</td>
                </tr>
                <tr>
                  <td>ESDC</td>
                  <td>0.2%+50ESDC</td>
                  <td>500000</td>
                </tr>
                <tr>
                  <td>AGC</td>
                  <td>0.2%+50AGC</td>
                  <td>500000</td>
                </tr>
              </tbody>
            </table>
            <!--<div class="rate-tips">({{$t('m.BTCwithdraw')}})</div>-->
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
  .rate-page{
    .ucenter-body h3{
      font-weight bold
      margin-bottom 20px
      font-size 16px
    }
    .rate-tips{
      margin-top 10px
      font-size 14px
      color #666666
    }
    .rate-table{
      width 100%
      thead{
        tr > th{
          padding 13px 10px
          background-color #7392FF
          border 1px solid #fff
          color #fff
        }
      }
      tbody{
        tr{
          background-color #ECF0FF
          td{
            text-align center
            padding 13px 10px
            border 1px solid #fff
            color #000000
          }
        }
        tr:hover{
          background-color #DBE3FF
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
    components : {NavHeader,sidebar,mFooter}
  }
</script>
