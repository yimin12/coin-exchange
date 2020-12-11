<template>
  <div class="exchange-dialog-main">
    <el-dialog
      :title="market"
      :visible.sync="visible"
      width="30%"
      @close="handleCloseDialog"
    >
      <div class="dialog-container">
        <div class="first-step" v-show="currentDialog==1">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="ruleForm">
            <el-form-item :label="$t('m.exchange.buySell')" prop="buysell">
              <el-select v-model="ruleForm.type" :placeholder="$t('m.pleaseChoose')" class="form-select"
                         @change="handleExchangeTypeChange" :disabled="exchangeDialogData.orderId!=null">
                <el-option
                  :label="$t('m.exchange.buying')"
                  :value="1">
                </el-option>
                <el-option
                  :label="$t('m.exchange.selling')"
                  :value="2">
                </el-option>
              </el-select>

            </el-form-item>
            <el-form-item :label="$t('m.exchange.transType')" prop="priceType">
              <el-select v-model="ruleForm.priceType"
                         :placeholder="$t('m.pleaseChoose')+($t('m.exchange.transType').toLowerCase())"
                         class="form-select"
                         @change="handlePriceTypeChange">
                <el-option :label="$t('m.exchange.marketPrice')" :value="1">
                </el-option>
                <el-option :label="$t('m.exchange.fixedPrice')" :value="2">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('m.exchange.price')" prop="price">
              <el-input v-model.number="ruleForm.price" type="number"
                        :disabled="this.ruleForm.priceType==1"></el-input>
            </el-form-item>
            <el-form-item :label="$t('m.exchange.amount')" prop="num">
              <!--<el-input-number v-model="ruleForm.num" :min="1" @keyup.native="handleInput"></el-input-number>-->
              <el-input v-model="ruleForm.num" @change="handleInput"></el-input>
            </el-form-item>
            <el-form-item :label="$t('m.exchange.deposit')">
              <el-input v-model="ruleForm.promise" :disabled="true" type="number"></el-input>
            </el-form-item>
          </el-form>


          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleNext">{{$t('m.exchange.confirmTheOrder')}}</el-button>
          </div>
        </div>
        <!--第二个弹窗-->
        <div class="second-step" v-show="currentDialog==2">
          <div class="confirm-info">
            <!--v-show="dialogData.orderId"-->
            <div v-show="exchangeDialogData.orderId" class="item">
              <span class="item-left">{{$t('m.exchange.orderID')}}</span>
              <span>{{exchangeDialogData.orderId}}</span>
            </div>
            <div class="item">
              <span class="item-left">{{$t('m.exchange.market')}}</span>
              <span>{{exchangeDialogData.market}}</span>
            </div>
            <div class="item">
              <span class="item-left">{{$t('m.exchange.buySell')}}</span>
              <span>{{ruleForm.type===1?$t('m.exchange.buying'):$t('m.exchange.selling')}}</span>
            </div>
            <div class="item">
              <span class="item-left">{{$t('m.exchange.transType')}}</span>
              <span>{{ruleForm.priceType==1?$t('m.exchange.marketPrice'):$t('m.exchange.fixedPrice')}}</span>
            </div>
            <div class="item">
              <span class="item-left">{{$t('m.exchange.price')}}</span>
              <span>{{ruleForm.fixedPrice}}</span>
            </div>
            <div class="item">
              <span class="item-left">{{$t('m.exchange.amount')}}</span>
              <span>{{ruleForm.num}}</span>
            </div>
            <div class="item">
              <span class="item-left">{{$t('m.exchange.deposit')}}</span>
              <span>{{ruleForm.promise}}</span>
            </div>
          </div>

          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleConfirm">{{$t('m.yes')}}</el-button>
          </div>
        </div>
      </div>
    </el-dialog>


  </div>
