<template>
  <div class="account-entry">
    <div class="topnav">
      <NavHeader :nobackground="true"></NavHeader>
    </div>
    <div class="account-wrap">
      <div class="account-content forget-password regist">
          <el-form ref="form" :model="ruleForm" :rules="rules" label-position="left" label-width="0px"
                   class="demo-ruleForm login-container">
            <h3 class="title">{{$t('m.userCenter.recoverPassword')}}</h3>

            <el-form-item>
              <el-select v-model="ruleForm.countryCode" filterable :placeholder="$t('m.pleaseChoose')" style="width: 100%">
                <el-option v-for="(item, key) in countryCodeMap.options"
                           :label="item.name"
                           :value="item.code"
                           :key="item.id">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item prop="mobile" class="space">
              <el-input type="text" v-model="ruleForm.mobile" auto-complete="off"
                        :placeholder="$t('m.loginRegist.plzInputAccount')"></el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input type="password"
                        v-model="ruleForm.password"
                        auto-complete="off"
                        :placeholder="$t('m.userCenter.plzRestPassword')"></el-input>
            </el-form-item>

            <el-form-item id="captcha" class="captcha" ref="captcha">
              <p :class="['wait',showCaptcha?'show':'hide']">{{$t('m.userCenter.verificationLoading')}}</p>
            </el-form-item>


            <el-row :gutter="15">
              <el-col :span="18">
                <el-form-item prop="validateCode" :error="errors.validateCode">
                  <el-input :placeholder="$t('m.userCenter.plzValidationCode')" v-model="ruleForm.validateCode"
                            :maxlength="6">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                   <validateCode :mobile="ruleForm.mobile" type="FORGOT_VERIFY" :countryCode="ruleForm.countryCode" :validateType="validateType"></validateCode>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item style="width:100%; margin-bottom: 5px">
              <el-button type="primary" class="button-primary" @click="submitForm" :loading="loading">{{$t('m.submit')}}
              </el-button>
            </el-form-item>
          </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import { loginRegist } from 'api/loginRegist'
  import { commonApi } from 'api/common'
  import loginMixin from "../../common/mixin/loginMixin";
  import validateCode from 'base/validate-code/validate-code'

  export default {
    mixins: [loginMixin],
    watch: {
      "ruleForm.mobile"(curVal) {
        if(curVal.indexOf("@")!==-1){
          this.validateType = 1
        }else{
          this.validateType = 0
        }
      }
    },
    data() {

      return {
        validateType:0,
        resultId:'', // 用于下一步重置密码的参数
        ruleForm    : {
          countryCode:'+86',
          mobile  : '',
          password   : '',
          validateCode : '',
        },
        errors    : {
          mobile  : '',
          u_code       : '',
          validateCode : '',
        },
        //表单验证规则
        rules      : {
          mobile : [
            {validator: this.checkMobile, trigger: 'blur'}
          ],
          password : [
            {required:true,message: this.$t('m.userCenter.plzRestPassword'),trigger:"blur"},
            {pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/,message: this.$t('m.userCenter.passwordFormatError'), trigger: 'blur'},
          ],
          validateCode: [
            {validator: this.checkValidateCode, trigger: 'blur'}
          ],
        },
      }
    },
    components: {
      NavHeader,
      mFooter,
      validateCode
    },
    methods   : {
      submitForm() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            const {geetest_challenge, geetest_validate, geetest_seccode} = this.captchaSuccess;
            const uuid = this.uuidCode ;
            console.log(uuid)
            this.loading = true;
            loginRegist.setPassword({
              geetest_challenge: geetest_challenge,
              uuid: uuid ,
              geetest_validate: geetest_validate ,
              geetest_seccode: geetest_seccode,
              mobile: this.ruleForm.mobile,
              password: this.ruleForm.password,
              validateCode: this.ruleForm.validateCode,
              countryCode :this.ruleForm.countryCode
            }).then(res => {
              this.$message({
                message: this.$t('m.userCenter.recoverPasswordSuccess'),
                type: 'success'
              });
              this.loading = false;
              this.$router.push('/login');
            }).catch(() => {
              this.loading = false;
            })
          } else {
            console.log('error submit');
            return false;
          }
        });
      }
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
@import "~common/stylus/login"
.wait {
  color: #fff;
}

.show {
  display: block;
}

.hide {
  display: none;
}

.captcha {
}
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .account-entry{
    .forget-password{
      .sendCode{
        background #7392FF
        border-color #7392FF
        padding 12px 16px
        display block
        width 100%
      }
      .validate-code{
        height:38px;
        width:100px;
        display: block;
        border-radius: 3px;
      }
    }
  }
</style>
<style lang="stylus" rel="stylesheet/stylus">
  .input-with-phone .el-input-group__append {
    padding: 0;
  }
.forget-password{
  .el-button--primary.is-disabled,
  .el-button--primary.is-disabled:active,
  .el-button--primary.is-disabled:focus,
  .el-button--primary.is-disabled:hover{
    background #97aeff !important
    border-color #97aeff !important
  }
}
</style>
