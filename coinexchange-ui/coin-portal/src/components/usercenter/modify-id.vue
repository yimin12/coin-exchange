<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.idAuth')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="1"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.idAuth')}}</h3>
          </div>
          <div class="ucenter-body">
            <!--未认证-->
            <el-row v-if="isAuth===0">
              <el-col :span="24">
                <div class="noteinfo">*{{$t('m.userCenter.certNotices')}}</div>
              </el-col>

              <el-col :span="12">
                <el-form :label-position="labelPosition" :model="ruleForm" :rules="rules" ref="ruleForm"
                         label-width="140px">
                  <el-form-item :label="$t('m.userCenter.fullName')" prop="name">
                    <el-input v-model="ruleForm.name"
                              :placeholder="$t('m.patch.placeholder.realName')"></el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.typeOfCertificate')" prop="voucher">
                    <el-select v-model="ruleForm.voucher" :placeholder="$t('m.userCenter.typeOfCertificate')">
                      <el-option :label="$t('m.userCenter.idCardType.1')" :value="1"></el-option>
                      <el-option :label="$t('m.userCenter.idCardType.2')" :value="2"></el-option>
                      <el-option :label="$t('m.userCenter.idCardType.3')" :value="3"></el-option>
                      <el-option :label="$t('m.userCenter.idCardType.4')" :value="4"></el-option>
                      <el-option :label="$t('m.userCenter.idCardType.5')" :value="5"></el-option>
                      <el-option :label="$t('m.userCenter.idCardType.9')" :value="9"></el-option>
                    </el-select>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.idNumber')" prop="voucherCode">
                    <el-input type="password" v-model="ruleForm.voucherCode" 
                              :placeholder="$t('m.patch.placeholder.voucherCode')"></el-input>
                  </el-form-item>

                  <el-form-item id="captcha" class="captcha" ref="captcha">
                    <p :class="['wait',showCaptcha?'show':'hide']">{{$t('m.userCenter.verificationLoading')}}</p>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">{{$t('m.submit')}}</el-button>
                  </el-form-item>

                </el-form>
              </el-col>
            </el-row>
            <!--初级认证-->

            <el-row v-else>
              <div class="noteinfo">*{{$t('m.userCenter.certificationInformation')}}</div>
              <table>
                <tr>
                  <th width="120">{{$t('m.userCenter.fullName')}}</th>
                  <td>{{realName}}</td>
                </tr>
                <tr>
                  <th>{{$t('m.userCenter.typeOfCertificate')}}</th>
                  <td>{{$t(`m.userCenter.idCardType.${idCardType}`)}}</td>
                </tr>
                <tr>
                  <th>{{$t('m.userCenter.idNumber')}}</th>
                  <td>{{idCard}}</td>
                </tr>
                <tr>
                  <th>{{$t('m.userCenter.certificationTime')}}</th>
                  <td>{{authTime}}</td>
                </tr>
              </table>
              <el-col :span="10">
                <!--高级认证 0审核中-->
                <el-button v-if="isAuth ===1&&seniorAuthStatus===3 " type="primary" @click="handleSeniorAuth">
                  {{$t('m.userCenter.advancedCertification.title')}}
                </el-button>
                <!--高级认证 1通过-->
                <!--高级认证 2拒绝-->
                <!--高级认证 3未填写-->
                <el-button v-if="isAuth ===1&&seniorAuthStatus===2 " type="danger" @click="handleSeniorAuth">
                  {{$t('m.userCenter.advancedCertification.auditNotPassed')}}
                </el-button>
                <el-tag v-if="isAuth ===1&&seniorAuthStatus===2 " class="senior-auth-desc">
                  {{$t('m.userCenter.advancedCertification.seniorAuthRefuse')}}:{{seniorAuthDesc}}
                </el-tag>
                <span
                  v-if="isAuth ===1&&seniorAuthStatus ===1">{{$t('m.userCenter.advancedCertification.passed')}}</span>
                <span
                  v-if="isAuth ===1&&seniorAuthStatus ===0">{{$t('m.userCenter.advancedCertification.underReview')}}</span>
                <span v-if="isAuth ===2">{{$t('m.userCenter.advancedCertification.passed')}}</span>

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
  import {getchilds, setAuth} from 'api/usercenter'
  import {OK} from 'api/config'
  import {stampToDate} from 'common/js/date'
  import {mapGetters, mapMutations} from 'vuex'
  import loginMixin from "../../common/mixin/loginMixin";
