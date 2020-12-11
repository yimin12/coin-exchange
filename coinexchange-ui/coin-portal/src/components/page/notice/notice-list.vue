<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.footer.notice')">

    <NavHeader></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.footer.notice')}}</h3>
          </div>
          <div class="ucenter-body">
            <div class="notice-list-tb">
              <el-table
                :data="noticeList"
                style="width: 100%"

                row-class-name="el-row-style"
              >
                <!--@row-click="handleRowClick"-->
                <el-table-column
                  label="标题"
                  width="600"

                >
                  <template slot-scope="scope">
                    <span @click="handleJumpDetail(scope.row.id)">{{scope.row.title}}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="created"
                  label="时间"
                >
                </el-table-column>

              </el-table>
              <el-pagination
                class="pager-container"
                background
                layout="prev, pager, next"
                :current-page.sync="page"
                :page-size="size"
                :total="total"
                @current-change="handlePageChange"
              >
              </el-pagination>

            </div>
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
  import {homeApi} from 'api/home'
  import {OK} from 'api/config'
  import {util} from 'common/js/util'
  import sidebar from 'components/page/sidebar'

  export default {
    data() {
      return {
        noticeList: [],
        index: 0,
        noticeDetail: {},
        page:1,
        size:5,
        total:0

      }
    },
    mounted() {
      this.index = this.$route.params.index
      console.log("详情页面：" + this.index);
      this._getNoticeList()
    },

    methods: {
      handlePageChange(currentPage) {
        console.log("handlePageChange:", currentPage);
        this.page = currentPage
        this._getNoticeList()
      },
      handleJumpDetail(id) {
        console.log("当前id:",id);
        this.$router.push(`/s/notice/notice-detail/${id}`)

      },
//      handleRowClick(row){
//        console.log("行被点击",row);
//        this.$router.push("/notice/notice-detail/" + row.$index)
//      },
      async _getNoticeList() {
        let res = await homeApi.getNoticeList(this.page,this.size)

        this.total = res.data.total
        this.page = res.data.current
        this.noticeList = res.data.records
        console.log("结果：",res.data)
      }
    },
    components: {
      NavHeader,
      mFooter,
      sidebar
    },

  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  .el-row-style {
    cursor pointer
  }
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
