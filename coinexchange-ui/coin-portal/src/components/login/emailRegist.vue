<template>
  <div class="account-entry"  v-wechat-title="$t('m.loginRegist.emailreg')">
    <div class="topnav">
      <NavHeader :nobackground="true"></NavHeader>
    </div>

    <div class="account-wrap">
      <div class="account-content regist">
        <el-form ref="form"
                 :model="ruleForm"
                 :rules="rules"
                 @submit.native.prevent
                 label-position="left"
                 label-width="0px">
          <div class="head-top">
            <router-link to="/regist">{{$t('m.loginRegist.mobilereg')}}</router-link>
          </div>
          <h3 class="title">{{$t('m.loginRegist.emailreg')}}</h3>

          <el-row :gutter="15">
            <el-col :span="18">
              <el-form-item class="email-box" prop="email" :error="errors.email">
                <el-input type="text"
                          class="email ipt"
                          v-model="ruleForm.email"
                          auto-complete="off"
                          :placeholder="$t('m.loginRegist.plemailreg')"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-input type="text" class="noindent" v-model="ruleForm.invitationCode" :disabled="this.$route.params.inviteCode" :placeholder="$t('m.userCenter.pleaseInviteCode')"></el-input>
            </el-col>
          </el-row>


          <el-form-item prop="password" :error="errors.password">
            <el-input type="password"
                      class="pass ipt"
                      v-model="ruleForm.password"
                      auto-complete="new-password"
                      :placeholder="$t('m.userCenter.setLoginPassword')"></el-input>
          </el-form-item>

          <el-form-item prop="confirmPassword" :error="errors.confirmPassword">
            <el-input type="password"
                      class="pass ipt"
                      v-model="ruleForm.confirmPassword"
                      auto-complete="off"
                      :placeholder="$t('m.userCenter.confirmLoginPassword')"></el-input>
          </el-form-item>


          <!--极验证-->
          <el-form-item id="captcha" class="captcha" ref="captcha">
            <p :class="['wait',showCaptcha?'show':'hide']">{{$t('m.userCenter.verificationLoading')}}</p>
          </el-form-item>


          <!--<el-row :gutter="15">-->
            <!--<el-col :span="18">-->
              <!--<el-form-item prop="validateCode" :error="errors.validateCode">-->
                <!--<el-input :placeholder="$t('m.loginRegist.inputemeilvcode')"-->
                          <!--v-model="ruleForm.validateCode"-->
                          <!--class="vcode ipt"-->
                          <!--:maxlength="6">-->
                <!--</el-input>-->
              <!--</el-form-item>-->
            <!--</el-col>-->

            <!--<el-col :span="6">-->
              <!--<el-form-item>-->
                <!--<validateCode :mobile="ruleForm.email" type="REGISTER_VERIFY" :check="true" :validateType="1"></validateCode>-->
              <!--</el-form-item>-->
            <!--</el-col>-->
          <!--</el-row>-->

          <!--<el-form-item>
            <el-input type="text"
                      v-model="ruleForm.invitationCode"
                      :disabled="this.$route.params.inviteCode"
                      auto-complete="off"
                      :placeholder="$t('m.userCenter.pleaseInviteCode')">
            </el-input>
          </el-form-item>-->

          <div class="left-btn">
            <!--<input type="checkbox" v-model="termsOfService">-->
            <a href="javascript:;" @click="handleAgreement" >{{$t('m.loginRegist.reading')}} <span>PCN{{$t('m.loginRegist.terms')}}</span></a>
          </div>

          <el-button class="button-primary" @click="handleRegist" :loading="loading">
            {{$t('m.userCenter.agreeToTheTerms')}}
          </el-button>

          <div class="right-btn">
            {{$t('m.userCenter.alreadyMember')}}
            <router-link to="/login" class="a">{{$t('m.userCenter.goToLogin')}}</router-link>
          </div>

        </el-form>
      </div>
    </div>

    <el-dialog
      :title="$t('m.userCenter.legalNotices')"
      :visible.sync="agreementDialogVisible"
      width="50%"
    >
      <div v-for="index in 5">
        <p>{{$t(`m.userCenter.agreementDetail.${index}.title`)}}</p>
        {{$t(`m.userCenter.agreementDetail.${index}.content`)}}
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="agreementDialogVisible = false">{{$t('m.yes')}}</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import {loginRegist} from 'api/loginRegist'
  import {OK} from 'api/config'
  import {cookieUtil} from 'common/js/cookieUtil'
  import validateCode from 'base/validate-code/validate-code'
  import {commonApi} from 'api/common'
  import loginMixin from "../../common/mixin/loginMixin";

  export default {
    mixins: [loginMixin],
    components: {
      NavHeader,
      mFooter,
      validateCode
    },
    data: function () {
      let checkValidateCode = (rule, value, callback) => {
        if (!value) {
          return callback(new Error(this.$t('m.userCenter.plzValidationCode')));
        }
        callback();
      };
      return {
        rules: {
          // 校验邮箱
          email: [
            {validator: this.checkEmail, trigger: 'blur'}
          ],
          // 校验密码
          password: [
            {required: true, message: this.$t('m.userCenter.plzPassword'), trigger: 'blur'},
            {
              pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/,
              message: this.$t('m.userCenter.passwordFormatError'),
              trigger: 'blur'
            },
          ],
          // 确认密码
          confirmPassword: [
            {required: true, message: this.$t('m.userCenter.plzPasswordAgain'), trigger: 'blur'},
            {
              validator: (rule, value, callback) => {
                if (value !== this.ruleForm.password) {
                  callback(new Error(this.$t('m.userCenter.confirmPwError')));
                } else {
                  callback();
                }
              },
              trigger: 'blur'
            }
          ],
          // 校验手机验证码
          validateCode: {validator: checkValidateCode, trigger: 'blur'}
        },
        ruleForm: {
          countryCode: '+86',
          email: '',
          password: '',
          confirmPassword: '',
          validateCode: '123456',
          invitationCode: '',
          agree: false
        },
        errors: {
          email: '',
          password: '',
          confirmPassword: '',
          validateCode: '',
        },
        agreementDialogVisible: false,
        termsOfService:true,
      }
    },

    methods: {
      // 展示用户服务协议
      handleAgreement() {
        this.agreementDialogVisible = true
      },
      _checkRegist(email) {
        commonApi.checkEmail(email).then(res => {
        }).catch(res => {
          this.errors.email = this.$t('m.userCenter.emailExisted');
        })
      },
      // 注册
      handleRegist() {
        this.$refs.form.validate((valid) => {
          if (!valid) {
            return false;
          }

          const {geetest_challenge, geetest_validate, geetest_seccode} = this.captchaSuccess;

          this.loading = true;
          this.ruleForm.mobile= this.ruleForm.email
          loginRegist.register(
            this.ruleForm,
            geetest_challenge, geetest_validate, geetest_seccode
          ).then((res) => {

            this.$message({
              message: this.$t('m.userCenter.registerSuccess'),
              type: 'success'
            });
            this.loading = false;
            this.$router.push('/login');
          }).catch(() => {
            this.captchaObj.reset();
            this.loading = false;
          })
        });
      }
    },
    mounted() {
      this.ruleForm.invitationCode = this.$route.params.inviteCode;
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/login"
</style>

