<template>

  <div class="ucenter-wrap">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content forget-password">
        <div class="ucenter-row">
            <sidebar :activeId="4"></sidebar>
            <div class="ucenter-main">
                <div class="ucenter-header">
                    <h3 class="title">重置交易密码</h3>
                </div>
                <div class="ucenter-body">
                    <el-row>
                      <el-col :span="11">

                        <el-form ref="AccountFrom" :model="account" :rules="rules" label-position="left" label-width="140px">
                          <el-form-item label="新密码" prop="u_password" class="space">
                            <el-input type="password" v-model="account.u_password" auto-complete="off"
                                      placeholder="设置交易密码"></el-input>
                          </el-form-item>
                          <el-form-item label="确认密码" prop="u_repassword" class="space">
                            <el-input type="password" v-model="account.u_repassword" auto-complete="off"
                                      placeholder="再次输入密码"></el-input>
                          </el-form-item>
                          <el-form-item style="width:100%; margin-bottom: 5px">
                            <el-button type="primary" @click="handleResetPwd" :loading="loading">完成
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
  import md5 from 'js-md5'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import sidebar from 'components/usercenter/sidebar'
  import { loginRegist } from 'api/loginRegist'
  import { OK } from 'api/config'
  import { cookieUtil } from 'common/js/cookieUtil'
  import  validateCode  from 'base/validate-code/validate-code'
  import { commonApi } from 'api/common'

  export default {

    data() {

      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.account.u_password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        validateImg: '',
        loading   : false,
        id:'',
        account    : {
          id:'',
          u_password  : '',
          u_repassword: '',
        },
        //表单验证规则
        rules      : {

          u_password  : [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min:6,message:'请输入6位以上密码',trigger: 'blur'},
          ],
          u_repassword: [
            {required: true, message: '请再输入密码', trigger: 'blur'},
            {min:6,message:'请输入6位以上密码',trigger: 'blur'},
            {validator: validatePass, trigger: 'blur'}
          ],
        }
      }
    },
    mounted() {
      this.id = this.$route.params.id

    },
    components: {
      NavHeader,
      mFooter,
      validateCode,
      sidebar
    },
    created(){
    },
    methods   : {
      handleResetPwd() {
        this.$refs.AccountFrom.validate((valid) => {
          if (valid) {
            this.loading = true;
            var params    = {
              id:this.id,
              newpassword  : md5(this.account.u_password),
              repassword: md5(this.account.u_repassword),
            };
            commonApi.registPayPassword(params).then(res => {
              if (res.statusCode === OK) {
                this.$router.push("/usercenter")
                this.$message({
                  message: "重置交易密码成功！",
                  type   : 'success'
                });

              } else {
                this.$message({
                  message: res.errmsg,
                  type   : 'error'
                });
              }
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
