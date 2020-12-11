<template>
  <div class="account-entry" v-wechat-title="$t('m.navheader.signup')">
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
            <router-link to="/emailregist">{{$t('m.loginRegist.emailreg')}}</router-link>
          </div>
          <h3 class="title">{{$t('m.navheader.signup')}}</h3>

          <el-form-item>
            <el-select v-model="ruleForm.countryCode"
                       filterable
                       :placeholder="$t('m.pleaseChoose')"
                       style="width: 100%">
              <el-option v-for="(item, key) in countryCodeMap.options"
                         :label="item.name"
                         :value="item.code"
                         :key="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-row :gutter="15">
            <el-col :span="18">
              <el-form-item prop="mobile"
                            :error="errors.mobile">
                <el-input type="text"
                          class="user ipt"
                          v-model="ruleForm.mobile"
                          auto-complete="user-register-phone"
                          :placeholder="$t('m.userCenter.plzPhoneNumber')"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-input type="text"
                        class="noindent"
                        v-model="ruleForm.invitationCode"
                        :disabled="this.$route.params.inviteCode"
                        auto-complete="off"
                        :placeholder="$t('m.userCenter.pleaseInviteCode')">
              </el-input>
            </el-col>
          </el-row>
          <el-form-item prop="password" :error="errors.password">
            <el-input type="password"
                      class="pass ipt"
                      v-model="ruleForm.password"
                      auto-complete="user-register-password"
                      :maxlength="20"
                      :placeholder="$t('m.userCenter.setLoginPassword')"></el-input>
          </el-form-item>

          <el-form-item prop="confirmPassword" :error="errors.confirmPassword">
            <el-input type="password"
                      class="pass ipt"
                      v-model="ruleForm.confirmPassword"
                      auto-complete="user-confirm-password"
                      :maxlength="20"
                      :placeholder="$t('m.userCenter.confirmLoginPassword')"></el-input>
          </el-form-item>

          <el-form-item id="captcha" class="captcha" ref="captcha">
            <p :class="['wait',showCaptcha?'show':'hide']">{{$t('m.userCenter.verificationLoading')}}</p>
          </el-form-item>
          <!--<el-row :gutter="15">-->

            <!--<el-col :span="18">-->
              <!--<el-form-item prop="validateCode" :error="errors.validateCode">-->
                <!--<el-input :placeholder="$t('m.loginRegist.plzInputCode')"-->
                          <!--v-model="ruleForm.validateCode"-->
                          <!--:maxlength="6"-->
                          <!--class="code ipt"-->
                <!--&gt;-->
                <!--</el-input>-->
              <!--</el-form-item>-->
            <!--</el-col>-->

            <!--<el-col :span="6">-->
              <!--<el-form-item>-->
                <!--<validateCode :mobile="ruleForm.mobile" type="REGISTER_VERIFY" :check="true"-->
                              <!--:countryCode="ruleForm.countryCode"></validateCode>-->
              <!--</el-form-item>-->
            <!--</el-col>-->

          <!--</el-row>-->

          <!--<el-form-item prop="invitationCode">-->
          <!--<el-input type="text"-->
          <!--v-model="ruleForm.invitationCode"-->
          <!--:disabled="this.$route.params.inviteCode"-->
          <!--auto-complete="off"-->
          <!--:placeholder="$t('m.userCenter.pleaseInviteCode')">-->
          <!--</el-input>-->
          <!--</el-form-item>-->

          <div class="account-help">

              <div class="left-btn">
                <input type="checkbox" v-model="termsOfService">
                <a href="javascript:;" @click="handleAgreement" class="a">{{$t('m.loginRegist.reading')}}<span>PCN{{$t('m.loginRegist.terms')}}</span></a>
              </div>

              <el-button class="button-primary" @click="handleRegist" :loading="loading">
                {{$t('m.userCenter.agreeToTheTerms')}}
              </el-button>
              <div class="right-btn">
                {{$t('m.userCenter.alreadyMember')}}
                <router-link to="/login" class="a">{{$t('m.userCenter.goToLogin')}}</router-link>
              </div>

          </div>

        </el-form>
      </div>
    </div>

    <el-dialog
      :title="$t('m.userCenter.legalNotices')"
      :visible.sync="agreementDialogVisible"
      width="45%"
    >
      <!--<div v-for="index in 5">-->
        <!--<p>{{$t(`m.userCenter.agreementDetail.${index}.title`)}}</p>-->
        <!--{{$t(`m.userCenter.agreementDetail.${index}.content`)}}-->
      <!--</div>-->
      <div v-html="$t('m.serviceAgreement')"></div>
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
  import {cookieUtil} from 'common/js/cookieUtil'
  // import validateCode from 'base/validate-code/validate-code'
  import {commonApi} from 'api/common'

  import loginMixin from "../../common/mixin/loginMixin";

  export default {
    mixins: [loginMixin],
    data() {
      let checkInvitationCode = (rule, value, callback) => {
        if (/[^0-9a-zA-Z]/.test(value)) {
          callback(new Error(this.$t('m.userCenter.inviteCodeError')));
        } else {
          callback()
        }
      }

      return {
        rules: {
          // 校验手机号
          mobile: [
            {validator: this.checkMobile, trigger: 'blur'}
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
          validateCode: [
            {validator: this.checkValidateCode, trigger: 'blur'}
          ],
          invitationCode: [
            {validator: checkInvitationCode, trigger: 'blur'}
          ]
        },
        ruleForm: {
          countryCode: '+86',
          mobile: '',
          password: '',
          confirmPassword: '',
          validateCode: '123456',
          invitationCode: '',
          agree: false
        },
        errors: {
          mobile: '',
          password: '',
          confirmPassword: '',
          validateCode: '',
        },

        agreementDialogVisible: false,
        showCaptcha: true,
        captchaObj: {},
        captchaSuccess: {},
        termsOfService: true
      }
    },
    components: {
      NavHeader,
      mFooter,
    },

    mounted() {
      this.ruleForm.invitationCode = this.$route.params.inviteCode;
    },
    methods: {
      _checkRegist(mobile, countryCode) {
        commonApi.checkMobile(mobile, countryCode).then(res => {
        }).catch(res => {
          this.errors.mobile = this.$t('m.userCenter.phoneNumberExisted');
        })
      },
      // 展示用户服务协议
      handleAgreement() {
        this.agreementDialogVisible = true
      },
      // 注册
      handleRegist() {
        this.$refs.form.validate((valid) => {
          if (!valid) {
            return false;
          }

          const {geetest_challenge, geetest_validate, geetest_seccode} = this.captchaSuccess;
          const uuid = this.uuidCode ;
          alert(uuid)
          if (!geetest_challenge || !geetest_validate || !geetest_seccode) {
            this.$message({
              message: this.$t('m.userCenter.geeTestButtonText'),
              type: 'error'
            });
            return;
          }
          this.loading = true;

          loginRegist.register(
            this.ruleForm,
            geetest_challenge, uuid , geetest_validate, geetest_seccode, 0
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
  .topnav {
    position fixed
    top 0
    left 0
    right 0
  }

  .agreement {
    cursor pointer
    color $color-global-blue
  }

  .mb10 {
    margin-bottom 10px
  }

  .account-entry {

    .regist {

      .el-button--primary.is-disabled,
      .el-button--primary.is-disabled:active,
      .el-button--primary.is-disabled:focus,
      .el-button--primary.is-disabled:hover {
        background #97aeff
        border-color #97aeff
      }
      .validate-code {
        height 38px
        width 100%
        border-radius 4px
        cursor pointer
      }
    }
  }
</style>
