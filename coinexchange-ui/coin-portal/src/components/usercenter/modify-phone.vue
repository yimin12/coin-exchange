<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.myPhoneNumber')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="2"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.myPhoneNumber')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-row>
              <el-col :span="14">
                <el-form :label-position="labelPosition" :model="ruleForm" :rules="rules" ref="forms"
                         label-width="140px">
                  <!--老手机号-->
                  <el-form-item :label="$t('m.userCenter.oldPhone')" prop="oldphone"
                                v-show="oldPhone!='undefined'">
                    <el-input :disabled="true" v-model="formatMobile"></el-input>
                  </el-form-item>
                  <!--老验证码-->
                  <el-form-item :label="$t('m.userCenter.SMSVerificationCode')" :error="errors.oldValidateCode"
                                prop="oldValidateCode">
                    <el-col :span="14">
                      <el-input :placeholder="$t('m.userCenter.pleasePhoneV')"
                                v-model="ruleForm.oldValidateCode"
                                :maxlength="6">
                      </el-input>
                    </el-col>
                    <el-col :span="1">&nbsp;</el-col>
                    <el-col :span="9">
                      <validateCode :mobile="oldPhone" type="VERIFY_OLD_PHONE" :check="false" :countryCode="ruleForm.countryCode"></validateCode>
                    </el-col>
                  </el-form-item>

                  <!--新手机号-->
                  <el-form-item :label="$t('m.userCenter.newPhone')" :error="errors.newMobilePhone" prop="newMobilePhone">
                    <div
                      style="width: 70px; height: 32px; background: #fff; position: absolute; top: 4px; left: 3px; z-index: 100; font-size: 15px; line-height: 34px; text-align: center; color: #999">
                      {{ruleForm.countryCode}}
                    </div>
                    <el-input :placeholder="$t('m.userCenter.plzPhoneNumber')" v-model="ruleForm.newMobilePhone">

                      <el-select v-model="ruleForm.countryCode" slot="prepend" :placeholder="$t('m.pleaseChoose')"
                                 style="width: 100px">
                        <el-option
                          v-for="(item, key) in countryCodeMap.options" :key="key" :label="item.name" :value="item.code">
                        </el-option>
                      </el-select>
                    </el-input>
                  </el-form-item>
                  <!--新验证码-->
                  <el-form-item :label="$t('m.userCenter.SMSVerificationCode')" :error="errors.validateCode"
                                prop="validateCode">
                    <el-col :span="14">
                      <el-input :placeholder="$t('m.userCenter.pleasePhoneV')"
                                v-model="ruleForm.validateCode"
                                :maxlength="6">
                      </el-input>
                    </el-col>
                    <el-col :span="1">&nbsp;</el-col>
                    <el-col :span="9">
                      <validateCode :mobile="ruleForm.newMobilePhone" type="CHANGE_PHONE_VERIFY" :check="true" :countryCode="ruleForm.countryCode"></validateCode>
                    </el-col>
                  </el-form-item>

                  <el-form-item>
                    <el-button type="primary" @click="submitForm('forms')">{{$t('m.modify')}}</el-button>
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
  import sidebar from 'components/usercenter/sidebar'
  import mFooter from 'components/m-footer/m-footer'
  import {util} from 'common/js/util'
  import {getchilds, getLoginRecord, setAuth, getAuth,
    serverBindMobile,verifyOldPhone} from 'api/usercenter'
  // import {commonApi} from 'api/common'
  import {mapMutations, mapGetters} from 'vuex'
  import loginMixin from "../../common/mixin/loginMixin";

  export default {
    mixins: [loginMixin],
    data() {
      return {
        labelPosition: 'left',
        ruleForm: {
          countryCode: '+86',
          newMobilePhone:'',
          oldValidateCode: '',
          validateCode: ''
        },
        errors: {
          // oldMobile: '',
          newMobilePhone:'',
          oldValidateCode: '',
          validateCode: '',
        },
        rules: {
          // oldMobile: [
          //   {validator: this.checkMobile, trigger: 'blur'}
          // ],
          newMobilePhone: [
            {validator: this.checkMobile, trigger: 'blur'}
          ],
          oldValidateCode: [
            {validator: this.checkValidateCode, trigger: 'blur'}
          ],
          validateCode: [
            {validator: this.checkValidateCode, trigger: 'blur'}
          ]
        },
      }
    },
    computed: {
      ...mapGetters({
        oldPhone: 'mobile',
        token: 'token',
      }),
      formatMobile() {
        return util.formatPhone(this.oldPhone);
        // return this.oldPhone
      },
    },
    components: {
      NavHeader,
      sidebar,
      mFooter,
    },
    watch: {
      countryCode(v) {
        this.ruleForm.countryCode = v;
      },
    },
    mounted(){
      // this.ruleForm.oldMobile = this.oldPhone
    },
    methods: {
      ...mapMutations([
          'SET_USERNAME',
          'SET_TOKEN',
          "SET_EXPIRE_TIME",
          'SET_MOBILE',
          'SET_REFRESH_TOKEN',
        ]
      ),
      trim: function (str) {
        var val = str + '';
        return val.replace(/(^\s*)|(\s*$)/g, "");
      },
      submitForm(formName) {
        let that = this
        this.$refs[formName].validate((valid) => {
          if (valid) {

            serverBindMobile(
              this.ruleForm.countryCode,
              this.ruleForm.newMobilePhone,
              this.ruleForm.validateCode,
              this.ruleForm.oldValidateCode
              ).then(res => {
              this.$message({
                message: this.$t('m.userCenter.modifyPhoneNumberSuccess'),
                type   : "success"
              })

              that.$router.push("/login")
              that.SET_EXPIRE_TIME('')
              that.SET_TOKEN('')
              that.SET_REFRESH_TOKEN('')
              that.SET_USERNAME('')
              that.SET_MOBILE('')
            })
          }
        });
      },
    },

  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
