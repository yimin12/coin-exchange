<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.fundPassword')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content forget-password">
      <div class="ucenter-row">
        <sidebar :activeId="4"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.fundPassword')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-row>
              <el-col :span="13">
                <el-form ref="forms" :model="forms" :rules="rules" label-position="left" label-width="140px">

                  <!--<el-form-item :label="$t('m.userCenter.phoneNumber')" prop="u_telephone" class="space">-->
                  <!--<el-input type="text" v-model="forms.u_telephone" auto-complete="off"-->
                  <!--:placeholder="$t('m.userCenter.plzPhoneNumber')" :maxlength="11"></el-input>-->
                  <!--</el-form-item>-->

                  <el-form-item :label="$t('m.userCenter.plzFundPassword')" prop="payPassword" class="space">
                    <el-input type="password" v-model="forms.payPassword" auto-complete="off"
                              :placeholder="$t('m.userCenter.plzFundPassword')"></el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.confirmFundPassword')" prop="confirmPayPassword" class="space">
                    <el-input type="password" v-model="forms.confirmPayPassword" auto-complete="off"
                              :placeholder="$t('m.userCenter.confirmFundPassword')"></el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.validationCode')" prop="validateCode">
                    <el-col :span="14">
                      <el-input :placeholder="$t('m.userCenter.plzValidationCode')"
                                v-model="forms.validateCode"
                                :maxlength="6">
                      </el-input>
                    </el-col>
                    <el-col :span="1">&nbsp;</el-col>
                    <el-col :span="9">
                      <validateCode :mobile="this.mobile" type="FORGOT_PAY_PWD_VERIFY" :countryCode="this.countryCode"></validateCode>

                    </el-col>
                  </el-form-item>


                  <el-form-item>
                    <el-button type="primary"  @click="handleSubmit" :loading="loading">{{$t('m.submit')}}
                    </el-button>
                  </el-form-item>
                </el-form>
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
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import sidebar from 'components/usercenter/sidebar'
  import { loginRegist } from 'api/loginRegist'
  import { OK } from 'api/config'
  import { cookieUtil } from 'common/js/cookieUtil'
  import  validateCode  from 'base/validate-code/validate-code'
  import { commonApi } from 'api/common'
  import {setPayPassword} from "../../api/usercenter";
  import { mapGetters } from 'vuex'

  export default {

    data() {
      let passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/
      let checkPayPassword = (rule, value, callback)=>{
        value = value.trim();
        if(value === ''){
          callback(new Error(this.$t('m.userCenter.plzFundPassword')));
        }else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else {
          callback()
        }
      }
      let checkConfirmPayPassword = (rule, value, callback)=>{
        value = value.trim();
        if(value === '') {
          callback(new Error(this.$t('m.userCenter.confirmFundPassword')));
        }else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        }else if(value !== this.forms.payPassword){
          callback(new Error(this.$t('m.userCenter.confirmPwError')));
        }else{
          callback()
        }
      }
      let checkValidateCode = (rule, value, callback) => {
        if (!value || !/^\d{6}/.test(value)) {
          callback(new Error(this.$t('m.userCenter.plzValidationCode')));
        }else{
          callback();
        }
      };
      return {
        loading   : false,
        forms    : {
          payPassword  : '',
          confirmPayPassword : '',
          validateCode : '',
        },
        //表单验证规则
        rules      : {
          payPassword : [
            {validator: checkPayPassword, trigger: 'blur'}
          ],
          confirmPayPassword : [
            {validator: checkConfirmPayPassword, trigger :'blur'}
          ],
          validateCode: [
            {validator: checkValidateCode, trigger: 'blur'}
          ],
        },
        lang:"zh-CN"
      }
    },
    mounted() {
    },
    computed : {
      ...mapGetters(['mobile','token','countryCode'])
    },
    components: {
      NavHeader,
      mFooter,
      validateCode,
      sidebar
    },
    methods   : {

      handleSubmit() {
        this.$refs.forms.validate((valid) => {
          if (valid) {
            this.loading = true;
            setPayPassword({
              payPassword : this.forms.payPassword,
              validateCode : this.forms.validateCode
            }).then((res)=>{
              if (res.errcode === OK) {
                this.$message({
                  message:this.$t('m.userCenter.modifySuccess'),
                  type:"success"
                })
                this.$router.push("/usercenter")
              }else{
                this.$message({
                  message:res.errmsg,
                  type:"error"
                })
              }
            }).catch((res)=>{
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
  @import "~common/stylus/usercenter"
</style>