</template>
<script>
  import {mathUtil} from 'common/js/math'

  export default {
    props: ["exchangeDialogData"],
    data() {
      let validateNum = (rule, value, callback) => {
        if (value <= 0) {
          callback(new Error('数量必须大于0'));
        } else if (value > this.exchangeDialogData.num) {
          callback(new Error('数量不能大于开仓数量'));
        } else {
          callback();
        }
      };
      return {
        visible: this.exchangeDialogData.visible,
        // 实时价格
        realTimePrice: '',
        market: '',
        // marketId     : this.exchangeDialogData.marketId,
        // 是否禁用价格输入框
        flag: true,
        currentDialog: 1,
        ruleForm: {
          type: '',
          priceType: 1,
          price: '',
          num: 0,
          promise: '',
          fixedPrice: ''

        },
        rules: {
          type: [
            {
              required: true,
              message: this.$t('m.pleaseChoose') + (this.$t('m.exchange.buySell').toLowerCase()),
              trigger: 'change'
            },
          ],
          priceType: [
            {
              required: true,
              message: this.$t('m.pleaseChoose') + (this.$t('m.exchange.transType').toLowerCase()),
              trigger: 'change'
            }
          ],
          price: [
            {required: true, type: 'number', message: this.$t('m.exchange.plzRightPrice'), trigger: 'blur'}
          ],
          num: [
            {required: true, type: 'number', message: this.$t('m.exchange.plzRightAmount'), trigger: 'blur'},
            {validator: validateNum, trigger: 'blur'}

          ],
          promise: [
            {required: true, message: this.$t('m.exchange.plzEnterMargin'), trigger: 'blur'}
          ]
        }
      }
    },
    watch: {
      'ruleForm.num'(curVal) {
        if (this.realTimePrice.baseMargin && curVal) {
          this.ruleForm.promise = mathUtil.accMul(curVal, this.realTimePrice.baseMargin)
        }
      },
      exchangeDialogData: {
        handler(curVal) {
          this.ruleForm.price = curVal.price
          this.ruleForm.fixedPrice = curVal.price
          this.market = curVal.market
          if (curVal.hasOwnProperty("num")) {
            this.ruleForm.num = curVal.num
          } else {
            this.ruleForm.num = 1
          }
          if (curVal.type != 0) {
            this.ruleForm.type = curVal.type
          } else {
            this.ruleForm.type = ''
          }
          this.visible = curVal.visible
        },
        // deep: true
      },
      /**
       *  当选择限价买入时取卖1价
       *  当选择限价卖出时去买1价
       **/
      realTimePrice(curVal) {
        if (this.ruleForm.price == '' || this.ruleForm.price == undefined) {
          if (this.ruleForm.type === 0 || this.ruleForm.priceType === 1) {
            this.ruleForm.price = curVal.price;
          } else if (this.ruleForm.type === 1) {
            this.ruleForm.price = curVal.sellPrice;
          } else {
            this.ruleForm.price = curVal.buyPrice;
          }
          this.ruleForm.promise = mathUtil.accMul(this.ruleForm.num, curVal.baseMargin)
        }
      }
    },
    methods: {
      handleInput() {
        this.ruleForm.num = parseInt(this.ruleForm.num)
      },
      /**
       * 修改市价限价
       * @param val 1 市价 2 限价
       */
      handlePriceTypeChange(val) {
        if (val === 1) {
          this.flag = true
        } else {
          this.flag = false
        }
        // 切换价格类型，重置当前价
        this.ruleForm.price = ''
      },
      /**
       * 修改买卖
       * @param val 1 买 2卖
       */
      handleExchangeTypeChange(val) {
        if (val === 1) {
          this.ruleForm.price = this.exchangeDialogData.buyPrice
        } else {
          this.ruleForm.price = this.exchangeDialogData.sellPrice
        }
      },
      /**
       * 关闭对话框，重置数据，停止试试更新对话框数据
       */
      handleCloseDialog() {
        this.ruleForm.promise = ''
        this.ruleForm.fixedPrice = ''
        this.ruleForm.num = 0
        this.flag = true
        this.visible = false
        this.$refs['ruleForm'].resetFields()
        this.realTimePrice = {}
        this.$emit("handleCloseDialog")
      },
      /**
       * 确认订单
       */
      handleNext() {
        this.$refs['ruleForm'].validate((valid) => {
          if (valid) {
            this.currentDialog = 2;
            this.market = this.$t('m.exchange.confirmOrder');
            //如果是市价 将实时的价格固定
            this.ruleForm.fixedPrice = this.ruleForm.price
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      /**
       *发送请求
       */
      handleConfirm() {
        let dialogData = {
          symbol: this.exchangeDialogData.symbol,
          volume: this.ruleForm.num,
          price: this.ruleForm.fixedPrice,
          priceType: this.ruleForm.priceType,
          type: this.ruleForm.type
        }
        if (this.exchangeDialogData.orderId) {
          dialogData.orderId = this.exchangeDialogData.orderId
        }

        this.$emit("submitOrder", dialogData)
        this.handleCloseDialog()
      },
    },
  }

</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .exchange-dialog-main {
    .dialog-container {
      .form-select {
        width: 100%;
      }
    }
    .second-step {
      .confirm-info {
        text-align center
        width: 80%
        margin 0 auto
        .item {
          display flex
          justify-content space-between
          .item-left {
            font-weight bold
          }

        }
      }
    }
    .dialog-footer {
      text-align right
    }
  }
</style>
