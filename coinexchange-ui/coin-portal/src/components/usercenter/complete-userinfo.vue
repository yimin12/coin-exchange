<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.completeInfo')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <div class="ucenter-main no-sidebar">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.completeInfo')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-row>
              <el-col :span="10">
                <el-form :label-position="labelPosition" :model="ruleForm2"  :rules="rules2" ref="ruleForm2" label-width="140px" class="demo-ruleForm">

                  <el-form-item :label="$t('m.userCenter.username')" prop="username">
                    <el-input type="text" :placeholder="$t('m.patch.placeholder.username')" v-model="ruleForm2.username"
                              auto-complete="off"></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('m.userCenter.email')" prop="email" v-show="showEmail">
                    <el-input type="text" :placeholder="$t('m.patch.placeholder.email')" v-model="ruleForm2.email" auto-complete="off"
                    ></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('m.userCenter.setFundPassword')" prop="password">
                    <el-input type="password" :placeholder="$t('m.patch.placeholder.password')"
                              v-model="ruleForm2.password" auto-complete="off"
                              :maxlength="20"
                    ></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('m.userCenter.confirmFundPassword')" prop="repassword">
                    <el-input type="password":placeholder="$t('m.patch.placeholder.repassword')"
                              v-model="ruleForm2.repassword" auto-complete="off"
                              :maxlength="20"
                    ></el-input>
                  </el-form-item>

                  <!--<el-form-item label="短信验证码" prop="vcode">
                    <el-input placeholder="请输入验证码" v-model="ruleForm2.vcode" class="input-with-select">
                      <el-button slot="append" v-on:click="getVCode" v-html="ruleForm2.sendVCodeText" style="width: 100px; text-align: center; padding: 0"></el-button>
                    </el-input>
                  </el-form-item>-->

                  <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm2')">{{$t('m.submit')}}</el-button>
                  </el-form-item>
                  <el-form-item style="margin-top: -10px">
                    <el-button @click="resetForm('ruleForm2')" class="el-button--reset">{{$t('m.reset')}}</el-button>
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
  import mFooter from 'components/m-footer/m-footer'
  import { util } from 'common/js/util'
  import { getchilds, setUserBaseInfo} from 'api/usercenter'
  import { OK } from 'api/config'
  import { mapGetters } from 'vuex'

  Vue.use(ToggleButton)
  export default {
    data(){
      // var nameReg = /(^[a-zA-Z]{1,10})+([0-9]{1,10})+[a-zA-Z0-9]*$/;
      var mailReg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;
      var validateUserName = (rule, value, callback) => {
        let lang = this.lang;
        if (value === '') {
          let emptyError = {
            'zh-CN' : "请输入用户名",
            'zh-HK' : "請輸入用戶名",
            'en-US' : "Please enter the username"
          }
          callback(new Error(emptyError[lang]));
          // } else if(!(nameReg.test(value))){
          //   callback(new Error(this.$t('m.userCenter.usernameError')));
        } else {
          callback();
        }
      };
      var validateMail = (rule, value, callback) => {
        let lang = this.lang;
        if (value === '') {
          let emptyError = {
            'zh-CN' : "请输入邮箱",
            'zh-HK' : "請輸入郵箱",
            'en-US' : "Please enter the mailbox"
          }
          callback(new Error(emptyError[lang]));
        } else if(!(mailReg.test(value))){
          let typeError = {
            'zh-CN' : "邮箱格式不正确",
            'zh-HK' : "郵箱格式不正確",
            'en-US' : "Incorrect mailbox format"
          }
          callback(new Error(typeError[lang]));
        }else {
          callback();
        }
      };
      let passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzPassword')));
        }else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else {
          callback();
        }
      };

      var validateRePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzPasswordAgain')));
        } else if (value !== this.ruleForm2.password) {
          callback(new Error(this.$t('m.userCenter.confirmPwError')));
        } else {
          callback();
        }
      };
      /*var checkVcode =(rule, value, callback)=>{
        if (value === '') {
          callback(new Error('验证码不能为空'));
        } else {
          callback();
        }
      };
      */
      return {
        showEmail:true,
        labelPosition: 'left',
        ruleForm2: {
          username:'',
          email: '',
          password: '',
          repassword: ''
        },
        rules2: {
          username:[
            { validator: validateUserName, trigger: 'blur' }
          ],
          email: [
            { validator: validateMail, trigger: 'blur' }
          ],
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          repassword: [
            { validator: validateRePass, trigger: 'blur' }
          ]
        },
        lang:"zh-CN"
      };
    },
    created(){
      this.bus.$on("change:language", (lang)=>{
        this.lang = lang.toLowerCase();
      })
    },
    mounted(){
      this.ruleForm2.email = this.email
      if(this.email === "" || this.email === null){
        this.showEmail =  true
      }else{
        this.showEmail = false
        delete this.ruleForm2.email
        delete this.rules2.email
      }
    },
    components: {
      NavHeader,
      mFooter,
    },
    computed:{
      ...mapGetters(['token',"email"])
    },
    methods   : {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            setUserBaseInfo(
              this.ruleForm2.email,
              this.ruleForm2.password,
              this.ruleForm2.username,
              this.token
            ).then(()=>{

              this.$message({
                message:"信息添加成功",
                type:"success"
              })
              this.$router.push("/usercenter");

            }).catch((res)=>{

              this.$message({
                message:res.errmsg,
                type:"error"
              })

            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      getVCode() {
        if(this.ruleForm2.sendVCodeOn) return;

        //api

        this.ruleForm2.sendVCodeOn = true;
        this.ruleForm2.sendVCodeText = 60;
        let t = setInterval(() => {
          this.ruleForm2.sendVCodeText--;
          if(this.ruleForm2.sendVCodeText<1){
            clearInterval(t);
            this.ruleForm2.sendVCodeOn = false;
            this.ruleForm2.sendVCodeText = this.$t('m.userCenter.resend');
          }
        },1000)
      }
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>

<style scoped lang="stylus" rel="stylesheet/stylus">
  .el-button--primary{
    background #7392FF
    border-color #7392FF
  }
  .el-button--reset{
    width 100%
    background-color #F0F0F0
    border-color #F0F0F0
    color #AAABB1
  }
  .el-button--reset:hover{
    background-color #e6e6e6
    border-color #e6e6e6
  }
  .el-button--primary:focus,
  .el-button--primary:hover{
    background #7392FF
    border-color #7392FF
  }
</style>