import { isAuth } from '../../store/getters'


  Vue.use(ToggleButton)
  let Timer = 0;

  export default {
    mixins: [loginMixin],

    data() {

      let checkName = (rules, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzRealName')));
        } else if (value.length < 2 || value.length > 20) {
          callback(new Error(this.$t('m.userCenter.realNameError')));
        } else {
          callback();
        }
      }
      let checkVoucher = (rules, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzCardType')))
        } else {
          callback();
        }
      }
      let checkVoucherCode = (rules, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzCardId')))
        } else {
          callback();
        }
      };
      return {
        labelPosition: 'left',
        ruleForm: {
          name: '',
          voucher: '',
          voucherCode: ''
        },
        rules: {
          name: [
            {validator: checkName, trigger: 'blur'}
          ],
          voucher: [
            {validator: checkVoucher, trigger: 'change'}
          ],
          voucherCode: [
            {validator: checkVoucherCode, trigger: 'blur'}
          ]
        },
      }
    },
    components: {
      NavHeader,
      sidebar,
      mFooter,
    },
    computed: {
      ...mapGetters([
        'isAuth',
        'idCard',
        'trueName',
        'idCardType',
        'realName',
        'authTime',
        'token',
        "seniorAuthStatus",
        "seniorAuthDesc",
        "lang",
      ]),
      // idCardTypeName(){
      //   return this.idCardTypeMap[this.idCardType];
      // }
    },
    mounted() {
      
    },
    methods: {
      ...mapMutations({
        setIsAuth: 'SET_ISAUTH',
        setRealName: 'SET_REALNAME',
        setIdCard: 'SET_IDCARD',
        setIdCardType: 'SET_IDCARDTYPE',
        setAuthTime: 'SET_AUTHTIME'
      }),
      handleSeniorAuth() {
        this.$router.push("modify-id-senior")
      },
      trim: function (str) {
        var val = str + '';
        return val.replace(/(^\s*)|(\s*$)/g, "");
      },
      submitForm(formName,checkStatus) {
        let root = this;
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            root.captchaObj.reset();
            console.log('error submit!!');
            return false;
          }
          // 极验返回结果有延迟，这里也要延迟
          if (Timer) clearTimeout(Timer);
          const {geetest_challenge, geetest_validate, geetest_seccode} = root.captchaSuccess;
          if (!geetest_challenge || !geetest_validate || !geetest_seccode) {
            if (checkStatus === true) {
              root.$message({
                message: this.$t('m.userCenter.geeTestButtonText'),
                type: 'error'
              });
              return;
            } else {
              Timer = setTimeout(() => {
                this.submitForm("ruleForm",true)
              }, 500);
              return;
            }
          }
          this.loading = true;

          let data = {
            idCard: this.ruleForm.voucherCode.trim(),
            idCardType: this.ruleForm.voucher,
            realName: this.ruleForm.name.trim(),
            uuid: root.uuidCode,
            geetest_challenge,
            geetest_validate,
            geetest_seccode
          }
          setAuth(data, this.token).then((res) => {
            this.setIsAuth(1);
            this.setRealName(data.realName);
            this.setIdCard(data.idCard.replace(data.idCard.substr(6, 6), "******"));
            this.setIdCardType(data.idCardType);
            this.setAuthTime(this.nowTime());
            if (this.$route.query.referrer != undefined) {
              this.$router.push({path: this.$route.query.referrer});
            } else {
              this.open2();
            }
          }).catch((res) => {
            this.$message.error(res.errmsg)
            this.captchaObj.reset();
            this.loading = false;
          })

        });
      },
      open2() {
        this.$confirm(this.$t('m.userCenter.realNameSuccess'), this.$t('m.userCenter.certificationTip'), {
          confirmButtonText: this.$t('m.userCenter.certificationNow'),
          cancelButtonText: this.$t('m.userCenter.rejectCertificate'),
          type: 'warning'
        }).then(() => {
          this.$router.push({path: '/usercenter/modify-id-senior'});
        }).catch(() => {

        });
      },
      nowTime() {
        let date = new Date();
        let months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
        let year = date.getFullYear();
        let month = months[date.getMonth()];
        let day = (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
        return `${year}-${month}-${day} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
      }
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
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
    width: 170px;
    margin-left: 140px;
    padding-bottom 12px;
  }
</style>
<style scoped>
  table th {
    color: #AAABB1;
    text-align: left;
  }

  table th,
  table td {
    padding: 8px 2px 30px 2px
  }

  .seniorAuth {
    width: 100px;
  }

  .senior-auth-desc {
    margin-top: 10px;
  }
</style>
