<template>
  <div class="validate-code">
    <span v-if="tag == 'span'" @click="sendSMSCode"
          v-text="smsButton.text"
          :disabled="smsButton.disabled"
          class="get-code">
    </span>
    <el-button v-else type="primary"
               @click="sendSMSCode"
               v-text="smsButton.text"
               :disabled="smsButton.disabled"
               class="sendCode">
    </el-button>
  </div>
</template>
<script>
  import { commonApi } from 'api/common'
  import { OK } from 'api/config'
  import {mapGetters} from 'vuex'

  export default {
    props:{
      // 调用接口的参数 (email 或者 mobile)
      mobile:{

      },
      // 短信模板
      type:{

      },
      // 是否调用接口校验
      check:{

      },
      // 验证码按钮的样式
      tag:{

      },
      //
      validateType: {
        default: 0  // 0 验证手机 1 验证邮箱
      },
      countryCode:{
        default: "+86"
      }
    },
    data(){
      return {
        smsButton: {
          text: this.$t('m.userCenter.sendSMSCode'),
          disabled:false,
          countDown:0
        },
        timer : 0
      }
    },
    computed:{
      ...mapGetters(['lang'])
    },
    watch:{
      lang(){
        this.smsButton.text = this.$t('m.userCenter.sendSMSCode');
      }
    },
    mounted(){
      this.smsButton.text = this.$t('m.userCenter.sendSMSCode');
    },
    destroyed() {
      if(this.timer) {
        clearInterval(this.timer);
      }
    },
    methods:{
      // 发送手机短信验证码
      async sendSMSCode() {
        let smsButton = this.smsButton;
        if(smsButton.disabled) return;
        let mobile = this.mobile;
        let countryCode = this.countryCode;
        if (typeof mobile === 'undefined' || mobile === '') {
          // 找回密码的时候判断是手机 还是邮箱
          if (this.validateType === 0) {
            this.$message({
              message: this.$t('m.userCenter.plzPhoneNumber'),
              type: 'error'
            });
          } else {
            this.$message({
              message: this.$t('m.loginRegist.plzInputEmail'),
              type: 'error'
            });
          }
          return;
        }
        if (this.validateType === 1) {
          let mailReg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;
          if (!mailReg.test(mobile)) {
            this.$message({
              message: this.$t('m.loginRegist.errorEmail'),
              type: 'error'
            });
            return;
          }
        }
        console.log("--------~~~~", this.validateType)
        let reg = /^1(3|4|5|6|7|8|9)\d{9}$/;
        if (this.validateType == 0) {
          if (countryCode === "+86") {
            if (!reg.test(mobile)) {
              this.$message({
                message: this.$t('m.userCenter.phoneFormatError'),
                type: 'error'
              });
              return;
            }
          }
        }

        if (typeof this.check !== 'undefined' && this.check) {
          if (this.validateType === 1) {
            let res = await
              commonApi.checkEmail(mobile);
            if (res.errcode !== 0) {
              return;
            }
          } else {
            let res = await
              commonApi.checkMobile(mobile, countryCode);
            if (res.errcode !== 0) {
              return;
            }
          }
        }
        this.smsButton.disabled = true;
        this._sendSms(mobile, this.type, countryCode, this.validateType);

        // 模拟验证码发送
        this.smsButton.countDown = 60;
        let Timer = setInterval(() => {
          this.smsButton.countDown--;
          this.smsButton.text = this.smsButton.countDown + ' s';
          if (this.smsButton.countDown < 1) {
            clearInterval(Timer);
            this.smsButton.disabled = false;
            this.smsButton.text = this.$t('m.userCenter.resend');
          }
        }, 1000);
      },

      async _sendSms(mobile, type, countryCode, validateType) {
        let res = await
          commonApi.sendSms(mobile, commonApi.SMS_TYPE[type], countryCode, validateType);
        console.log("发送结果：", res)
        this.$message({
          message: this.$t('m.userCenter.sendSMSCodeSuccess'),
          type: 'success'
        });
      },

      reset() {
        this.smsButton.text = this.$t('m.userCenter.sendSMSCode');
        this.smsButton.disabled = false;
        this.smsButton.countDown = 0;
      }
    }
  }
</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .validate-code{
    .get-code {
      cursor pointer
      color $color-global-purple
      font-size $font-size-small
      text-align center;
      margin: auto;
    }
    .sendCode {
      background #7392FF
      border-color #7392FF
      padding 12px 16px
      display block
      width 100%
    }
  }

</style>
