<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.modifyPassword')">
    <NavHeader activeTab="5"></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="3"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.modifyPassword')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-row>
              <el-col :span="13">
                <el-form :label-position="labelPosition" :model="ruleForm" :rules="rules" ref="ruleForm"
                         label-width="140px">
                  <el-form-item :label="$t('m.userCenter.oldPassword')" prop="password">
                    <el-input type="password" :maxlength="20" :placeholder="$t('m.userCenter.oldPassword')"
                              v-model="ruleForm.password" auto-complete="off">
                      <!-- <router-link slot="suffix" to="/forget-password" class="link suffix">忘记密码?</router-link> -->
                    </el-input>
                  </el-form-item>
                  <el-form-item :label="$t('m.userCenter.newPassword')" prop="newpassword">
                    <el-input type="password" :maxlength="20" :placeholder="$t('m.userCenter.newPassword')"
                              v-model="ruleForm.newpassword" auto-complete="off"></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('m.userCenter.confirmLoginPassword')" prop="repassword">
                    <el-input type="password" :maxlength="20" :placeholder="$t('m.userCenter.confirmLoginPassword')"
                              v-model="ruleForm.repassword" auto-complete="off"></el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.validationCode')" prop="validateCode">
                    <el-col :span="14">
                      <el-input :placeholder="$t('m.userCenter.pleasePhoneV')"
                                v-model="ruleForm.validateCode"
                                :maxlength="6">
                      </el-input>
                    </el-col>
                    <el-col :span="1">&nbsp;</el-col>
                    <el-col :span="9">
                      <validateCode :mobile="ruleForm.mobile" type="CHANGE_LOGIN_PWD_VERIFY"
                                    :countryCode="ruleForm.countryCode"></validateCode>
                    </el-col>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">{{$t('m.submit')}}</el-button>
                  </el-form-item>
                  <el-form-item style="margin-top: -10px">
                    <el-button @click="resetForm('ruleForm')" class="el-button--reset">{{$t('m.reset')}}</el-button>
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
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/usercenter/sidebar'
  import mFooter from 'components/m-footer/m-footer'
  import {util} from 'common/js/util'
  import {updateUserPassword} from 'api/usercenter'
  import {commonApi} from "../../api/common";
  import {OK} from 'api/config'
  import {mapGetters} from 'vuex'
  import loginMixin from "../../common/mixin/loginMixin";
import { Alert } from 'element-ui'

  Vue.use(ToggleButton)
  export default {
    mixins: [loginMixin],
    data() {
      let passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzOldPassword')));
        } else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else {
          callback();
        }
      };

      let validatePassComplex = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzNewPassword')));
        } else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else {
          callback();
        }
      };
      var validateRePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzPasswordAgain')));
        } else if (value !== this.ruleForm.newpassword) {
          callback(new Error(this.$t('m.userCenter.confirmPwError')));
        } else {
          callback();
        }
      };

      return {
        labelPosition: 'left',
        ruleForm: {
          mobile: '',
          password: '',
          newpassword: '',
          repassword: '',
          validateCode: '',
          countryCode: this.countryCode
        },

        rules: {
          password: [
            {validator: validatePass, trigger: 'blur'}
          ],
          newpassword: [
            {validator: validatePassComplex, trigger: 'blur'}
          ],
          repassword: [
            {validator: validateRePass, trigger: 'blur'},
            {validator: validatePassComplex, trigger: 'blur'}
          ],
          validateCode: {validator: this.checkValidateCode, trigger: 'blur'}
        },
      };
    },
    mounted() {
      this.bus.$on("change:language", (lang) => {
        this.lang = lang.toLowerCase();
      })
      this.ruleForm.mobile = this.mobile
      console.log("mounted:", this.ruleForm.mobile)
    },

    components: {
      NavHeader,
      mFooter,
      sidebar
    },
    computed: {
      ...mapGetters(['mobile', 'token', 'countryCode'])
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.ruleForm.password === this.ruleForm.newpassword) {
              alert("xxxxx")
              this.$message({
                message: this.$t('m.userCenter.plzRestPassword'),
                type: "error"
              });
              return;
            }
            let data = {
              oldpassword: this.ruleForm.password,
              newpassword: this.ruleForm.newpassword,
              validateCode: this.ruleForm.validateCode,
            };
            updateUserPassword(data, this.token).then((res) => {
              this.$message({
                message: this.$t('m.userCenter.modifySuccess'),
                type: "success"
              })
              this.$router.push("/usercenter")
            }).catch((res) => {
              this.$message({
                message: res.errmsg,
                type: "error"
              })
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      // 发送手机短信验证码
    }
  }
</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  .el-button--reset {
    width 100%
    background-color #F0F0F0
    border-color #e2e2e2
    color #AAABB1
  }

  .el-button--reset:hover {
    background-color #e6e6e6
    border-color #e6e6e6
  }
</style>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
