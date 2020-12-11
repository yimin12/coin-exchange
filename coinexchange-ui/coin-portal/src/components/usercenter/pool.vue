<template>

  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.myPhoneNumber')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="8"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.poolCommunity')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-tabs>
              <el-tab-pane :label="$t('m.userCenter.createPool')">
                <el-row>
                  <el-col :span="14">
                    <el-form label-position="left" :model="ruleForm" :rules="rules" ref="forms"
                             label-width="140px">
                      <el-form-item :label="$t('m.userCenter.poolName')" prop="name">
                        <el-input v-model="ruleForm.name"></el-input>
                      </el-form-item>
                      <el-form-item :label="$t('m.userCenter.poolDescription')" prop="description">
                        <el-input v-model="ruleForm.description"></el-input>
                      </el-form-item>

                      <el-form-item>
                        <el-button type="primary" @click="submitForm('forms')">{{$t('m.yes')}}</el-button>
                      </el-form-item>

                    </el-form>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane :label="$t('m.userCenter.minePool')">
                <el-row>
                  <el-col :span="5">{{$t('m.userCenter.poolName')}}：</el-col>
                  <el-col :span="14"><span>{{minePool.name}}</span></el-col>
                </el-row>
                <el-row>
                  <el-col :span="5">{{$t('m.userCenter.poolDescription')}}：</el-col>
                  <el-col :span="14"><span>{{minePool.description}}</span></el-col>
                </el-row>
                <el-row>
                  <el-col :span="5">{{$t('m.userCenter.poolRemark')}}：</el-col>
                  <el-col :span="14"><span>{{minePool.remark}}</span>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="5">{{$t('m.userCenter.poolStatus')}}：</el-col>
                  <el-col :span="14"><span>{{$t(`m.userCenter.poolStatusMap.${minePool.status}`)}}</span>
                  </el-col>
                </el-row>
              </el-tab-pane>
            </el-tabs>
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
  import {createPool, minePool} from 'api/usercenter'
  import {mapGetters} from 'vuex'

  export default {
    data() {
      let checkName = (rule, value, callback) => {
        value = value.trim();
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzPoolName')));
        } else {
          callback()
        }
      };
      return {
        ruleForm: {
          name: "",
          description: ""
        },
        minePool: {},

        // errors: {
        //   mobile: '',
        //   validateCode: '',
        // },
        rules: {
          name: [
            {validator: checkName, trigger: 'blur'}
          ]
        },
      }
    },
    computed: {
      ...mapGetters([
        "token",
      ]),

    },
    components: {
      NavHeader,
      sidebar,
      mFooter,
    },
    mounted() {
      this._getPool()
    },
    methods: {
      async _getPool() {
        let res = await minePool();
        this.minePool = res.data
      },
      submitForm(formName) {
        let that = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            that._createPool()
          }
        });
      },
      async _createPool() {
        let res = await createPool(this.ruleForm)
        if (res.errcode === 0) {
          this.$message({
            type: "success",
            message: this.$t("m.userCenter.createPoolSuccess")
          })
        }
      }
    },

  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
