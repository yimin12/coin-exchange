<template>
  <div class="ucenter-wrap btoc">
    <NavHeader activeTab="3"></NavHeader>
    <div class="ucenter-content">
        <div class="ucenter-row">
            <div class="ucenter-main no-sidebar">
                <div class="ucenter-header">
                    <h3 class="title">
                      BTCX兑换CNY
                    </h3>
                </div>
                <div class="ucenter-body">
                    <el-row :gutter="40">
                      <el-col :span="12">

                        <el-form ref="form" label-position="top">

                          <el-form-item>
                            <div slot="label" class="label-row">
                              <span>买入BTCX</span>
                              <span>可用(CNY):{{balanceAmount[0]}}</span>
                            </div>
                            <el-input placeholder="买入价(￥)" :disabled="true">
                              <div slot="suffix" class="price green">1</div>
                            </el-input>
                          </el-form-item>

                          <el-form-item>
                            <div slot="label" class="label-row">
                              <span>买入量(BTCX)</span>
                              <span>可用(CNY):{{balanceAmount[0]}}</span>
                            </div>
                            <el-input type="number" placeholder="请输入买入量" v-model="needCny" min="1"></el-input>
                            <div class="sub-title">需要CNY：{{needCny}}</div>
                          </el-form-item>
                          <el-button type="primary" @click="handleCnyBtcxExchange(1)">
                            CNY兑换BTCX
                          </el-button>
                        </el-form>
                        <div class="buy-sell-tips">
                          <p>温馨提示</p>
                          <ul class="tips-content">
                            <li>BTCX与CNY兑换后，为实时到账，请放心使用。</li>
                            <li>如订单产生纠纷，请立即联系hht.one客服进行处理</li>
                          </ul>
                        </div>

                      </el-col>
                      <el-col :span="12">

                        <el-form ref="form" label-position="top">

                          <el-form-item>
                            <div slot="label" class="label-row">
                              <span>卖出BTCX</span>
                              <span>可用(BTCX):{{balanceAmount[1]}}</span>
                            </div>
                            <el-input placeholder="卖出价(￥)" :disabled="true">
                              <div slot="suffix" class="price red">1</div>
                            </el-input>
                          </el-form-item>

                          <el-form-item>
                            <div slot="label" class="label-row">
                              <span>卖出量(BTCX)</span>
                              <span>可用(BTCX):{{balanceAmount[1]}}</span>
                            </div>
                            <el-input type="number" placeholder="请输入卖出量" v-model="needBTCX" min="1"></el-input>
                            <div class="sub-title">需要BTCX：{{needBTCX}}</div>
                          </el-form-item>
                          <el-button class="btn-btcx-sell" @click="handleCnyBtcxExchange(0)" >
                            BTCX兑换CNY
                          </el-button>
                        </el-form>
                        <div class="buy-sell-tips">
                          <p>温馨提示</p>
                          <ul class="tips-content">
                            <li>BTCX与CNY兑换后，为实时到账，请放心使用。</li>
                            <li>如订单产生纠纷，请立即联系hht.one客服进行处理</li>
                          </ul>
                        </div>
                      </el-col>
                    </el-row>
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
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import VueClipboard from 'vue-clipboard2'
  import dom from 'common/js/dom'
  import { financeApi } from 'api/finance'
  import { OK } from 'api/config'
  import financeHeader from './finance-header'
  Vue.use(VueClipboard)
  Vue.use(ToggleButton)
  export default {
    data(){
      return {
        input4       : '',
        balanceAmount: [],
        needCny      : 0,
        needBTCX     : 0,
        exchangeValue :0
      }
    },
    created(){

    },
    mounted(){
      this.$confirm('<p>1.将CNY兑换成BTCX后，使用C2C功能，卖出BTCX所得CNY入账至银行卡中；</p><p>2、BTCX兑换成CNY功能关闭，使用C2C功能可直接卖出BTCX。</p>', '温馨提示', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        showCancelButton:false,
        type: 'warning'
      }).then(() => {

      }).catch(() => {

      });
      this._userGetExchangeAccountValue()
    },
    components: {
      NavHeader,
      mFooter,
      financeHeader
    },
    methods   : {
      handleCnyBtcxExchange(exchangeType){
        if(exchangeType == 0){
          this.$alert('前往C2C进行交易。', '提示', {
            type             : 'success',
            confirmButtonText: '确定',
            callback         : action => {
              this.$router.replace("/finance/c2c")
            }
          });
          return

        }
        if(this.needCny == ""){
          this.needCny = 0
        }
        if(this.needBTCX == ""){
          this.needBTCX = 0
        }
        console.log(this.needCny,this.needBTCX);
        // 对输入做校验
        if(this.needCny > this.balanceAmount[0]){
          this.$message("没有足额的CNY可供兑换！");
          return
        }
        if(this.needBTCX>this.balanceAmount[1]){
          this.$message("没有足额的BTCX可供兑换！");
          return
        }
        if(exchangeType == 0){
          this.exchangeValue = this.needBTCX
        }else{
          this.exchangeValue = this.needCny
        }

        financeApi.userSimpleExchange(this.exchangeValue,exchangeType).then(res => {
          if (res.statusCode === OK) {
            this.$message({
              message: '转换成功',
              type   : 'success'
            });
            this._userGetExchangeAccountValue()
          }else{
            this.$message({
              message: res.errmsg,
              type   : 'error'
            });
          }

        })
      },
      _userGetExchangeAccountValue(){
        financeApi.userGetExchangeAccountValue().then(res => {
          if (res.statusCode === OK) {
            console.log("兑换账户信息：", res.result);
            let result = res.result
            // cny
            if (result[0].coinId == 1) {
              this.balanceAmount = [result[0].balanceAmount, result[1].balanceAmount]
            } else {
              this.balanceAmount = [result[1].balanceAmount, result[0].balanceAmount]
            }

          }
        })
      }

    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
  .btoc{
    .el-form-item__label{
      display block
      line-height 20px
    }
  }
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .label-row{
    display flex
    justify-content space-between
  }
  .price{
    margin-right 10px
    font-weight bold
    &.green{
      color #0BB474
    }
    &.red{
      color #F61F33
    }
  }
  .sub-title{
    color $color-global-text-light
    font-size 14px
  }
  .buy-sell-tips {
    margin-top 16px
    color $color-global-text-light
    font-size $font-size-medium
    .tips-content {
      margin-top 10px
      li {
        margin-top 10px
      }
    }
  }
  .btn-btcx-sell{
    width 100%
    background-color #F61F33
    border-color #F61F33
  }
  .btn-btcx-sell,
  .btn-btcx-sell:hover{
    color #fff
  }
</style>
