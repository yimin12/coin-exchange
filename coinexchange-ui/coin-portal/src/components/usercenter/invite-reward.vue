<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.invitationReward')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="7"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.invitationReward')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-row>
              <el-col :span="14">
                <el-form ref="form" label-position="left" label-width="140px">

                  <el-form-item class="copy-btn" :label="$t('m.userCenter.inviteCode')" prop="inviteCode">
                    <el-input type="text" v-model="inviteCode" auto-complete="off" :disabled="true">
                      <el-button slot="append" v-clipboard:copy="inviteCode"
                                 v-clipboard:success="onCopy"
                                 v-clipboard:error="onError"
                                 :plain="true"
                                 size="mini"
                      >{{$t('m.userCenter.copy')}}
                      </el-button>
                    </el-input>
                  </el-form-item>

                  <el-form-item class="copy-btn" :label="$t('m.userCenter.inviteLink')" prop="inviteLink">
                    <el-input type="text" v-model="inviteLink" auto-complete="off" :disabled="true">
                      <el-button slot="append" v-clipboard:copy="inviteLink"
                                 v-clipboard:success="onCopy"
                                 v-clipboard:error="onError"
                                 :plain="true"
                                 size="mini"
                      >{{$t('m.userCenter.copy')}}
                      </el-button>
                    </el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.qrcode')" prop="inviteLink">
                    <div ref="qrcodeImg"></div>
                    <a href="javascript:;" class="link suffix"
                       style="position:absolute;top:25px;left:120px"
                       @click="saveQC">{{$t('m.userCenter.saveQRCode')}}</a>
                  </el-form-item>

                </el-form>
              </el-col>
            </el-row>
            <el-row class="invite">
              <h3 class="stitle">{{$t('m.userCenter.inviteRecodes')}}</h3>
              <el-table
                :data="childTbDataPage"
                ref="turnoverTb"
                header-cell-class-name="table-header"
              >
                <el-table-column
                  prop="username"
                  :label="$t('m.userCenter.username')"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="mobile"
                  :label="$t('m.userCenter.phoneNumber')"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="date"
                  :label="$t('m.userCenter.certificationTime')"
                  align="center"
                >
                </el-table-column>
              </el-table>
              <el-pagination
                background
                layout=" prev, pager, next"
                :page-size="pageSize"
                :total="childTbData.length"
                @current-change="handlePageChange"
              >
              </el-pagination>
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
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
<style lang="stylus" scoped>
  .stitle {
    font-size 20px
    margin-bottom 20px
  }

  .invite {
    margin-top 50px
  }
</style>
<script>
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/usercenter/sidebar'
  import mFooter from 'components/m-footer/m-footer'

  import {util} from 'common/js/util'
  import {cookieUtil} from 'common/js/cookieUtil'

  import {getchilds, getLoginRecord, getAuth} from 'api/usercenter'
  import {OK} from 'api/config'
  import QRCode from 'qrcodejs2'
  import {mapMutations, mapGetters} from 'vuex'
  import VueClipboard from 'vue-clipboard2'
  Vue.use(VueClipboard)
  export default {
    components: {
      NavHeader,
      mFooter,
      sidebar
    },
    data: () => ({
      inviteCodeAddr: `${process.env.DOMAIN}/#/regist/`,
      isFirstAuth: false,
      childTbDataPage: [],
      childTbData: [],
      loginRecordTbData: [],
      childrenTotal: 0,
      pageSize: 10,
    }),
    created() {

    },
    computed: {
      ...mapGetters([
        'isAuth',
        'mobile',
        'username',
        'trueName',
        'inviteCode',
        'payPassSetting',
        'idCard',
        'token',
      ]),
      inviteLink() {
        return this.inviteCodeAddr + this.inviteCode
      }
    },
    methods: {
      ...mapMutations({
        setIsAuth: 'SET_ISAUTH',
      }),
      saveQC() {
        let qc = this.$refs.qrcodeImg
        let img = qc.getElementsByTagName('img')[0]
        let a = document.createElement("a");
        a.href = img.src;
        a.download = "qrcode.png";
        a.click();
      },
      onCopy: function (e) {
        this.$message({
          message: this.$t('m.copySuccess'),
          type: 'success'
        });
      },
      onError: function (e) {
        this.$message(this.$t('m.copyError'));
      },
      _getChilds() {
        getchilds(this.token).then(res => {
          let result = res.data
          console.log("下线接口post：", result)
          for (let i = 0; i < result.length; i++) {
            let item = result[i];
            let newItem = {};
            newItem.username = (item.username) ? item.username : item.mobile
            newItem.mobile = item.mobile
            newItem.date = item.created
            this.childTbData.push(newItem)
          }
          this.childrenTotal = result.length;
          this.handlePageChange(1);
          console.log("下线接口length:", this.childTbData);
        }).catch(res => {

        });
      },
      handlePageChange(page) {
        let start = this.pageSize * (page - 1);
        let end = start + this.pageSize;
        this.childTbDataPage = this.childTbData.slice(start, end);
      },
      handleCurrentChange: function (val) {
        this.currentPage = val;
      },
    },
    mounted() {
      let ref = this.$refs.qrcodeImg
      let qrcode = new QRCode(ref, {
        text: this.inviteLink,
        width: 90,
        height: 90,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
      });
      this._getChilds()
    }
  }
</script>
